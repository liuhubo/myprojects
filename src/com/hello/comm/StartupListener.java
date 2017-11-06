package com.hello.comm;

import java.io.IOException;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.alibaba.fastjson.JSONObject;
import com.hello.function.PropClient;
import com.hello.util.ThreadPools;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownListener;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP.Queue.BindOk;

public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private RabbitAdmin rabbitAdmin;
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		ApplicationContext applicationContext = event.getApplicationContext();
		// 防止重复执行
		if (applicationContext.getParent() == null) {
			// LogUtils.COMMON.debug("startup listener, begin declare exchange!");
			ensureExchange();
			ThreadPools.getInstance().executeListenMqMsg(() -> listenMqMsg());
		}
	}

	private void listenMqMsg() {
		try {
			RabbitTemplate _rabbitTemplate = new RabbitTemplate(rabbitTemplate.getConnectionFactory());
			_rabbitTemplate.execute(channel -> {
				String queue = "battery_unlock_state_queue";
				BindOk bindResult = channel.queueBind(queue, Consts.BATTERY_UNLOCK_EXCHANGE, "");
//				LogUtils.PUSH.debug("battery unlock bindResult:{},tpl:{},channel:{},queueName:{}", bindResult, _rabbitTemplate, channel, queue);

				DefaultConsumer servletConsumer = new DefaultConsumer(channel) {
					@Override
					public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
						String message = new String(body);
//						LogUtils.PUSH.debug(">>revice battery unlock msg,will call AsyncRequestProcessor completeAsyncRequest:{}", message);
						onUnlockMsgRceived(message);
					}
				};
//				LogUtils.COMMON.debug("servlet:declare consumer finish!");
				channel.basicConsume(queue, true, servletConsumer);
				addShutdownListener(channel, queue, true, servletConsumer);
				return true;
			});
		} catch (Exception ex) {
//			LogUtils.ERROR.error("127.0.0.1||0||{}||receive battery unlock mq listener error", ex.getClass().getName(), ex);
		}
	}

	protected void onUnlockMsgRceived(String message) {
		JSONObject json = JSONObject.parseObject(message);
		//TODO Business Logic
		System.out.println(message);
	}

	private void ensureExchange() {
		String tcpMessageExchange = PropClient.get("rabbitmq.tcp_new_message_exchange");
		try {
			Exchange tmExchange = new DirectExchange(tcpMessageExchange, true, false);
			rabbitAdmin.declareExchange(tmExchange);
			// LogUtils.COMMON.debug("declare exchange:{} ok!", tcpMessageExchange);
		} catch (Exception ex) {
			// LogUtils.ERROR.error("127.0.0.1||0||{}||declare exchange:{} error",
			// ex.getClass().getName(), tcpMessageExchange, ex);
		}

	}

	private void addShutdownListener(Channel channel, String queue, boolean autoAck, DefaultConsumer servletConsumer) {
		try {
			channel.addShutdownListener(new ShutdownListener() {
				@Override
				public void shutdownCompleted(ShutdownSignalException cause) {
					while (true) {
						try {
							channel.basicConsume(queue, autoAck, servletConsumer);
							addShutdownListener(channel, queue, autoAck, servletConsumer);
							break;
						} catch (Exception e) {
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
					}
				}
			});
		} catch (Exception e) {
		}
	}

}

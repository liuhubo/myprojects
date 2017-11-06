package com.hello.msg;

import java.io.IOException;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hello.comm.Consts;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownListener;
import com.rabbitmq.client.ShutdownSignalException;

public class MsgMain {
	static ApplicationContext context = null;
	public static void main(String[] args) throws InterruptedException {
		context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		AmqpTemplate amqpTemplate = context.getBean(AmqpTemplate.class);
		while(true) {
			amqpTemplate.convertAndSend(Consts.BATTERY_UNLOCK_EXCHANGE, "", "this is a test msg..");
			Thread.currentThread().sleep(3000);
		}
//		RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
//		AmqpAdmin amqpAmin = context.getBean(AmqpAdmin.class);
//		RabbitAdmin rabbitAdmin = context.getBean(RabbitAdmin.class);
		/*Exchange exchange_1=new FanoutExchange("exchange_1",true,false);
		rabbitAdmin.declareExchange(exchange_1);
		Exchange exchange_2=new FanoutExchange("exchange_2",true,false);
		rabbitAdmin.declareExchange(exchange_2);*?
		/*queue2 listener*/
		/*RabbitTemplate rabbitTemplate_2 = new RabbitTemplate(rabbitTemplate.getConnectionFactory());
		rabbitTemplate_2.execute(channel->{
		channel.queueBind("queue_2", "exchange_2", "");
		DefaultConsumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException{
				String msg = new String(body);
				System.out.println("Queue2 receive new msg:"+msg);
			}
		};
			channel.basicConsume("queue_2", true, consumer);
			addShutdownListener(channel,"queue_2",true,consumer);
			return true;
		});*/
		/*queue1 listener*/
		/*RabbitTemplate rabbitTemplate_1 = new RabbitTemplate(rabbitTemplate.getConnectionFactory());
		rabbitTemplate_1.execute(channel->{
		channel.queueBind("queue_1", "exchange_1", "");
		DefaultConsumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException{
				String msg = new String(body);
				System.out.println("Queue1 receive new msg:"+msg);
			}
		};
			channel.basicConsume("queue_1", true, consumer);
			addShutdownListener(channel,"queue_1",true,consumer);
			return true;
		});*/
	}

	private static void addShutdownListener(Channel channel, String queue, boolean b, DefaultConsumer consumer) {
		channel.addShutdownListener(new ShutdownListener() {
			@Override
			public void shutdownCompleted(ShutdownSignalException cause) {
				System.out.println("shutdown completed!");
				while (true) {
					try {
						channel.basicConsume(queue, true, consumer);
						addShutdownListener(channel, queue, b, consumer);
						break;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
}

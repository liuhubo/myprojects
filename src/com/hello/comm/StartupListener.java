package com.hello.comm;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.hellobike.oss.logic.iface.BikeStatusService;
import com.hellobike.oss.logic.iface.entity.CommonObject;
import com.hellobike.oss.logic.iface.enums.OriginAlertType;
import com.carkey.base.model.Proto;
import com.easybike.base.proto.Protos;
import com.easybike.fault.enumerations.EnumRideStatus;
import com.easybike.order.ifaces.OrderCommonIface;
import com.hello.function.PropClient;
import com.hello.util.ThreadPools;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP.Queue.BindOk;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownListener;
import com.rabbitmq.client.ShutdownSignalException;

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
//            LogUtils.COMMON.debug("startup listener, begin declare exchange!");
            ensureExchange();
            ThreadPools.getInstance().executeListenMqMsg(() -> listenMqMsg());
        }
    }

    private Object listenMqMsg() {
		// TODO Auto-generated method stub
		return null;
	}

	private void ensureExchange() {
        String tcpMessageExchange = PropClient.get("rabbitmq.tcp_new_message_exchange");
        try {
            Exchange tmExchange = new DirectExchange(tcpMessageExchange, true, false);
            rabbitAdmin.declareExchange(tmExchange);
//            LogUtils.COMMON.debug("declare exchange:{} ok!", tcpMessageExchange);
        } catch (Exception ex) {
//            LogUtils.ERROR.error("127.0.0.1||0||{}||declare exchange:{} error", ex.getClass().getName(), tcpMessageExchange, ex);
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

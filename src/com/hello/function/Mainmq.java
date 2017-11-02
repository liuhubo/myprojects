package com.hello.function;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Mainmq {

	public static void main(String[] args) {
		ApplicationContext con = new ClassPathXmlApplicationContext("applicationContext.xml");
		AmqpTemplate amqpTemplate = con.getBean(AmqpTemplate.class);
		RabbitAdmin rabbitAdmin = con.getBean(RabbitAdmin.class);
		Exchange fronEx = new FanoutExchange("battery_unlock_exchange", true, false);
		rabbitAdmin.declareExchange(fronEx);
		amqpTemplate.convertAndSend("bikeNo:3700001");
	}

}

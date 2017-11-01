package com.hello.msg;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Producer {
	private static ApplicationContext context =null;
	public static void main(String[] args) throws InterruptedException {
		context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		AmqpTemplate amqpTemplate = context.getBean(AmqpTemplate.class);
		RabbitAdmin rabbitAdmin = context.getBean(RabbitAdmin.class);
//		Exchange exchange = new FanoutExchange("exchange_2", true, false);
//		rabbitAdmin.declareExchange(exchange);
		Thread t1=new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					exe("exchange_1","{bikeNo:10001}");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		Thread t2=new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					exe("exchange_2","{unlockState:ok}");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		t1.start();
		t2.start();
	}

	public static void exe(String exchange,String json) throws InterruptedException{
		AmqpTemplate amqpTemplate = context.getBean(AmqpTemplate.class);
		while (true) {
			amqpTemplate.convertAndSend(exchange, "", json);
			System.out.println("Thread:"+Thread.currentThread().getId()+" send OK,sleep 1 sec");
			Thread.currentThread().sleep(1000L);
		}
	}
	
}

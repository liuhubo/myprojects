package com.hello.kfk;

import java.util.Properties;

import com.hello.function.PropClient;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
/**
 * kafka消息生产
 * @author liuhubo
 *
 */
public class KProducer {
	private Producer<String, String> producer;
	private ProducerConfig kConfig;
	private Properties properties;

	public KProducer() {
		properties = new Properties();
		String brokers = new StringBuffer(PropClient.get("k.host")).append(":").append(PropClient.get("k.port")).toString();
		properties.put("metadata.broker.list", brokers);
		properties.put("serializer.class", PropClient.get("k.serializer.class"));
		properties.put("request.required.acks", "0");
		properties.put("producer.type", PropClient.get("k.pro.type"));
//		properties.put("batch.num.messages", 200);
//		properties.put("send.buffer.bytes", PropClient.get("k.send.buff.size"));
		kConfig = new ProducerConfig(properties);
		this.producer = new Producer<>(kConfig);
		
	}
	
	public void createMes(String topic,Object msg) {
		long dat = System.currentTimeMillis();
		String key = new StringBuffer("Msg-Key-").append(dat).toString();
		/*构建msg主题*/
		KeyedMessage<String, String> msgBody = new KeyedMessage<String, String>(topic, key, msg.toString());
		/*创建发送*/
		producer.send(msgBody);
	}
	
	public static void main(String args[]) throws InterruptedException {
		KProducer p = new KProducer();
		String [] tpic = PropClient.get("k.topic").split(",");
//		for (String topic : tpic) {
			long dat = System.currentTimeMillis();
//			System.out.println("create msg for topic:" + topic + "|msg:" + dat);
			p.createMes(tpic[0], dat);
			Thread.currentThread().sleep(500);
//		}
	}
}

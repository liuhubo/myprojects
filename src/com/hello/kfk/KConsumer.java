package com.hello.kfk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.hello.function.PropClient;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class KConsumer {
private static ConsumerConnector consumer;
private Properties properties;
private String topic;
public KConsumer() {
	properties = new Properties();
	String zk = new StringBuffer(PropClient.get("z.host")).append(":").append(PropClient.get("z.port")).toString();
	properties.put("zookeeper.connect", zk);
	String group = "battery-state";
	properties.put("group.id", group);
	properties.put("zookeeper.session.timeout.ms", PropClient.get("z.session.timeout"));
	properties.put("zookeeper.sync.time.ms", PropClient.get("z.sync.timeout"));
	properties.put("auto.commit.interval.ms", PropClient.get("z.auto.commit.interval"));
	ConsumerConfig config = new ConsumerConfig(properties);
	consumer =Consumer.createJavaConsumerConnector(config);
	this.topic = PropClient.get("k.topic");
}

	public void onMessage() {
		Map<String, Integer> topicCntMap = new HashMap<>();
		String[] tpic = topic.split(",");
		topic = tpic[0];
		topicCntMap.put(tpic[0], 1);
//		topicCntMap.put(tpic[1], 1);
		readMsg(topicCntMap);

	}

	private void readMsg(Map<String, Integer> tpicCntMap) {
		String msg = null;
//		Set<String> topicSet = tpicCntMap.keySet();
//		for (String topic : topicSet) {
			Map<String, List<KafkaStream<byte[], byte[]>>> msgStream = consumer.createMessageStreams(tpicCntMap);
			KafkaStream<byte[], byte[]> kStream = msgStream.get(topic).get(0);
			ConsumerIterator<byte[], byte[]> it = kStream.iterator();
			while (it.hasNext()) {
				msg = new String(it.next().message());
				System.out.println("kfk received a msg:" + msg);
			}
//		}
	}
	
	public static void main(String [] args) {
		KConsumer consume = new KConsumer();
		System.out.println("msg consumer start up...");
		while(true) {
			try {
			consume.onMessage();
			}catch(Exception e) {
				if(consumer!=null) {
					consumer.shutdown();;
				}
				e.printStackTrace();
			}
		}
	}
}
package com.hello.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@Component
public class RedisTopicListener implements MessageListener {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    @Override
    public void onMessage(Message message, byte[] pattern) {
        byte[] body = message.getBody();
        String event = (String) redisTemplate.getValueSerializer().deserialize(body);
        String channel = new String(message.getChannel());
        if (!event.equals("expired")) {
            return;
        }

        try {
            String prefix = channel.split(":")[3];
            String value = channel.split(":")[4];
            switch (prefix) {
            case "bikeExpireNoData":
                ThreadPools.getInstance().executePushTask(new Runnable() {
                    @Override
                    public void run() {
                        bikeExpireNoData(value); 
                    }
                });
                break;
            default:
                break;
            }
        } catch (Exception ex) {
            try {
            } catch (Exception e) {
            }
        }
    }

    private void bikeExpireNoData(String bikeNo) {
       System.out.println(">>>redis receive bikeNo:"+bikeNo);
    }
}

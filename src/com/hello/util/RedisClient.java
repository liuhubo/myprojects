package com.hello.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

public interface RedisClient {

	String set(String key, String value);

	String set(String key, String value, long seconds);

    String get(String key);

    String getAndSet(String key, String newValue);

    String del(String key);

    String expire(String key, long seconds);

    String setMap(String key, Map<String, String> map);

    String setMap(String key, Map<String, String> map, long seconds);

    String setMapField(String key, String hashKey, Object value);

    String setMapField(String key, String hashKey, Object value, long seconds);

    String delMapField(String key, String hashKey);

    Map<String, String> getMap(String key);

    String getMapField(String key, String hashKey);

    boolean setNx(String key, String value);

    String addSet(String key, String value);

    long addSets(String key, List<String> list);

    Set<String> getSet(String key);

    boolean inSet(String key, String value);

    boolean remSet(String key, String value);

    long getIncr();

    boolean exists(String key);

    Set<String> keys(String pattern);

    long incr(String key);

    long incr(String key, long value);

    long incrAndSetTimeout(String key, long timeout);

    long incrAndSetTimeout(String key, long value, long timeout);

    long desc(String key);

    // 获取排序队列的长度
    long zCard(String key);

    // 放入队列
    boolean zAdd(String key, String member, Double score);

    // 从队列获取元素
    Set<TypedTuple<String>> zRange(String key, int start, int end);

    // 删除队列元素
    boolean zRem(String key, String member);

    // 获取在队列中的位置
    long zRank(String key, String memeber);

    // 获取list中指定位置的元素
    String lIndex(String key, int index);

    // 裁剪list长度
    String lTrim(String key, int start, int end);

    // 获取list的长度
    long lLength(String key);

    // 往list中插入元素
    String lPush(String key, String value);

    // 获取list中的元素
    List<String> lRange(String key, int start, int end);
    
    String rPubMsg(String channel,String msg);
}

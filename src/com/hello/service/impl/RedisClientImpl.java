package com.hello.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import com.hello.comm.Consts;
import com.hello.function.PropClient;
import com.hello.util.RedisClient;
import com.hello.util.RedisKey;
import com.hello.util.RedisTopicListener;
import com.hello.util.ThreadPools;
/**
 * redis默认db->0
 * 切换db  select 1
 * 默认0-15，16个db，16个实例
 * @author liuhubo
 *
 */


@Service("redisClient")
public class RedisClientImpl implements RedisClient {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisTopicListener topicListener;

    @PostConstruct
    public void init() {
        ThreadPools.getInstance().executeRedisSubscribeTask(() -> pSubscibe(redisTemplate, topicListener));
    }

    private void pSubscibe(final RedisTemplate<String, String> _redis, RedisTopicListener _topicListener) {
        _redis.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                int db = PropClient.getInt("redis.db");
                String pattern = "__keyspace@" + db + "__:" + Consts.REDIS_EXPIRE_EVENT_PREFIX + "*";
//                LogUtils.PUSH.debug("pSubscribe pattern is {}", pattern);
                connection.pSubscribe(_topicListener, pattern.getBytes());
//                String pattern = "__keyspace@" + db + "__:" + Consts.REDIS_EXPIRE_EVENT_PREFIX + "*";
                String p = "powerBikeBattery:*";
//                LogUtils.PUSH.debug("pSubscribe pattern is {}", pattern);
                System.out.println("--->subcribe topic pattern:"+p);
                connection.pSubscribe(_topicListener, p.getBytes());
                return true;
            }
        });
    }

    @Override
    public String set(String key, String value) {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        valueOps.set(key, value);
        return Consts.OK;
    }

    @Override
    public String set(String key, String value, long seconds) {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        valueOps.set(key, value, seconds, TimeUnit.SECONDS);
        return Consts.OK;
    }

    @Override
    public String get(String key) {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        return valueOps.get(key);
    }

    @Override
    public String getAndSet(String key, String newValue) {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        return valueOps.getAndSet(key, newValue);
    }

    @Override
    public String del(String key) {
        redisTemplate.delete(key);
        return Consts.OK;
    }

    @Override
    public String expire(String key, long seconds) {
        redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
        return Consts.OK;
    }

    @Override
    public String setMap(String key, Map<String, String> map) {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        hashOps.putAll(key, map);
        return Consts.OK;
    }

    @Override
    public String setMap(String key, Map<String, String> map, long seconds) {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        hashOps.putAll(key, map);
        redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
        return Consts.OK;
    }

    @Override
    public String setMapField(String key, String hashKey, Object value) {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        hashOps.put(key, hashKey, String.valueOf(value));
        return Consts.OK;
    }

    @Override
    public String setMapField(String key, String hashKey, Object value, long seconds) {
        boolean hasKey = redisTemplate.hasKey(key);
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        hashOps.put(key, hashKey, String.valueOf(value));
        if (!hasKey) {
            redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
        }
        return Consts.OK;
    }

    @Override
    public Map<String, String> getMap(String key) {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        return hashOps.entries(key);
    }

    @Override
    public String getMapField(String key, String hashKey) {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        return hashOps.get(key, hashKey);
    }

    @Override
    public String delMapField(String key, String hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
        return Consts.OK;
    }

    @Override
    public boolean setNx(final String key, final String value) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.setNX(key.getBytes(), value.getBytes());
            }
        });
    }

    @Override
    public String addSet(String key, String value) {
        redisTemplate.opsForSet().add(key, value);
        return Consts.OK;
    }

    @Override
    public long addSets(String key, List<String> list) {
        if (list == null || list.size() <= 0) {
            return 0L;
        }
        String[] array = new String[list.size()];
        list.toArray(array);
        return redisTemplate.opsForSet().add(key, array);
    }

    @Override
    public Set<String> getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    @Override
    public boolean inSet(String key, String value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    @Override
    public boolean remSet(String key, String value) {
        return redisTemplate.opsForSet().remove(key, value) == 1;
    }

    @Override
    public long getIncr() {
        return redisTemplate.opsForValue().increment(RedisKey.UID_INC, 1);
    }

    @Override
    public boolean exists(final String key) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.exists(key.getBytes());
            }
        });
    }

    @Override
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    @Override
    public long incr(String key) {
        return incr(key, 1);
    }

    @Override
    public long incr(String key, long value) {
        return redisTemplate.opsForValue().increment(key, value);
    }

    @Override
    public long incrAndSetTimeout(String key, long timeout) {
        return incrAndSetTimeout(key, 1L, timeout);
    }

    @Override
    public long incrAndSetTimeout(String key, long value, long timeout) {
        boolean hasKey = redisTemplate.hasKey(key);
        Long result = redisTemplate.opsForValue().increment(key, value);
        if (!hasKey) {
            redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        }
        return result;
    }

    @Override
    public long desc(String key) {
        return redisTemplate.opsForValue().increment(key, -1);
    }


    @Override
    public long zCard(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    @Override
    public boolean zAdd(String key, String member, Double score) {
        return redisTemplate.opsForZSet().add(key, member, score);
    }

    @Override
    public Set<TypedTuple<String>> zRange(String key, int start, int end) {
        return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    @Override
    public boolean zRem(String key, String member) {
        return redisTemplate.opsForZSet().remove(key, member) == 1;
    }

    @Override
    public long zRank(String key, String memeber) {
        Long rank = redisTemplate.opsForZSet().rank(key, memeber);
        return rank == null ? -1 : rank;
    }

    @Override
    public String lIndex(String key, int index) {
        return redisTemplate.opsForList().index(key, index);
    }

    @Override
    public String lTrim(String key, int start, int end) {
        redisTemplate.opsForList().trim(key, start, end);
        return Consts.OK;
    }

    @Override
    public long lLength(String key) {
        return redisTemplate.opsForList().size(key);
    }

    @Override
    public String lPush(String key, String value) {
        redisTemplate.opsForList().leftPush(key, value);
        return Consts.OK;
    }

    @Override
    public String rPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }
    
    @Override
    public List<String> lRange(String key, int start, int end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

	@Override
	public String rPubMsg(String channel,String msg) {
		redisTemplate.convertAndSend(channel, msg);
		return msg;
	}
}

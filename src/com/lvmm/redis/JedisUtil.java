package com.group.redis;

import java.util.List;

import com.group.utils.JedisConfig;
import com.group.utils.JedisConfig.Jedis_Config;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {
	private static JedisPool pool=null;
	private static Jedis jedis=null;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}

	static {
		init();
	}
	static void init() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(50);//给pool分配最大jedis实例，通过pool.getResouce()获取Jedis实例，-1表示不限制实例数
		config.setMaxIdle(256);//控制pool中空闲的jedis实例
		config.setMaxWaitMillis(5000L);//当borrow(引入)一个jedis实例最大等待时间，超出时间报JedisConnectionException
		config.setTestOnBorrow(true);//borrow(引入)redis实例时，是否提前validate,true表示得到的jedis实例是可用的|获取连接时出发ping
		config.setTestOnReturn(true);//返回释放连接时释放出发ping;
		config.setTestWhileIdle(true);//定时对线程池空闲现场进行检查
		config.setMinEvictableIdleTimeMillis(600000);//逐出连接的最小空闲时间 默认是10mins
		config.setTimeBetweenEvictionRunsMillis(300001);
		config.setNumTestsPerEvictionRun(-1);//每次逐出检查时，逐出的最大数目，如果为负数就是1/abs,默认3
		pool = new JedisPool(config, JedisConfig.getValue(Jedis_Config.IP
				.getValue()), Integer.parseInt(JedisConfig
				.getValue(Jedis_Config.PORT.getValue())),10000);//创建redis pool 超时时间为10s
		isJedisConn();
	}
	
	public static String get(String key){
		String value=null;
		try{
			Jedis jedis=pool.getResource();
		value=jedis.get(key);
		}catch(Exception e){
			pool.returnBrokenResource(jedis);//释放redis对象
		}finally{
			close(jedis);
		}
		return value; 	
	}
	
	public static byte[] get(byte[] key){
		byte [] value=null;
		try{
			Jedis jedis=pool.getResource();
		value=jedis.get(key);
		}catch(Exception e){
			pool.returnBrokenResource(jedis);
		}finally{
			close(jedis);//返还到连接池
		}
		return value;
	}
	
	public static void close(Jedis jedis){
		try{
		pool.returnResource(jedis);
		}catch(Exception e){
			if(jedis.isConnected()){
				jedis.quit();
				//jedis.close();
				jedis.disconnect();
			}
		}
	}
	
	public static void set(String key,String value){
		Jedis jedis=null;
		try{
			jedis=pool.getResource();
			jedis.set(key, value);
		}catch(Exception e){
			pool.returnBrokenResource(jedis);
		}finally{
			close(jedis);//归还到连接池
		}
	}
	
	public static void set(String key,String value,int expireDate){
		Jedis jedis=null;
		try{
			jedis=pool.getResource();
			jedis.set(key, value);
			jedis.expire(key, expireDate);
		}catch(Exception e){
			pool.returnBrokenResource(jedis);
		}finally{
			close(jedis);//归还到连接池
		}
	}
	
	public static void set(byte[] key,byte[] value){
		Jedis jedis=null;
		try{
			jedis=pool.getResource();
			jedis.set(key, value);
		}catch(Exception e){
			pool.returnBrokenResource(jedis);
		}finally{
			close(jedis);//归还到连接池
		}
	}
	public static void set(byte[] key,byte[] value,int expireDate){
		Jedis jedis=null;
		try{
			jedis=pool.getResource();
			jedis.set(key, value);
			jedis.expire(key, expireDate);
		}catch(Exception e){
			pool.returnBrokenResource(jedis);
		}finally{
			close(jedis);//归还到连接池
		}
	}
	
	/**
	 * 存取Redis队列-顺序存储
	 * @param byte[] key redis键名
	 * @param byte[] value redis键值
	 */
	public static void lpush(byte[] key,byte[] value){
		Jedis jedis=null;
		try{
		jedis=pool.getResource();
		jedis.lpush(key, value);
		}catch(Exception  e){
			pool.returnBrokenResource(jedis);
		}finally{
			close(jedis);
		}
	}
	
	/**
	 * 
	 * 存储Redis队列-反向存储
	 * @param byte[] key redis键名
	 * @param byte[] value redis键值
	 */
	public static void rpush(byte[] key, byte[] value) {
		Jedis jedis=pool.getResource();
		try{
			jedis=pool.getResource();
			jedis.rpush(key, value);
			}catch(Exception  e){
				pool.returnBrokenResource(jedis);
			}finally{
				close(jedis);
			}
	}
	/**
	 * 获取队列数据
	 * @param byte[] key redis键名
	 * @return List<byte[]>
	 */
	public static List<byte[]> lpopList(byte [] key){
		Jedis jedis=pool.getResource();
		List<byte[]> list=null;
		try{
			list=jedis.lrange(key, 0, -1);
		}catch(Exception e){
			pool.returnBrokenResource(jedis);
		}finally{
			close(jedis);
		}
		return list;
	}
	
	/**
	 * 存取Redis队列-顺序存储
	 * @param String key redis键名
	 * @param String value redis键值
	 */
	public static void lpush(String key,String value){
		Jedis jedis=null;
		try{
		jedis=pool.getResource();
		jedis.lpush(key, value);
		}catch(Exception  e){
			pool.returnBrokenResource(jedis);
		}finally{
			close(jedis);
		}
	}
	
	/**
	 * 
	 * 存储Redis队列-反向存储
	 * @param String key redis键名
	 * @param String value redis键值
	 */
	public static void rpush(String key,String value) {
		Jedis jedis=pool.getResource();
		try{
			jedis=pool.getResource();
			jedis.rpush(key, value);
			}catch(Exception  e){
				pool.returnBrokenResource(jedis);
			}finally{
				close(jedis);
			}
	}
	
	/**
	 * 获取队列数据
	 * @param String key redis键名
	 * @return List<String>
	 */
	public static List<String> lpopList(String key){
		Jedis jedis=pool.getResource();
		List<String> list=null;
		try{
			list=jedis.lrange(key, 0, -1);
		}catch(Exception e){
			pool.returnBrokenResource(jedis);
		}finally{
			close(jedis);
		}
		return list;
	}
	
	/**
	 * 获取队列数据
	 * @param String key Redis键名
	 * @return String
	 */
	public static String getPopValue(String key){
		Jedis jedis=null;
		String value=null;
		try{
			jedis=pool.getResource();
			value=jedis.rpop(key);
		}catch(Exception e){
			pool.returnBrokenResource(jedis);
		}finally{
			close(jedis);
		}
		return value;
	}
	
	/**
	 * 删除redis指定<K,V> pair
	 * @param String key 键名
	 */
	public static void delete(String key){
		Jedis jedis=null;
		try{
			jedis=pool.getResource();
			jedis.del(key);
		}catch(Exception e){
			pool.returnBrokenResource(jedis);
		}finally{
			close(jedis);
		}
	}
	
	public static void trimAll(String key){
		Jedis jedis=pool.getResource();
		try{
			jedis.ltrim(key, 1,0);
		}catch(Exception e){
			pool.returnBrokenResource(jedis);
		}finally{
			close(jedis);
		}
	}
	/**
	 * 获取指定范围的元素 
	 * @param String key redis键名
	 * @param int start 起始位置
	 * @param int end 终止位置
	 * @return
	 */
	public static List<String> lrange(String key,int start,int end){
		Jedis jedis=null;
		List<String> list=null;
		try{
			jedis=pool.getResource();
			list=jedis.lrange(key, start, end);
		}catch(Exception e){
			pool.returnBrokenResource(jedis);
		}finally{
			close(jedis);
		}
		return list;
	}
	
	public static List<String> lrange(String key){
		List<String> list = null;
		list = lrange(key, 0, -1);
		return list;
	}
	static boolean isJedisConn(){
		Jedis jedis=pool.getResource();
		try{
		String success=null;
		success=jedis.ping();
		System.out.println("Jedis connection status:"+success);
		}catch(Exception e){
			 e.printStackTrace();
			 return false;
		}
		return true;
	}
	
	static boolean add(String key,String value){
		Jedis jedis=pool.getResource();
		Long i=jedis.setnx(key, value);
		return i==1;
	}
}

package com.javabase.base.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis工具类
 * 
 * @author admin
 *
 */
@Component("redisUtil")
public class RedisUtil {
	@Autowired
    private JedisPool redisPool;

    public Jedis getJedis() {
         return redisPool.getResource();
    }

    public String set(String key,String value) {
         Jedis jedis = getJedis();
         try {
              return jedis.set(key, value);
         } finally {
              jedis.close();
         }
    }

    /**
    *
    * @param key
    * @param value
    * @param expire seconds(int)
    * @return
    */
    public String set(String key,String value,int expireTime) {
         Jedis jedis = getJedis();
         try {
              return jedis.setex(key, expireTime, value);
         } finally {
              jedis.close();
         }
    }

    public String get(String key) {
         Jedis jedis = getJedis();
         try {
              return jedis.get(key);
         } finally {
              jedis.close();
         }
    }

    public Long del(final String... keys) {
         Jedis jedis = getJedis();
         try {
              return jedis.del(keys);
         } finally {
              jedis.close();
         }
    }

    public Long del(String key) {
         Jedis jedis = getJedis();
         try {
              return jedis.del(key);
         } finally {
              jedis.close();
         }
    }
}


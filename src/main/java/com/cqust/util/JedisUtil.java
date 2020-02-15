package com.cqust.util;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class JedisUtil {
	
    private static RedisTemplate<String,String> redisTemplate;
    
    @Autowired
    public JedisUtil(StringRedisTemplate redisTemplate) {
    	JedisUtil.redisTemplate = redisTemplate;
    }
	/**
     * 保存值 可以设置过期时间和时间单位
     */
    public static boolean set(String key, String value, long saveTime, TimeUnit timeUnit) {
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        if (saveTime <= 0) {
            opsForValue.set(key, value);
        } else {
            opsForValue.set(key, value, saveTime, timeUnit);
        }
        return true;
    }
    /**
     * 保存值 可以设置过期时间，默认单位为秒
     *
     * @param saveTimeSec 保存时长(秒) <=0 永久保存 >0 时长
     */
    public  boolean set(String key, String value, long saveTimeSec) {
        return set(key, value, saveTimeSec, TimeUnit.SECONDS);
    }

    /**
     * 保存值 永久保存
     */
    public  boolean set(String key, String value) {
        return set(key, value, -1);
    }

    /**
     * 获取值
     */
    public static  String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 移除键值
     */
    public  boolean remove(String key) {
        if (!exists(key)) {
            return false;
        }
        redisTemplate.delete(key);
        //return delete != null && delete;
        return true; 
    }

    /**
     * 判断是否存在
     */
    public  boolean exists(String key) {
        Boolean hasKey = redisTemplate.hasKey(key);
        return hasKey != null && hasKey;
    }
}

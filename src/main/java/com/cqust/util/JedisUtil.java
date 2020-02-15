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
     * ����ֵ �������ù���ʱ���ʱ�䵥λ
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
     * ����ֵ �������ù���ʱ�䣬Ĭ�ϵ�λΪ��
     *
     * @param saveTimeSec ����ʱ��(��) <=0 ���ñ��� >0 ʱ��
     */
    public  boolean set(String key, String value, long saveTimeSec) {
        return set(key, value, saveTimeSec, TimeUnit.SECONDS);
    }

    /**
     * ����ֵ ���ñ���
     */
    public  boolean set(String key, String value) {
        return set(key, value, -1);
    }

    /**
     * ��ȡֵ
     */
    public static  String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * �Ƴ���ֵ
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
     * �ж��Ƿ����
     */
    public  boolean exists(String key) {
        Boolean hasKey = redisTemplate.hasKey(key);
        return hasKey != null && hasKey;
    }
}

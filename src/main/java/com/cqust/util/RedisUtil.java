package com.cqust.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * Redis Util
 * @author phil
 * @date 2017��8��14��
 *
 */
public class RedisUtil {
	 
		@Resource
	 private static StringRedisTemplate redisTemplate;

	    public RedisUtil(StringRedisTemplate redisTemplate) {
	        RedisUtil.redisTemplate = redisTemplate;
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
	    public static boolean set(String key, String value, long saveTimeSec) {
	        return set(key, value, saveTimeSec, TimeUnit.SECONDS);
	    }

	    /**
	     * ����ֵ ���ñ���
	     */
	    public static boolean set(String key, String value) {
	        return set(key, value, -1);
	    }

	    /**
	     * ��ȡֵ
	     */
	    public static String get(String key) {
	        return redisTemplate.opsForValue().get(key);
	    }

	    /**
	     * �Ƴ���ֵ
	     */
	    public static boolean remove(String key) {
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
	    public static boolean exists(String key) {
	        Boolean hasKey = redisTemplate.hasKey(key);
	        return hasKey != null && hasKey;
	    }
}

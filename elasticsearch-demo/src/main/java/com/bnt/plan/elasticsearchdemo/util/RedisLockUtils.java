package com.bnt.plan.elasticsearchdemo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redis分布式锁工具
 *
 * @author bnt
 * @version 1.0.0
 * @create 2024/9/2 上午11:34 bnt
 * @history
 */
@Slf4j
@Component
public class RedisLockUtils {
    @Autowired
    private RedisTemplate redisTemplate;

    public boolean lock(String key, String value, Long expireTime) {
        final boolean result = Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent("redis_lock_" + key, value, expireTime, TimeUnit.SECONDS));
        if (result) {
            System.out.println("redis加锁成功,key:" + key);
        }
        return result;
    }

    public boolean unlock(String key) {
        final boolean result = Boolean.TRUE.equals(redisTemplate.delete("redis_lock_" + key));
        if (result){
            System.out.println("redis解锁成功,key:" + key);
        }
        return result;
    }
}

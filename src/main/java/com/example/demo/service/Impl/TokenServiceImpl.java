package com.example.demo.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenServiceImpl {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void blackListAccessToken(String token, long duration, TimeUnit unit){
        redisTemplate.opsForValue().set(token, "BLACKLISTED", duration, unit);
    }

    public boolean isTokenBlacklisted(String token) {
        return redisTemplate.opsForValue().get(token) != null;
    }
}

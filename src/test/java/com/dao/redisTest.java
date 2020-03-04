package com.dao;

import com.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.lang.model.type.ArrayType;
import java.util.Arrays;

public class redisTest extends BaseTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("test",123);
        System.out.println(valueOperations.get("test"));

    }
}

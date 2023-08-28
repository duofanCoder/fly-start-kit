package com.duofan.fly.web.starter;

import com.duofan.fly.commons.redis.service.RedisService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.w3c.dom.html.HTMLLegendElement;

@SpringBootTest
class FlyStarterApplicationTests {

    @Resource(name = "redisService")
    private RedisService redisService;

    @Test
    void contextLoads() {
        redisService.set("hello","nmihoa");
        listOps.leftPush("greet:hello", "222");
        listOps.leftPush("greet:baga", "2222");
        ops.set("duofan", "zhizhizhi");
        String duofan = (String) ops.get("duofan");
        System.out.println(duofan);
    }

    @Resource(name = "redisTemplate")
    private ValueOperations<String, Object> ops;


    // inject the actual template
    @Autowired
    private RedisTemplate<String, String> template;

    // inject the template as ListOperations
    @Resource(name = "redisTemplate")
    private ListOperations<String, String> listOps;

}

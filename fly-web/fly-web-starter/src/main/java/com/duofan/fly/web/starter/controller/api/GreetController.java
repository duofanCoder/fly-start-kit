package com.duofan.fly.web.starter.controller.api;

import com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo;
import com.duofan.fly.core.base.entity.FlyUser;
import com.duofan.fly.core.mapper.FlyUserMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/greet")
public class GreetController {

    @Resource
    private FlyUserMapper userMapper;

    @GetMapping("hello1")
    @FlyAccessInfo(opName = "说句你好")
    public ResponseEntity<String> hello() {
        List<FlyUser> flyUsers = userMapper.selectList(null);
        System.out.println(flyUsers);
        return ResponseEntity.ok("hello");
    }

    @GetMapping("hello")
    @FlyAccessInfo(opName = "说句你好", isGrantToAll = true, needAuthenticated = false)
    public ResponseEntity<String> helloGrantAll() {
        List<FlyUser> flyUsers = userMapper.selectList(null);
        System.out.println(flyUsers);
        return ResponseEntity.ok("hello");
    }
}

package com.duofan.fly.web.starter.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("greet")
public class GreetController {

    @RequestMapping("hello")
    public String hello(){
        return "hello";
    }
}

package com.duofan.fly.web.starter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("greet")
@Tag(name = "打招呼")
public class GreetController {

    @Operation(summary = "普通body请求")
    @PostMapping("hello")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("hello");
    }
}

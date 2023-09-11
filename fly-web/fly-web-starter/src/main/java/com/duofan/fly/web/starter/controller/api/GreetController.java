package com.duofan.fly.web.starter.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("greet")
public class GreetController {

    @Operation(summary = "普通body请求")
    @GetMapping("hello")
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("hello");
    }
}

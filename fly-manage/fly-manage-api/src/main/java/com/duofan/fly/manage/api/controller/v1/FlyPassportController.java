package com.duofan.fly.manage.api.controller.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/19
 */

@Slf4j
@RestController
@RequestMapping("passport")
public class FlyPassportController {

    @PostMapping("/login")
    public void fakeLogin(@RequestBody LoginRequest loginRequest) {
        System.out.println(loginRequest);

        return;
    }

    @PostMapping("/logout")
    public void fakeLogout() {
        return;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LoginRequest {
        private String username;
        private String password;
    }
}

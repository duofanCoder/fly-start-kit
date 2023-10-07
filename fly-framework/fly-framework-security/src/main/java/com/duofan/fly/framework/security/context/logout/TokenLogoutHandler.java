package com.duofan.fly.framework.security.context.logout;

import com.duofan.fly.core.base.constant.security.SecurityConstant;
import com.duofan.fly.framework.security.constraint.FlyTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 * token 缓存处理
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/1
 */
public class TokenLogoutHandler implements LogoutHandler {

    private final FlyTokenService tokenService;

    public TokenLogoutHandler(FlyTokenService tokenService1) {
        this.tokenService = tokenService1;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader(SecurityConstant.TOKEN_HEADER_KEY);
        tokenService.delete(token);
    }
}

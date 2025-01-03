package com.duofan.fly.framework.security.context.jwt;

import cn.hutool.core.util.StrUtil;
import com.duofan.fly.core.base.constant.security.SecurityConstant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT 过滤器
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/27
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtAuthenticationProvider authenticationProvider;
    private final String loginPath = "/api/v1/passport/login";

    public JwtAuthenticationFilter(JwtAuthenticationProvider jwtAuthenticationProvider) {
        this.authenticationProvider = jwtAuthenticationProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(SecurityConstant.TOKEN_HEADER_KEY);
        if (!request.getRequestURI().endsWith(loginPath) && StrUtil.isNotBlank(token)) {
            try {
                authenticationProvider.authenticate(token, request, response);
            } catch (Exception e) {
                request.setAttribute("jwtFilter.error", e);
                request.getRequestDispatcher("/error/jwtFilter").forward(request, response);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }


}

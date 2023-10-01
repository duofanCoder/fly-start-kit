package com.duofan.fly.framework.security.context.jwt;

import cn.hutool.core.util.StrUtil;
import com.duofan.fly.framework.security.property.SecurityConstant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
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
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter implements InitializingBean {

    private JwtAuthenticationProvider authenticationProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(SecurityConstant.TOKEN_HEADER_KEY);
        // 如果认证失败，返回false内部会自动解析异常返回
        if (StrUtil.isNotBlank(token) && authenticationProvider.authenticate(token)) {
            doFilter(request, response, filterChain);
        }
        // 无TOKEN直接通过
        if (StrUtil.isBlank(token)) {
            doFilter(request, response, filterChain);
        }
    }


    @Override
    public void afterPropertiesSet() {
        Assert.notNull(this.authenticationProvider, "authenticationProvider未配置");
    }


}

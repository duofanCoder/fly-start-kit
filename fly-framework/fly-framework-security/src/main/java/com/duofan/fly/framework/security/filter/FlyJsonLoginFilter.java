package com.duofan.fly.framework.security.filter;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * JSON 登陆处理
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/19
 */
@Slf4j
public class FlyJsonLoginFilter extends UsernamePasswordAuthenticationFilter {

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (reqSupport(request)) {
            JSONObject body = JSON.parseObject(request.getInputStream());
            UsernamePasswordAuthenticationToken unauthenticated = UsernamePasswordAuthenticationToken.unauthenticated(body.getString(super.getUsernameParameter()),
                    body.getString(super.getPasswordParameter()));
            // Allow subclasses to set the "details" property
            setDetails(request, unauthenticated);
            return this.getAuthenticationManager().authenticate(unauthenticated);
        }
        return super.attemptAuthentication(request, response);
    }

    boolean reqSupport(HttpServletRequest req) {
        if (!HttpMethod.POST.matches(req.getMethod())) {
            return false;
        }

        MediaType mediaType = null;
        try {
            mediaType = MediaType.parseMediaType(req.getContentType());
        } catch (Exception e) {
            log.warn("登陆请求方式不支持:{}", e.getMessage());
            throw new UnsupportedOperationException();
        }

        return MediaType.APPLICATION_JSON.isCompatibleWith(mediaType);
    }
}

package com.duofan.fly.framework.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.log.LogMessage;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 登陆验证码过滤器
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/13
 */
@Slf4j
public class FlyLoginCaptchaFilter extends OncePerRequestFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login",
            "POST");

    private static final String FLY_FORM_CAPTCHA_UID_KEY = "captchaUid";

    private static final String FLY_FORM_CAPTCHA_CODE_KEY = "captchaCode";


    private String captchaParameter = FLY_FORM_CAPTCHA_UID_KEY;

    private String captchaCodeParameter = FLY_FORM_CAPTCHA_CODE_KEY;

    private final AntPathRequestMatcher loginRequestMatcher;

    public FlyLoginCaptchaFilter(AntPathRequestMatcher loginRequestMatcher) {
        loginRequestMatcher = DEFAULT_ANT_PATH_REQUEST_MATCHER;
        this.loginRequestMatcher = loginRequestMatcher;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        if (requiresHandleRequest(request, response)) {
            String parameter = request.getParameter(captchaParameter);
        }

    }

    protected boolean requiresHandleRequest(HttpServletRequest request, HttpServletResponse response) {
        if (this.loginRequestMatcher.matches(request)) {
            return true;
        }
        if (this.logger.isTraceEnabled()) {
            this.logger
                    .trace(LogMessage.format("Did not match request to %s", this.loginRequestMatcher));
        }
        return false;
    }


    public void setCaptchaParameter(String captchaParameter) {
        this.captchaParameter = captchaParameter;
    }

    public void setCaptchaCodeParameter(String captchaCodeParameter) {
        this.captchaCodeParameter = captchaCodeParameter;
    }
}

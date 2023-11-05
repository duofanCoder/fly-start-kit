package com.duofan.fly.api.verification.proptery;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 当面付配置配置类
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/27
 */


@Data
@ConfigurationProperties(prefix = "fly.verification")
public class VerificationProperty {
    private PhoneVerificationProperty phone = new PhoneVerificationProperty();

    private EmailVerificationProperty email = new EmailVerificationProperty();

    private CaptchaVerificationProperty captcha = new CaptchaVerificationProperty();

    @Data
    public static class CaptchaVerificationProperty {
        private boolean enabled = true;

        // 有效时间 分钟
        private int effectiveTime = 3;


        private int width = 120;
        private int height = 40;
        private int codeLength = 4;

    }

    @Data
    public static class EmailVerificationProperty {
        private boolean enabled = false;
        // 有效时间 分钟
        private int effectiveTime = 10;
    }

    @Data
    public static class PhoneVerificationProperty {
        private boolean enabled = false;
        // 有效时间 分钟
        private int effectiveTime = 3;
    }
}

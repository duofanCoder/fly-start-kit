package com.duofan.fly.core.spi.message;

import java.io.IOException;
import java.util.Map;
import java.util.Set;


public interface VerificationCodeSender {
    void sendVerificationCode(Set<String> recipients, String template, Map<String, Object> parameters) throws IOException;
}

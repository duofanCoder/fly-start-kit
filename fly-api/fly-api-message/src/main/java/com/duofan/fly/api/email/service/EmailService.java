//package com.duofan.fly.api.email.service;
//
//import com.duofan.fly.core.spi.message.VerificationCodeSender;
//import com.duofan.fly.core.spi.message.VerificationCodeStorage;
//
//public class EmailService {
//
//    private final VerificationCodeSender verificationCodeSender;
//    private final VerificationCodeStorage cacheService;
//
//    public EmailService(VerificationCodeSender verificationCodeSender, VerificationCodeStorage cacheService) {
//        this.verificationCodeSender = verificationCodeSender;
//        this.cacheService = cacheService;
//    }
//
//    public void sendVerificationCode(String recipientEmail) {
//        if (!cacheService.containsEmail(recipientEmail)) {
//            // 生成验证码逻辑
//            String verificationCode = generateVerificationCode();
//
//            // 将验证码保存到缓存中，绑定邮箱地址
//            cacheService.saveVerificationCode(recipientEmail, verificationCode);
//
//            // 发送邮件
//            emailVerificationCodeSender.sendVerificationCode(recipientEmail, verificationCode);
//        } else {
//            // 如果在短时间内已经发送过验证码，则不再发送
//            System.out.println("Verification code has already been sent to this email address.");
//        }
//    }
//
//    // 其他方法...
//}

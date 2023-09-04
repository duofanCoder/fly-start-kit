package com.duofan.fly.web.starter.abstractExternalTest;

public class Son extends Student implements InHome {
    public Son() {
        System.out.println("儿子构造");
    }

    @Override
    public void goHome() {
        System.out.println("在家");
    }

    @Override
    public void speak() {
        System.out.println("我是儿子在家说话");
    }
}

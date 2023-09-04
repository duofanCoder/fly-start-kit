package com.duofan.fly.web.starter.abstractExternalTest;

public class Student extends Person {
    public Student() {
        System.out.println("我是学生");
    }

    /**
     * 假设不实现则父类人抽象将会执行
     */
    @Override
    public void speak() {
        System.out.println("学生说话");
    }
}

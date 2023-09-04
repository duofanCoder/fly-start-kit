package com.duofan.fly.web.starter.abstractExternalTest;

public abstract class Person  implements ISpeak{

    public Person() {
        System.out.println("我是人构造");
        this.speak();
    }

    @Override
    public void speak() {
        System.out.println("抽象人  什么也不做 说话");
    }
}

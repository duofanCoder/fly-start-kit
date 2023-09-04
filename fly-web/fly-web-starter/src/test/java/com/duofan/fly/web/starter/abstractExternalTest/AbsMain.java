package com.duofan.fly.web.starter.abstractExternalTest;

import javax.sound.midi.Soundbank;

public class AbsMain {

    /**
     * 继承多态关系研究
     * @param args
     */
    public static void main(String[] args) {
        Son son = new Son();
        son.goHome();
        System.out.println(son);
    }
}

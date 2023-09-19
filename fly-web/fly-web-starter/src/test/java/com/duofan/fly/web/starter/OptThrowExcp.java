package com.duofan.fly.web.starter;

import java.util.Optional;

/**
 * 异常
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/19
 */
public class OptThrowExcp {

    static String get() {
        throw new UnsupportedOperationException("asd");
    }


    public static void main(String[] args) {
        String s = Optional.ofNullable(get()).orElseThrow();
        System.out.println("213312");
        System.out.println(s);

        System.out.println("hello");
    }
}

package com.duofan.fly.framework.mvc.initRunner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * 启动初始化
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/17
 */
@Component
@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
public class BootInit implements CommandLineRunner {

    @Value("${spring.application.name}")
    private String appName;

    @Value("${server.port}")
    private String serverPort;

    @Value("${server.servlet.context-path:}")
    private String serverContextPath;


    @Override
    public void run(String... args) throws Exception {
        log.info("""

                        ----------------------------------------------------------
                        \tApplication '{}' is running! Access URLs:
                        \tLocal: \t\thttp://localhost:{}
                        \tExternal: \thttp://{}:{}
                        \tDoc: \thttp://{}:{}{}/doc.html
                        ----------------------------------------------------------""",
                appName,
                serverPort,
                InetAddress.getLocalHost().getHostAddress(),
                serverPort,
                InetAddress.getLocalHost().getHostAddress(),
                serverPort, serverContextPath
        );
    }
}

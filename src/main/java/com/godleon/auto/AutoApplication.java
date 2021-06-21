package com.godleon.auto;

import com.godleon.auto.socket.WebSocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * web-automation
 * @author leon
 * @date 2021-06-21
 */
@SpringBootApplication
public class AutoApplication {

    public static void main(String[] args) {
        new WebSocketServer();
        SpringApplication.run(AutoApplication.class, args);
    }

}

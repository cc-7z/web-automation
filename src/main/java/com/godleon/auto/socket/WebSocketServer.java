package com.godleon.auto.socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebSocketServer {

    private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    private ServerSocket serverSocket;

    public WebSocketServer() {
        try {
            serverSocket = new ServerSocket(5000);
            log.info("websocket已开启！");
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        new Thread(() -> {
            try{
                while(true){
                    Socket socket = serverSocket.accept();
                    new ServerSocketThread(socket).start();
                }
            }catch(IOException e){
                log.error("连接异常：", e);
            }
        }).start();
    }
}

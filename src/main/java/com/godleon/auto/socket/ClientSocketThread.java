package com.godleon.auto.socket;

import com.godleon.auto.container.AutoContainerMain;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientSocketThread extends Thread{

    private static final Logger LOG = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    // 与服务器保持长连接的socket实例
    private Socket socket;
    // 是否自动重连
    private boolean reconnect = Boolean.parseBoolean(AutoContainerMain.getProperty("autoReconnection"));

    private volatile boolean keepPing = true;

    public ClientSocketThread(){}

    public boolean setSocket(int count){
        int time = 0;
        if(count > 0){
            if(count < 10){ // 断线重连，小于10次，间隔为10秒
                time = 10000;
            }else{	// 10次后，重连的间隔时间为30秒
                time = 30000;
            }
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        initSocket();

        return this.socket != null;

    }
    // 连接服务器
    public void initSocket(){
        try {
            socket = new Socket(AutoContainerMain.getProperty("serviceAddress"), Integer.parseInt(AutoContainerMain.getProperty("servicePort")));
            sendMessageToServer(AutoContainerMain.getProperty("deviceName"));

            LOG.info("与服务器建立连接成功！");
        } catch (IOException e1) {
            LOG.info("与服务端建立socket连接异常。");
        }
    }
    // 持续接收服务器推送的消息
    public void run(){
        setSocket(0);
        new Thread(() -> {//启一个线程，每5秒ping一次服务器
            while(keepPing){
                try {
                    Thread.sleep(5000);
                    sendMessageToServer("1");
                } catch (Exception e) {
                }
            }
        }).start();
        DataInputStream in;
        byte[] bytes;
        int len;
        String content;
        while(true){
            try{
                in = new DataInputStream(socket.getInputStream());
                len = in.readInt();
                bytes = new byte[len];
                in.read(bytes);
                content = new String(bytes, "UTF-8");
                LOG.info("收到服务端推送的消息：" + content);
                AutoContainerMain.runTask(content);
            }catch(Exception e){
                //如果配置文件中配置自动重连为false，则退出循环，不进行重连
                if(!reconnect){
                    keepPing = false;
                    break;
                }
                reconnect();
            }
        }
    }

    public void reconnect(){
        this.closeSocket();
        this.socket = null;
        int count = 0;
        while(!setSocket(count++));//重连
    }

    // 关闭连接
    public void closeSocket() {
        if(socket != null){
            try {
                socket.close();
            } catch (IOException e) {
                LOG.error("关闭socket异常：", e);
            }
        }
    }
    // 发送消息给服务器
    public void sendMessageToServer(String content) {
        while(true){
            try {
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                byte[] contentBytes = (content).getBytes("UTF-8");
                out.writeInt(contentBytes.length);
                out.write(contentBytes);
                out.flush();
                //LOG.debug("发送消息：" + content);
                return;
            } catch (IOException e) {
                LOG.error("发送消息给服务器异常：", e);
                if(!reconnect){
                    keepPing = false;
                    return;
                }
                reconnect();
            }
        }
    }
}

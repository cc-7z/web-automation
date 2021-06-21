package com.godleon.auto.socket;

import com.godleon.auto.container.AutoContainerMain;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Establish a long link with the browser
 * @author leon
 * @version 1.0.0
 */
public class ServerSocketThread extends Thread{
    private static final Logger LOG = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    private Socket socket;

    private String resContent = "";

    public ServerSocketThread(){}

    public ServerSocketThread(Socket socket){
        this.socket = socket;
    }

    public void setSocket(Socket socket){
        this.socket = socket;
    }

    public void run(){
        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            byte[] buff = new byte[1024];
            int count = -1;
            String req = "";
            count = in.read(buff);
            req = new String(buff, 0, count);
            String secKey = getSecWebSocketKey(req);

            String response="HTTP/1.1 101 Switching Protocols\r\n"
                    + "Upgrade: websocket\r\n"
                    + "Connection: Upgrade\r\n"
                    + "Sec-WebSocket-Accept: " + getSecWebSocketAccept(secKey) + "\r\n\r\n";
            out.write(response.getBytes());
            AutoContainerMain.setServerSocketThread(this);

            while(true){
                count = in.read(buff);
                for(int i=0; i<count-6; i++){
                    buff[i+6] = (byte) (buff[i%4+2]^buff[i+6]);
                }
                String content = new String(buff, 6, count-6, "UTF-8");
                if(content.endsWith("$_#")){
                    resContent += content.substring(0, content.length()-3);
                    LOG.debug("收到浏览器消息：{}", resContent);
                    synchronized(this){
                        this.notifyAll();
                    }
                }else{
                    resContent += content;
                }
            }
        } catch (Exception e) {
            AutoContainerMain.removeServerSocketThread();
            LOG.info("webSocket通过异常关闭。");
        }
    }
    // 发送消息给浏览器
    public String sendMessageToClient(String content) {
        resContent = "";
        try {
            LOG.debug("发送消息给牛奶器：" + content);
            OutputStream out = this.socket.getOutputStream();
            byte[] bytes = content.getBytes("UTF-8");
            byte[] head = new byte[2];
            head[0] = -127;
            head[1] = (byte) (bytes.length);
            out.write(head);
            out.write(bytes);
            out.flush();
            synchronized(this){
                try {
                    this.wait(50000);
                } catch (InterruptedException e) {
                    LOG.error("线程等待中被打断：", e);
                }
            }
        } catch (IOException e1) {
            LOG.error("webSocket发送消息异常：", e1);
        }
        return resContent;
    }

    public void closeSession(){
        if(socket != null){
            try {
                socket.close();
            } catch (IOException e) {
                LOG.error("webSocket关闭异常：", e);
            }
        }
    }

    private String getSecWebSocketKey(String req){
        Pattern p = Pattern.compile("^(Sec-WebSocket-Key:).+",Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);
        Matcher m = p.matcher(req);
        if(m.find()){
            String foundstring = m.group();
            return foundstring.split(":")[1].trim();
        }
        else{
            return null;
        }
    }

    private String getSecWebSocketAccept(String key) throws Exception{
        String guid = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
        key += guid;
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(key.getBytes("ISO-8859-1"), 0, key.length());
        byte[] shaHash = md.digest();
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(shaHash);
    }
}

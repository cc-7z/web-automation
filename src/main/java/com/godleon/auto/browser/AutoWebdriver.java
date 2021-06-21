package com.godleon.auto.browser;

import com.godleon.auto.container.AutoContainerMain;

/**
 * browser driver operation in web
 * @author leon
 */
public class AutoWebdriver {
    /**
     * 发送指令给浏览器，打开指定的网页
     * @param url 网页的url
     */
    public void open(String url){
        String status = null;
        int count = 0;
        String command = "{\"t\":1,\"n\":\"open\", \"a\":[\"" + url + "\"]}";
        do{
            status = AutoContainerMain.sendMessageToBrowser(command);
        }while("fail".equals(status) && ++count<10);
    }

}

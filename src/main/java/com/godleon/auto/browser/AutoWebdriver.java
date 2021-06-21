package com.godleon.auto.browser;

import com.godleon.auto.container.AutoContainerMain;

import java.util.NoSuchElementException;

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
    /**
     * 获取当前页面的url
     * @return 当前页面的url
     */
    public String url(){
        return AutoContainerMain.sendMessageToBrowser("{\"t\":1,\"n\":\"url\", \"a\":[]}");
    }
    /**
     * 通过id获取元素
     * @param id 元素的id
     * @return 找到的元素。如果没有符合要求的元素，则会抛出 NoSuchElementException 异常
     */
    public WebElement findElementById(String id){
        String res = findElementBasic("{\"t\":0,\"n\":\"id\", \"a\":[\"" + id + "\"]}");
        if(res == null || "{}".equals(res)){
            throw new NoSuchElementException("没有找到元素，方法：findElementById，args: [" + id + "]");
        }
        return AutoContainerMain.json2Obj(res, WebElement.class);
    }
    /**
     * 通过标签名查找元素
     * @param tagName 标签名称
     * @return 第一个符合要求的元素。如果没有符合要求的元素，则会抛出 NoSuchElementException 异常
     */
    public WebElement findElementByTagName(String tagName){
        return findElementByTagName(tagName, 0);
    }
    /**
     * 通过标签名和下标查找元素
     * @param tagName 标签名称
     * @param index 下标
     * @return 找到的元素。如果没有符合要求的元素，则会抛出 NoSuchElementException 异常
     */
    public WebElement findElementByTagName(String tagName, int index){
        String res = findElementBasic("{\"t\":0,\"n\":\"tname\", \"a\":[\"" + tagName + "\", " + index + "]}");
        if("{}".equals(res)){
            throw new NoSuchElementException("没有找到元素，方法：findElementByTagName，args: [" + tagName + ", " + index + "]");
        }
        return AutoContainerMain.json2Obj(res, WebElement.class);
    }
    /**
     * 通过元素的文本内容查找元素，查找的时候，会把元素的文本，去掉首尾空格后，再与传入的html进行匹配
     * @param html 文本内容
     * @return 第一个符合要求的元素。如果没有符合要求的元素，则会抛出 NoSuchElementException 异常
     */
    public WebElement findElementByHtml(String html){
        return findElementByHtml(html, 0);
    }
    /**
     * 通过元素的文本和下标查找元素，查找的时候，会把元素的文本，去掉首尾空格后，再与传入的html进行匹配
     * @param html 文本内容
     * @param index 下标
     * @return 符合要求的元素。如果没有符合要求的元素，则会抛出 NoSuchElementException 异常
     */
    public WebElement findElementByHtml(String html, int index){
        String res = findElementBasic("{\"t\":0,\"n\":\"html\", \"a\":[\"" + html + "\", " + index + "]}");
        if("{}".equals(res)){
            throw new NoSuchElementException("没有找到元素，方法：findElementByHtml，args: [" + html + ", " + index + "]");
        }
        return AutoContainerMain.json2Obj(res, WebElement.class);
    }
    /**
     * 通过元素自身的文本（不包含子标签及其文本）查找元素
     * @param html 文本内容
     * @return 第一个符合要求的元素。如果没有符合要求的元素，则会抛出 NoSuchElementException 异常
     */
    public WebElement findElementBySelfHtml(String html){
        return findElementBySelfHtml(html, 0);
    }
    /**
     * 通过标签自身的文本（不包含子标签及其文本）和下标查找元素
     * @param html 文本内容
     * @param index 下标
     * @return 符合要求的元素。如果没有符合要求的元素，则会抛出 NoSuchElementException 异常
     */
    public WebElement findElementBySelfHtml(String html, int index){
        String res = findElementBasic("{\"t\":0,\"n\":\"shtml\", \"a\":[\"" + html + "\", " + index + "]}");
        if("{}".equals(res)){
            throw new NoSuchElementException("没有找到元素，方法：findElementBySelfHtml，args: [" + html + ", " + index + "]");
        }
        return AutoContainerMain.json2Obj(res, WebElement.class);
    }
    /**
     * 通过元素的属性查找元素
     * @param attrName 属性名
     * @param attrValue 属性值
     * @return 第一个符合要求的元素。如果没有符合要求的元素，则会抛出 NoSuchElementException 异常
     */
    public WebElement findElementByAttribute(String attrName, String attrValue){
        return findElementByAttribute(attrName, attrValue, 0);
    }
    /**
     * 通过元素的属性和下标查找元素
     * @param attrName 属性名
     * @param attrValue 属性值
     * @param index 下标
     * @return 符合要求的元素。如果没有符合要求的元素，则会抛出 NoSuchElementException 异常
     */
    public WebElement findElementByAttribute(String attrName, String attrValue, int index){
        String res = findElementBasic("{\"t\":0,\"n\":\"attr\", \"a\":[\"" + attrName + "\", \"" + attrValue + "\", " + index + "]}");
        if("{}".equals(res)){
            throw new NoSuchElementException("没有找到元素，方法：findElementByAttribute，args: [" + attrName + ", " + attrValue + ", " + index + "]");
        }
        return AutoContainerMain.json2Obj(res, WebElement.class);
    }
    /**
     * 通过元素的class查找元素
     * @param className class的值
     * @return 第一个符合要求的元素。如果没有符合要求的元素，则会抛出 NoSuchElementException 异常
     */
    public WebElement findElementByClassName(String className){
        return findElementByClassName(className, 0);
    }
    /**
     * 通过元素的class和下标查找元素
     * @param className class的值
     * @param index 下标
     * @return 符合要求的元素。如果没有符合要求的元素，则会抛出 NoSuchElementException 异常
     */
    public WebElement findElementByClassName(String className, int index){
        String res = findElementBasic("{\"t\":0,\"n\":\"cname\", \"a\":[\"" + className + "\", " + index + "]}");
        if("{}".equals(res)){
            throw new NoSuchElementException("没有找到元素，方法：findElementByClassName，args: [" + className + ", " + index + "]");
        }
        return AutoContainerMain.json2Obj(res, WebElement.class);
    }
    /**
     * 关闭当前标签页
     */
    public void closeCurrentPage(){
        AutoContainerMain.sendMessageToBrowser("{\"t\":2,\"n\":\"ccp\", \"a\":[]}");
    }
    /**
     * 查找网页元素的方法的统一调用，发送消息给浏览器
     * @param command 查找元素的指令
     * @return 浏览器的返回信息
     */
    private String findElementBasic(String command){
        if(!AutoContainerMain.getRunFind()){
            AutoContainerMain.runFbl();
        }
        String res = AutoContainerMain.sendMessageToBrowser(command);
        if(AutoContainerMain.getRunFind()){
            int time = AutoContainerMain.getTimeout()*2;
            while(("".equals(res) || "{}".equals(res)) && --time > 0){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                AutoContainerMain.runFbl();
                res = AutoContainerMain.sendMessageToBrowser(command);
            }
        }
        return res;
    }

}

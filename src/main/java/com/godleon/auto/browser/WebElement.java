package com.godleon.auto.browser;

import com.godleon.auto.container.AutoContainerMain;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Map;
import java.util.NoSuchElementException;

public class WebElement {
    /**
     * 元素的名称。
     * 通过查找元素的方法找到的元素，都会在浏览器端进行保存，保存时，就是以这个名称作为键
     */
    private String e;
    /**
     * 元素的数量
     * 通过查找元素的方法，找到的元素，可能会找到多个，这个n就代表查找到的元素的个数
     * 可以根据n的值做循环，从而对查找元素的方法，所有符合条件的元素进行遍历
     */
    private int n;

    public String gete() {
        return e;
    }
    public void sete(String e) {
        this.e = e;
    }
    public int getN() {
        return n;
    }
    public void setN(int n) {
        this.n = n;
    }
    // 查找元素的方法基本与WebDriver类似，只是没有通过id查找
    public WebElement findElementByTagName(String tagName){
        return findElementByTagName(tagName, 0);
    }

    public WebElement findElementByTagName(String tagName, int index){
        String res = findElementBasic("{\"t\":0,\"n\":\"tname\", \"a\":[\"" + tagName + "\", " + index + ", \"" + this.e + "\"]}");
        if("{}".equals(res)){
            throw new NoSuchElementException("没有找到元素，方法：findElementByTagName，args: [" + tagName + ", " + index + "]");
        }
        return AutoContainerMain.json2Obj(res, WebElement.class);
    }

    public WebElement findElementByHtml(String html){
        return findElementByHtml(html, 0);
    }

    public WebElement findElementByHtml(String html, int index){
        String res = findElementBasic("{\"t\":0,\"n\":\"html\", \"a\":[\"" + html + "\", " + index + ", \"" + this.e + "\"]}");
        if("{}".equals(res)){
            throw new NoSuchElementException("没有找到元素，方法：findElementByHtml，args: [" + html + ", " + index + "]");
        }
        return AutoContainerMain.json2Obj(res, WebElement.class);
    }

    public WebElement findElementBySelfHtml(String html){
        return findElementBySelfHtml(html, 0);
    }

    public WebElement findElementBySelfHtml(String html, int index){
        String res = findElementBasic("{\"t\":0,\"n\":\"shtml\", \"a\":[\"" + html + "\", " + index + ", \"" + this.e + "\"]}");
        if("{}".equals(res)){
            throw new NoSuchElementException("没有找到元素，方法：findElementByHtml，args: [" + html + ", " + index + "]");
        }
        return AutoContainerMain.json2Obj(res, WebElement.class);
    }

    public WebElement findElementByAttribute(String attrName, String attrValue){
        return findElementByAttribute(attrName, attrValue, 0);
    }

    public WebElement findElementByAttribute(String attrName, String attrValue, int index){
        String res = findElementBasic("{\"t\":0,\"n\":\"attr\", \"a\":[\"" + attrName + "\", \"" + attrValue + "\", " + index + ", \"" + this.e + "\"]}");
        if("{}".equals(res)){
            throw new NoSuchElementException("没有找到元素，方法：findElementByAttribute，args: [" + attrName + ", " + attrValue + ", " + index + "]");
        }
        return AutoContainerMain.json2Obj(res, WebElement.class);
    }

    public WebElement findElementByClassName(String className){
        return findElementByClassName(className, 0);
    }

    public WebElement findElementByClassName(String className, int index){
        String res = findElementBasic("{\"t\":0,\"n\":\"cname\", \"a\":[\"" + className + "\", " + index + ", \"" + this.e + "\"]}");
        if("{}".equals(res)){
            throw new NoSuchElementException("没有找到元素，方法：findElementByClassName，args: [" + className + ", " + index + "]");
        }
        return AutoContainerMain.json2Obj(res, WebElement.class);
    }
    /**
     * 找当前元素的上一个兄弟元素
     * @return 找到的元素。如果没有符合要求的元素，则会抛出 NoSuchElementException 异常
     */
    public WebElement getBeforeElement(){
        String res = findElementBasic("{\"t\":0,\"n\":\"prev\", \"a\":[\"" + this.e + "\"]}");
        if("{}".equals(res)){
            throw new NoSuchElementException("没有找到元素，方法：getBeforeElement，args: []");
        }
        return AutoContainerMain.json2Obj(res, WebElement.class);
    }
    /**
     * 找当前元素的下一个兄弟元素
     * @return 找到的元素。如果没有符合要求的元素，则会抛出 NoSuchElementException 异常
     */
    public WebElement getAfterElement(){
        String res = findElementBasic("{\"t\":0,\"n\":\"next\", \"a\":[\"" + this.e + "\"]}");
        if("{}".equals(res)){
            throw new NoSuchElementException("没有找到元素，方法：getAfterElement，args: []");
        }
        return AutoContainerMain.json2Obj(res, WebElement.class);
    }
    /**
     * 找当前元素的父元素
     * @return 找到的元素。如果没有符合要求的元素，则会抛出 NoSuchElementException 异常
     */
    public WebElement getParent(){
        String res = findElementBasic("{\"t\":0,\"n\":\"parent\", \"a\":[\"" + this.e + "\"]}");
        if("{}".equals(res)){
            throw new NoSuchElementException("没有找到元素，方法：getParent，args: []");
        }
        return AutoContainerMain.json2Obj(res, WebElement.class);
    }
    /**
     * 找当前元素的第一个子元素
     * @return 找到的元素。如果没有符合要求的元素，则会抛出 NoSuchElementException 异常
     */
    public WebElement getChild(){
        return getChild(0);
    }
    /**
     * 找当前元素的第 index 个子元素
     * @param index 要找的子元素的下标，从0开始
     * @return 找到的元素。如果没有符合要求的元素，则会抛出 NoSuchElementException 异常
     */
    public WebElement getChild(int index){
        String res = findElementBasic("{\"t\":0,\"n\":\"child\", \"a\":[\"" + this.e + "\", " + index + "]}");
        if("{}".equals(res)){
            throw new NoSuchElementException("没有找到元素，方法：getChild，args: [" + index + "]");
        }
        return AutoContainerMain.json2Obj(res, WebElement.class);
    }
    /**
     * 点击元素，操作鼠标进行点击
     */
    public void click(){
        mouseOver();
        Robot r = AutoContainerMain.getRobot();
        r.delay(500);
        r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        r.delay(AutoContainerMain.getRandomInt(110, 130));
        r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        r.delay(200);
    }
    /**
     * 点击元素外面，通常用于使元素失去焦点
     * @param x 相对元素在X轴上的偏移，为正数时，是相对元素右边界往右偏移的值，为负数时，则是相对元素的左边界往左偏移的值
     * @param y 相对元素在Y轴上的偏移，为正数时，是相对元素下边界往下偏移的值，为负数时，则是相对元素的上边界往上偏移的值
     */
    public void click(int x, int y){
        view();
        Bounds b = getBounds();
        int clickX = 0;
        int clickY = 0;
        if(x < 0){
            clickX = b.getL() - x;
        }else{
            clickX = b.getL() + b.getW() + x;
        }
        if(y < 0){
            clickY = b.getT() - y;
        }else{
            clickY = b.getT() + b.getH() + y;
        }
        Robot r = AutoContainerMain.getRobot();
        r.mouseMove(clickX, clickY);
        r.delay(500);
        r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        r.delay(AutoContainerMain.getRandomInt(110, 130));
        r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        r.delay(200);
    }
    /**
     * 点击元素，用于当前元素的祖先元素中有滚动条的情况
     * @param parent 带滚动条的祖先元素
     */
    public void click(WebElement parent){
        parent.mouseOver();
        viewForParent(parent);
        Robot r = AutoContainerMain.getRobot();
        Bounds vb = getBounds(parent);
        r.mouseMove(vb.getL() + vb.getW()/2 + AutoContainerMain.getRandomInt(-2, 2), vb.getT() + vb.getH()/2 + AutoContainerMain.getRandomInt(-2, 2));
        r.delay(500);
        r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        r.delay(AutoContainerMain.getRandomInt(110, 130));
        r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        r.delay(200);
    }
    /**
     * 将鼠标移动到当前元素上
     * @return 鼠标在元素内的位值
     */
    public int[] mouseOver(){
        view();
        Robot r = AutoContainerMain.getRobot();
        Bounds vb = getBounds();
        int[] coordinate = new int[2];
        coordinate[0] = vb.getL() + vb.getW()/2 + AutoContainerMain.getRandomInt(-2, 2);
        coordinate[1] = vb.getT() + vb.getH()/2 + AutoContainerMain.getRandomInt(-2, 2);
        r.mouseMove(coordinate[0], coordinate[1]);
        r.delay(AutoContainerMain.getRandomInt(30, 60));
        return coordinate;
    }
    /**
     * 在当前元素中输入内容
     * @param value 要输入的内容
     */
    public void sendKeys(String value){
        AutoContainerMain.sendMessageToBrowser("{\"t\":1,\"n\":\"value\",\"a\":[\"" + this.e + "\", \"" + value + "\"]}");
    }
    /**
     * 获取当前元素的属性值
     * @param attr 属性的名称
     * @return 对应属性名称的值
     */
    public String getAttribute(String attr){
        return AutoContainerMain.sendMessageToBrowser("{\"t\":1,\"n\":\"getAttr\", \"a\":[\"" + this.e + "\", \"" + attr + "\"]}");
    }
    /**
     * 设置当前元素的属性值
     * @param name 属性名
     * @param value 属性值
     */
    public void setAttribute(String name, String value){
        AutoContainerMain.sendMessageToBrowser("{\"t\":1,\"n\":\"setAttr\",\"a\":[\"" + this.e + "\", \"" + name + "\", \"" + value +"\"]}");
    }
    /**
     * 获取当前元素的文本
     * @return 元素的文本
     */
    public String getHtml(){
        return AutoContainerMain.sendMessageToBrowser("{\"t\":1,\"n\":\"getHtml\", \"a\":[\"" + this.e + "\"]}");
    }
    /**
     * 让元素在页面上显示出来。根据元素是否在屏幕中显示，操作鼠标滚轮向上或是向下滚动，直到元素显示在屏幕上
     */
    public void view(){
        String res = AutoContainerMain.sendMessageToBrowser("{\"t\":1,\"n\":\"view\", \"a\":[\"" + this.e + "\"]}");
        int dis = Integer.parseInt(res);
        Robot r = AutoContainerMain.getRobot();
        if(dis != 0){
            r.mouseWheel(dis);
            r.delay(200);
            view();
        }
    }
    /**
     * 让元素在页面上显示出来，用于祖先元素有滚动条的情况
     * @param parent 有滚动条的祖先元素
     */
    public void viewForParent(WebElement parent){
        String res = AutoContainerMain.sendMessageToBrowser("{\"t\":1,\"n\":\"viewP\", \"a\":[\"" + this.e + "\", \"" + parent.e + "\"]}");
        int dis = Integer.parseInt(res);
        Robot r = AutoContainerMain.getRobot();
        if(dis != 0){
            r.mouseWheel(dis);
            r.delay(200);
            viewForParent(parent);
        }

    }
    /**
     * 拖拽元素
     * @param x 横向拖拽距离，为正数，向右拖动，为负数，向左拖动
     * @param y 纵向拖拽距离，为正数，向下拖动，为负数，下上拖动
     */
    public void drag(int x, int y){
        int[] coordinate = mouseOver();
        Robot r = AutoContainerMain.getRobot();
        r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        r.delay(AutoContainerMain.getRandomInt(40, 70));
        for(int i=1; i<10; ++i){
            r.mouseMove(coordinate[0] + x*i/10, coordinate[1] + y*i/10);
            r.delay(AutoContainerMain.getRandomInt(40, 70));
        }
        r.mouseMove(coordinate[0] + x, coordinate[1] + y);
        r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        r.delay(200);
    }
    /**
     * 获取单选框、复选框等的选中状态
     * @return 为选中状态，返回true，未选中状态，返回false
     */
    public boolean checked(){
        String res = AutoContainerMain.sendMessageToBrowser("{\"t\":1,\"n\":\"checked\", \"a\":[\"" + this.e + "\"]}");
        Map<?, ?> result = AutoContainerMain.json2Obj(res, Map.class);
        if(result.get("status") == null){
            throw new RuntimeException("获取选中状态失败！");
        }
        return Boolean.parseBoolean(String.valueOf(result.get("status")));
    }
    /**
     * 获取元素的边界
     * @return Bounds对象，四个值，分别代表元素的左上x轴、y轴，元素的宽、高
     */
    private Bounds getBounds(){
        String res = AutoContainerMain.sendMessageToBrowser("{\"t\":1,\"n\":\"bounds\", \"a\":[\"" + this.e + "\"]}");
        return AutoContainerMain.json2Obj(res, Bounds.class);
    }
    /**
     * 获取元素的边界，用于祖先元素有滚动条时
     * @param parent 有滚动条的祖先元素
     * @return Bounds对象
     */
    private Bounds getBounds(WebElement parent){
        String res = AutoContainerMain.sendMessageToBrowser("{\"t\":1,\"n\":\"bounds\", \"a\":[\"" + this.e + "\", \"" + parent.e + "\"]}");
        return AutoContainerMain.json2Obj(res, Bounds.class);

    }

    @Override
    public String toString() {
        return "WebElement [e=" + e + ", n=" + n + "]";
    }

    private String findElementBasic(String command){
        AutoContainerMain.runFbl();
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
            if(res == null) return "{}";
        }
        return res;
    }
}

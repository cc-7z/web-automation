package com.godleon.auto.context;

import com.godleon.auto.browser.AutoWebdriver;
import com.godleon.auto.container.AutoContainerMain;
import java.util.Map;

/**
 * abstract class
 * @author leon
 * @date  2021-06-21
 */
public abstract class AutomationContext {
    /**
     * 浏览器对象，可以理解为浏览器的window和document的结合体
     */
    protected AutoWebdriver webdriver;
    /**
     * 任务的id，是单个任务的id
     */
    protected int tlid;

    public void setDriver(AutoWebdriver webdriver){
        this.webdriver = webdriver;
    }

    public AutoWebdriver getDriver(){
        return webdriver;
    }

    public void setTlid(int tlid){
        this.tlid = tlid;
    }

    public int getTlid(){
        return tlid;
    }
    /**
     * 获取配置文件中的配置
     * @param key 配置项的key
     * @return 配置项的值
     */
    protected String getProperty(String key){
        return AutoContainerMain.getProperty(key);
    }

    public abstract void run(Map<?, ?> arguments) throws Exception;
}

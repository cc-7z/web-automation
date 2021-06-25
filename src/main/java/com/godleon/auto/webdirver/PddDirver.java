package com.godleon.auto.webdirver;


import com.godleon.auto.context.AutomationContext;

import java.util.Map;

/**
 * Pdd buy WebDirver
 * @author leon
 * @version 1.0.0
 */
public class PddDirver extends AutomationContext {


    @Override
    public void run(Map<?,?> arguments) throws Exception {
        String url1 = String.valueOf(arguments.get("url"));
        System.out.println(url1);
        webdriver.open(url1);
//        webdriver.findElementById("kw").sendKeys("Java");
//        webdriver.findElementById("su").click();
        Thread.sleep(5000);
    }
}
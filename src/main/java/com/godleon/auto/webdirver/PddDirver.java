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
    public void run(String url) throws Exception {
        String url1 = String.valueOf(url);
        webdriver.open(url1);
        Thread.sleep(5000);
    }
}

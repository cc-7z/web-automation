package com.godleon.auto.task;

import com.godleon.auto.entity.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;

/**
 * Task realization
 * @author leon
 * @version 1.0.0
 */
public class TaskRunnable implements Runnable {

    private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    private Task task;

    public TaskRunnable(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        if (task.getMethodType() == 0) {
            try {
                startBrowser();
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void startBrowser(){

        new Thread(() -> {
            try {
                // --kiosk
                Process ps = Runtime.getRuntime().exec("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe --start-maximized --new-window http://mobile.pinduoduo.com/login.html");
                ps.waitFor();
            } catch (IOException ex) {
                log.error("启动牛奶器异常，信息： ", ex);
            } catch (InterruptedException ex) {
                log.error("启动牛奶器异常，信息： ", ex);
            }
        }).start();
    }
}

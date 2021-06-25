package com.godleon.auto.task;


import com.godleon.auto.container.AutoContainerMain;
import com.godleon.auto.context.AutomationContext;
import com.godleon.auto.entity.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Map;

/**
 * Task realization
 *
 * @author leon
 * @version 1.0.0
 */
public class TaskRunnableBuy implements Runnable {

    private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    private Task task;

    public TaskRunnableBuy(Task task) {
        this.task = task;
    }

    @Override
    public void run() {

        try {

            AutomationContext context = null;
            try {
                context = (AutomationContext) Class.forName("com.godleon.auto.webdirver.PddDirver").newInstance();
            } catch (ClassNotFoundException e) {
                context = AutoContainerMain.getTask("com.godleon.auto.webdirver.PddDirver");
            }
            if(context == null){
                throw new Exception("消息中的className在终端没有找到，请先推送相应的jar文件到终端！");
            }

            context.setDriver(AutoContainerMain.getWebDriver());
            context.setTlid(task.getTlid());
            context.run(AutoContainerMain.json2Obj(task.getArguments(), Map.class));

        } catch (Exception e) {
            log.error("执行脚本出现异常，信息： ", e);
        } finally {
             //关闭浏览器
            if (task.getMethodType() == 0) {
                closeBrowser();
            }
        }


    }
    private void closeBrowser(){
        try {
            String res = AutoContainerMain.sendMessageToBrowser("{\"t\":1,\"n\":\"rt\",\"a\":[]}");
            Map<Object, Object> response = AutoContainerMain.mapper.readValue(res, Map.class);
            int left = (int) response.get("l");
            int top = (int) response.get("t");
            Robot r = AutoContainerMain.getRobot();
            r.mouseMove(left-15, top+10);
            r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            r.delay(50);
            r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            r.delay(200);
        } catch (Exception e) {
            log.error("关闭牛奶器异常，信息： ", e);
        }
    }
}

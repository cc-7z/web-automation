package com.godleon.auto.container;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.godleon.auto.browser.AutoWebdriver;
import com.godleon.auto.context.AutomationContext;
import com.godleon.auto.entity.Task;
import com.godleon.auto.socket.ServerSocketThread;
import com.godleon.auto.socket.ClientSocketThread;
import com.godleon.auto.task.TaskRunnable;
import com.godleon.auto.task.TaskRunnableBuy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * Singleton mode, container class, store some public variables and public methods..
 * @author leon
 * @version 1.0.0
 */
public class AutoContainerMain {
    private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    private static final AutoContainerMain autoContainerMain = new AutoContainerMain();

    /**
     * 与浏览器保持长连接的线程
     */
    private ServerSocketThread serverSocketThread;

    /**
     *与服务器保持长连接的线程
     */
    private ClientSocketThread clientSocketThread;
    /**
     * JSON字符串和对象互转的对象
     */
    public static ObjectMapper mapper = new ObjectMapper();
    /**
     * 浏览器对象
     */
    private AutoWebdriver autoWebdriver = new AutoWebdriver();
    /**
     * 存放配置文件中的所有配置
     */
    private Properties properties = new Properties();

    /**
     * 自定义类加载器
     */
    private ClientClassLoader clientClassLoader = null;
    /**
     * 线程池
     */
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 5, 20, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));

    /**
     * 开启连接服务器的线程，程序启动时，就会调用该方法
     * @return
     */
    public static boolean runSocketThread(){
        if(autoContainerMain.clientSocketThread != null){
            return false;
        }
        autoContainerMain.clientSocketThread = new ClientSocketThread();
        autoContainerMain.clientSocketThread.start();
        return true;
    }

    public static ClientSocketThread getClientSocketThread(){
        return autoContainerMain.clientSocketThread;
    }
    /**
     * 执行任务，收到服务器推送的消息后，调用该方法，参数为服务器推送的消息
     */
    public static void runTask(String content){
        Task task = null;
        try {
            task = mapper.readValue(content, Task.class);
        } catch (Exception e) {
            log.error("下发消息不对，不能转成对象", e);
            String tlid = "0";
            int startIndex = content.indexOf("tlid\"");
            int endIndex = -1;
            if(startIndex != -1){
                endIndex = content.indexOf(",", startIndex + 5);
                if(endIndex != -1){
                    tlid = content.substring(startIndex+5, endIndex).replaceAll("\\D", "");
                }
            }
            getClientSocketThread().sendMessageToServer("{\"type\": \"RESULT\", \"args\":{\"tlid\":" + tlid + ",\"status\":1, \"info\":\"任务执行失败，下发消息不能解析，信息：" + e.getMessage() + "\"}}");
            return;
        }
        autoContainerMain.executor.execute(new TaskRunnable(task));
    }

    public static void runBuy(Task task){
        autoContainerMain.executor.execute(new TaskRunnableBuy(task));
    }

    public static void runSingleBuy(Task task){
        autoContainerMain.executor.execute(new TaskRunnableBuy(task));
    }

    /**
     *  与浏览器建立长连接的线程，保存为全局可使用
     */
    public static void setServerSocketThread(ServerSocketThread serverSocketThread){
        if(autoContainerMain.serverSocketThread != null){
            autoContainerMain.serverSocketThread.closeSession();
        }
        autoContainerMain.serverSocketThread = serverSocketThread;
    }
    /**
     * 发送消息给浏览器的方法
     */
    public static String sendMessageToBrowser(String message){
        if(autoContainerMain.serverSocketThread == null){
            throw new RuntimeException("连接断开！");
        }
        return autoContainerMain.serverSocketThread.sendMessageToClient(message);
    }
    /**
     * 浏览器断开连接，长连接的线程关掉
     */
    public static void removeServerSocketThread(){
        if(autoContainerMain.serverSocketThread != null){
            autoContainerMain.serverSocketThread.closeSession();
        }
        autoContainerMain.serverSocketThread = null;
    }

    /**
     * 通过类全名，到类加载器中拿对应的实例
     * @param className
     * @return
     */
    public static AutomationContext getTask(String className){
        return autoContainerMain.clientClassLoader.getTask(className);
    }

    public static AutoWebdriver getWebDriver(){
        return autoContainerMain.autoWebdriver;
    }


    /**
     * 获取配置文件中配置项的值，参数为配置项的key
     * @param key
     * @return
     */
    public static String getProperty(String key){
        return autoContainerMain.properties.getProperty(key);
    }

}

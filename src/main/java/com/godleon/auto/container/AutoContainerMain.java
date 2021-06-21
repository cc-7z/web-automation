package com.godleon.auto.container;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.godleon.auto.browser.AutoWebdriver;
import com.godleon.auto.browser.FindBeforeListener;
import com.godleon.auto.context.AutomationContext;
import com.godleon.auto.entity.Task;
import com.godleon.auto.socket.ServerSocketThread;
import com.godleon.auto.socket.ClientSocketThread;
import com.godleon.auto.task.TaskRunnable;
import com.godleon.auto.task.TaskRunnableBuy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
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
     * 机器人类，用于操作键盘和鼠标
     */
    private Robot robot;
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
     * 随机数对象
     */
    private Random random = new Random();

    /**
     * 查找元素前执行的方法集合，比如监控浏览器有没有出现滑动条等
     */
    private List<FindBeforeListener> fblList = new ArrayList<>();

    /**
     * 控制查找元素时，是否执行FblList里的方法
     */
    private boolean runFind = true;

    /**
     * 浏览器操作的默认超时时间
     */
    private int timeout = 10;
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

    /**
     * JSON字符串转对象的方法
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T json2Obj(String json, Class<T> clazz){
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("json字符串转对象异常，String： " + json + "。", e);
        }
    }

    public static int getTimeout(){
        return autoContainerMain.timeout;
    }

    /**
     * 获取机器人实例的方法
     * @return
     */
    public static Robot getRobot(){
        if(autoContainerMain.robot == null){
            synchronized(AutoContainerMain.class){
                if(autoContainerMain.robot == null){
                    try {
                        autoContainerMain.robot = new Robot();
                    } catch (AWTException e) {
                        throw new RuntimeException("创建机器人对象失败", e);
                    }
                }
            }
        }
        return autoContainerMain.robot;
    }

    /**
     * 生成随机整数的方法，区间为[start,end)
     * @param start
     * @param end
     * @return
     */
    public static int getRandomInt(int start, int end){
        if(start >= end) return end;
        return autoContainerMain.random.nextInt(end-start)+start;
    }

    public static void runFbl(){
        autoContainerMain.runFind = false;
        autoContainerMain.fblList.forEach(item -> {
            try {
                item.handle();
            } catch (Exception e) {
                return;
            }
        });
        autoContainerMain.runFind = true;
    }

    public static boolean getRunFind(){
        return autoContainerMain.runFind;
    }
}

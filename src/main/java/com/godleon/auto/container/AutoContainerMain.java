package com.godleon.auto.container;

import com.godleon.auto.context.AutomationContext;
import com.godleon.auto.entity.Task;
import com.godleon.auto.task.TaskRunnable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * Singleton mode, container class, store some public variables and public methods..
 * @author leon
 * @date 2021-06-21
 */
public class AutoContainerMain {
    private static final Logger LOG = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    private static final AutoContainerMain domain = new AutoContainerMain();
    /**
     * 存放配置文件中的所有配置
     */
    private Properties properties = new Properties();
    /**
     * 线程池
     */
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 5, 20, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));

    /**
     * 执行任务，收到服务器推送的消息后，调用该方法，参数为服务器推送的消息
     */
    public static void runTask(Task task){
        domain.executor.execute(new TaskRunnable(task));
    }

    /**
     * 获取配置文件中配置项的值，参数为配置项的key
     * @param key
     * @return
     */
    public static String getProperty(String key){
        return domain.properties.getProperty(key);
    }

}

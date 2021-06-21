package com.godleon.auto.container;

import com.godleon.auto.context.AutomationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClientClassLoader extends ClassLoader{

    private static final Logger LOG = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    private String path;
    private static Map<String, byte[]> classMap = new ConcurrentHashMap<>();//存放class文件的二进制数据
    private static Map<String, AutomationContext> classNameMap = new ConcurrentHashMap<>();

    public ClientClassLoader(String path) {
        this.path = path;
        preReadJarFile(path);
    }

    @Override
    public Class<?> findClass(String name) {
        try {
            byte[] result = getClass(name);
            if (result == null) {
                throw new ClassNotFoundException();
            } else {
                return defineClass(name, result, 0, result.length);
            }
        } catch (ClassNotFoundException e) {
            LOG.error("加载类异常：", e);
        }
        return null;
    }

    public Class<?> findClass(String name, byte[] data){
        Class<?> clazz = null;
        try{
            clazz = defineClass(name, data, 0, data.length);
        }catch(java.lang.LinkageError e){
            LOG.error("加载类异常：", e);
        }
        return clazz;
    }

    private byte[] getClass(String className) {
        if (classMap.containsKey(className)) {
            return classMap.get(className);
        } else {
            return null;
        }
    }

    private void preReadJarFile(String classPath) {
        File[] files = new File(classPath).listFiles();
        if (files != null) {
            for (File file : files) {
                scanJarFile(file);
            }
        }
    }

    private void scanJarFile(File file) {

        if (file.isFile() && file.getName().endsWith(".jar")) {
            try {
                readJAR(new JarFile(file));
            } catch (IOException e) {
                LOG.error("读jar文件异常：", e);
            }
        } else if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                scanJarFile(f);
            }
        }

    }

    private void readJAR(JarFile jar) throws IOException {
        Enumeration<JarEntry> en = jar.entries();
        while (en.hasMoreElements()) {
            JarEntry je = en.nextElement();
            String name = je.getName();
            if (!name.endsWith(".class")) {
                continue;
            }
            String className = name.replace("/", ".").replace(".class", "");
            if(classMap.containsKey(className)){
                continue;
            }
            InputStream input = null;
            ByteArrayOutputStream baos = null;
            try {
                input = jar.getInputStream(je);
                baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int bytesNumRead = -1;
                while ((bytesNumRead = input.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesNumRead);
                }
                Class<?> clazz = findClass(className, baos.toByteArray());
                if(AutomationContext.class.isAssignableFrom(clazz)){
                    AutomationContext task = (AutomationContext) clazz.newInstance();
                    classNameMap.put(className, task);
                }
            } catch (Exception e) {
                LOG.error("读取jar文件里的class文件出错：", e);
            } finally {
                if (baos != null) {
                    baos.close();
                }
                if (input != null) {
                    input.close();
                }
            }
        }
    }

    public AutomationContext getTask(String className){
        AutomationContext scc = classNameMap.get(className);
        if(scc == null){
            preReadJarFile(path);
            return classNameMap.get(className);
        }
        return scc;
    }
}

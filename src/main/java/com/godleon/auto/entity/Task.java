package com.godleon.auto.entity;


import java.util.Date;

/**
 * task entity
 * @author leon
 * @version 1.0.0
 */
public class Task {

    private int tid;
    private int tlid;
    private String taskName;
    private String deviceName;
    private String className;
    private int methodType;
    private String arguments;
    private int taskCount;
    private int successCount;
    private int errorCount;
    private Date createTime;
    private Date updateTime;

    public int getTid() {
        return tid;
    }
    public void setTid(int tid) {
        this.tid = tid;
    }
    public int getTlid() {
        return tlid;
    }
    public void setTlid(int tlid) {
        this.tlid = tlid;
    }
    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public String getDeviceName() {
        return deviceName;
    }
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public int getMethodType() {
        return methodType;
    }
    public void setMethodType(int methodType) {
        this.methodType = methodType;
    }
    public String getArguments() {
        return arguments;
    }
    public void setArguments(String arguments) {
        this.arguments = arguments;
    }
    public int getTaskCount() {
        return taskCount;
    }
    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }
    public int getSuccessCount() {
        return successCount;
    }
    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }
    public int getErrorCount() {
        return errorCount;
    }
    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    @Override
    public String toString() {
        return "Task [tid=" + tid + ", tlid=" + tlid + ", taskName=" + taskName + ", deviceName=" + deviceName
                + ", className=" + className + ", methodType=" + methodType + ", arguments=" + arguments
                + ", taskCount=" + taskCount + ", successCount=" + successCount + ", errorCount=" + errorCount
                + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
    }
}

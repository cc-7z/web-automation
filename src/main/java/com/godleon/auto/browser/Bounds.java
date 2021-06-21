package com.godleon.auto.browser;

public class Bounds {

    /**
     * 元素的左边距，相对电脑屏幕
     */
    private int l;
    /**
     * 元素的上边距，相对电脑屏幕
     */
    private int t;
    /**
     * 元素的宽
     */
    private int w;
    /**
     * 元素的高
     */
    private int h;

    public int getL() {
        return l;
    }
    public void setL(int l) {
        this.l = l;
    }
    public int getT() {
        return t;
    }
    public void setT(int t) {
        this.t = t;
    }
    public int getW() {
        return w;
    }
    public void setW(int w) {
        this.w = w;
    }
    public int getH() {
        return h;
    }
    public void setH(int h) {
        this.h = h;
    }
    @Override
    public String toString() {
        return "Bounds [l=" + l + ", t=" + t + ", w=" + w + ", h=" + h + "]";
    }
}

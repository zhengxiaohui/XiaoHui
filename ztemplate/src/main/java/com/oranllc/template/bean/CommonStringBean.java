package com.oranllc.template.bean;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/7/8
 * 描述：
 */
public class CommonStringBean {

    private int totle;
    private String data;
    private int codeState;
    private String message;

    public int getTotle() {
        return totle;
    }

    public void setTotle(int totle) {
        this.totle = totle;
    }

    public int getCodeState() {
        return codeState;
    }

    public void setCodeState(int codeState) {
        this.codeState = codeState;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

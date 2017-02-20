package com.oranllc.template.bean;

import java.util.List;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/7/8
 * 描述：
 */
public class CommonListStringBean {

    private int total;
    private List<String> data;
    private String message;
    private int codeState;
    public int getTotal() {
        return total;
    }
    public void setTotal(int  total) {
        this.total=total;
    }
    public List<String> getData() {
        return data;
    }
    public void setData(List<String>  data) {
        this.data=data;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String  message) {
        this.message=message;
    }
    public int getCodeState() {
        return codeState;
    }
    public void setCodeState(int  codeState) {
        this.codeState=codeState;
    }
}

package com.zbase.bean;

public class BaseJson {
    private boolean success;//是否成功
    private String message;//返回提示信息

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

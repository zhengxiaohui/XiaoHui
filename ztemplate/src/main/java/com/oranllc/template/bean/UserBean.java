package com.oranllc.template.bean;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/11/11
 * 描述：
 */
public class UserBean {
    private int total;
    private Data data;
    private String message;
    private int codeState;
    public int getTotal() {
        return total;
    }
    public void setTotal(int  total) {
        this.total=total;
    }
    public Data getData() {
        return data;
    }
    public void setData(Data  data) {
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
    public class Data {
        private String tel;
        private String token;
        private int isAlert;
        public String getTel() {
            return tel;
        }
        public void setTel(String  tel) {
            this.tel=tel;
        }
        public String getToken() {
            return token;
        }
        public void setToken(String  token) {
            this.token=token;
        }

        public int getIsAlert() {
            return isAlert;
        }

        public void setIsAlert(int isAlert) {
            this.isAlert = isAlert;
        }
    }
}



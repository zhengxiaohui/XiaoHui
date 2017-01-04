package com.zdemo.bean;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/5/26
 * 描述：
 */
public class IpArea {
    private Result result;
    private String reason;
    private String resultcode;
    public Result getResult() {
        return result;
    }
    public void setResult(Result  result) {
        this.result=result;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String  reason) {
        this.reason=reason;
    }
    public String getResultcode() {
        return resultcode;
    }
    public void setResultcode(String  resultcode) {
        this.resultcode=resultcode;
    }
    public class Result {
        private String area;
        private String location;
        public String getArea() {
            return area;
        }
        public void setArea(String  area) {
            this.area=area;
        }
        public String getLocation() {
            return location;
        }
        public void setLocation(String  location) {
            this.location=location;
        }
    }
}


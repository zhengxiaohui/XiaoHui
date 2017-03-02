package com.oranllc.template.bean;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/3/2
 * 描述：
 */
public class LoginBean {
    private int total;
    private Data data;
    private String message;
    private int codeState;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCodeState() {
        return codeState;
    }

    public void setCodeState(int codeState) {
        this.codeState = codeState;
    }

    public class Data {
        private String birthday;
        private String qq;
        private String wx;
        private String mail;
        private String imagePath;
        private String nickName;
        private String susId;
        private int sex;
        private String tell;
        private int type;
        private String token;
        private String homeTown;
        private int state;

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getWx() {
            return wx;
        }

        public void setWx(String wx) {
            this.wx = wx;
        }

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getSusId() {
            return susId;
        }

        public void setSusId(String susId) {
            this.susId = susId;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getTell() {
            return tell;
        }

        public void setTell(String tell) {
            this.tell = tell;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getHomeTown() {
            return homeTown;
        }

        public void setHomeTown(String homeTown) {
            this.homeTown = homeTown;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}


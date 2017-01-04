package com.oranllc.jpush;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/12/9
 * 描述：通用推送实体格式
 */
public class PushBean {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private int type;//业务逻辑类型，1,2,3等区分不同的业务操作
        private String entityId;//实体id，活动id，公告id等
        private String title;//标题，有时候能用到
        private String content;//内容，有时候能用到
        private String url;//链接，有时候跳转WebView用到

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getEntityId() {
            return entityId;
        }

        public void setEntityId(String entityId) {
            this.entityId = entityId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}

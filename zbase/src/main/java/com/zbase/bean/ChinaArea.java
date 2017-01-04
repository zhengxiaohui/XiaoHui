package com.zbase.bean;

import java.util.List;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/5/20
 * 描述：国家的省市县结构
 */
public class ChinaArea {

    private String id;

    private String parentId;

    private String name;

    private String type;

    private List<Province> child;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setChild(List<Province> child) {
        this.child = child;
    }

    public List<Province> getChild() {
        return this.child;
    }

    public class Province {
        private String id;

        private String parentId;

        private String name;

        private String type;

        private List<City> child;

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getParentId() {
            return this.parentId;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return this.type;
        }

        public void setChild(List<City> child) {
            this.child = child;
        }

        public List<City> getChild() {
            return this.child;
        }

        public class City {
            private String id;

            private String parentId;

            private String name;

            private String type;

            private List<County> child;

            public void setId(String id) {
                this.id = id;
            }

            public String getId() {
                return this.id;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getParentId() {
                return this.parentId;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getName() {
                return this.name;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getType() {
                return this.type;
            }

            public void setChild(List<County> child) {
                this.child = child;
            }

            public List<County> getChild() {
                return this.child;
            }

            public class County {
                private String id;

                private String parentId;

                private String name;

                private String type;

                public void setId(String id) {
                    this.id = id;
                }

                public String getId() {
                    return this.id;
                }

                public void setParentId(String parentId) {
                    this.parentId = parentId;
                }

                public String getParentId() {
                    return this.parentId;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getName() {
                    return this.name;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getType() {
                    return this.type;
                }

            }

        }

    }

}



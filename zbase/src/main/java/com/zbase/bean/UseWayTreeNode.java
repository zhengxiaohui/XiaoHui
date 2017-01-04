package com.zbase.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/4/14
 * 描述：使用方式的树形结构
 */
public class UseWayTreeNode extends TreeNode{

    private String id;
    private String name;
    private String is_useable;
    private String type;

    public UseWayTreeNode() {

    }

    public UseWayTreeNode(String id, String name, String is_useable, String type) {
        this.id = id;
        this.name = name;
        this.is_useable = is_useable;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIs_useable() {
        return is_useable;
    }

    public void setIs_useable(String is_useable) {
        this.is_useable = is_useable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}

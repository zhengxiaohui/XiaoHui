package com.zbase.bean;

/**
 * 创建人：LX
 * 创建日期：2016/7/11
 * 描述：拼音快速定位检索模型
 */
public class SortModel {

    protected String name;   //显示的数据
    protected String sortLetters;  //显示数据拼音的首字母

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
}

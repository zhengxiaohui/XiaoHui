package com.zdemo.bean;

import java.io.Serializable;
import java.util.List;

public class Person implements Serializable{
	public static final int SEX_MAN = 1;
    public static final int SEX_FEMALE = 2;
    public static final int SEX_UNKNOWN = 3;

    private String name;
    private int sex;
    private int age;
    private String email;
    private List<String> telephones;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getSex() {
        return sex;
    }
    public void setSex(int sex) {
        this.sex = sex;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public List<String> getTelephones() {
        return telephones;
    }
    public void setTelephones(List<String> telephones) {
        this.telephones = telephones;
    }
}

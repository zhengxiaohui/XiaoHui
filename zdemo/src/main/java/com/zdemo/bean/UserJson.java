package com.zdemo.bean;

import com.zbase.bean.BaseJson;

import java.util.List;


public class UserJson extends BaseJson {
	private List<User> userList;

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
}

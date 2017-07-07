package com.zdemo.bean;

import com.zbase.bean.BaseJson;
import com.zbase.interfaces.IPullToRefreshResponse;

import java.util.List;

public class ActivityListJson extends BaseJson implements IPullToRefreshResponse<Activity> {
	private List<Activity> activityList;

	public List<Activity> getActivityList() {
		return activityList;
	}

	public void setActivityList(List<Activity> activityList) {
		this.activityList = activityList;
	}

	@Override
	public List<Activity> getList() {
		return activityList;
	}

//	@Override
//	public String getLastId() {
//		return getList().get(getList().size()).getActivityId();
//	}

}
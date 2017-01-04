package com.zdemo.bean;

public class Activity {
	private String activityId;// 活动Id
	private String activityContent;// 活动内容
	private String activitySite;// 活动地点
	private String remark;// 备注
	private String activityCategoryName;// 活动类别名称
	private boolean isSameCity;// 是否同城
	private boolean isOnLine;// 是否线上
	private boolean isIndoor;// 是否室内
	private boolean isFreePay;// 是否免费
	private int totalPeopleCount;// 活动总人数
	private int joinPeopleCount;// 活动参加人数（不包括组织人）
	private String activityTime;// 活动时间
	private String username;// 活动组织者用户名
	private String createTime;// 创建时间

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getActivityContent() {
		return activityContent;
	}

	public void setActivityContent(String activityContent) {
		this.activityContent = activityContent;
	}

	public String getActivitySite() {
		return activitySite;
	}

	public void setActivitySite(String activitySite) {
		this.activitySite = activitySite;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getActivityCategoryName() {
		return activityCategoryName;
	}

	public void setActivityCategoryName(String activityCategoryName) {
		this.activityCategoryName = activityCategoryName;
	}

	public boolean isSameCity() {
		return isSameCity;
	}

	public void setIsSameCity(boolean isSameCity) {
		this.isSameCity = isSameCity;
	}

	public boolean isOnLine() {
		return isOnLine;
	}

	public void setIsOnLine(boolean isOnLine) {
		this.isOnLine = isOnLine;
	}

	public boolean isIndoor() {
		return isIndoor;
	}

	public void setIsIndoor(boolean isIndoor) {
		this.isIndoor = isIndoor;
	}

	public boolean isFreePay() {
		return isFreePay;
	}

	public void setIsFreePay(boolean isFreePay) {
		this.isFreePay = isFreePay;
	}

	public int getTotalPeopleCount() {
		return totalPeopleCount;
	}

	public void setTotalPeopleCount(int totalPeopleCount) {
		this.totalPeopleCount = totalPeopleCount;
	}

	public int getJoinPeopleCount() {
		return joinPeopleCount;
	}

	public void setJoinPeopleCount(int joinPeopleCount) {
		this.joinPeopleCount = joinPeopleCount;
	}

	public String getActivityTime() {
		return activityTime;
	}

	public void setActivityTime(String activityTime) {
		this.activityTime = activityTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
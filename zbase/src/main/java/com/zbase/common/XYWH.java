package com.zbase.common;

public class XYWH {
	
	public XYWH() {
		
	}
	
	/**
	 * 控件的位置和宽高
	 * @param x 横坐标
	 * @param y 纵坐标
	 * @param w 宽度
	 * @param h 高度
	 */
	public XYWH(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	private float x;
	private float y;
	private float w;
	private float h;
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getW() {
		return w;
	}
	public void setW(float w) {
		this.w = w;
	}
	public float getH() {
		return h;
	}
	public void setH(float h) {
		this.h = h;
	}
	
}

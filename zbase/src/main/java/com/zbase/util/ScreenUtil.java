package com.zbase.util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.zbase.common.XYWH;

/**
 * 跟屏幕有关的方法
 * @author z
 *
 */
public class ScreenUtil {
	public static int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}
	
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
	   final float scale = context.getResources().getDisplayMetrics().density;
	   return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
	   final float scale = context.getResources().getDisplayMetrics().density;
	   return (int) (pxValue / scale + 0.5f);
	}
	
	/**
	 * 判断某个点是否在某个View的内部，经常用于手指以移动时的边界检查
	 * @param x 点的横坐标
	 * @param y 点的纵坐标
	 * @param xywh View的坐标和宽高
	 * @return
	 */
	public static boolean isPointInsideView(int x,int y,XYWH xywh){
		if(xywh!=null){
			if(x>xywh.getX()&&x<(xywh.getX()+xywh.getW())&&y>xywh.getY()&&y<(xywh.getY()+xywh.getH())){
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取状态栏高度
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	/**
	 * 设置状态栏颜色
	 * @param activity
	 * @param resid 颜色id，如R.id.c3
	 */
	public void setStatusBar(Activity activity, int resid) {
		Window window = activity.getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		ViewGroup decorViewGroup = (ViewGroup) window.getDecorView();
		View statusBarView = new View(window.getContext());
		int statusBarHeight = getStatusBarHeight(window.getContext());
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, statusBarHeight);
		params.gravity = Gravity.TOP;
		statusBarView.setLayoutParams(params);
		statusBarView.setBackgroundResource(resid);
		decorViewGroup.addView(statusBarView);
	}
	
}

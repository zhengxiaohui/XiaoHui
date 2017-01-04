package com.zbase.manager;

import android.app.Activity;

import java.util.Stack;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 *
 * @author z
 */
public class ActivityStackManager {

    private static Stack<Activity> activityStack;
    private static ActivityStackManager instance;
    private OnAppDisplayListener onAppDisplayListener;
    public int activityStartedCount = 0;

    public int getActivityStartedCount() {
        return activityStartedCount;
    }

    public void setActivityStartedCount(int activityStartedCount) {
        this.activityStartedCount = activityStartedCount;
    }

    public OnAppDisplayListener getOnAppDisplayListener() {
        return onAppDisplayListener;
    }

    public void setOnAppDisplayListener(OnAppDisplayListener onAppDisplayListener) {
        this.onAppDisplayListener = onAppDisplayListener;
    }

    public interface OnAppDisplayListener {
        void onFront();//前台显示

        void onBack();//隐藏到后台
    }


    private ActivityStackManager() {
    }

    /**
     * 单一实例
     */
    public static ActivityStackManager getInstance() {
        if (instance == null) {
            instance = new ActivityStackManager();
        }
        return instance;
    }

    public int getSize() {
        return activityStack.size();
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        if (activityStack.isEmpty()) {
            return null;
        } else {
            Activity activity = activityStack.lastElement();
            return activity;
        }
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishCurrentActivity() {
        Activity activity = activityStack.lastElement();
        finishAssignActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishAssignActivity(Activity activity) {
        if (activity != null) {
            if (activityStack.contains(activity)) {
                activityStack.remove(activity);
            }
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivityByClass(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishAssignActivity(activity);
                break;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    //退出栈中指定Activity上面的Activity
    public void finishAboveActivity(Class cls) {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            finishAssignActivity(activity);
        }
    }

    //判断Activity是否存在
    public boolean isActivityExit(Class cls) {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (activityStack.get(i).getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 退出应用程序
     */
    public void exitApp() {
        try {
            finishAllActivity();
            android.os.Process.killProcess(android.os.Process.myPid());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package com.zbase.manager;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import static android.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/10/17
 * 描述：Fragment管理器，管理可以回退的类似Activity栈结构的多个Fragment
 */
public class FragmentStackManager {

    private FragmentManager fragmentManager;

    public FragmentStackManager(Activity activity) {
        fragmentManager = activity.getFragmentManager();
    }

    /**
     * 添加Fragment
     * @param containerViewId
     * @param fragment
     */
    public void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }

    /**
     * 弹出栈顶的Fragment
     */
    public void popFragment() {
        fragmentManager.popBackStack();
    }

    /**
     * 弹出指定名称的栈和其上的所有栈
     * @param name
     */
    public void popIncludeAboveFragment(String name) {
        fragmentManager.popBackStack(name, POP_BACK_STACK_INCLUSIVE);
    }

    /**
     * 栈中是否存在Fragment
     * @return
     */
    public boolean hasBackStackFragment() {
        int count = fragmentManager.getBackStackEntryCount();
        if (count == 0) {
            return false;
        }
        return true;
    }

}

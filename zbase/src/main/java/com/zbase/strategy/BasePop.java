package com.zbase.strategy;

import android.content.Context;

import com.zbase.util.PopUtil;

/**
 * Created by Administrator on 2015/2/10.
 * 默认弹出对话框样式，子类可以继承此类重新定义弹出方法，就是改变弹出的样式。
 * 预定于多个子类继承此类，形成策略模式，在MyApplication里面调用setBasePop(BasePop basePop)方法，
 * 传入子类，就会自动调用改子类的方法
 */
public class BasePop {
    /**
     * 子类覆盖此方法即可
     * @param context
     */
    public void showProgressDialog(Context context){
        PopUtil.showProgressDialog(context);
    };

    /**
     * 子类不必覆盖此方法
     */
    public void closeProgressDialog(){
        PopUtil.dismissProgressDialog();
    };
}

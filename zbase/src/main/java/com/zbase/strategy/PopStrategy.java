package com.zbase.strategy;

import android.content.Context;

/**
 * Created by Administrator on 2015/2/10.
 * 弹出各种对话框的代理类，通过此类来实例化具体的对话框类
 * 自动调用默认的显示样式，如果需要改变显示样式，需要在MyApplication里面调用setBasePop(BasePop basePop)这种类型的方法
 */
public class PopStrategy {

    private static BasePop basePop=new BasePop();

    public static void setBasePop(BasePop basePop){
        PopStrategy.basePop=basePop;
    }

    public static void showProgressDialog(Context context){
        if(basePop!=null){
            basePop.showProgressDialog(context);
        }
    }

    public static void closeProgressDialog(){
        if(basePop!=null){
            basePop.closeProgressDialog();
        }
    }


}

package com.zbase.manager;

import android.content.Context;

import com.zbase.common.ZSharedPreferences;
import com.zbase.listener.RecordChangeListener;
import com.zbase.util.ListUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/10/18 0018
 * 描述：记录列表管理器
 */
public class RecordManager {

    private Context context;
    private String keyString;
    private int maxSize;
    private RecordChangeListener recordChangeListener;

    public void setRecordChangeListener(RecordChangeListener recordChangeListener) {
        this.recordChangeListener = recordChangeListener;
    }

    public RecordManager(Context context, String keyString, int maxSize){
        this.context=context;
        this.keyString=keyString;
        this.maxSize=maxSize;
    }

    public void add(String content){
        List<String> list=new ArrayList<>();
        if (ZSharedPreferences.getInstance(context).getList(keyString)!=null) {
            list= ZSharedPreferences.getInstance(context).getList(keyString);
        }
        ListUtil.pushOutLast(maxSize,list,content);
        ZSharedPreferences.getInstance(context).putList(keyString,list);
        if (recordChangeListener!=null) {
            recordChangeListener.onRecordChange(list);
        }
    }

    public List<String> getList(){
        if (ZSharedPreferences.getInstance(context).getList(keyString)==null) {
            return new ArrayList<>();
        }
        return ZSharedPreferences.getInstance(context).getList(keyString);
    }

    public void clear(){
        List<String> list=new ArrayList<>();
        ZSharedPreferences.getInstance(context).putList(keyString,list);
        if (recordChangeListener!=null) {
            recordChangeListener.onRecordChange(list);
        }
    }
}

package com.zbase.listener;

import com.zbase.bean.AppInfo;

import java.util.List;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/11/15 0015
 * 描述：
 */
public interface OnObtainAppInfoListListener {
    void onObtainAppInfoList(List<AppInfo> list);
}

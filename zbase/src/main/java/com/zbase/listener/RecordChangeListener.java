package com.zbase.listener;

import java.util.List;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/10/18 0018
 * 描述：RecordManager记录变化后的回调接口
 */
public interface RecordChangeListener {
    void onRecordChange(List<String> list);
}

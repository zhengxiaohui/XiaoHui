package com.zbase.interfaces;

import java.util.List;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/7/19
 * 描述：
 */
public interface IPullToRefreshResponse<T> {
    List<T> getList();
}

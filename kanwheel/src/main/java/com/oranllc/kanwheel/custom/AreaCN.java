package com.oranllc.kanwheel.custom;

import java.util.ArrayList;

/**
 * @author Zz
 * @version v1.0.2
 * @Description: 中国地区信息
 * @date 2014-9-24 下午5:10:01
 * @Copyright: Copyright (c) 2014 Gude
 * 巧妙运用了递归，看来类定义也可以是递归的结构
 */
public class AreaCN {
    public String name;
    public ArrayList<AreaCN> sub;

    public boolean isEmpty() {
        return null == sub || sub.size() == 0;
    }
}
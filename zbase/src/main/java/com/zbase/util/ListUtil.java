package com.zbase.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/3/1
 * 描述：List工具类，常用的一些list复杂操作
 */
public class ListUtil {

    /**
     * 根据每页大小拆分List
     *
     * @param list        需要拆分的list
     * @param subListSize 每页大小
     * @param <T>         泛型
     * @return
     */
    public static <T> List<List<T>> splitList(List<T> list, int subListSize) {
        List<List<T>> listList = new ArrayList<>();
        int subListCount = list.size() / subListSize + 1;
        for (int i = 0; i < subListCount; i++) {
            if (i == subListCount - 1) {//如果是最后一页，则截取余数位置
                int remainder = list.size() % subListSize;
                listList.add(list.subList(i * subListSize, i * subListSize + remainder));
            } else {
                listList.add(list.subList(i * subListSize, (i + 1) * subListSize));
            }
        }
        return listList;
    }

    /**
     * 将list的第一个元素顶出，要进来的元素排最后，其实就是队列先进先出的数据结构，要设置一个最大个数
     * @param maxSize list的最大长度，就是最大个数
     * @param list
     * @param t
     * @param <T>
     */
    public static <T> void pushOutFirst(int maxSize, List<T> list, T t) {
        if (list.contains(t)) {
            return;
        }
        if (list.size() == maxSize) {
            list.remove(0);
        }
        list.add(t);
    }

}

package com.zbase.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.zbase.bean.SortModel;
import com.zbase.other.CharacterParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/7/12
 * 描述：分组快速定位控件适配器
 */

public abstract class BaseGroupQuickLocationAdapter<T extends SortModel> extends ZBaseAdapterAdvance<T> implements SectionIndexer {

    public BaseGroupQuickLocationAdapter(Context context) {
        super(context);
    }

    public abstract class MyViewHolder extends ViewHolder {
        private LinearLayout firstLetterLinearLayout;
        private TextView firstLetterTextView;
        private TextView contentTextView;

        @Override
        protected void findView(View view) {
            firstLetterLinearLayout = (LinearLayout) view.findViewById(findFirstLetterLinearLayoutId());
            firstLetterTextView = (TextView) view.findViewById(findFirstLetterTextViewId());
            contentTextView = (TextView) view.findViewById(findContentTextViewId());
        }

        @Override
        protected void setListener(int position) {

        }

        @Override
        protected void initValue(int position, T sortModel) {
            //根据position获取分类的首字母的Char ascii值
            int section = getSectionForPosition(position);
            //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
            if (position == getPositionForSection(section)) {
                firstLetterLinearLayout.setVisibility(View.VISIBLE);
                firstLetterTextView.setText(sortModel.getSortLetters());
            } else {
                firstLetterLinearLayout.setVisibility(View.GONE);
            }
            contentTextView.setText(sortModel.getName());
        }
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    @Override
    public int getSectionForPosition(int position) {
        char charAt = list.get(position).getSortLetters().charAt(0);
        return charAt;
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    @Override
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    public abstract int findFirstLetterLinearLayoutId();

    public abstract int findFirstLetterTextViewId();

    public abstract int findContentTextViewId();


    /**
     * 传入的list是后台已经排序好的，并且返回了首字母的
     * 这个方法在后台一次性返回大量的数据的情况下会快很多
     * 本来就是父类的方法，这边我只是拿出来对比看
     *
     * @param setList
     */
    @Override
    public void setList(List<T> setList) {
        super.setList(setList);
    }

    /**
     * 传入的list是无序的，并且没有返回首字母
     * 这个方法最普遍适用，但是如果一次性返回的数据特别多的时候，本地的页面会卡住1分钟左右，太慢，容易会卡死
     *
     * @param data
     */
    public void setNoSortList(List<T> data) {
        List<T> list = filledData(data);
        setList(list);
    }

    /**
     * 直接传字符串数组
     * @param names
     */
    public void setNoSortArray(String[] names) {
        List<T> tList = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            SortModel sortModel = new SortModel();
            T t = (T) sortModel;
            t.setName(names[i]);
            tList.add(t);
        }
        List<T> list = filledData(tList);
        setList(list);
    }

    private List<T> filledData(List<T> list) {
        CharacterParser characterParser = CharacterParser.getInstance();
        for (int i = 0; i < list.size(); i++) {
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(list.get(i).getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();
            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                list.get(i).setSortLetters(sortString.toUpperCase());
            } else {
                list.get(i).setSortLetters("#");
            }
        }
        PinyinComparator pinyinComparator = new PinyinComparator();  //实例化汉字转拼音类
        Collections.sort(list, pinyinComparator);
        return list;
    }

    private class PinyinComparator implements Comparator<SortModel> {
        public int compare(SortModel o1, SortModel o2) {
            if (o1.getSortLetters().equals("@")
                    || o2.getSortLetters().equals("#")) {
                return -1;
            } else if (o1.getSortLetters().equals("#")
                    || o2.getSortLetters().equals("@")) {
                return 1;
            } else {
                return o1.getSortLetters().compareTo(o2.getSortLetters());
            }
        }
    }
}

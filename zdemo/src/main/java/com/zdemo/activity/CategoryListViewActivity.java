package com.zdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import com.zdemo.R;
import com.zdemo.adapter.CategoryAdapter;

/**
 * 创建人：郑晓辉
 * 创建日期：2015/12/28
 * 描述：带分组的ListView，组名可以是英文字母，也可以是任何文字
 */
public class CategoryListViewActivity extends Activity {

    private String[] string = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_category_listview);

        ListView listview = (ListView) findViewById(R.id.listview);
        CategoryAdapter adapter = new CategoryAdapter(this);

        int size = string.length;
        for (int i = 0; i < size; i++) {
            adapter.addSeparatorItem(string[i]);
            for (int k = 0; k < 5; k++) {
                adapter.addItem("item " + k);
            }
        }
        listview.setAdapter(adapter);
    }
}

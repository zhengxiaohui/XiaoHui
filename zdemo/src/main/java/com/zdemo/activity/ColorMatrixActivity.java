package com.zdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zbase.view.ColorMatrixView;
import com.zdemo.R;

/**
 * 创建人：郑晓辉
 * 创建日期：2015/11/25
 * 描述：
 */
public class ColorMatrixActivity extends Activity implements View.OnClickListener {

    private float[] colorArrayDefault = {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};
    private Button button = null;
    private Button button2 = null;
    private ColorMatrixView myColorMatrixView = null;
    private EditText[] editTextArray = null;
    private float colorArray[] = null;
    private int[] EditTextID = {R.id.Edit1, R.id.Edit2, R.id.Edit3, R.id.Edit4, R.id.Edit5,
            R.id.Edit6, R.id.Edit7, R.id.Edit8, R.id.Edit9, R.id.Edit10,
            R.id.Edit11, R.id.Edit12, R.id.Edit13, R.id.Edit14, R.id.Edit15,
            R.id.Edit16, R.id.Edit17, R.id.Edit18, R.id.Edit19, R.id.Edit20};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_matrix);
        button = (Button) findViewById(R.id.Button);
        button.setOnClickListener(this);
        button2 = (Button) findViewById(R.id.Button2);
        button2.setOnClickListener(this);
        editTextArray = new EditText[20];
        colorArray = new float[20];
        for (int i = 0; i < 20; i++) {
            editTextArray[i] = (EditText) findViewById(EditTextID[i]);
        }
        myColorMatrixView = (ColorMatrixView) findViewById(R.id.myColorMatrixView);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Button:
                for (int i = 0; i < 20; i++) {
                    String value = editTextArray[i].getText().toString().trim();
                    if (TextUtils.isEmpty(value)) {
                        value = "0";
                    }
                    colorArray[i] = Float.valueOf(value);
                }
                myColorMatrixView.setColorArray(colorArray);
                break;
            case R.id.Button2:
                for (int i = 0; i < editTextArray.length; i++) {
                    editTextArray[i].setText(String.valueOf((int)colorArrayDefault[i]));
                }
                myColorMatrixView.setColorArray(colorArrayDefault);
                break;
        }
    }

}

package com.oranllc.kanwheel.custom;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oranllc.kanwheel.R;
import com.oranllc.kanwheel.adapters.AbstractWheelAdapter;
import com.oranllc.kanwheel.listener.OnWheelScrollListener;
import com.oranllc.kanwheel.WheelView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.SoftReference;
import java.util.ArrayList;


/**
 *  中国地区选择器，弹出对话框，包含中国的省，市，县三级选择。
 *  用法：  tvContent = (TextView) findViewById(R.id.tvContent);
 mPicker=new ChineseAreaPicker(this,tvContent);
 findViewById(R.id.linearLayout1).setHeadClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
mPicker.onClick();
}
});
 */
public class ChineseAreaPicker extends Dialog {

    private SoftReference<ArrayList<AreaCN>> mAreaCN;
    private ArrayList<AreaCN> mAreaCNs;
    private int mValue1 = -1, mValue2 = -1, mValue3 = -1;
    private WheelView mWheelView1;
    private WheelView mWheelView2;
    private WheelView mWheelView3;
    private Context context;
    private TextView tvContent;//最后显示的文本控件

    public ChineseAreaPicker(Context context,TextView tvContent) {
        super(context);
        this.context=context;
        this.tvContent=tvContent;
        setContentView(R.layout.wheel_dialog_address_picker);
        init();
        initView();
        initPicker();
    }

    private void init(){
        if (isHavaAreaCNs()) {//有数据
            //无内容则给默认值
            if (tvContent.getText().length() == 0) {
                flushProvince(0, 0, 0);
            }
        } else {
            loadSelectArea();//加载数据
        }
    }

    public void onClick(){
        if (isHavaAreaCNs()) {//如果已经有地区数据
            openSelectArea();
        } else {
            loadSelectArea();//加载数据
        }
    }

    private boolean isHavaAreaCNs() {
        return null != mAreaCNs && mAreaCNs.size() > 0;
    }

    private void openSelectArea() {
        setCurrentItem(mValue1, mValue2, mValue3);
        show();
    }

    private void loadSelectArea() {
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                mAreaCNs = getAreaCN(context);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                //无内容默认第一项
                if (tvContent.getText().length() == 0) {
                    flushProvince(0, 0, 0);
                }
            }
        };

        task.execute();
    }

    private void flushProvince(int province, int city, int county) {
        StringBuilder sBuilder = new StringBuilder();
        if (null != mAreaCNs && mAreaCNs.size() > province) {
            AreaCN area = mAreaCNs.get(province);
            sBuilder.append(area.name).append(' ');
            if (null != area.sub && area.sub.size() > city) {
                area = area.sub.get(city);
                sBuilder.append(area.name).append(' ');
                if (null != area.sub && area.sub.size() > county) {
                    sBuilder.append(area.sub.get(county).name);
                }
            }
        }
        tvContent.setText(sBuilder.toString());
    }

    private void initView() {
        mWheelView1 = (WheelView) findViewById(R.id.wheelView1);
        mWheelView2 = (WheelView) findViewById(R.id.wheelView2);
        mWheelView3 = (WheelView) findViewById(R.id.wheelView3);

        mWheelView1.addScrollingListener(onWheelScrollLis);
        mWheelView2.addScrollingListener(onWheelScrollLis);
        mWheelView3.addScrollingListener(onWheelScrollLis);

        mWheelView1.setViewAdapter(new AreaAdapter());
        mWheelView2.setViewAdapter(new AreaAdapter());
        mWheelView3.setViewAdapter(new AreaAdapter());

        //弹出滚轮框的大小，如果不设置，宽度将不是充满屏幕的，就是布局中没有效果的时候要在代码中设置
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getScreenWidth(context), dip2px(context, 200));
        LinearLayout view = (LinearLayout) mWheelView1.getParent();
        view.setLayoutParams(params);

        findViewById(R.id.tvOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flushProvince(mWheelView1.getCurrentItem(), mWheelView2.getCurrentItem(), mWheelView3.getCurrentItem());
                dismiss();
            }
        });

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                ((AreaAdapter) mWheelView1.getViewAdapter()).setData(null);
                ((AreaAdapter) mWheelView2.getViewAdapter()).setData(null);
                ((AreaAdapter) mWheelView3.getViewAdapter()).setData(null);
            }
        });
    }

    public int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //初始化数据内容
    private void initPicker() {
        if (null == mAreaCNs || mAreaCNs.size() == 0) {
            return;
        }
        resetPicker1(0);
        resetPicker2(0);
        resetPicker3(0);
    }

    private void setCurrentItem(int val1, int val2, int val3) {
        resetPicker1(val1);
        resetPicker2(val2);
        resetPicker3(val3);
    }

    //重置一级
    private void resetPicker1(int current) {
        if (null == mWheelView1 || null == mAreaCNs) {
            return;
        }
        flushAdapterData((AreaAdapter) mWheelView1.getViewAdapter(), mAreaCNs);
        mWheelView1.setCurrentItem(current);
    }

    //重置二级
    private void resetPicker2(int current) {
        if (null == mWheelView2 || null == mAreaCNs) {
            return;
        }
        flushAdapterData((AreaAdapter) mWheelView2.getViewAdapter(), mAreaCNs.get(mWheelView1.getCurrentItem()).sub);
        mWheelView2.setCurrentItem(current);
    }

    //重置三级
    private void resetPicker3(int current) {
        if (null == mWheelView3 || null == mAreaCNs) {
            return;
        }
        flushAdapterData((AreaAdapter) mWheelView3.getViewAdapter(), mAreaCNs.get(mWheelView1.getCurrentItem()).sub.get(mWheelView2.getCurrentItem()).sub);
        mWheelView3.setCurrentItem(current);
    }

    //更新Adapter
    private void flushAdapterData(AreaAdapter adapter, ArrayList<AreaCN> datas) {
        if (null == datas || datas.size() == 0) {
            adapter.setData(null);
            return;
        }
        int size = datas.size();
        String[] sNp = new String[size];
        for (int i = 0; i < size; i++) {
            sNp[i] = datas.get(i).name;
        }
        adapter.setData(sNp);
    }

    public interface OnClickPickerListener {
        public void onClickPickerChange(int npValue1, int npValue2, int npValue3);
    }

    private class AreaAdapter extends AbstractWheelAdapter {
        private String[] datas;

        public void setData(String[] datas) {
            this.datas = datas;
            notifyDataChangedEvent();
        }

        @Override
        public int getItemsCount() {
            return null == datas ? 0 : datas.length;
        }

        @Override
        public View getItem(int index, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.wheel_item_dialog_address_picker_text, null);
                viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tvName.setText(datas[index]);
            return convertView;
        }

        class ViewHolder {
            public TextView tvName;
        }
    }

    private OnWheelScrollListener onWheelScrollLis = new OnWheelScrollListener() {
        //省市的oldVal
        int[] oldVal = new int[2];

        @Override
        public void onScrollingStarted(WheelView wheel) {
            if (wheel.getId() == R.id.wheelView1) {
                oldVal[0] = wheel.getCurrentItem();
            } else if (wheel.getId() == R.id.wheelView2) {
                oldVal[1] = wheel.getCurrentItem();
            }
        }

        @Override
        public void onScrollingFinished(WheelView wheel) {
            if (wheel.getId() == R.id.wheelView1 && oldVal[0] != wheel.getCurrentItem()) {
                resetPicker2(0);
                resetPicker3(0);
            } else if (wheel.getId() == R.id.wheelView2 && oldVal[1] != wheel.getCurrentItem()) {
                resetPicker3(0);
            }
        }
    };

    //中国地区信息列表
    public ArrayList<AreaCN> getAreaCN(Context context) {
        if (null != mAreaCN && null != mAreaCN.get() && mAreaCN.get().size() > 0) {
            return mAreaCN.get();
        }
        try {
            ArrayList<AreaCN> roots = new ArrayList<AreaCN>();
            String jsonString = readAssertResource(context, "data/areacn");
            if (null != jsonString) {
                JSONObject jsonObject;
                JSONArray jArrayI = new JSONArray(jsonString);
                JSONArray jArrayJ;
                JSONArray jArrayK;
                AreaCN areaCNI;
                AreaCN areaCNJ;
                AreaCN areaCNK;
                int lengthI = jArrayI.length();
                int lengthJ;
                int lengthK;
                for (int i = 0; i < lengthI; i++) {
                    jsonObject = jArrayI.optJSONObject(i);
                    if (null != jsonObject) {
                        areaCNI = new AreaCN();
                        areaCNI.name = jsonObject.optString("name");
                        jArrayJ = jsonObject.optJSONArray("sub");
                        if (null != jArrayJ) {
                            lengthJ = jArrayJ.length();
                            if (lengthJ > 0) {
                                areaCNI.sub = new ArrayList<>(lengthJ);
                            }
                            for (int j = 0; j < lengthJ; j++) {
                                jsonObject = jArrayJ.optJSONObject(j);
                                areaCNJ = new AreaCN();
                                areaCNJ.name = jsonObject.optString("name");
                                jArrayK = jsonObject.optJSONArray("sub");
                                if (null != jArrayK) {
                                    lengthK = jArrayK.length();
                                    if (lengthK > 0) {
                                        areaCNJ.sub = new ArrayList<>(lengthK);
                                    }
                                    for (int k = 0; k < lengthK; k++) {
                                        jsonObject = jArrayK.optJSONObject(k);
                                        areaCNK = new AreaCN();
                                        areaCNK.name = jsonObject.optString("name");
                                        areaCNJ.sub.add(areaCNK);
                                    }
                                }
                                areaCNI.sub.add(areaCNJ);
                            }
                        }
                        roots.add(areaCNI);
                    }
                }
                mAreaCN = new SoftReference<>(roots);
                return roots;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取Assert下面的文件
     *
     * @param context
     * @param strAssertFileName 文件全路径名，如"mytxt.txt"或"txt/mytxt.txt"
     * @return
     */
    private String readAssertResource(Context context, String strAssertFileName) {
        AssetManager assetManager = context.getAssets();
        String strResponse = "";
        try {
            InputStream inputStream = assetManager.open(strAssertFileName);
            BufferedReader bufferedReader = null;
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } catch (IOException e) {
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                    }
                }
            }
            strResponse = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strResponse;
    }

}

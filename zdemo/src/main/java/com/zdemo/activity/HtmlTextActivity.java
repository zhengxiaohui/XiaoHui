package com.zdemo.activity;

import java.net.URL;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.widget.TextView;

import com.zdemo.R;


public class HtmlTextActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.html_text);

        TextView tv=(TextView)findViewById(R.id.txt_html);
        String html="<html><head><title>TextView使用HTML</title></head><body><p><strong>强调</strong></p><p><em>斜体</em></p>"
        		+"<p><a href=\"http://www.dreamdu.com/xhtml/\">超链接HTML入门</a>学习HTML!</p><p><font color=\"#aabb00\">颜色1"
        		+"</p><p><font color=\"#00bbaa\">颜色2</p><h1>标题1</h1><h3>标题2</h3><h6>标题3</h6><p>大于>小于<</p><p>" +
        		"下面是网络图片</p><img src=\"http://avatar.csdn.net/0/3/8/2_zhang957411207.jpg\"/></body></html>";
        
        ImageGetter imgGetter = new Html.ImageGetter() {
            public Drawable getDrawable(String source) {
                  Drawable drawable = null;
                  URL url;  
                  try {   
                      url = new URL(source);  
                      drawable = Drawable.createFromStream(url.openStream(), "");  //获取网路图片
                  } catch (Exception e) {  
                      return null;  
                  }  
                  drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
                                .getIntrinsicHeight());
                  return drawable; 
            }
    };
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());//滚动
        tv.setText(Html.fromHtml(html,imgGetter,null));    
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

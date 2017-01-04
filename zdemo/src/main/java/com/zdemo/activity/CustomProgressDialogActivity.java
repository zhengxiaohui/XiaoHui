package com.zdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.zbase.util.PopUtil;
import com.zdemo.R;

public class CustomProgressDialogActivity extends Activity{
	
	private Button button1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_custom_progress_dialog);
		button1=(Button)findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PopUtil.showLoadingDialog(CustomProgressDialogActivity.this, "疼你故疼你故");
			}
		});
	}
}

package com.example.activity;

import com.example.share4_15.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MessageSettActivity extends Activity {

	private ImageButton back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.message_set_activity);
		back = (ImageButton) findViewById(R.id.message_setting_back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	public void imgOnClick(View v) {
		int tag = Integer.parseInt(v.getTag().toString());
		if (tag == 0) {
			v.setBackgroundResource(R.drawable.xiaoxi_pressed);
			v.setTag("1");
		} else {
			v.setBackgroundResource(R.drawable.xiaoxi_normal);
			v.setTag("0");
		}
	}
}

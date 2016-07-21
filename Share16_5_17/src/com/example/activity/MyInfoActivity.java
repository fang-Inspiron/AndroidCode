package com.example.activity;

import com.example.share4_15.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MyInfoActivity extends Activity {

	RelativeLayout sixinlayout;
	RelativeLayout newguanzhulayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.my_info_activity);
		ImageButton back = (ImageButton) findViewById(R.id.myinfo_back);
		sixinlayout = (RelativeLayout) findViewById(R.id.my_sixin);
		newguanzhulayout = (RelativeLayout) findViewById(R.id.my_newguanzhu);
		
		newguanzhulayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),NewGuanZhuActivity.class);
				startActivity(intent);
			}
		});
		
		sixinlayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),SiXinActivity.class);
				startActivity(intent);
			}
		});
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}
}

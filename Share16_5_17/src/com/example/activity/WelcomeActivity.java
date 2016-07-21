package com.example.activity;

import cn.bmob.v3.Bmob;

import com.example.share4_15.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

public class WelcomeActivity extends Activity{

	ImageView im ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		
		//≥ı ºªØBmob SDK
		Bmob.initialize(WelcomeActivity.this, "29ca8d195de08c21b758a1545ffb84cc");
		
		new Thread(){
			@Override
			public void run() {
				try {
					Thread.sleep(1500);
					Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
					startActivity(intent);
					finish();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
		
	}
}

package com.example.share;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class MyInforNewConcern extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myinfor_newconcern);
	}
	
	public void MyButtonOnClick(View view) {
		if (Integer.parseInt(view.getTag().toString()) == 0) {
			view.setBackgroundResource(R.drawable.guanzhu_pressed);
			view.setTag(1);
		} else {
			view.setBackgroundResource(R.drawable.guanzhu_normal);
			view.setTag(0);
		}
	}
}

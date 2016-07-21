package com.example.share;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class MySetMessageSet extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myset_messageset);
	}

	public void MyCheckedOnClick(View view) {

		if (Integer.parseInt(view.getTag().toString()) == 0) {
			view.setBackgroundResource(R.drawable.xiaoxi_pressed);
			view.setTag(1);
		} else {
			view.setBackgroundResource(R.drawable.xiaoxi_normal);
			view.setTag(0);
		}

	}
}

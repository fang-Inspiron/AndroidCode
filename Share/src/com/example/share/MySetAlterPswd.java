package com.example.share;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class MySetAlterPswd extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myset_alterpswd);
	}
}

package com.qlf.plants.activity;

import com.qlf.plants.MainActivity;
import com.qlf.plants.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class ChooseActivity extends Activity implements OnClickListener {

	Button login;
	Button register;
	Button visitor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.choose_main);
		initView();
	}

	public void initView() {
		login = (Button) findViewById(R.id.login_login);
		register = (Button) findViewById(R.id.login_register);
		visitor = (Button) findViewById(R.id.login_visitor);

		login.setOnClickListener(this);
		register.setOnClickListener(this);
		visitor.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.login_login:
			intent.setClass(getApplicationContext(), LoginActivity.class);
			startActivity(intent);
			break;
		case R.id.login_register:
			intent.setClass(getApplicationContext(), RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.login_visitor:
			intent.setClass(getApplicationContext(), MainActivity.class);
			startActivity(intent);
			break;
		}
		finish();
	}
}

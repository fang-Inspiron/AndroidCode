package com.example.share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Login extends Activity {
	
	private Button button_login;
	private Button button_register;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		button_login = (Button) findViewById(R.id.button_login);
		button_register = (Button) findViewById(R.id.button_register);
		
		button_login.setOnClickListener(new LoginClickListener());
		button_register.setOnClickListener(new RegisterClickListener());
	}
	
	class LoginClickListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(Login.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
		
	}
	
	class RegisterClickListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(Login.this, Register.class);
			startActivity(intent);
		}
		
	}
}

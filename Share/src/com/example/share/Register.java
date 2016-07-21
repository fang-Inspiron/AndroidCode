package com.example.share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Register extends Activity{
	private Button button_sureRegister;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		button_sureRegister = (Button) findViewById(R.id.button_sureRagister);
		button_sureRegister.setOnClickListener(new RegisterClickListener());
	}
	
	class RegisterClickListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(Register.this, Login.class);
			startActivity(intent);
			finish();
		}
		
	}
}

package com.fastfood.activity;

import java.util.HashMap;
import java.util.Map;

import com.data.UserData;
import com.fastfood.thread.LoginThread;

import com.fastfood.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private Button button_login;
	private TextView tv_freeRegister;
	private TextView tv_findPassword;
	private EditText editText_phone;
	private EditText editText_password;
	Map<String, Object> data;
	private ProgressDialog progressDialog;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case LoginThread.NETWORK_ERROR:
				// progressDialog.dismiss();
				Toast.makeText(LoginActivity.this, "网络连接错误", Toast.LENGTH_SHORT)
						.show();
				break;
			case LoginThread.SUCCESS:
				data = (Map<String, Object>) msg.obj;
				if (data.containsKey("checkStr") && data.containsKey("userId")) {
					System.out.println(data);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("check", data.get("checkStr"));
					map.put("userId", data.get("userId"));
					UserData.saveUserInfo(LoginActivity.this, map);
					progressDialog.dismiss();
					Toast.makeText(getApplicationContext(), "登陆成功！",
							Toast.LENGTH_LONG).show();
					Intent intent = new Intent(LoginActivity.this,	MainActivity.class);
					intent.putExtra("success", true);
					startActivity(intent);
				}
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		findID();
	}

	public void findID() {
		button_login = (Button) findViewById(R.id.button_login);
		tv_freeRegister = (TextView) findViewById(R.id.tv_freeRegister);
		tv_findPassword = (TextView) findViewById(R.id.tv_findPassword);
		editText_phone = (EditText) findViewById(R.id.editText_phone);
		editText_password = (EditText) findViewById(R.id.editText_password);
	}

	public void MyLoginLayoutOnClick(View view) {
		Intent intent = new Intent();
		switch (view.getId()) {
		case R.id.tv_freeRegister:
			intent.setClass(LoginActivity.this, RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.tv_findPassword:
//			intent.setClass(LoginActivity.this, RegisterActivity.class);
//			startActivity(intent);
			break;
		case R.id.button_login:
			new LoginThread(handler, editText_phone.getText().toString(),
					editText_password.getText().toString()).start();
			progressDialog = new ProgressDialog(LoginActivity.this);
			progressDialog.setTitle("提示");
			progressDialog.setMessage("登陆中......");
			progressDialog.show();
			break;
		default:
			break;
		}
	}
}

package com.qlf.plants.activity;

import java.util.Map;

import com.qlf.plants.MainActivity;
import com.qlf.plants.R;
import com.qlf.plants.thread.LoginThread;
import com.qlf.plants.userdata.UserData;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {

	EditText username, password;
	Button login;
	private ProgressDialog progressDialog;
	private Map<String, Object> data;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			progressDialog.dismiss();
			if (msg.what == LoginThread.SUCCESS) {
				data = (Map<String, Object>) msg.obj;
				if (Integer.parseInt(data.get("code").toString()) == 1) {
					Map<String, Object> map = (Map<String, Object>) data
							.get("data");
					Toast.makeText(getApplicationContext(), "登陆成功",
							Toast.LENGTH_SHORT).show();
					UserData userData = new UserData(getApplicationContext());
					userData.saveUserInfo(map);

					Intent intent = new Intent(getApplicationContext(),
							MainActivity.class);
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(getApplicationContext(),
							data.get("data").toString(), Toast.LENGTH_LONG)
							.show();
					username.setText("");
					password.setText("");
				}
			} else if (msg.what == 0x123) {
				Toast.makeText(getApplicationContext(), "请检查网络",
						Toast.LENGTH_LONG).show();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_main);
		UserData userData = new UserData(getApplicationContext());

		initView();
		if (userData.getUserInfo() != null) {
			username.setText(userData.getUserInfo().get("phone").toString());
		}
	}

	private void initView() {
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		login = (Button) findViewById(R.id.login);

		username.setOnClickListener(this);
		password.setOnClickListener(this);
		login.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.username:
			break;
		case R.id.password:
			break;
		case R.id.login:
			intent.setClass(getApplicationContext(), AskPosActivity.class);

			String phone = username.getText().toString();
			String pass = password.getText().toString();
			if (pass.equals("")) {
				Toast.makeText(getApplicationContext(), "请输入密码",
						Toast.LENGTH_SHORT).show();
			} else if (phone.equals("")) {
				Toast.makeText(getApplicationContext(), "请输入手机号",
						Toast.LENGTH_SHORT).show();
			} else {
				
				new LoginThread(handler, pass, phone).start();
				progressDialog = new ProgressDialog(LoginActivity.this);
				progressDialog.setTitle("提示");
				progressDialog.setMessage("加载中...");
				progressDialog.show();
			}
			break;
		}
	}
}

package com.example.activity;

import java.util.HashMap;
import java.util.Map;

import cn.bmob.v3.listener.SaveListener;

import com.bean.User;
import com.data.UserData;
import com.example.share.MainActivity;
import com.example.share4_15.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	public static String userName = "";
	public static String password = "";
	private Button bt_login;
	private Button bt_regist;
	private EditText et_username;
	private EditText et_password;
	private ProgressDialog progressDialog;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 100) {
				progressDialog.dismiss();
				//将用户数据保存在本地
				Map<String, String> map = new HashMap<String, String>();
				map.put("username", userName);
				map.put("password", password);
				UserData.saveUserInfo(getApplicationContext(), map);
				// 登录成功跳转首页
				Intent intent = new Intent(LoginActivity.this,	MainActivity.class);
				startActivity(intent);
				finish();
			} else if (msg.what == 101) {
				progressDialog.dismiss();
			}  
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_activity);
		GradientDrawable drawable = new GradientDrawable();
		drawable.setShape(GradientDrawable.RECTANGLE);
		drawable.setStroke(1, Color.WHITE);
		bt_login = (Button) findViewById(R.id.login_bt_denglu);
		bt_regist = (Button) findViewById(R.id.login_bt_zhuce);
		et_username = (EditText) findViewById(R.id.login_username);
		et_password = (EditText) findViewById(R.id.login_pass);
		
		if (UserData.getUserInfo(getApplicationContext()) != null) {
			String username = UserData.getUserInfo(getApplicationContext()).get("username").toString();
			String password = UserData.getUserInfo(getApplicationContext()).get("password").toString();
			et_username.setText(username);
			et_password.setText(password);
		}
		
		
		bt_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (et_username.getText().toString().equals("")) {
					Toast.makeText(LoginActivity.this, "用户名不能为空",
							Toast.LENGTH_SHORT).show();
				} else if (et_password.getText().toString().equals("")) {
					Toast.makeText(LoginActivity.this, "密码不能为空",
							Toast.LENGTH_SHORT).show();
				} else if (!et_username.getText().toString().equals("")
						&& !et_password.getText().toString().equals("")) {
					progressDialog = new ProgressDialog(LoginActivity.this);
					progressDialog.setTitle("提示");
					progressDialog.setMessage("登录中...");
					progressDialog.show();

					userName = et_username.getText().toString();
					password = et_password.getText().toString();

					User user = new User();
					user.setUsername(userName);
					user.setPassword(password);
					user.login(LoginActivity.this, new SaveListener() {

						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub
							handler.sendEmptyMessage(100);
							Toast.makeText(LoginActivity.this, "登录成功",
									Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onFailure(int arg0, String arg1) {
							// TODO Auto-generated method stub
							handler.sendEmptyMessage(101);
							Toast.makeText(LoginActivity.this, "登录失败",
									Toast.LENGTH_SHORT).show();
						}
					});

					new Thread() {
						@Override
						public void run() {
							try {
								Thread.sleep(1500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}.start();
				} else {
					Toast.makeText(LoginActivity.this, "请输入信息",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		bt_regist.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 跳转注册页面
				Intent intent = new Intent(LoginActivity.this,
						RegistActivity.class);
				startActivity(intent);
			}
		});
	}
}

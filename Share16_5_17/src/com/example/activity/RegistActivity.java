package com.example.activity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

import com.bean.User;
import com.example.share4_15.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistActivity extends Activity {

	private Button bt_reist;
	private EditText et_youxiang;
	private EditText et_username;
	private EditText et_pass;
	private String username;
	private String password;
	private String email;

	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			Toast.makeText(RegistActivity.this, "注册成功",
					Toast.LENGTH_SHORT).show();
			LoginActivity.userName = username;
			LoginActivity.password = password;
			finish();
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.regist_activity);
		et_youxiang = (EditText) findViewById(R.id.regist_youxiang);
		et_username = (EditText) findViewById(R.id.regist_username);
		et_pass = (EditText) findViewById(R.id.regist_pass);
		bt_reist = (Button) findViewById(R.id.regist_bt_zhuce);
		bt_reist.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				email = et_youxiang.getText().toString();
				username = et_username.getText().toString();
				password = et_pass.getText().toString();
				if (email.equals("")) {
					Toast.makeText(RegistActivity.this, "邮箱不能为空",
							Toast.LENGTH_SHORT).show();
				} else if (username.equals("")) {
					Toast.makeText(RegistActivity.this, "用户名不能为空",
							Toast.LENGTH_SHORT).show();
				} else if (password.equals("")) {
					Toast.makeText(RegistActivity.this, "密码不能为空",
							Toast.LENGTH_SHORT).show();
				} else {
					ProgressDialog progressDialog = new ProgressDialog(RegistActivity.this);
					progressDialog.setTitle("Share");
					progressDialog.setMessage("注册中...");
					progressDialog.show();
					
					User user = new User();
					user.setUsername(username);
					user.setPassword(password);
					user.setEmail(email);
					user.signUp(RegistActivity.this, new SaveListener() {
						
						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub
							Toast.makeText(RegistActivity.this, "注册成功", Toast.LENGTH_LONG).show();
						}
						
						@Override
						public void onFailure(int arg0, String arg1) {
							// TODO Auto-generated method stub
							Toast.makeText(RegistActivity.this, "注册失败", Toast.LENGTH_LONG).show();
						}
					});
					new Thread(){
						public void run() {
							try {
								Thread.sleep(2000);
								handler.sendEmptyMessage(1001);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						};
					}.start();
				}
			}
		});
	}
}

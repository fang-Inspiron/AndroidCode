package com.qlf.plants.activity;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.qlf.plants.R;
import com.qlf.plants.thread.RegisterThread;

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

public class RegisterActivity extends Activity {

	private Button register;
	private EditText username;
	private EditText password;
	private EditText passwordTwo;
	private EditText email;
	private EditText phone;
	private Map<String, Object> data;
	private ProgressDialog progressDialog;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			progressDialog.dismiss();
			if (msg.what == RegisterThread.SUCCESS) {
				data = (Map<String, Object>) msg.obj;

				if (Integer.parseInt(data.get("code").toString()) == 1) {
					Toast.makeText(getApplicationContext(),
							data.get("data").toString(), Toast.LENGTH_LONG)
							.show();
					finish();
					Intent intent = new Intent(getApplicationContext(),
							LoginActivity.class);
					startActivity(intent);
				} else if (Integer.parseInt(data.get("code").toString()) == -1) {
					Toast.makeText(getApplicationContext(),
							data.get("data").toString(), Toast.LENGTH_LONG)
							.show();
					phone.setText("");
				} else if (Integer.parseInt(data.get("code").toString()) == -2) {
					Toast.makeText(getApplicationContext(),
							data.get("data").toString(), Toast.LENGTH_LONG)
							.show();
					username.setText("");
					email.setText("");
					phone.setText("");
					password.setText("");
					passwordTwo.setText("");
				}
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_main);

		initView();

		register = (Button) findViewById(R.id.register);
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// http
				String userName = username.getText().toString();
				String passWord = password.getText().toString();
				String eMail = email.getText().toString();
				String pHone = phone.getText().toString();
				String passWordTwo = passwordTwo.getText().toString();
				if (userName.equals("") || passWord.equals("")
						|| eMail.equals("") || pHone.equals("")
						|| passWordTwo.equals("")) {
					Toast.makeText(getApplicationContext(), "请填写完整信息",
							Toast.LENGTH_SHORT).show();
				} else if (!isEmail(eMail)) {
					Toast.makeText(getApplicationContext(), "邮箱格式错误",
							Toast.LENGTH_SHORT).show();
					email.setText("");
				} else if (!isMobileNO(pHone)) {
					Toast.makeText(getApplicationContext(), "手机号格式错误",
							Toast.LENGTH_SHORT).show();
					phone.setText("");
				} else if (!passWord.equals(passWordTwo)) {
					Toast.makeText(getApplicationContext(), "两次密码不一致",
							Toast.LENGTH_SHORT).show();
					password.setText("");
					passwordTwo.setText("");
				} else {
					new RegisterThread(handler, userName, passWord, eMail,
							pHone).start();
					progressDialog = new ProgressDialog(RegisterActivity.this);
					progressDialog.setTitle("提示");
					progressDialog.setMessage("加载中...");
					progressDialog.show();
				}

			}
		});
	}

	private void initView() {
		username = (EditText) findViewById(R.id.user_name);
		password = (EditText) findViewById(R.id.user_password);
		email = (EditText) findViewById(R.id.user_email);
		phone = (EditText) findViewById(R.id.user_phone);
		passwordTwo = (EditText) findViewById(R.id.user_password_two);
	}

	// 判断手机格式是否正确
	public boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);

		return m.matches();
	}

	// 判断email格式是否正确
	public boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	// 判断是否全是数字
	public boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
}

package com.xrml.kuaican.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xrml.kuaican.R;
import com.xrml.kuaican.customer.toast.CustomToast;
import com.xrml.kuaican.net.GetCodeThread;
import com.xrml.kuaican.net.GetUserRegisterThread;
import com.xrml.kuaican.net.IsConnection;
import com.xrml.kuaican.util.ActionBarUtil;
import com.xrml.kuaican.util.MD5StringUtil;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends Activity {

	// 注册成功
	private final static String REGISTER_SUCC = "CBEA0ABDF6E393C33C540E604C8F4E8C";
	// 手机号码已被注册
	private final static String PHONE_NUM_INVALID = "A3A2BA02F08301C857AFCFDFC192DF04";
	// 网络错误，请重新注册
	private final static String INTERNER_ERROR = "C732B67F7AD59A691E15B328A501508D";
	// 对不起，你无权注册
	private final static String NO_ROOT = "F1F5FF7206C408B26AA1A2B63EDBAEF3";

	EditText phoneNumber, password, identifyingCode;
	Button getIdentifyingCode, register, passwordEye;

	RegisterOnClickListener onRegistClickListener;
	private Pattern p = null;
	private Matcher m = null;
	private TextView home;
	ProgressDialog progressDialog;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GetCodeThread.NETWORK_ERROR:
			case GetUserRegisterThread.NETWORK_ERROR:
				CustomToast.showToast(RegisterActivity.this, "网络连接错误", 2000);
				break;
			case GetCodeThread.SUCCESS:
				String getCodeResult = (String) msg.obj;
				checkGetCode(getCodeResult);
				break;
			case GetUserRegisterThread.SUCCESS:
				String getUserRegisterResult = (String) msg.obj;
				checkUserRegister(getUserRegisterResult);
				break;
			}
		}
	};

	// 验证返回结果
	private void checkUserRegister(String result) {
		progressDialog.dismiss();
		if (result.equals(REGISTER_SUCC)) {
			CustomToast.showToast(RegisterActivity.this, "注册成功,请先登录", 2000);
			finish();
		} else if (result.equals(PHONE_NUM_INVALID)) {
			CustomToast.showToast(RegisterActivity.this, "手机号码已被注册", 2000);
		} else if (result.equals(INTERNER_ERROR)) {
			CustomToast.showToast(RegisterActivity.this, "网络错误，请重新注册", 2000);
		} else if (result.equals(NO_ROOT)) {
			CustomToast.showToast(RegisterActivity.this, "对不起，你无权注册", 2000);
		} else {
		}
	}

	private void checkGetCode(String result) {
		if (result.equals("update_ok")) {
			CustomToast.showToast(RegisterActivity.this, "获取验证码成功", 2000);
		} else if (result.endsWith("update_error")) {
			CustomToast.showToast(RegisterActivity.this, "获取验证码失败", 2000);
		} else {
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		ActionBarUtil.initActionBar(getApplicationContext(), getActionBar(),
				"注册");
		findView();
		setListener();
		progressDialog = new ProgressDialog(RegisterActivity.this);
		progressDialog.setTitle("校帮");
		progressDialog.setMessage("注册中...");
	}

	void findView() {
		// 手机号
		phoneNumber = (EditText) findViewById(R.id.registerEditText1);
		// 密码
		password = (EditText) findViewById(R.id.registerEditText2);
		// 验证码
		identifyingCode = (EditText) findViewById(R.id.registerEditText3);
		passwordEye = (Button) findViewById(R.id.registerButton2);
		getIdentifyingCode = (Button) findViewById(R.id.registerButton3);
		register = (Button) findViewById(R.id.registerButton1);
		home = (TextView) getActionBar().getCustomView().findViewById(
				R.id.actionbar_user_state);
		home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	void setListener() {
		onRegistClickListener = new RegisterOnClickListener();
		getIdentifyingCode.setOnClickListener(onRegistClickListener);
		register.setOnClickListener(onRegistClickListener);
		passwordEye.setOnTouchListener(new PasswordEyeListener());
	}

	class RegisterOnClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			if (!IsConnection.checkNet(RegisterActivity.this)) {
				CustomToast.showToast(RegisterActivity.this, "网络连接错误，请检查网络连接",
						2000);
				return;
			}
			int id = arg0.getId();
			if (id == R.id.registerButton3) {// 获取验证码
				p = Pattern.compile("^[1]+\\d{10}");
				String phoneNum = phoneNumber.getText().toString();
				m = p.matcher(phoneNum);
				if (phoneNum == null || phoneNum.equals("")) {
					phoneNumber.requestFocus();
					CustomToast
							.showToast(RegisterActivity.this, "请输入手机号", 2000);
				} else if (!m.matches()) {
					phoneNumber.requestFocus();
					CustomToast.showToast(RegisterActivity.this, "手机号码输入不正确",
							2000);
				} else {
					new GetCodeThread(handler, phoneNum).start();
				}
			}
			if (id == R.id.registerButton1) {// 注册键
				String phoneNum = phoneNumber.getText().toString();
				String passWord = password.getText().toString();
				String checkCode = identifyingCode.getText().toString();
				String checkStr = getCheckStr();
				if (checkInput(phoneNum, passWord, checkCode)) {
					progressDialog.show();
					new GetUserRegisterThread(handler, phoneNum, passWord,
							checkCode, checkStr).start();
				}
			}
		}
	}

	// 验证用户输入
	private boolean checkInput(String phoneNum, String passWord,
			String checkCode) {
		boolean b = true;
		p = Pattern.compile("[A-Za-z0-9]*");
		if (phoneNum == null || phoneNum.equals("")) {
			phoneNumber.requestFocus();
			CustomToast.showToast(RegisterActivity.this, "请输入手机号", 2000);
			return false;
		}
		m = p.matcher(phoneNum);
		if (!m.matches()) {
			phoneNumber.requestFocus();
			CustomToast.showToast(RegisterActivity.this, "手机号输入不正确", 2000);
			return false;
		}
		if (passWord == null || passWord.equals("")) {
			password.requestFocus();
			CustomToast.showToast(RegisterActivity.this, "请输入密码", 2000);
			return false;
		}
		m = p.matcher(passWord);
		if (!m.matches()) {
			password.requestFocus();
			CustomToast.showToast(RegisterActivity.this, "密码格式不正确", 2000);
			return false;
		}
		if (checkCode == null || checkCode.equals("")) {
			identifyingCode.requestFocus();
			CustomToast.showToast(RegisterActivity.this, "请输入验证码", 2000);
			b = false;
		}
		return b;
	}

	// 获取效验码
	private String getCheckStr() {
		Date date = new Date();
		SimpleDateFormat mydate = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(mydate.format(date));
		String serviceActiion = MD5StringUtil.MD5Encode(mydate.format(date));
		return serviceActiion;
	}

	class PasswordEyeListener implements OnTouchListener {

		@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
		public boolean onTouch(View arg0, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				passwordEye.setBackground(getResources().getDrawable(
						R.drawable.register_eye_pressde));
				password.setTransformationMethod(HideReturnsTransformationMethod
						.getInstance());
			}
			if (event.getAction() == MotionEvent.ACTION_UP) {
				passwordEye.setBackground(getResources().getDrawable(
						R.drawable.register_eye_normal));
				password.setTransformationMethod(PasswordTransformationMethod
						.getInstance());
			}
			return true;
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
}

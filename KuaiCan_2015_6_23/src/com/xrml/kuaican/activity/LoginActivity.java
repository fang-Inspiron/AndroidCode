package com.xrml.kuaican.activity;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xrml.kuaican.R;
import com.xrml.kuaican.customer.toast.CustomToast;
import com.xrml.kuaican.data.App;
import com.xrml.kuaican.model.Area;
import com.xrml.kuaican.model.User;
import com.xrml.kuaican.net.GetUserLoginThread;
import com.xrml.kuaican.net.IsConnection;
import com.xrml.kuaican.util.ActionBarUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View.OnClickListener;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity implements OnClickListener {
	// 登录界面用户输入的账号和密码
	private EditText et_phoneNumber = null;
	private EditText et_passWord = null;
	// 找回密码按钮
	private Button bt_forgetPasswd = null;
	// 用户登录的按钮
	private Button bt_Login = null;
	// 注册按钮
	private Button bt_Register = null;
	// 开始登录时的进度条
	private ProgressDialog progressDialog = null;
	// 用户手机号和密码
	private String userPhoneNumber;
	private String userPassword;
	private TextView home;
	// 自动登录和记住密码
	private SharedPreferences spData;
	private CheckBox savePassword;
	// 正则表达式
	private Pattern p = null;
	private Matcher m = null;
	private Map<String, Object> map = null;
	Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GetUserLoginThread.NETWORK_ERROR:
				progressDialog.dismiss();
				CustomToast.showToast(LoginActivity.this, "网络连接错误", 2000);
				break;
			case GetUserLoginThread.SUCCESS:
				map = (Map<String, Object>) msg.obj;
				checkResult();
				break;
			}
		}
	};

	private void checkResult() {
		try {
			App app = (App) getApplication();
			app.setUser(null);
			app.setUser(new User());
			app.getUser().setCheckStr(map.get("checkStr").toString());
			app.getUser().setUserId(map.get("userId").toString());
			app.getUser().setPhone(userPhoneNumber);
			CustomToast.showToast(LoginActivity.this, "登录成功", 2000);
			// 登陆成功后记住用户名和密码
			if (savePassword.isChecked()) {
				Editor editor = spData.edit();
				editor.putString("USERPHONENUMBER", userPhoneNumber);
				editor.putString("USERPASSWORD", userPassword);
				editor.commit();
				if (app.getArea() == null) {
					SharedPreferences spData = getSharedPreferences("userinfo",
							Context.MODE_PRIVATE);
					Area area = new Area();
					area.setComplainPhone(spData.getString("COMPLAINPHONE", ""));
					area.setCurrAreaId(spData.getString("CURRAREAID", ""));
					area.setCurrAreaName(spData.getString("CURRAREANAME", ""));
					area.setCurrCity(spData.getString("CURRCITY", ""));
					area.setDes(spData.getString("DES", ""));
					area.setOrderConsumeTime(spData.getString(
							"ORDERCONSUMETIME", ""));
					if (area.getCurrAreaName().equals("")
							|| area.getCurrAreaName() == null) {
						app.setArea(null);
					} else {
						app.setArea(area);
					}
				}
			}
			finish();
		} catch (Exception e) {
			progressDialog.dismiss();
			CustomToast.showToast(LoginActivity.this, "用户不存在或密码错误", 2000);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		// ActionBarUtil.initMainActionBar(getApplicationContext(),
		// getActionBar(), "登录", "", 2);
		ActionBarUtil.initActionBar(getApplicationContext(), getActionBar(),
				"登录");
		spData = this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		initView();
		setListener();
		progressDialog = new ProgressDialog(LoginActivity.this);
		progressDialog.setTitle("校帮");
		progressDialog.setMessage("登录中...");
		initInfo();
	}

	// 初始化用户信息
	private void initInfo() {
		// 判断记住密码多选框状态
		if (spData.getBoolean("ISCHECK", false)) {
			savePassword.setChecked(true);
			et_phoneNumber.setText(spData.getString("USERPHONENUMBER", ""));
			et_passWord.setText(spData.getString("USERPASSWORD", ""));
		}
	}

	@Override
	protected void onDestroy() {
		progressDialog.dismiss();
		super.onDestroy();
	}

	private void setListener() {
		bt_Register.setOnClickListener(this);
		bt_forgetPasswd.setOnClickListener(this);
		bt_Login.setOnClickListener(this);
		savePassword.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (savePassword.isChecked()) {
					spData.edit().putBoolean("ISCHECK", true).commit();
				} else {
					spData.edit().putBoolean("ISCHECK", false).commit();
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.ButtonUserLoginConfirme: // 登陆
			// 检查网络连接
			if (!IsConnection.checkNet(LoginActivity.this)) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						LoginActivity.this)
						.setTitle("校帮")
						.setMessage("网络连接失败,是否进行网络设置?")
						.setPositiveButton("确认",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int arg1) {
										dialog.dismiss();
										startActivity(new Intent(
												Settings.ACTION_WIRELESS_SETTINGS));
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int arg1) {
										dialog.dismiss();
									}
								});
				builder.setCancelable(false);
				builder.create().show();
				CustomToast.showToast(LoginActivity.this, "网络连接错误，请检查网络连接",
						2000);
			} else if (checkInput()) {
				progressDialog.show();
				new GetUserLoginThread(handler, userPhoneNumber, userPassword)
						.start();
			}
			break;
		case R.id.free_button:// 注册
			intent.setClass(LoginActivity.this, RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.backPassword_button:// 忘记密码
			startActivity(new Intent(this, ResetPasswordActivity.class));
			break;
		}
	}

	private boolean checkInput() {
		boolean b = true;
		userPhoneNumber = et_phoneNumber.getText().toString();
		userPassword = et_passWord.getText().toString();
		if (!checkPhoneNum(userPhoneNumber) || !checkPassWord(userPassword))
			b = false;
		return b;
	}

	// 正则表达式验证手机号
	public boolean checkPhoneNum(String userPhoneNumber) {
		if (userPhoneNumber == null || userPhoneNumber.equals("")) {
			et_phoneNumber.requestFocus();
			CustomToast.showToast(LoginActivity.this, "请输入手机号", 2000);
			return false;
		}
		p = Pattern.compile("^[1]+\\d{10}");
		m = p.matcher(userPhoneNumber);
		if (!m.matches()) {
			et_phoneNumber.requestFocus();
			CustomToast.showToast(LoginActivity.this, "手机号码输入不正确，请重新输入", 2000);
			return false;
		}
		return true;
	}

	// 正则表达式验证密码
	public boolean checkPassWord(String userPassword) {
		if (userPassword == null || userPassword.equals("")) {
			et_passWord.requestFocus();
			CustomToast.showToast(LoginActivity.this, "请输入密码", 2000);
			return false;
		}
		p = Pattern.compile("[A-Za-z0-9]*");
		m = p.matcher(userPassword);
		if (!m.matches()) {
			et_passWord.requestFocus();
			CustomToast.showToast(LoginActivity.this, "密码格式不正确", 2000);
			return false;
		}
		return true;
	}

	private void initView() {
		// 用户名
		et_phoneNumber = (EditText) findViewById(R.id.EditTextUserLoginAccount);
		// 密码
		et_passWord = (EditText) findViewById(R.id.EditTextUserLoginPassword);
		// 找回密码
		bt_forgetPasswd = (Button) findViewById(R.id.backPassword_button);
		// 登录
		bt_Login = (Button) findViewById(R.id.ButtonUserLoginConfirme);
		// 注册
		bt_Register = (Button) findViewById(R.id.free_button);
		// 记住密码
		savePassword = (CheckBox) findViewById(R.id.save_password);

		home = (TextView) getActionBar().getCustomView().findViewById(
				R.id.actionbar_user_state);
		home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
}

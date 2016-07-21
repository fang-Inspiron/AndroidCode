package com.xrml.kuaican.activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xrml.kuaican.R;
import com.xrml.kuaican.customer.toast.CustomToast;
import com.xrml.kuaican.net.GetCodeThread;
import com.xrml.kuaican.net.IsConnection;
import com.xrml.kuaican.net.ResetPasswordThread;
import com.xrml.kuaican.util.ActionBarUtil;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ResetPasswordActivity extends Activity {

	// 网络错误，请重新注册
	private final static String INTERNER_ERROR = "C732B67F7AD59A691E15B328A501508D";

	EditText phoneNumber, identifyingCode;
	Button getIdentifyingCode, register;

	private Pattern p = null;
	private Matcher m = null;
	private TextView home;
	ProgressDialog progressDialog;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GetCodeThread.NETWORK_ERROR:
			case ResetPasswordThread.NETWORK_ERROR:
				CustomToast.showToast(ResetPasswordActivity.this, "网络连接错误",
						2000);
				break;
			case GetCodeThread.SUCCESS:
				String getCodeResult = (String) msg.obj;
				checkGetCode(getCodeResult);
				break;
			case ResetPasswordThread.SUCCESS:
				String getUserRegisterResult = (String) msg.obj;
				checkUserRegister(getUserRegisterResult);
				break;
			}
		}
	};

	// 验证返回结果
	private void checkUserRegister(String result) {
		progressDialog.dismiss();
		if (result.equals("update_ok")) {
			CustomToast.showToast(ResetPasswordActivity.this,
					"重置成功，密码为000000,请及时修改", 2000);
			finish();
		} else if (result.equals("update_error")) {
			CustomToast.showToast(ResetPasswordActivity.this, "重置失败", 2000);
		} else if (result.equals(INTERNER_ERROR)) {
			CustomToast.showToast(ResetPasswordActivity.this, "网络错误，请重新注册",
					2000);
		} else {
			CustomToast.showToast(ResetPasswordActivity.this, "重置失败", 2000);
		}
	}

	private void checkGetCode(String result) {
		if (result.equals("update_ok")) {
			CustomToast.showToast(ResetPasswordActivity.this, "获取验证码成功", 2000);
		} else if (result.endsWith("update_error")) {
			CustomToast.showToast(ResetPasswordActivity.this, "获取验证码失败", 2000);
		} else {
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_psd);
		ActionBarUtil.initActionBar(getApplicationContext(), getActionBar(),
				"重置密码");
		findView();
		setListener();
		progressDialog = new ProgressDialog(ResetPasswordActivity.this);
		progressDialog.setTitle("校帮");
		progressDialog.setMessage("注册中...");
	}

	void findView() {
		// 手机号
		phoneNumber = (EditText) findViewById(R.id.reset_EditText1);
		// 验证码
		identifyingCode = (EditText) findViewById(R.id.reset_EditText3);
		getIdentifyingCode = (Button) findViewById(R.id.reset_Button3);
		register = (Button) findViewById(R.id.reset_Button1);
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
		RegisterOnClickListener onClickListener = new RegisterOnClickListener();
		getIdentifyingCode.setOnClickListener(onClickListener);
		register.setOnClickListener(onClickListener);
	}

	class RegisterOnClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			if (!IsConnection.checkNet(ResetPasswordActivity.this)) {
				CustomToast.showToast(ResetPasswordActivity.this,
						"网络连接错误，请检查网络连接", 2000);
				return;
			}
			int id = arg0.getId();
			if (id == R.id.reset_Button3) {// 获取验证码
				p = Pattern.compile("^[1]+\\d{10}");
				String phoneNum = phoneNumber.getText().toString();
				m = p.matcher(phoneNum);
				if (phoneNum == null || phoneNum.equals("")) {
					phoneNumber.requestFocus();
					CustomToast.showToast(ResetPasswordActivity.this, "请输入手机号",
							2000);
				} else if (!m.matches()) {
					phoneNumber.requestFocus();
					CustomToast.showToast(ResetPasswordActivity.this,
							"手机号码输入不正确", 2000);
				} else {
					new GetCodeThread(handler, phoneNum).start();
				}
			}
			if (id == R.id.reset_Button1) {// 重置密码
				String phoneNum = phoneNumber.getText().toString();
				String checkCode = identifyingCode.getText().toString();
				if (checkInput(phoneNum, checkCode)) {
					progressDialog.show();
					new ResetPasswordThread(handler, phoneNum, checkCode)
							.start();
				}
			}
		}
	}

	// 验证用户输入
	private boolean checkInput(String phoneNum, String checkCode) {
		boolean b = true;
		p = Pattern.compile("[A-Za-z0-9]*");
		if (phoneNum == null || phoneNum.equals("")) {
			phoneNumber.requestFocus();
			CustomToast.showToast(ResetPasswordActivity.this, "请输入手机号", 2000);
			return false;
		}
		m = p.matcher(phoneNum);
		if (!m.matches()) {
			phoneNumber.requestFocus();
			CustomToast.showToast(ResetPasswordActivity.this, "手机号输入不正确", 2000);
			return false;
		}
		if (checkCode == null || checkCode.equals("")) {
			identifyingCode.requestFocus();
			CustomToast.showToast(ResetPasswordActivity.this, "请输入验证码", 2000);
			b = false;
		}
		return b;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
}

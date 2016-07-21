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
	// ��¼�����û�������˺ź�����
	private EditText et_phoneNumber = null;
	private EditText et_passWord = null;
	// �һ����밴ť
	private Button bt_forgetPasswd = null;
	// �û���¼�İ�ť
	private Button bt_Login = null;
	// ע�ᰴť
	private Button bt_Register = null;
	// ��ʼ��¼ʱ�Ľ�����
	private ProgressDialog progressDialog = null;
	// �û��ֻ��ź�����
	private String userPhoneNumber;
	private String userPassword;
	private TextView home;
	// �Զ���¼�ͼ�ס����
	private SharedPreferences spData;
	private CheckBox savePassword;
	// ������ʽ
	private Pattern p = null;
	private Matcher m = null;
	private Map<String, Object> map = null;
	Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GetUserLoginThread.NETWORK_ERROR:
				progressDialog.dismiss();
				CustomToast.showToast(LoginActivity.this, "�������Ӵ���", 2000);
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
			CustomToast.showToast(LoginActivity.this, "��¼�ɹ�", 2000);
			// ��½�ɹ����ס�û���������
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
			CustomToast.showToast(LoginActivity.this, "�û������ڻ��������", 2000);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		// ActionBarUtil.initMainActionBar(getApplicationContext(),
		// getActionBar(), "��¼", "", 2);
		ActionBarUtil.initActionBar(getApplicationContext(), getActionBar(),
				"��¼");
		spData = this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		initView();
		setListener();
		progressDialog = new ProgressDialog(LoginActivity.this);
		progressDialog.setTitle("У��");
		progressDialog.setMessage("��¼��...");
		initInfo();
	}

	// ��ʼ���û���Ϣ
	private void initInfo() {
		// �жϼ�ס�����ѡ��״̬
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
		case R.id.ButtonUserLoginConfirme: // ��½
			// �����������
			if (!IsConnection.checkNet(LoginActivity.this)) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						LoginActivity.this)
						.setTitle("У��")
						.setMessage("��������ʧ��,�Ƿ������������?")
						.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int arg1) {
										dialog.dismiss();
										startActivity(new Intent(
												Settings.ACTION_WIRELESS_SETTINGS));
									}
								})
						.setNegativeButton("ȡ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int arg1) {
										dialog.dismiss();
									}
								});
				builder.setCancelable(false);
				builder.create().show();
				CustomToast.showToast(LoginActivity.this, "�������Ӵ���������������",
						2000);
			} else if (checkInput()) {
				progressDialog.show();
				new GetUserLoginThread(handler, userPhoneNumber, userPassword)
						.start();
			}
			break;
		case R.id.free_button:// ע��
			intent.setClass(LoginActivity.this, RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.backPassword_button:// ��������
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

	// ������ʽ��֤�ֻ���
	public boolean checkPhoneNum(String userPhoneNumber) {
		if (userPhoneNumber == null || userPhoneNumber.equals("")) {
			et_phoneNumber.requestFocus();
			CustomToast.showToast(LoginActivity.this, "�������ֻ���", 2000);
			return false;
		}
		p = Pattern.compile("^[1]+\\d{10}");
		m = p.matcher(userPhoneNumber);
		if (!m.matches()) {
			et_phoneNumber.requestFocus();
			CustomToast.showToast(LoginActivity.this, "�ֻ��������벻��ȷ������������", 2000);
			return false;
		}
		return true;
	}

	// ������ʽ��֤����
	public boolean checkPassWord(String userPassword) {
		if (userPassword == null || userPassword.equals("")) {
			et_passWord.requestFocus();
			CustomToast.showToast(LoginActivity.this, "����������", 2000);
			return false;
		}
		p = Pattern.compile("[A-Za-z0-9]*");
		m = p.matcher(userPassword);
		if (!m.matches()) {
			et_passWord.requestFocus();
			CustomToast.showToast(LoginActivity.this, "�����ʽ����ȷ", 2000);
			return false;
		}
		return true;
	}

	private void initView() {
		// �û���
		et_phoneNumber = (EditText) findViewById(R.id.EditTextUserLoginAccount);
		// ����
		et_passWord = (EditText) findViewById(R.id.EditTextUserLoginPassword);
		// �һ�����
		bt_forgetPasswd = (Button) findViewById(R.id.backPassword_button);
		// ��¼
		bt_Login = (Button) findViewById(R.id.ButtonUserLoginConfirme);
		// ע��
		bt_Register = (Button) findViewById(R.id.free_button);
		// ��ס����
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

package com.xrml.kuaican.activity;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xrml.kuaican.R;
import com.xrml.kuaican.customer.toast.CustomToast;
import com.xrml.kuaican.data.App;
import com.xrml.kuaican.net.ChangePasswordThread;
import com.xrml.kuaican.util.ActionBarUtil;

public class ChangePassword extends Activity {

	private static final String TAG = "ChangePassword";
	// ԭ�������
	private static final String OLDPASSWORDERROR = "40885C152C1BA6F5D1D20A06CBAB7DFA";
	// �޸ĳɹ�
	private static final String CHANGESUCC = "update_ok";
	// ��¼ʱЧ�����µ�¼
	private static final String LOGINFAILURE = "F1F5FF7206C408B26AA1A2B63EDBAEF3";

	private Button bt_change_password_submit;

	private Pattern p = null;
	private Matcher m = null;
	private TextView tv_userPhongNumber;
	private EditText et_change_password_oldpassword;
	private EditText et_change_password_newpassword_1;
	private EditText et_change_password_newpassword_2;

	private Map<String, Object> map;
	private String phoneNumber = null;
	private String checkStr = null;
	private ProgressDialog progressDialog;
	private TextView home;
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case ChangePasswordThread.NETWORK_ERROR:
				CustomToast.showToast(ChangePassword.this, "��ȡ��Ϣʧ��", 2000);
				break;
			case ChangePasswordThread.SUCCESS:
				map = (Map<String, Object>) msg.obj;
				checkResult((String) map.get("response"));
				break;
			}
		}

		private void checkResult(String response) {
			progressDialog.dismiss();
			if (response.equals(OLDPASSWORDERROR)) {
				Log.i(TAG, "ԭ�������");
				CustomToast.showToast(ChangePassword.this, "ԭ�������", 2000);
			} else if (response.equals(CHANGESUCC)) {
				Log.i(TAG, "�޸ĳɹ�");
				et_change_password_oldpassword.setText("");
				et_change_password_newpassword_1.setText("");
				et_change_password_newpassword_2.setText("");
				CustomToast.showToast(ChangePassword.this, "�޸ĳɹ�,�����µ�½...",
						2000);
				App app = (App) getApplication();
				app.setUser(null);
				SharedPreferences spData = getSharedPreferences("userinfo",
						Context.MODE_PRIVATE);
				spData.edit().putString("USERPASSWORD", "").commit();
				Intent intent = new Intent(ChangePassword.this,
						LoginActivity.class);
				intent.putExtra("isChangePassword", "true");
				startActivity(intent);
				finish();
			} else if (response.equals(LOGINFAILURE)) {
				Log.i(TAG, "��¼ʧЧ�����µ�¼");
				CustomToast.showToast(ChangePassword.this, "��¼ʧЧ�����µ�¼", 2000);
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_password);
		ActionBarUtil.initActionBar(getApplicationContext(), getActionBar(),
				"�޸�����");
		initView();
		App app = (App) getApplication();
		phoneNumber = app.getUser().getPhone();
		checkStr = app.getUser().getCheckStr();
		tv_userPhongNumber.setText(phoneNumber);
		progressDialog = new ProgressDialog(ChangePassword.this);
		progressDialog.setTitle("У��");
		progressDialog.setMessage("�����޸���...");
	}

	private void initView() {
		home = (TextView) getActionBar().getCustomView().findViewById(
				R.id.actionbar_user_state);
		home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		et_change_password_oldpassword = (EditText) findViewById(R.id.et_change_password_oldpassword);
		et_change_password_newpassword_1 = (EditText) findViewById(R.id.et_change_password_newpassword_1);
		et_change_password_newpassword_2 = (EditText) findViewById(R.id.et_change_password_newpassword_2);
		tv_userPhongNumber = (TextView) findViewById(R.id.tv_change_password_phonenumb);
		bt_change_password_submit = (Button) findViewById(R.id.bt_change_password_submit);
		bt_change_password_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String oldPassword = et_change_password_oldpassword.getText()
						.toString();
				String newPassword_1 = et_change_password_newpassword_1
						.getText().toString();
				String newPassword_2 = et_change_password_newpassword_2
						.getText().toString();
				boolean flag = checkInput(oldPassword, newPassword_1,
						newPassword_2);
				if (flag) {
					progressDialog.show();
					new ChangePasswordThread(handler, phoneNumber, oldPassword,
							newPassword_1, checkStr).start();
				}
			}

			private boolean checkInput(String oldPassword,
					String newPassword_1, String newPassword_2) {
				p = Pattern.compile("[A-Za-z0-9]*");
				Log.i(TAG, TAG + "-->check()");
				if (oldPassword == null || oldPassword.equals("")) {
					et_change_password_oldpassword.requestFocus();
					CustomToast.showToast(ChangePassword.this, "ԭ���벻��Ϊ��", 2000);
					return false;
				}
				m = p.matcher(oldPassword);
				if (!m.matches()) {
					et_change_password_oldpassword.requestFocus();
					CustomToast
							.showToast(ChangePassword.this, "ԭ�����ʽ����ȷ", 2000);
					return false;
				}
				if (newPassword_1 == null || newPassword_1.equals("")) {
					et_change_password_newpassword_1.requestFocus();
					CustomToast.showToast(ChangePassword.this, "�����벻��Ϊ��", 2000);
					return false;
				}
				m = p.matcher(newPassword_1);
				if (!m.matches()) {
					et_change_password_newpassword_1.requestFocus();
					CustomToast
							.showToast(ChangePassword.this, "�������ʽ����ȷ", 2000);
					return false;
				}
				if (newPassword_2 == null || newPassword_2.equals("")) {
					et_change_password_newpassword_2.requestFocus();
					CustomToast.showToast(ChangePassword.this, "���ٴ���������", 2000);
					return false;
				}
				if (!newPassword_1.equals(newPassword_2)) {
					et_change_password_newpassword_1.setText("");
					et_change_password_newpassword_2.setText("");
					et_change_password_newpassword_1.requestFocus();
					CustomToast.showToast(ChangePassword.this, "�������벻һ��������������",
							2000);
					return false;
				}
				if (newPassword_1.equals(newPassword_2)) {
					return true;
				}
				return true;
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

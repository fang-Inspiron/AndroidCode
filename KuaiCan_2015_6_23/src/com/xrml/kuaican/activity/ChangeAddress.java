package com.xrml.kuaican.activity;

import java.util.Map;

import com.xrml.kuaican.R;
import com.xrml.kuaican.customer.toast.CustomToast;
import com.xrml.kuaican.net.ChangeAddressThread;
import com.xrml.kuaican.net.DeleteAddressThread;
import com.xrml.kuaican.util.ActionBarUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ChangeAddress extends Activity {

	private static final String CHANGE_OK = "update_ok";
	private static final String CHANGE_ERROR = "C732B67F7AD59A691E15B328A501508D";
	private static final String CHANGE_FAILE = "update_error";

	private String orderPhone;// �û��绰
	private String userName;// �û�����
	private String orderAddress;// ���͵�ַ
	private String receiveId;// ��ַId
	private TextView tvUserName;// ��ַ�û�����
	private TextView tvUserPhone;// ��ַ�ֻ���
	private TextView tvOrderAddress;// ���͵�ַ
	@SuppressWarnings("unused")
	private TextView tvFlag;// �Ƿ�ΪĬ�ϵ�ַ
	private TextView home;
	private Button btDeleteAddress;// ɾ����ַ
	private Button btSaveAddress;// �����ַ
	private Map<String, Object> data;
	private ProgressDialog progressDialog;

	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case ChangeAddressThread.NETWORK_ERROR:
				CustomToast.showToast(ChangeAddress.this, "�޸�ʧ��", 2000)
						;
				break;

			case ChangeAddressThread.SUCCESS:
				data = (Map<String, Object>) msg.obj;
				checkResult("����ɹ�", "���ݿ��������", "����ʧ��");
				break;
			case DeleteAddressThread.NETWORK_ERROR:
				CustomToast.showToast(ChangeAddress.this, "ɾ��ʧ��", 2000)
						;
				break;
			case DeleteAddressThread.SUCCESS:
				data = (Map<String, Object>) msg.obj;
				checkResult("ɾ���ɹ�", "���ݿ��������", "ɾ��ʧ��");
				break;
			}
		}
	};

	private void checkResult(String ok, String error, String faile) {
		progressDialog.dismiss();
		String response = data.get("response").toString();
		if (response.equals(CHANGE_OK)) {
			CustomToast.showToast(ChangeAddress.this, ok, 2000);
			finish();
		} else if (response.equals(CHANGE_ERROR)) {
			CustomToast.showToast(ChangeAddress.this, error, 2000)
					;
		} else if (response.equals(CHANGE_FAILE)) {
			CustomToast.showToast(ChangeAddress.this, faile, 2000)
					;
		} else {
			CustomToast.showToast(ChangeAddress.this, "δ֪����", 2000)
					;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_address);
		ActionBarUtil.initActionBar(getApplicationContext(), getActionBar(),
				"�޸ĵ�ַ");
		progressDialog = new ProgressDialog(ChangeAddress.this);
		progressDialog.setTitle("У��");
		initInfo();
		findViews();
	}

	private void findViews() {
		tvUserName = (TextView) findViewById(R.id.change_address_userName);
		tvUserName.setText(userName);
		tvUserPhone = (TextView) findViewById(R.id.change_address_userPhone);
		tvUserPhone.setText(orderPhone);
		tvOrderAddress = (TextView) findViewById(R.id.change_address_orderAddress);
		tvOrderAddress.setText(orderAddress);
		tvFlag = (TextView) findViewById(R.id.change_address_flag);
		// ɾ����ַ
		btDeleteAddress = (Button) findViewById(R.id.change_address_deleteAddress);
		btDeleteAddress.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new DeleteAddressThread(handler, receiveId).start();
				progressDialog.setMessage("ɾ����ַ��...");
				progressDialog.show();
			}
		});
		// �����ַ
		btSaveAddress = (Button) findViewById(R.id.change_address_saveAddress);
		btSaveAddress.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				orderPhone = tvUserPhone.getText().toString();
				userName = tvUserName.getText().toString();
				orderAddress = tvOrderAddress.getText().toString();
				new ChangeAddressThread(handler, orderPhone, userName,
						orderAddress, receiveId).start();
				progressDialog.setMessage("�����ַ��...");
				progressDialog.show();
			}
		});
		home = (TextView)getActionBar().getCustomView().findViewById(R.id.actionbar_user_state);
		home.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	private void initInfo() {
		Intent intent = getIntent();
		orderPhone = intent.getStringExtra("orderPhone");
		userName = intent.getStringExtra("userName");
		orderAddress = intent.getStringExtra("orderAddress");
		receiveId = intent.getStringExtra("receiveId");
	}
}

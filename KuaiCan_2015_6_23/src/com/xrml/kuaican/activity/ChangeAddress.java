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

	private String orderPhone;// 用户电话
	private String userName;// 用户姓名
	private String orderAddress;// 订餐地址
	private String receiveId;// 地址Id
	private TextView tvUserName;// 地址用户姓名
	private TextView tvUserPhone;// 地址手机号
	private TextView tvOrderAddress;// 订餐地址
	@SuppressWarnings("unused")
	private TextView tvFlag;// 是否为默认地址
	private TextView home;
	private Button btDeleteAddress;// 删除地址
	private Button btSaveAddress;// 保存地址
	private Map<String, Object> data;
	private ProgressDialog progressDialog;

	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case ChangeAddressThread.NETWORK_ERROR:
				CustomToast.showToast(ChangeAddress.this, "修改失败", 2000)
						;
				break;

			case ChangeAddressThread.SUCCESS:
				data = (Map<String, Object>) msg.obj;
				checkResult("保存成功", "数据库操作错误", "保存失败");
				break;
			case DeleteAddressThread.NETWORK_ERROR:
				CustomToast.showToast(ChangeAddress.this, "删除失败", 2000)
						;
				break;
			case DeleteAddressThread.SUCCESS:
				data = (Map<String, Object>) msg.obj;
				checkResult("删除成功", "数据库操作错误", "删除失败");
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
			CustomToast.showToast(ChangeAddress.this, "未知错误", 2000)
					;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_address);
		ActionBarUtil.initActionBar(getApplicationContext(), getActionBar(),
				"修改地址");
		progressDialog = new ProgressDialog(ChangeAddress.this);
		progressDialog.setTitle("校帮");
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
		// 删除地址
		btDeleteAddress = (Button) findViewById(R.id.change_address_deleteAddress);
		btDeleteAddress.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new DeleteAddressThread(handler, receiveId).start();
				progressDialog.setMessage("删除地址中...");
				progressDialog.show();
			}
		});
		// 保存地址
		btSaveAddress = (Button) findViewById(R.id.change_address_saveAddress);
		btSaveAddress.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				orderPhone = tvUserPhone.getText().toString();
				userName = tvUserName.getText().toString();
				orderAddress = tvOrderAddress.getText().toString();
				new ChangeAddressThread(handler, orderPhone, userName,
						orderAddress, receiveId).start();
				progressDialog.setMessage("保存地址中...");
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

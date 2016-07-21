package com.xrml.kuaican.activity;

import java.util.Map;

import com.xrml.kuaican.R;
import com.xrml.kuaican.customer.toast.CustomToast;
import com.xrml.kuaican.data.App;
import com.xrml.kuaican.net.NewAddressThread;
import com.xrml.kuaican.util.ActionBarUtil;
import com.xrml.kuaican.util.ValidateUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class NewAddresActivity extends Activity {
	private App app;
	private String userId;
	EditText nameText, phoneNumberText, addressText;
	Button button;
	CheckBox checkBox;
	ProgressDialog dialog;
	private TextView home;
	boolean checked = true;
	Map<String, Object> map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newaddress);
		ActionBarUtil.initActionBar(getApplicationContext(), getActionBar(),
				"新增地址");
		initInfo();
		findViews();
		setListener();
	}

	private void initInfo() {
		app = (App) getApplication();
		userId = app.getUser().getUserId();
	}

	void findViews() {
		nameText = (EditText) findViewById(R.id.newAddressEditText2);
		phoneNumberText = (EditText) findViewById(R.id.newAddressEditText3);
		addressText = (EditText) findViewById(R.id.newAddressEditText1);
		button = (Button) findViewById(R.id.newAddressButton1);
		checkBox = (CheckBox) findViewById(R.id.newAddressCheckbox);
		dialog = new ProgressDialog(this);
		dialog.setMessage("正在提交数据，请稍后");
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
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				checked = arg1;
			}
		});
		button.setOnClickListener(new ButtonListener());
	}

	class NewAddressHandler extends Handler {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			dialog.dismiss();
			switch (msg.what) {
			case NewAddressThread.NETWORK_ERROR:
				CustomToast.showToast(NewAddresActivity.this, "添加失败，请稍后再试",
						2000);
				break;
			case NewAddressThread.SUCCESS:
				map = (Map<String, Object>) msg.obj;
				String result = (String) map.get("response");
				if (result.equals(NewAddressThread.RESPONSE_DATEBASE_ERROR)) {
					CustomToast.showToast(NewAddresActivity.this,
							"数据库操作错误，请稍后再试", 2000);
				} else if (result.equals(NewAddressThread.RESPONSE_ERROR)) {
					CustomToast.showToast(NewAddresActivity.this, "提交失败，请稍后再试",
							2000);
				} else if (result.equals(NewAddressThread.RESPONSE_OK)) {
					CustomToast.showToast(NewAddresActivity.this, "恭喜你，提交成功",
							2000);
					finish();
				}

				break;
			}
		}
	}

	class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			String name, phoneNumber, address;
			name = nameText.getText().toString();
			phoneNumber = phoneNumberText.getText().toString();
			address = addressText.getText().toString();
			if (name.equals("")) {
				CustomToast.showToast(NewAddresActivity.this, "姓名不能为空",
						2000);
			} else if (!ValidateUtils.isPhoneValidate(phoneNumber)) {
				CustomToast.showToast(NewAddresActivity.this, "请检查手机号码是否填写正确",
						2000);
			} else if (address.equals("")) {
				CustomToast.showToast(NewAddresActivity.this, "地址不能为空",
						2000);
			} else {
				dialog.show();
				new NewAddressThread(new NewAddressHandler(), phoneNumber,
						name, address, userId).start();
			}
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

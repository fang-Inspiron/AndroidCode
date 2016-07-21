package com.xrml.kuaican.activity;

import java.util.Map;

import com.xrml.kuaican.R;
import com.xrml.kuaican.customer.toast.CustomToast;
import com.xrml.kuaican.data.App;
import com.xrml.kuaican.net.SendSuggessedThread;
import com.xrml.kuaican.util.ActionBarUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FeedBack extends Activity {

	private String SENDOK = "update_ok";
	private String SENDFAILE = "C732B67F7AD59A691E15B328A501508D";

	private App app;
	private String userPhone;
	private String suggestionStr;
	private EditText feed_back_advice;
	private TextView home;
	private Button feed_back_sendButton;
	private ProgressDialog progressDialog;
	private Map<String, Object> data;

	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SendSuggessedThread.NETWORK_ERROR:
				CustomToast.showToast(FeedBack.this, "ÍøÂçÁ´½Ó´íÎó", 2000);
				break;
			case SendSuggessedThread.SUCCESS:
				data = (Map<String, Object>) msg.obj;
				checkResult();
				break;
			}
		}

	};

	private void checkResult() {
		progressDialog.dismiss();
		String result = data.get("response").toString();
		if (SENDOK.equals(result)) {
			CustomToast.showToast(FeedBack.this, "ÁôÑÔ³É¹¦", 2000);
			feed_back_advice.setText("");
		} else if (SENDFAILE.equals(result)) {
			CustomToast.showToast(FeedBack.this, "Êý¾Ý¿â²Ù×÷´íÎó", 2000);
		} else {
			CustomToast.showToast(FeedBack.this, "Î´Öª´íÎó", 2000);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feed_back);
		ActionBarUtil.initActionBar(getApplicationContext(), getActionBar(),
				"Òâ¼û·´À¡");
		progressDialog = new ProgressDialog(FeedBack.this);
		progressDialog.setTitle("Ð£°ï");
		progressDialog.setMessage("ÁôÑÔ·¢ËÍÖÐ...");
		initAppInfo();
		findViews();
	}

	private void initAppInfo() {
		app = (App) getApplication();
		if (app.getUser() == null) {
			CustomToast.showToast(FeedBack.this, "ÇëÏÈµÇÂ½", 2000);
			startActivity(new Intent(FeedBack.this, LoginActivity.class));
		}
		userPhone = app.getUser().getPhone();
	}

	private void findViews() {
		feed_back_advice = (EditText) findViewById(R.id.feed_back_advice);
		feed_back_sendButton = (Button) findViewById(R.id.feed_back_sendButton);
		feed_back_sendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				suggestionStr = feed_back_advice.getText().toString();
				if (suggestionStr.equals("") || suggestionStr == null) {
					CustomToast.showToast(FeedBack.this, "ÇëÊäÈëÁôÑÔ", 2000);
					System.out.println("suggestionStr--->" + suggestionStr);
				} else {
					new SendSuggessedThread(handler, userPhone, suggestionStr)
							.start();
					progressDialog.show();
				}
			}
		});
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

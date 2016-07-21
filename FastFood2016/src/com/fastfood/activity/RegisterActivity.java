package com.fastfood.activity;

import java.util.Date;
import java.util.Map;

import com.fastfood.R;
import com.fastfood.thread.GetVerifyCodethread;
import com.fastfood.thread.RegisterThread;
import com.fastfood.utils.ActionBarUtil;
import com.fastfood.utils.MD5StringUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private EditText et_phone;
	private EditText et_password;
	private EditText et_verification;
	private Button show_hide_password;
	private Button btn_gain;
	private Button btn_register;
	Map<String, Object> data;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == GetVerifyCodethread.SUCCESS) {
				data = (Map<String, Object>) msg.obj;
				Toast.makeText(RegisterActivity.this,data.get("response").toString(), Toast.LENGTH_LONG).show();
			} 
			if (msg.what==RegisterThread.SUCCESS) {
				data = (Map<String, Object>) msg.obj;
				switch (data.get("response").toString()) {
				case RegisterThread.REGISTER_SUCCESS:
					Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_LONG).show();
					Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
					startActivity(intent);
					break;
				case RegisterThread.HAS_REGISTER:
					Toast.makeText(RegisterActivity.this, "手机号码已经被注册！", Toast.LENGTH_LONG).show();
					break;
				case RegisterThread.NET_ERROR:
					Toast.makeText(RegisterActivity.this, "网络错误，请重新注册！", Toast.LENGTH_LONG).show();
					break;
				case RegisterThread.NO_ACCESS:
					Toast.makeText(RegisterActivity.this, "对不起，您无权注册！", Toast.LENGTH_LONG).show();
					break;
				default:
					break;
				}
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		ActionBarUtil.initActionBar(getApplicationContext(), getActionBar(),
				"返回", 0);
		getID();

		show_hide_password.setOnTouchListener(new MyTouchListener());
		btn_gain.setOnClickListener(new MyClickListener());
		btn_register.setOnClickListener(new MyClickListener());
	}

	class MyTouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				et_password
						.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				et_password.setInputType(InputType.TYPE_CLASS_TEXT
						| InputType.TYPE_TEXT_VARIATION_PASSWORD);
			}
			return false;
		}

	}

	class MyClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.btn_gain) {
				String phone = et_phone.getText().toString();
				new GetVerifyCodethread(handler, phone).start();
			}
			if (v.getId() == R.id.btn_register) {

				new RegisterThread(handler, et_phone.getText().toString(),
						et_password.getText().toString(), et_verification
								.getText().toString(),
						MD5StringUtil.MD5Encode(new Date().toString())).start();
				
			}
		}

	}

	public void getID() {
		et_phone = (EditText) findViewById(R.id.et_phone);
		et_password = (EditText) findViewById(R.id.et_password);
		et_verification = (EditText) findViewById(R.id.et_verification);
		show_hide_password = (Button) findViewById(R.id.show_hide_password);
		btn_gain = (Button) findViewById(R.id.btn_gain);
		btn_register = (Button) findViewById(R.id.btn_register);
	}

}

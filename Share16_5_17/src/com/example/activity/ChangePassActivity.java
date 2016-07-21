package com.example.activity;

import java.util.HashMap;
import java.util.Map;

import cn.bmob.v3.listener.UpdateListener;

import com.bean.User;
import com.data.UserData;
import com.example.fragment.MyFragment;
import com.example.share4_15.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class ChangePassActivity extends Activity implements OnClickListener {

	private ImageButton back;
	private EditText oldpass;
	private EditText xinpass1;
	private EditText xinpass2;
	private Button change_pass_bt;
	private ProgressDialog progressDialog;
	private String xinpass1Str;
	private String oldpassStr;
	private String xinpass2Str;
	private  boolean flag;
	public static String objectId;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			progressDialog.dismiss();
			LoginActivity.password = xinpass1Str;
			Toast.makeText(ChangePassActivity.this, "密码修改成功",Toast.LENGTH_SHORT).show();
			finish();
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.change_pass_activity);
		progressDialog = new ProgressDialog(ChangePassActivity.this);
		progressDialog.setTitle("SHARE");
		progressDialog.setMessage("密码修改中...");
		findViews();
	}

	private void findViews() {
		back = (ImageButton) findViewById(R.id.change_back);
		oldpass = (EditText) findViewById(R.id.change_old_pass);
		xinpass1 = (EditText) findViewById(R.id.change_xin_pass);
		xinpass2 = (EditText) findViewById(R.id.change_xin_pass2);
		change_pass_bt = (Button) findViewById(R.id.change_bt_sub);
		back.setOnClickListener(this);
		change_pass_bt.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.change_back:
			finish();
			break;
		case R.id.change_bt_sub:
			if (!check()) {
				return;
			}
			AlertDialog.Builder builder = new AlertDialog.Builder(
					ChangePassActivity.this)
					.setTitle("提示")
					.setMessage("确认修改?")
					.setPositiveButton("确认",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int arg1) {
									progressDialog.show();
									new Thread() {
										public void run() {
											try {
												Thread.sleep(2000);
												handler.sendEmptyMessage(100);
											} catch (InterruptedException e) {
												e.printStackTrace();
											}
										};
									}.start();
									dialog.dismiss();
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
			builder.create().show();
			break;
		}
	}

	private boolean check() {
		oldpassStr = oldpass.getText().toString();
		xinpass1Str = xinpass1.getText().toString();
		xinpass2Str = xinpass2.getText().toString();
		if (oldpassStr.equals("") || oldpassStr == null) {
			Toast.makeText(ChangePassActivity.this, "旧密码不能为空",
					Toast.LENGTH_SHORT).show();
			return false;
		}else if (xinpass1Str.equals("")||xinpass1Str == null) {
			Toast.makeText(ChangePassActivity.this, "新密码不能为空",
					Toast.LENGTH_SHORT).show();
			return false;
		}else if (xinpass2Str.equals("")||xinpass2Str == null) {
			Toast.makeText(ChangePassActivity.this, "请再次输入密码",
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (!oldpassStr.equals(LoginActivity.password)) {
			Toast.makeText(ChangePassActivity.this, "旧密码不正确",
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (!xinpass1Str.equals(xinpass2Str)) {
			Toast.makeText(ChangePassActivity.this, "新密码输入不一致",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		
		User user = new User();
		user.setPassword(xinpass1Str);
		final String name = UserData.getUserInfo(getApplicationContext()).get("username").toString();
		user.update(getApplicationContext(), objectId, new UpdateListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Map<String, String> map = new HashMap<String, String>();
				map.put("username", name);
				map.put("password", xinpass1Str);
				UserData.saveUserInfo(getApplicationContext(), map);
				flag = true;
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				flag = false;
				System.out.println("密码修改失败");
			}
		});
		if (flag) {
			return true;
		} else {
			return false;
		}
	}
}

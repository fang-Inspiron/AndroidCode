package com.example.activity;

import com.example.share4_15.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SheZhiActivity extends Activity implements OnClickListener {
	private RelativeLayout jibenziliao;
	private RelativeLayout xiugaimima;
	private RelativeLayout xiaoxishezhi;
	private RelativeLayout guanyu;
	private RelativeLayout qingchuhuancun;
	private ImageButton back;
	private ProgressDialog progressDialog;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			progressDialog.dismiss();
			Toast.makeText(SheZhiActivity.this, "缓存已清除", Toast.LENGTH_SHORT).show();
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setting_activity);
		progressDialog = new ProgressDialog(SheZhiActivity.this);
		progressDialog.setTitle("SHARE");
		progressDialog.setMessage("清除缓存中...");
		findViews();
	}

	private void findViews() {
		jibenziliao = (RelativeLayout) findViewById(R.id.setting_ziliao);
		xiugaimima = (RelativeLayout) findViewById(R.id.setting_xiugaimima);
		xiaoxishezhi = (RelativeLayout) findViewById(R.id.setting_xiaoxishezhi);
		guanyu = (RelativeLayout) findViewById(R.id.setting_guanyu);
		qingchuhuancun = (RelativeLayout) findViewById(R.id.setting_qingchuhuancun);
		back = (ImageButton) findViewById(R.id.setting_back);
		back.setOnClickListener(this);
		jibenziliao.setOnClickListener(this);
		xiugaimima.setOnClickListener(this);
		xiaoxishezhi.setOnClickListener(this);
		guanyu.setOnClickListener(this);
		qingchuhuancun.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		// 基本资料
		case R.id.setting_ziliao:
			intent.setClass(SheZhiActivity.this, JiBenZiLiaoActivity.class);
			startActivity(intent);
			break;
		// 修改密码
		case R.id.setting_xiugaimima:
			intent.setClass(SheZhiActivity.this, ChangePassActivity.class);
			startActivity(intent);
			break;
		// 消息设置
		case R.id.setting_xiaoxishezhi:
			intent.setClass(SheZhiActivity.this, MessageSettActivity.class);
			startActivity(intent);
			break;
		// 关于SHARE
		case R.id.setting_guanyu:

			break;
		// 清除缓存
		case R.id.setting_qingchuhuancun:
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
			break;
		case R.id.setting_back:
			finish();
			break;
		}
	}
}

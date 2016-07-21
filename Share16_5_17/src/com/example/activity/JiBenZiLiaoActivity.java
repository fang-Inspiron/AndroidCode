package com.example.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.bean.User;
import com.data.UserData;
import com.example.share4_15.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class JiBenZiLiaoActivity extends Activity {
	private ImageButton back;
	private ImageView head;
	private TextView nickName;
	private TextView signature;
	private TextView sex;
	private TextView email;

	public List<User> listUser = new ArrayList<User>();
	public Map<String, Bitmap> headImg = new HashMap<String, Bitmap>();
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			listUser = (List<User>) msg.obj;
			setMyInfo();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.jibenziliao_activity);

		findId();
		setListener();
		geneItems();
	}
	
	public void setMyInfo() {
		nickName.setText(UserData.getUserInfo(getApplicationContext()).get("username").toString());
		signature.setText(listUser.get(0).getSignature());
		sex.setText(listUser.get(0).getSex());
		email.setText(listUser.get(0).getEmail());
		if (headImg.get("head" + ".jpg") != null) {
			Bitmap bp = headImg.get("head" + ".jpg");
			head.setImageBitmap(bp);
		}
	}

	// 获取数据
	public void geneItems() {
		BmobQuery<User> query = new BmobQuery<User>();
		// findObjects是通过内部代码线程实现的，不能在主线程中得到结果！
		query.findObjects(getApplicationContext(), new FindListener<User>() {
			@Override
			public void onError(int arg0, String arg1) {
				System.out.println("查询失败" + arg1);
			}

			@Override
			public void onSuccess(List<User> object) {
				Message m = new Message();
				m.what = 1;
				m.obj = object;
				handler.sendMessage(m);
			}
		});
	}

	public void setListener() {
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	public void findId() {
		back = (ImageButton) findViewById(R.id.ziliao_back);
		head = (ImageView) findViewById(R.id.head);
		nickName = (TextView) findViewById(R.id.nickName);
		signature = (TextView) findViewById(R.id.signature);
		sex = (TextView) findViewById(R.id.sex);
		email = (TextView) findViewById(R.id.email);
	}

}

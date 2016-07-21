package com.xrml.kuaican.activity;

import java.util.List;
import java.util.Map;

import com.xrml.kuaican.MainActivity;
import com.xrml.kuaican.R;
import com.xrml.kuaican.customer.toast.CustomToast;
import com.xrml.kuaican.data.App;
import com.xrml.kuaican.model.Area;
import com.xrml.kuaican.net.GetAllArea;
import com.xrml.kuaican.util.ActionBarUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ChooseArea extends Activity {
	private ListView lv_all_area;
	private TextView home;
	private App app;
	private String[] allAreaName;
	private Map<String, Object> data;
	private List<Map<String, Object>> mData;
	private SharedPreferences spData;
	private String isFirstLogin;
	private ProgressDialog progressDialog;
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			progressDialog.dismiss();
			switch (msg.what) {
			case GetAllArea.NETWORK_ERROR:
				CustomToast.showToast(ChooseArea.this, "获取区域失败", 2000);
				break;
			case GetAllArea.SUCCESS:
				data = (Map<String, Object>) msg.obj;
				System.out.println(data.toString());
				checkResult();
				break;
			}
		}
	};

	@SuppressWarnings("unchecked")
	private void checkResult() {
		try {
			mData = (List<Map<String, Object>>) data.get("list");
			allAreaName = new String[mData.size()];
			for (int i = 0; i < mData.size(); i++) {
				Map<String, Object> map = mData.get(i);
				allAreaName[i] = map.get("areaName").toString();
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					R.layout.item_city_list, allAreaName);
			lv_all_area.setAdapter(adapter);
			lv_all_area.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					Area area = app.getArea();
					area.setCurrAreaId(mData.get(position).get("areaId")
							.toString());
					area.setCurrAreaName(mData.get(position).get("areaName")
							.toString());
					area.setComplainPhone(mData.get(position)
							.get("complainPhone").toString());
					area.setDes(mData.get(position).get("des").toString());
					area.setOrderConsumeTime(mData.get(position)
							.get("orderConsumeTime").toString());
					spData = getSharedPreferences("userinfo",
							Context.MODE_PRIVATE);
					Editor editor = spData.edit();
					editor.putString("CURRAREAID", area.getCurrAreaId());
					editor.putString("CURRAREANAME", area.getCurrAreaName());
					editor.putString("COMPLAINPHONE", area.getComplainPhone());
					editor.putString("DES", area.getDes());
					editor.putString("ORDERCONSUMETIME",
							area.getOrderConsumeTime());
					editor.putString("CURRCITY", area.getCurrCity());
					if (isFirstLogin.equals("yes")) {
						editor.putString("isFirstLogin", "No").commit();
						editor.commit();
						Intent intent = new Intent(ChooseArea.this,
								MainActivity.class);
						startActivity(intent);
					}
					finish();
				}

			});
		} catch (Exception e) {
			CustomToast.showToast(ChooseArea.this, "对不起，该城市未开放", 2000);
			app.setArea(null);
			Intent intent = new Intent(ChooseArea.this, ChooseCity.class);
			startActivity(intent);
			finish();
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_area);
		ActionBarUtil.initActionBar(getApplicationContext(), getActionBar(),
				"选择区域");
		app = (App) getApplication();
		progressDialog = new ProgressDialog(ChooseArea.this);
		progressDialog.setTitle("校帮");
		progressDialog.setMessage("区域加载中,请稍后...");
		Intent intent = getIntent();
		isFirstLogin = intent.getStringExtra("isFirstLogin");
		if (isFirstLogin == null) {
			isFirstLogin = "NO";
		}
		new GetAllArea(handler, app.getArea().getCurrCity(), 1 + "").start();
		progressDialog.show();
		lv_all_area = (ListView) findViewById(R.id.lv_all_area);
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

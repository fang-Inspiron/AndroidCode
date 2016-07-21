package com.xrml.kuaican.activity;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xrml.kuaican.R;
import com.xrml.kuaican.customer.toast.CustomToast;
import com.xrml.kuaican.data.App;
import com.xrml.kuaican.model.Area;
import com.xrml.kuaican.net.GetAllCityThread;
import com.xrml.kuaican.net.GetCurrLocation;
import com.xrml.kuaican.net.IsConnection;
import com.xrml.kuaican.util.ActionBarUtil;

/**
 * @author Administrator 得到当前城市名称
 */
public class ChooseCity extends Activity {

	private App app;
	private ListView lv_all_city;
	private ListView lv_curr_city;
	private TextView home;
	private GetCurrLocation currLocation;
	private String[] allCityName;
	private Map<String, Object> data;
	private List<Map<String, Object>> mData;
	private String[] currCityName;
	private String isFirstLogin = "";
	private ProgressDialog progressDialog;
	Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			progressDialog.dismiss();
			switch (msg.what) {
			case 0:
				CustomToast
						.showToast(getApplicationContext(), "获取当前城市失败", 2000);
				break;
			case 1:
				String cityName = (String) msg.obj;
				currCityName = new String[] { cityName.substring(0,
						cityName.length() - 1) };
				setAdapter(currCityName);
				break;
			case GetAllCityThread.NETWORK_ERROR:
				CustomToast.showToast(ChooseCity.this, "获取全部城市失败", 2000);
				break;
			case GetAllCityThread.SUCCESS:
				data = (Map<String, Object>) msg.obj;
				checkResult();
				break;
			case 1001:
				if (IsConnection.checkNet(ChooseCity.this)) {
					new GetAllCityThread(handler, 1 + "").start();
					handler.removeMessages(1001);
				} else {
					handler.sendEmptyMessageDelayed(1001, 1500);
				}
				break;
			}

		}

	};

	@SuppressWarnings("unchecked")
	private void checkResult() {
		try {
			mData = (List<Map<String, Object>>) data.get("list");
			allCityName = new String[mData.size()];
			for (int i = 0; i < mData.size(); i++) {
				Map<String, Object> map = mData.get(i);
				allCityName[i] = map.get("cityName").toString();
			}
			ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
					R.layout.item_city_list, allCityName);
			lv_all_city.setAdapter(adapter2);
			lv_all_city.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					if (app.getArea() == null) {
						Area area = new Area();
						area.setCurrCity(mData.get(position).get("cityName")
								.toString());
						app.setArea(area);
					} else {
						app.getArea().setCurrCity(allCityName[position]);
					}
					Intent intent = new Intent(ChooseCity.this,
							ChooseArea.class);
					intent.putExtra("isFirstLogin", isFirstLogin);
					startActivity(intent);
					finish();
				}
			});
		} catch (Exception e) {
			CustomToast.showToast(ChooseCity.this, "没有城市", 2000);
		}
	}

	@Override
	protected void onResume() {
		progressDialog = new ProgressDialog(ChooseCity.this);
		progressDialog.setTitle("校帮");
		progressDialog.setMessage("城市加载中,请稍后...");
		progressDialog.show();
		super.onResume();
	}

	private void setAdapter(String[] currCityName) {
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
				R.layout.item_city_list, currCityName);
		lv_curr_city.setAdapter(adapter1);
		currLocation.stopListener();
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_city);
		ActionBarUtil.initActionBar(getApplicationContext(), getActionBar(),
				"选择城市");
		app = (App) getApplication();
		if (!IsConnection.checkNet(ChooseCity.this)) {
			handler.sendEmptyMessage(1001);
			AlertDialog.Builder builder = new AlertDialog.Builder(
					ChooseCity.this)
					.setTitle("校帮")
					.setMessage("网络连接失败,是否进行网络设置?")
					.setPositiveButton("确认",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int arg1) {
									dialog.dismiss();
									startActivity(new Intent(
											Settings.ACTION_WIRELESS_SETTINGS));
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int arg1) {
									dialog.dismiss();
									finish();
								}
							});
			builder.setCancelable(false);
			builder.create().show();
		}
		Intent intent = getIntent();
		isFirstLogin = intent.getStringExtra("isFirstLogin");
		if (isFirstLogin == null) {
			isFirstLogin = "NO";
		}
		currLocation = new GetCurrLocation(getApplicationContext(), handler);
		new GetAllCityThread(handler, 1 + "").start();
		lv_curr_city = (ListView) findViewById(R.id.lv_curr_city);
		lv_all_city = (ListView) findViewById(R.id.lv_all_city);

		lv_curr_city.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if (app.getArea() == null) {
					Area area = new Area();
					area.setCurrCity(currCityName[position]);
					app.setArea(area);
				} else {
					app.getArea().setCurrCity(currCityName[position]);
				}
				Intent intent = new Intent(ChooseCity.this, ChooseArea.class);
				intent.putExtra("isFirstLogin", isFirstLogin);
				startActivity(intent);
				finish();
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
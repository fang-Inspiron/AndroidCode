package com.xrml.kuaican.activity;

import java.util.List;
import java.util.Map;

import com.xrml.kuaican.R;
import com.xrml.kuaican.customer.toast.CustomToast;
import com.xrml.kuaican.data.App;
import com.xrml.kuaican.net.GetUserAddressThread;
import com.xrml.kuaican.util.ActionBarUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MyOrderAddress extends Activity {
	private App app;
	private String userId;
	private Button addAddress;
	private Map<String, Object> data;
	private List<Map<String, Object>> mData;
	private ListView addListView;
	private TextView home;
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GetUserAddressThread.NETWORK_ERROR:
				CustomToast.showToast(MyOrderAddress.this, "获取地址失败",
						2000);
				break;

			case GetUserAddressThread.SUCCESS:
				data = (Map<String, Object>) msg.obj;
				checkResult();
				break;
			}
		};
	};

	@SuppressWarnings("unchecked")
	public void checkResult() {
		try {
			String response = data.get("response").toString();
			System.out.println("response--->" + response);
			CustomToast.showToast(MyOrderAddress.this, "当前没有地址,请新增地址",
					2000);

		} catch (Exception e) {
			mData = (List<Map<String, Object>>) data.get("list");
			ListAdapter listAdapter = new ListAdapter(MyOrderAddress.this);
			addListView.setAdapter(listAdapter);
			try {
				Intent intent = getIntent();
				if (intent.getStringExtra("OrderActivity").equals(
						"OrderActivity")) {
					System.out.println("从提交订单条转过来的...");
					addListView
							.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0,
										View view, int position, long arg3) {
									TextView tvUserName = (TextView) view
											.findViewById(R.id.order_adds_userName);
									TextView tvOrderPhone = (TextView) view
											.findViewById(R.id.order_adds_userPhone);
									TextView tvOrderAddress = (TextView) view
											.findViewById(R.id.order_adds_orderAddress);
									OrderActivity.send_location
											.setText(tvOrderAddress.getText()
													.toString());
									OrderActivity.send_name.setText(tvUserName
											.getText().toString());
									OrderActivity.send_phone
											.setText(tvOrderPhone.getText()
													.toString());
									finish();
								}

							});

				}
			} catch (Exception e2) {
				System.out.println("从收货地址跳转过来的....");
			}

		}
	}

	class ListAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public ListAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return mData == null ? 0 : mData.size();
		}

		@Override
		public Object getItem(int position) {
			return mData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View arg1, ViewGroup arg2) {
			final Map<String, Object> data = mData.get(position);
			LinearLayout ll = (LinearLayout) mInflater.inflate(
					R.layout.my_order_adds_layout, null);
			// 用户名
			TextView order_adds_userName = (TextView) ll
					.findViewById(R.id.order_adds_userName);
			order_adds_userName.setText(data.get("userName").toString());
			// 用户手机号
			TextView order_adds_userPhone = (TextView) ll
					.findViewById(R.id.order_adds_userPhone);
			order_adds_userPhone.setText(data.get("orderPhone").toString());
			// 地址
			TextView order_adds_orderAddress = (TextView) ll
					.findViewById(R.id.order_adds_orderAddress);
			order_adds_orderAddress
					.setText(data.get("orderAddress").toString());
			// 地址ID
			TextView order_adds_receiveId = (TextView) ll
					.findViewById(R.id.order_adds_receiveId);
			order_adds_receiveId.setText(data.get("receiveId").toString());
			// 修改地址
			Button order_adds_changeAdd = (Button) ll
					.findViewById(R.id.order_adds_changeAdd);
			order_adds_changeAdd.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(MyOrderAddress.this,
							ChangeAddress.class);
					intent.putExtra("orderPhone", data.get("orderPhone")
							.toString());
					intent.putExtra("userName", data.get("userName").toString());
					intent.putExtra("orderAddress", data.get("orderAddress")
							.toString());
					intent.putExtra("receiveId", data.get("receiveId")
							.toString());
					startActivity(intent);
				}
			});
			return ll;
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_order_adds);
		ActionBarUtil.initActionBar(getApplicationContext(), getActionBar(),
				"收货地址");
		initAppInfo();
		initListViewData();
		initFindViews();
	}

	@Override
	protected void onStart() {
		super.onStart();
		initListViewData();
	}

	private void initListViewData() {
		new GetUserAddressThread(handler, userId).start();
	}

	private void initFindViews() {
		addListView = (ListView) findViewById(R.id.my_order_adds_listView);

		addAddress = (Button) findViewById(R.id.my_order_adds_addAddress);
		addAddress.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MyOrderAddress.this,
						NewAddresActivity.class);
				startActivity(intent);
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

	private void initAppInfo() {
		app = (App) getApplication();
		userId = app.getUser().getUserId();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
}

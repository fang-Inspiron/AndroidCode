package com.xrml.kuaican.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xrml.kuaican.MainActivity;
import com.xrml.kuaican.R;
import com.xrml.kuaican.customer.toast.CustomToast;
import com.xrml.kuaican.data.App;
import com.xrml.kuaican.net.GetUserAddressThread;
import com.xrml.kuaican.net.OrderSubmitThread;
import com.xrml.kuaican.util.ActionBarUtil;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;

public class OrderActivity extends Activity {

	private App app;
	private String areaId;
	private String userId;
	private String orderAddress;
	private String orderPhone;
	private String orderConsumeTimeStr;
	private String orderConsumeTime[];
	private String orderWay;
	private String orderMore;
	private String alipayWay;
	private String checkStr;
	private Map<String, Object> data;
	private Map<String, Object> addressData;
	private List<Map<String, Object>> mAddressData;
	Button submit;
	Button chang_address;
	static TextView send_location;
	static TextView send_name;
	static TextView send_phone;
	private TextView home;
	Button send_time;
	TextView send_other;
	CheckedTextView aplay1;
	CheckedTextView aplay2;
	ActionBar actionBar;
	private PopupMenu popupMenu;
	private ProgressDialog progressDialog;

	Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case OrderSubmitThread.NETWORK_ERROR:
				progressDialog.dismiss();
				CustomToast.showToast(getApplicationContext(), "提交失败", 2000);
				break;
			case OrderSubmitThread.SUCCESS:
				data = new HashMap<String, Object>();
				data = (Map<String, Object>) msg.obj;
				checkSuccessResult();
				break;
			case GetUserAddressThread.NETWORK_ERROR:
				progressDialog.dismiss();
				CustomToast.showToast(getApplicationContext(), "提交失败", 2000);
				break;
			case GetUserAddressThread.SUCCESS:
				addressData = (Map<String, Object>) msg.obj;
				initAddress();
				break;
			}
		}
	};

	private void checkSuccessResult() {
		progressDialog.dismiss();
		String s = data.get("response").toString();
		if (s.equals(OrderSubmitThread.SUBMIT_OK)) {
			CustomToast.showToast(getApplicationContext(), "提交成功", 2000);
			app.goodsList = null;
			app.goodsList = new ArrayList<Map<String, Object>>();
		} else if (s.equals(OrderSubmitThread.SOMEGOODSNOT_NOT)) {
			CustomToast
					.showToast(getApplicationContext(), "对不起，某些商品不能配送", 2000);
		} else if (s.equals(OrderSubmitThread.PLEASEEARLY_NOT)) {
			CustomToast
					.showToast(getApplicationContext(), "对不起，请提前半小时预订", 2000);
		} else if (s.equals(OrderSubmitThread.LOGIN_NOT)) {
			CustomToast.showToast(getApplicationContext(), "登录过期，请重试", 2000);
		} else if (s.equals(OrderSubmitThread.NET_ERROR)) {
			CustomToast.showToast(getApplicationContext(), "网络错误，请重试", 2000);
		}
		finish();
	}

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.refer_order_form);

		ActionBarUtil.initActionBar(getApplicationContext(), getActionBar(),
				"编辑订单");
		progressDialog = new ProgressDialog(OrderActivity.this);
		progressDialog.setTitle("校帮");
		progressDialog.setMessage("正在提交...");
		app = (App) getApplication();
		findViews();
		initSendTimeMenu();
		setOnclick();
		if (app.getUser() != null && app.getUser().getUserId() != null) {
			new GetUserAddressThread(handler, app.getUser().getUserId())
					.start();
		} else {
			CustomToast.showToast(OrderActivity.this, "请先登陆", 2000);
			startActivity(new Intent(OrderActivity.this, LoginActivity.class));
		}

	}

	@Override
	protected void onRestart() {
		super.onRestart();
		initSendTimeMenu();
	}

	private void initSendTimeMenu() {
		if (app.getArea() == null
				|| app.getArea().getOrderConsumeTime() == null
				|| app.getArea().getOrderConsumeTime().equals("")) {
			CustomToast.showToast(OrderActivity.this, "请选择区域", 2000);
			send_time.setText("00:00");
			Intent intent = new Intent(OrderActivity.this, ChooseCity.class);
			startActivity(intent);
			send_time.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					CustomToast.showToast(OrderActivity.this, "请选择区域", 2000);
				}
			});
		} else {
			orderConsumeTime = fromatTime(app.getArea().getOrderConsumeTime()
					.trim());
			popupMenu = new PopupMenu(OrderActivity.this, send_time);
			Menu menu = popupMenu.getMenu();
			for (int i = 0; i < orderConsumeTime.length; i++) {
				menu.add(orderConsumeTime[i]);
			}
			send_time.setText(orderConsumeTime[0] + "");
			popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

				@Override
				public boolean onMenuItemClick(MenuItem item) {
					send_time.setText(item.getTitle());
					return false;
				}
			});
			send_time.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					popupMenu.show();
				}
			});
		}
	}

	@SuppressWarnings("unchecked")
	private void initAddress() {
		try {
			String response = addressData.get("response").toString();
			System.out.println("response--->" + response);
			CustomToast.showToast(OrderActivity.this, "当前没有地址,请新增地址", 2000);
			chang_address.setText("增");

		} catch (Exception e) {
			mAddressData = (List<Map<String, Object>>) addressData.get("list");
			Map<String, Object> addMap = mAddressData.get(0);
			if (addMap.get("flag") != null) {
				send_location.setText(addMap.get("orderAddress").toString());
				send_name.setText(addMap.get("userName").toString());
				send_phone.setText(addMap.get("orderPhone").toString());
			}

		}
	}

	public void setOnclick() {
		orderAddress = send_location.getText().toString();
		orderPhone = send_phone.getText().toString();
		orderConsumeTimeStr = send_time.getText().toString();
		orderMore = send_other.getText().toString();
		submit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (send_location.getText().toString().equals("")
						|| send_phone.getText().toString().equals("")) {
					CustomToast.showToast(getApplicationContext(),
							"请正确填写信息...", 2000);
				} else {
					AlertDialog.Builder builder = new Builder(
							OrderActivity.this);
					builder.setTitle("提示");
					builder.setMessage("确认提交?");
					builder.setPositiveButton("确认",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int arg1) {
									dialog.dismiss();
									if (app.getUser() == null) {
										CustomToast.showToast(
												OrderActivity.this, "请先登录",
												2000);
										Intent intent = new Intent(
												OrderActivity.this,
												LoginActivity.class);
										startActivity(intent);
										return;
									}
									setAll();
									new OrderSubmitThread(handler, areaId,
											MainActivity.app.goodsList, userId,
											orderAddress, orderPhone,
											orderConsumeTimeStr, orderWay,
											orderMore, alipayWay, checkStr)
											.start();
									progressDialog.show();
								}
							});
					builder.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int arg1) {
									dialog.dismiss();
								}
							});
					builder.create().show();

				}
			}
		});
		aplay1.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				aplay1.setChecked(true);
				aplay2.setChecked(false);
			}
		});
		aplay2.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				CustomToast.showToast(OrderActivity.this, "暂不支持", 2000);
				// aplay1.setChecked(false);
				// aplay2.setChecked(true);
			}
		});
		chang_address.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(OrderActivity.this,
						MyOrderAddress.class);
				intent.putExtra("OrderActivity", "OrderActivity");
				startActivity(intent);
			}
		});

	}

	private String[] fromatTime(String orderConsumeTimeStr2) {
		String times[] = null;
		System.out.println("orderConsumeTimeStr2 = " + orderConsumeTimeStr2);
		times = orderConsumeTimeStr2.split("#");
		for (int i = 0; i < times.length; i++) {
			System.out.println("time[" + i + "] = " + times[i]);
		}
		return times;
	}

	public void findViews() {
		submit = (Button) findViewById(R.id.refer_order_ok);
		send_location = (TextView) findViewById(R.id.send_location);
		send_name = (TextView) findViewById(R.id.send_name);
		send_phone = (TextView) findViewById(R.id.send_phone);
		send_time = (Button) findViewById(R.id.send_time);
		send_other = (TextView) findViewById(R.id.send_other);
		aplay1 = (CheckedTextView) findViewById(R.id.aplay1);
		aplay2 = (CheckedTextView) findViewById(R.id.aplay2);
		chang_address = (Button) findViewById(R.id.send_change_address);
		home = (TextView) getActionBar().getCustomView().findViewById(
				R.id.actionbar_user_state);
		home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	public void setAll() {
		setAreaId(app.getArea().getCurrAreaId());
		setUserId(app.getUser().getUserId().toString());
		setUSER418C5509E2171D55B0AEE5C2EA4442B5(app.getUser().getCheckStr());
		setAlipayWay();
		setOrderAddress(send_location.getText().toString());
		setOrderPhone(send_phone.getText().toString());
		setOrderConsumeTime(send_time.getText().toString());
		setOrderMore(send_other.getText().toString());
		setOrderWay("1");
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String string) {
		this.areaId = string;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String string) {
		this.userId = string;
	}

	public String getOrderAddress() {
		return orderAddress;
	}

	public void setOrderAddress(String orderAddress) {
		this.orderAddress = orderAddress;
	}

	public String getOrderPhone() {
		return orderPhone;
	}

	public void setOrderPhone(String orderPhone) {
		this.orderPhone = orderPhone;
	}

	public String getOrderConsumeTime() {
		return orderConsumeTimeStr;
	}

	public void setOrderConsumeTime(String orderConsumeTime) {
		this.orderConsumeTimeStr = orderConsumeTime;
	}

	public String getOrderWay() {
		return orderWay;
	}

	public void setOrderWay(String orderWay) {
		this.orderWay = orderWay;
	}

	public String getOrderMore() {
		return orderMore;
	}

	public void setOrderMore(String orderMore) {
		this.orderMore = orderMore;
	}

	public String getAlipayWay() {

		return alipayWay;
	}

	public void setAlipayWay() {
		if (aplay1.isChecked())
			alipayWay = "1";
		if (aplay2.isChecked())
			alipayWay = "2";
	}

	public String getUSER418C5509E2171D55B0AEE5C2EA4442B5() {
		return checkStr;
	}

	public void setUSER418C5509E2171D55B0AEE5C2EA4442B5(
			String uSER418C5509E2171D55B0AEE5C2EA4442B5) {
		checkStr = uSER418C5509E2171D55B0AEE5C2EA4442B5;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
}

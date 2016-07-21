package com.xrml.kuaican.activity;

import com.xrml.kuaican.R;
import com.xrml.kuaican.customer.toast.CustomToast;
import com.xrml.kuaican.data.App;
import com.xrml.kuaican.util.ActionBarUtil;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class OrderExplain extends Activity {
	private App app;
	private String orderExplainDes;
	private String orderExplainPhone;
	private TextView order_explain_callPhone;// 拨打的电话
	private TextView order_explain_desc;// 订餐说明
	private TextView home;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_explain);
		ActionBarUtil.initActionBar(getApplicationContext(), getActionBar(),
				"使用说明");
		findViews();
		initInfo();
	}

	private void initInfo() {
		app = (App) getApplication();
		if (app.getArea() == null) {
			CustomToast.showToast(OrderExplain.this, "请先选择区域",
					2000);
		} else {
			orderExplainDes = app.getArea().getDes();
			orderExplainPhone = app.getArea().getComplainPhone();
			order_explain_desc.setText(orderExplainDes + ",详情");
			order_explain_callPhone.setText(orderExplainPhone);
		}
	}

	private void findViews() {
		order_explain_desc = (TextView) findViewById(R.id.order_explain_desc);
		// 设置订餐描述 order_explain_desc.setText("");
		order_explain_callPhone = (TextView) findViewById(R.id.order_explain_callPhone);
		order_explain_callPhone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 启动拨打电话
				Intent intent = new Intent();
				intent.setAction("android.intent.action.DIAL");
				intent.setData(Uri.parse("tel:" + orderExplainPhone));
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
}

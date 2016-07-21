package com.fastfood.mine;

import com.fastfood.R;
import com.fastfood.utils.ActionBarUtil;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MyOrderActivity extends Activity {

	private Button today_orders;
	private Button trade_record;
	private ListView listView_todayOrder, listView_tradeRecord;
	private String[] orderNum = {"00010001"};
	private LinearLayout layout_todayOrder;
	private LinearLayout layout_tradeRecord;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_order);
		ActionBarUtil.initActionBar(MyOrderActivity.this, getActionBar(), "ÎÒµÄ¶©µ¥", 0);
		
		getId();
		listView_todayOrder.setAdapter(new MyBaseAdapter1(this));
		listView_tradeRecord.setAdapter(new MyBaseAdapter2(this));
	}
	
	public void getId() {
		today_orders = (Button) findViewById(R.id.today_orders);
		trade_record = (Button) findViewById(R.id.trade_record);
		layout_todayOrder = (LinearLayout) findViewById(R.id.layout_todayOrder);
		layout_tradeRecord = (LinearLayout) findViewById(R.id.layout_tradeRecord);
		listView_todayOrder = (ListView) findViewById(R.id.listView_todayOrder);
		listView_tradeRecord = (ListView) findViewById(R.id.listView_tradeRecord);
	}
	

	public void MyOrderChangeListener(View view) {
		if (view.getId()==R.id.today_orders) {
			if (Integer.parseInt(today_orders.getTag().toString()) == 0) {
				today_orders.setTextColor(getResources().getColor(R.color.orange_text));
				today_orders.setBackgroundColor(Color.WHITE);
				trade_record.setTextColor(Color.BLACK);
				trade_record.setBackgroundColor(getResources().getColor(R.color.gray1));
				today_orders.setTag(1);
				trade_record.setTag(0);
				layout_todayOrder.setVisibility(View.VISIBLE);
				layout_tradeRecord.setVisibility(View.INVISIBLE);
			} 
		}
		if (view.getId()==R.id.trade_record) {
			if (Integer.parseInt(trade_record.getTag().toString()) == 0) {
				trade_record.setTextColor(getResources().getColor(R.color.orange_text));
				trade_record.setBackgroundColor(Color.WHITE);
				today_orders.setTextColor(Color.BLACK);
				today_orders.setBackgroundColor(getResources().getColor(R.color.gray1));
				trade_record.setTag(1);
				today_orders.setTag(0);
				layout_todayOrder.setVisibility(View.INVISIBLE);
				layout_tradeRecord.setVisibility(View.VISIBLE);
			}
		}
	}
	
	class MyBaseAdapter1 extends BaseAdapter {

		LayoutInflater ll;

		public MyBaseAdapter1(Context c) {
			// TODO Auto-generated constructor stub
			this.ll = LayoutInflater.from(c);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return orderNum.length;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
				convertView = (LinearLayout)ll.inflate(R.layout.item_today_order, null);
			return convertView;
		}

	}
	
	class MyBaseAdapter2 extends BaseAdapter {

		LayoutInflater ll;

		public MyBaseAdapter2(Context c) {
			// TODO Auto-generated constructor stub
			this.ll = LayoutInflater.from(c);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return orderNum.length;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
				convertView = (LinearLayout)ll.inflate(R.layout.item_trade_record, null);
			return convertView;
		}

	}
}

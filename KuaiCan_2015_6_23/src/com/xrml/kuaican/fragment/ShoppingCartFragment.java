package com.xrml.kuaican.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xrml.kuaican.MainActivity;
import com.xrml.kuaican.R;
import com.xrml.kuaican.activity.LoginActivity;
import com.xrml.kuaican.activity.OrderActivity;
import com.xrml.kuaican.customer.toast.CustomToast;
import com.xrml.kuaican.data.App;
import com.xrml.kuaican.net.ShoppingCartThread;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 购物车Fragment
 * 
 * @author Administrator
 * 
 */
public class ShoppingCartFragment extends Fragment {
	final static int CHANGE = 1;
	private TextView good_name;
	private ImageButton good_delete;
	private TextView good_price;
	private TextView good_num;
	private TextView good_expense;
	private TextView good_total_price;
	private ImageButton good_sub;
	private ImageButton good_add;
	private static ListView shop_list;
	private Button submit_order;
	private View rootView;
	public MyAdapter adapter;
	private App app;
	ActionBar actionBar;
	private String freight;
	// private String allGoodsPrice;
	private String totalPrice;

	static Map<String, Object> data = new HashMap<String, Object>();
	public Handler myHandler = new Handler() {

		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			if (msg.what == ShoppingCartThread.SUCCESS) {
				data = (Map<String, Object>) msg.obj;
				System.out.println("data.toString()-->" + data.toString());
				freight = data.get("freight").toString().substring(0, 3);

				totalPrice = data.get("totalPrice").toString();
				good_expense.setText(freight);
				good_total_price.setText(totalPrice);
			}
		}
	};

	public void change(Context context) {
		adapter = new MyAdapter(context);
		shop_list.setAdapter(adapter);
		List<Map<String, Object>> ls = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < MainActivity.app.goodsList.size(); i++) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("goodsId", MainActivity.app.goodsList.get(i).get("goodsId"));
			m.put("goodsCount",
					MainActivity.app.goodsList.get(i).get("goodsCount"));
			ls.add(m);
		}
		CustomToast.showToast(getActivity(), "成功加入购物车", 2000);
		new ShoppingCartThread(myHandler, 1, ls).start();
	}

	@Override
	public void onStart() {
		adapter = new MyAdapter(getActivity());
		shop_list.setAdapter(adapter);
		super.onStart();
	}

	public void changeCart() {
		List<Map<String, Object>> ls = new ArrayList<Map<String, Object>>();
		if (MainActivity.app.goodsList.size() == 0) {
			good_expense.setText("0");
			good_total_price.setText("0");
		} else {
			for (int i = 0; i < MainActivity.app.goodsList.size(); i++) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("goodsId",
						MainActivity.app.goodsList.get(i).get("goodsId"));
				m.put("goodsCount",
						MainActivity.app.goodsList.get(i).get("goodsCount"));
				ls.add(m);
			}
			new ShoppingCartThread(myHandler, 1, ls).start();
		}
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		app = MainActivity.app;
		rootView = inflater.inflate(R.layout.shopping_car_main, container,
				false);
		shop_list = (ListView) rootView.findViewById(R.id.shopping_car_list);
		submit_order = (Button) rootView.findViewById(R.id.submit_Order);
		good_expense = (TextView) rootView.findViewById(R.id.good_expense);
		good_total_price = (TextView) rootView
				.findViewById(R.id.good_total_price);
		submit_order.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (app.goodsList == null || app.goodsList.size() == 0) {
					CustomToast.showToast(getActivity(), "请选择商品",
							2000);
				} else if (app.getUser() == null) {
					CustomToast.showToast(getActivity(), "请先登录",
							2000);
					Intent intent = new Intent(getActivity(),
							LoginActivity.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent(getActivity()
							.getApplicationContext(), OrderActivity.class);
					startActivity(intent);
				}
			}
		});
		shop_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				view.invalidate();
			}

		});

		return rootView;
	}

	class MyAdapter extends BaseAdapter {

		private LayoutInflater ll = null;

		public MyAdapter(Context context) {
			this.ll = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return MainActivity.app.goodsList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			convertView = (LinearLayout) ll.inflate(R.layout.shopping_car_item,
					null);
			good_name = (TextView) convertView
					.findViewById(R.id.shopping_car_good_name);
			good_delete = (ImageButton) convertView
					.findViewById(R.id.shopping_car_item_del);
			good_price = (TextView) convertView
					.findViewById(R.id.shopping_car_item_price);
			good_add = (ImageButton) convertView
					.findViewById(R.id.shopping_car_add_count);
			good_num = (TextView) convertView
					.findViewById(R.id.shopping_car_item_count);
			good_sub = (ImageButton) convertView
					.findViewById(R.id.shopping_car_sub_count);
			good_name.setText(MainActivity.app.goodsList.get(position)
					.get("goodsName").toString());
			good_price.setText(MainActivity.app.goodsList.get(position)
					.get("goodsPrice").toString());
			good_num.setText(MainActivity.app.goodsList.get(position)
					.get("goodsCount").toString());

			good_delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					app.goodsList.remove(position); // data
					changeCart();
					adapter.notifyDataSetChanged();
				}
			});
			good_sub.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					View view = (View) v.getParent();
					TextView tv = (TextView) view
							.findViewById(R.id.shopping_car_item_count);

					String str = tv.getText().toString();
					int t = Integer.parseInt(str);
					if (t > 1) {
						String str1 = Integer.toString(t - 1);
						tv.setText(str1);

						Integer c = Integer.parseInt(MainActivity.app.goodsList
								.get(position).get("goodsCount").toString());
						c--;
						MainActivity.app.goodsList.get(position).remove(
								"goodsCount");
						MainActivity.app.goodsList.get(position).put(
								"goodsCount", c.toString());
						changeCart();
					}
				}
			});

			good_add.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					View view = (View) v.getParent();
					TextView tv = (TextView) view
							.findViewById(R.id.shopping_car_item_count);

					String str = tv.getText().toString();
					int t = Integer.parseInt(str);
					String str1 = Integer.toString(t + 1);
					tv.setText(str1);
					Integer c = Integer.parseInt(MainActivity.app.goodsList
							.get(position).get("goodsCount").toString());
					c++;
					MainActivity.app.goodsList.get(position).remove(
							"goodsCount");
					MainActivity.app.goodsList.get(position).put("goodsCount",
							c.toString());

					changeCart();
				}
			});
			return convertView;
		}

	}

}

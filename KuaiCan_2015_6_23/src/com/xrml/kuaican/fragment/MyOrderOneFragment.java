package com.xrml.kuaican.fragment;

import java.util.List;
import java.util.Map;

import com.xrml.kuaican.R;
import com.xrml.kuaican.activity.ChooseCity;
import com.xrml.kuaican.activity.LoginActivity;
import com.xrml.kuaican.customer.toast.CustomToast;
import com.xrml.kuaican.data.App;
import com.xrml.kuaican.net.ChangeOrderStateThread;
import com.xrml.kuaican.net.GetOrderListDataThread;
import com.xrml.kuaican.util.ReFlashListView;
import com.xrml.kuaican.util.ReFlashListView.IReflashListener;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyOrderOneFragment extends Fragment implements IReflashListener {

	private MyOrderTwoFragment myOrderTwoFragment;
	private App app;
	private View rootView;
	private Map<String, Object> data;
	public List<Map<String, Object>> mData;
	private ReFlashListView listView;// 订单ListView
	private TextView tv;
	private String userId;
	private ProgressDialog progressDialog;

	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GetOrderListDataThread.NETWORK_ERROR:
				listView.reflashComplete();
				tv.setText("获取订单失败");
				CustomToast.showToast(getActivity(), "获取订单列表失败", 2000);
				break;
			case GetOrderListDataThread.SUCCESS:
				listView.reflashComplete();
				data = (Map<String, Object>) msg.obj;
				try {
					mData = (List<Map<String, Object>>) data.get("list");
				} catch (Exception e) {
					mData = null;
				}
				fromatMData();
				if (data == null) {
					tv.setVisibility(View.VISIBLE);
					listView.setVisibility(View.GONE);
					tv.setText("啊偶...- -!没有订单,\n快去首页订购吧");
				} else {
					tv.setVisibility(View.GONE);
					listView.setVisibility(View.VISIBLE);
					ListAdapter listAdapter = new ListAdapter(getActivity());
					listView.setAdapter(listAdapter);
				}
				break;
			case ChangeOrderStateThread.NETWORK_ERROR:
				CustomToast.showToast(getActivity(), "网络错误", 2000);
				break;
			case ChangeOrderStateThread.SUCCESS:
				progressDialog.dismiss();
				Map<String, Object> data = (Map<String, Object>) msg.obj;
				String response = data.get("response").toString();
				if (response.equals("update_ok")) {
					CustomToast.showToast(getActivity(), "已确认收货", 2000);
				} else if (response.equals("F1F5FF7206C408B26AA1A2B63EDBAEF3")) {
					CustomToast.showToast(getActivity(), "登录失效,请重新登录", 2000);
					Intent intent = new Intent(getActivity(),
							LoginActivity.class);
					startActivity(intent);
				} else if (response.equals("update_error")) {
					CustomToast.showToast(getActivity(), "收获失败,请重新验收", 2000);
				} else {
					CustomToast.showToast(getActivity(), "未知错误", 2000);
				}
				initListData();
				break;
			}
		}

	};

	/**
	 * 格式化订单数据
	 */
	private void fromatMData() {
		try {
			Map<String, Object> data = mData.get(0);
			int specieNum = 0;
			data.put("goodsName0", data.get("goodsName"));
			data.put("goodsPrice0", data.get("goodsPrice"));
			data.put("goodsCount0", data.get("goodsCount"));
			data.put("orderSpecie", specieNum);
			for (int i = 1; i < mData.size(); i++) {
				Map<String, Object> tmpData = mData.get(i);
				if ((data.get("orderId").toString()).equals((tmpData
						.get("orderId").toString()))) {
					data.remove("orderSpecie");
					i--;
					specieNum++;
					data.put("orderSpecie", specieNum);
					data.put("goodsName" + specieNum, tmpData.get("goodsName"));
					data.put("goodsCount" + specieNum,
							tmpData.get("goodsCount"));
					data.put("goodsPrice" + specieNum,
							tmpData.get("goodsPrice"));
					mData.remove(tmpData);
				} else {
					data = tmpData;
					data.put("goodsName0", data.get("goodsName"));
					data.put("goodsPrice0", data.get("goodsPrice"));
					data.put("goodsCount0", data.get("goodsCount"));
					specieNum = 0;
					data.put("orderSpecie", specieNum);
				}
			}
		} catch (Exception e) {
			data = null;
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
					R.layout.my_order_has_one_layout, null);
			// 订单号
			TextView order_one_orderId = (TextView) ll
					.findViewById(R.id.order_one_orderId);
			order_one_orderId.setText("订单号:" + data.get("orderId").toString());
			// 动态添加view
			LinearLayout layout = (LinearLayout) ll
					.findViewById(R.id.myOrderHasOrderLinearLayout);
			int orderSpecie = (Integer) data.get("orderSpecie");
			for (int i = 0; i <= orderSpecie; i++) {
				LinearLayout layout2 = new LinearLayout(getActivity());

				TextView tv1 = new TextView(getActivity());
				tv1.setText(data.get("goodsName" + i).toString());
				tv1.setTextSize(17);
				tv1.setGravity(Gravity.CENTER_VERTICAL);
				LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				params1.weight = 1.0f;
				tv1.setLayoutParams(params1);
				layout2.addView(tv1);

				TextView tv2 = new TextView(getActivity());
				tv2.setText(data.get("goodsCount" + i).toString() + "份");
				tv2.setTextSize(17);
				tv2.setGravity(Gravity.CENTER_VERTICAL
						| Gravity.CENTER_HORIZONTAL);
				LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				params2.weight = 1.0f;
				tv2.setLayoutParams(params2);
				layout2.addView(tv2);

				TextView tv3 = new TextView(getActivity());
				tv3.setText(data.get("goodsPrice" + i).toString() + "元/份");
				tv3.setTextSize(17);
				tv3.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
				LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				params3.weight = 1.0f;
				tv3.setLayoutParams(params3);
				layout2.addView(tv3);

				layout.addView(layout2);
			}
			// 下单时间
			TextView order_one_orderTime = (TextView) ll
					.findViewById(R.id.order_one_orderTime);
			order_one_orderTime.setText("下单时间:"
					+ data.get("orderTime").toString());
			// 消费时间
			TextView order_one_consumeTime = (TextView) ll
					.findViewById(R.id.order_one_consumeTime);
			order_one_consumeTime.setText("消费时间:"
					+ data.get("consumeTime").toString());
			// 备注
			TextView order_one_order_more = (TextView) ll
					.findViewById(R.id.order_one_order_more);
			order_one_order_more.setText(data.get("orderMore").toString());
			// 总价
			TextView order_one_totalPrice = (TextView) ll
					.findViewById(R.id.order_one_totalPrice);
			order_one_totalPrice.setText("合计:"
					+ data.get("totalPrice").toString() + "元");
			// 确认收货
			Button order_one_comfirmGoods = (Button) ll
					.findViewById(R.id.order_one_comfirmGoods);
			order_one_comfirmGoods.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					AlertDialog.Builder builder = new Builder(getActivity());
					builder.setTitle("提示");
					builder.setMessage("确认收货");
					builder.setPositiveButton("确认",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int arg1) {
									dialog.dismiss();
									myOrderTwoFragment.addDateFormData(data);
									new ChangeOrderStateThread(handler, data
											.get("orderId").toString(), 2 + "",
											app.getUser().getCheckStr())
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
			});
			// 未送投诉
			Button order_one_notSendComplaint = (Button) ll
					.findViewById(R.id.order_one_notSendComplaint);
			order_one_notSendComplaint
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							app = (App) getActivity().getApplication();
							if (app.getArea() != null
									&& app.getArea().getComplainPhone() != null
									&& !app.getArea().getComplainPhone()
											.equals("")) {
								String phoneNumb = app.getArea()
										.getComplainPhone();
								Intent intent = new Intent();
								intent.setAction("android.intent.action.DIAL");
								intent.setData(Uri.parse("tel:" + phoneNumb));
								startActivity(intent);
							} else {
								CustomToast.showToast(getActivity(), "请先选择区域",
										2000);
								Intent intent = new Intent(getActivity(),
										ChooseCity.class);
								startActivity(intent);
							}
						}
					});
			return ll;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = (View) inflater.inflate(R.layout.fragment_my_order_one,
				container, false);
		progressDialog = new ProgressDialog(getActivity());
		progressDialog.setTitle("校帮");
		progressDialog.setMessage("正在确认收货...");
		initAppInfo();
		findViews();
		initListData();
		return rootView;
	}

	private void initAppInfo() {
		app = (App) getActivity().getApplication();
		userId = app.getUser().getUserId();
	}

	private void initListData() {
		new GetOrderListDataThread(handler, userId,
				app.getUser().getCheckStr(), 1, 1).start();
	}

	private void findViews() {
		listView = (ReFlashListView) rootView
				.findViewById(R.id.myOrderOneListView1);
		listView.setInterface(this);
		tv = (TextView) rootView.findViewById(R.id.tv_myOrderOne);
		myOrderTwoFragment = new MyOrderTwoFragment();
	}

	/*
	 * 下拉刷新，更新数据
	 */
	@Override
	public void onReflash() {
		initListData();
	}
}

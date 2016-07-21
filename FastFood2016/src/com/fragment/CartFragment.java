package com.fragment;

import java.util.Map;

import com.bean.GoodsBean;
import com.fastfood.R;
import com.fastfood.activity.MainActivity;
import com.fastfood.activity.SubmitOrderActivity;
import com.fastfood.thread.ShoppingCartThread;
import com.fastfood.utils.CommonAdapter;
import com.fastfood.utils.ViewHolder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CartFragment extends Fragment {

	private View rootView;
	private ListView listView_cart;

	private TextView fare_value;
	private TextView total_price;
	private Button submit_Order;

	CommonAdapter<GoodsBean> commonAdapter;
	Map<String, Object> data_cart;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case ShoppingCartThread.SUCCESS:
				initCart();
				data_cart = (Map<String, Object>) msg.obj;
				System.out.println(data_cart);
				break;
			case ShoppingCartThread.NETWORK_ERROR:
				Toast.makeText(getActivity(), "ÍøÂçÁ¬½Ó´íÎó", Toast.LENGTH_SHORT)
						.show();
				break;

			default:
				break;
			}
		}

	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_cart, container, false);
		listView_cart = (ListView) rootView.findViewById(R.id.listView_cart);

		fare_value = (TextView) rootView.findViewById(R.id.fare_value);
		total_price = (TextView) rootView.findViewById(R.id.total_price);
		submit_Order = (Button) rootView.findViewById(R.id.submit_Order);
		
		submit_Order.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity().getApplicationContext(), SubmitOrderActivity.class);
				startActivity(intent);
			}
		});
		return rootView;
	}

	public void change() {
		if (MainActivity.cartData != null && MainActivity.cartData.size() > 0) {
			new ShoppingCartThread(handler, MainActivity.cartData).start();
		}
	}

	public void initCart() {
		if (listView_cart == null) {
			listView_cart = (ListView) rootView.findViewById(R.id.listView_cart);
		}
		commonAdapter = new CommonAdapter<GoodsBean>(getActivity(),
				MainActivity.cartData, R.layout.item_cart) {
			@Override
			public void convert(ViewHolder holder, final GoodsBean t) {
				// TODO Auto-generated method stub
				((TextView) holder.getView(R.id.tv_title)).setText(t
						.getGoodsName());
				((TextView) holder.getView(R.id.tv_price_value)).setText(String
						.valueOf(t.getGoodsPrice()));
				((TextView) holder.getView(R.id.tv_num_value)).setText(String
						.valueOf(t.getGoodsCount()));
				fare_value.setText(String.valueOf(data_cart.get("freight")));
				total_price.setText(String.valueOf(data_cart.get("totalPrice")));
				
				((ImageButton) holder.getView(R.id.ib_add)).setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								t.addGoodsCount();
								MainActivity.fragment_cart= MainActivity.getCartFragment();
								MainActivity.fragment_cart.change();
							}
						});
				((ImageButton) holder.getView(R.id.ib_sub)).setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								t.subGoodsCount();
								MainActivity.fragment_cart= MainActivity.getCartFragment();
								MainActivity.fragment_cart.change();
							}
						});
				((ImageButton) holder.getView(R.id.ib_delete)).setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								t.setGoodsCount(0);
								MainActivity.cartData.remove(t);
								MainActivity.fragment_cart= MainActivity.getCartFragment();
								MainActivity.fragment_cart.change();
								commonAdapter.notifyDataSetChanged();
								if (MainActivity.cartData.size() == 0) {
									fare_value.setText("0.0");
									total_price.setText("0.0");
								}
							}
						});
			}
		};
		listView_cart.setAdapter(commonAdapter);
	}

}

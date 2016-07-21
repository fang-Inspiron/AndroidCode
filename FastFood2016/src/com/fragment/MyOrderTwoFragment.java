package com.fragment;

import com.fastfood.R;
import com.fastfood.utils.ReFlashListView;
import com.fastfood.utils.ReFlashListView.IReflashListener;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyOrderTwoFragment extends Fragment implements IReflashListener{
	
	private View rooView;
	private ReFlashListView listView;
	private TextView tv;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rooView = inflater.inflate(R.layout.fragment_myorder_two, null);
		
		findId();
		tv.setText("啊偶...- -!没有订单,\n快去首页订购吧");
		
		return rooView;
	}
	
	public void findId() {
		tv = (TextView) rooView.findViewById(R.id.tv_myOrderTwo);
		listView = (ReFlashListView) rooView.findViewById(R.id.myOrderTwoListView1);
	}

	@Override
	public void onReflash() {
		// TODO Auto-generated method stub
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
		}, 2000);
	}
}

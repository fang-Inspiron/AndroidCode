package com.fragment;

import com.fastfood.R;
import com.fastfood.utils.ReFlashListView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.TextView;

public class MyOrderOneFragment extends Fragment{

	private View rooView;
	private ReFlashListView listView;
	private TextView tv;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rooView = inflater.inflate(R.layout.fragment_myorder_one, null);
		
		findId();
		tv.setText("��ż...- -!û�ж���,\n��ȥ��ҳ������");
		
		return rooView;
	}
	
	public void findId() {
		tv = (TextView) rooView.findViewById(R.id.tv_myOrderOne);
		listView = (ReFlashListView) rooView.findViewById(R.id.myOrderOneListView1);
	}
}
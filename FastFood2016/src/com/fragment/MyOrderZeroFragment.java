package com.fragment;

import com.fastfood.R;
import com.fastfood.utils.ReFlashListView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyOrderZeroFragment extends Fragment {

	private View rootView;
	private ReFlashListView listView;// ¶©µ¥ListView
	private TextView tv;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater.inflate(R.layout.fragment_myorder_zero, null);
		
		findId();
		tv.setText("°¡Å¼...- -!Ã»ÓÐ¶©µ¥,\n¿ìÈ¥Ê×Ò³¶©¹º°É");
		
		return rootView;
	}
	
	
	public void findId() {
		listView = (ReFlashListView) rootView.findViewById(R.id.myOrderZeroListView1);
		tv = (TextView) rootView.findViewById(R.id.tv_myOrderZero);
	}

}

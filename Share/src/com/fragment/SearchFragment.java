package com.fragment;

import com.example.share.R;
import com.example.share.ShangChuanActivity;
import com.fragment.HomeFragment.MyBaseAdapter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class SearchFragment extends Fragment {
	private View rootview;
	public static EditText et_search;
	private ImageView iv_search;
	private ImageView shangchuan;
	private LinearLayout search;
	private LinearLayout search_result;
	private ProgressDialog progressDialog;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			progressDialog.dismiss();
			search.setVisibility(View.GONE);
			search_result.setVisibility(View.VISIBLE);
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootview = inflater.inflate(R.layout.fragment_search, container, false);
		et_search = (EditText) rootview.findViewById(R.id.et_search);
		iv_search = (ImageView) rootview.findViewById(R.id.iv_search);
		shangchuan = (ImageView) rootview.findViewById(R.id.shangchuan);
		search = (LinearLayout) rootview.findViewById(R.id.search);
		search_result = (LinearLayout) rootview.findViewById(R.id.search_result);
		
		iv_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 跳转到搜索页面
				String searchStr = et_search.getText().toString();
				if (searchStr.equals("大白")) {
					progressDialog = new ProgressDialog(getActivity());
					progressDialog.setTitle("提示");
					progressDialog.setMessage("搜索中...");
					progressDialog.show();
					new Thread() {
						@Override
						public void run() {
							try {
								Thread.sleep(2000);
								handler.sendEmptyMessage(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}.start();
				}
			}
		});
		et_search.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				et_search.setText("");
				return false;
			}
		});
		
		shangchuan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), ShangChuanActivity.class);
				startActivity(intent);
			}
		});
		
		return rootview;
	}
	
	
	
}

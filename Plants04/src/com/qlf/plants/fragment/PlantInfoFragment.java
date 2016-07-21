package com.qlf.plants.fragment;

import com.qlf.plants.MainActivity;
import com.qlf.plants.R;
import com.qlf.plants.activity.Pop;
import com.qlf.plants.view.DefinedScrollView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 利用自定义的ScrollView加载视图来实现翻页效果，下面用页码和总页数标识当前的视图是第几屏
 * 
 * @author WANGXIAOHONG
 * 
 */
public class PlantInfoFragment extends Fragment implements OnClickListener {
	private LinearLayout mLinearLayout;
	private LinearLayout.LayoutParams param;
	private DefinedScrollView scrollView;
	private LayoutInflater inflater;
	private int pageCount = 0;

	private ImageView back;
	// 上下两个布局
	public View addview1 = null, addview2 = null;
	// 补光、浇水、加湿、通风
	private ImageView buguang, jiaoshui, jiashi, tongfeng;
	private View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView  = inflater.inflate(R.layout.plants_info_main, container, false);
		//setupView();
		back = (ImageView) rootView.findViewById(R.id.action_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.mLeftMenu.toggle();
			}
		});
		if (addview1 != null) {
			initView1();
		}
		if (addview2 != null) {
			initView2();
		}
		return rootView;
	}
	

	private void initView2() {

	}

	private void initView1() {
		buguang = (ImageView) addview1.findViewById(R.id.buguang);
		jiaoshui = (ImageView) addview1.findViewById(R.id.jiaoshui);
		jiashi = (ImageView) addview1.findViewById(R.id.jiashi);
		tongfeng = (ImageView) addview1.findViewById(R.id.tongfeng);

		buguang.setOnClickListener(this);
		jiaoshui.setOnClickListener(this);
		jiashi.setOnClickListener(this);
		tongfeng.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getActivity(), Pop.class);
		switch (v.getId()) {
		case R.id.buguang:
			intent.putExtra("id", 1);
			startActivity(intent);
			break;
		case R.id.jiaoshui:
			intent.putExtra("id", 2);
			startActivity(intent);
			break;
		case R.id.jiashi:
			intent.putExtra("id", 3);
			startActivity(intent);
			break;
		case R.id.tongfeng:
			intent.putExtra("id", 4);
			startActivity(intent);
			break;
		}
	}

//	private void setupView() {
//		scrollView = (DefinedScrollView) rootView.findViewById(R.id.definedview);
//		pageCount = 2;
//		for (int i = 0; i < pageCount; i++) {
//			param = new LinearLayout.LayoutParams(
//					android.view.ViewGroup.LayoutParams.FILL_PARENT,
//					android.view.ViewGroup.LayoutParams.FILL_PARENT);
//			inflater = getActivity().getLayoutInflater();
//
//			if (i == 0) {
//				addview1 = inflater.inflate(R.layout.plants_info_main_first,
//						null);
//				mLinearLayout = new LinearLayout(getActivity());
//				mLinearLayout.addView(addview1, param);
//				scrollView.addView(mLinearLayout);
//				ImageView back = (ImageView) addview1
//						.findViewById(R.id.action_back);
//				back.setOnClickListener(new OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						MainActivity.mLeftMenu.toggle();
//					}
//				});
//
//			} else {
//				addview2 = inflater.inflate(R.layout.plants_info_main_second,
//						null);
//				mLinearLayout = new LinearLayout(getActivity());
//				mLinearLayout.addView(addview2, param);
//				scrollView.addView(mLinearLayout);
//				ImageView back = (ImageView) addview2
//						.findViewById(R.id.action_back);
//				back.setOnClickListener(new OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						MainActivity.mLeftMenu.toggle();
//					}
//				});
//			}
//
//		}
//
//		scrollView.setPageListener(new DefinedScrollView.PageListener() {
//			@Override
//			public void page(int page) {
//			}
//		});
//	}

}
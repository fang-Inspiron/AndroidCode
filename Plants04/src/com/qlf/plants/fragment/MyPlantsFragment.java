package com.qlf.plants.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.qlf.plants.MainActivity;
import com.qlf.plants.R;
import com.qlf.plants.activity.PlantInfoActivity;
import com.qlf.plants.bean.PosBean;
import com.qlf.plants.scan.ScanCaptureAct;
import com.qlf.plants.thread.GetTerminalThread;
import com.qlf.plants.userdata.UserData;
import com.qlf.plants.utils.CommonAdapter;
import com.qlf.plants.utils.ViewHolder;
import com.qlf.plants.view.XListView;
import com.qlf.plants.view.XListView.IXListViewListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

public class MyPlantsFragment extends Fragment implements IXListViewListener {

	View rootView;
	ImageView back;
	public android.support.v4.app.FragmentManager fm;
	public FragmentTransaction transaction;

	private ImageView add;
	private XListView listView;
	public static LinearLayout plantView;
	public static Context context;
	private static List<PosBean> posData;
	public static MyPlantsFragment my;
	private CommonAdapter<PosBean> mAdapter;
	int i = 0;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == GetTerminalThread.SUCCESS) {
				Map<String, Object> data = (Map<String, Object>) msg.obj;
				if (Integer.parseInt(data.get("code").toString()) == 1) {
					List<Map<String, Object>> map = (List<Map<String, Object>>) data
							.get("data");
					posData = new ArrayList<PosBean>();
					for (int i = 0; i < map.size(); i++) {
						PosBean bean = new PosBean();
						bean.setTerminalName(map.get(i).get("terminalName")
								.toString());
						bean.setTerminalId(Integer.parseInt(map.get(i)
								.get("terminalId").toString()));
						bean.setSim(map.get(i).get("sim").toString());
						bean.setAddress(map.get(i).get("address").toString());
						posData.add(bean);

					}
					initList();
					onLoad();
				} else {
					Toast.makeText(getActivity(), data.get("data").toString(),
							Toast.LENGTH_SHORT).show();
				}
			} else if (msg.what == 0x123) {
				Toast.makeText(getActivity(), "请检查网络", Toast.LENGTH_LONG)
						.show();
			}
		};
	};
	private Handler mHandler;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater.inflate(R.layout.myplants_main, container, false);
		back = (ImageView) rootView.findViewById(R.id.action_back);
		add = (ImageView) rootView.findViewById(R.id.add_img);
		listView = (XListView) rootView.findViewById(R.id.plant_list);
		context = getActivity();
		my = this;
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Intent intent = new Intent();
					intent.setClass(getActivity(), ScanCaptureAct.class);
					startActivityForResult(intent, 30);
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(getActivity(), "相机打开失败,请检查相机是否可正常使用",
							Toast.LENGTH_LONG).show();
				}
			}
		});
		UserData userData = new UserData(getActivity());
		if (userData.getUserInfo() != null) {
			new GetTerminalThread(handler, Integer.parseInt(userData
					.getUserInfo().get("userId").toString()), userData
					.getUserInfo().get("certificate").toString()).start();
			back.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					MainActivity.mLeftMenu.toggle();
				}
			});
			initListView();
		}
		return rootView;
	}

	public static String getName(int id) {
		for (int i = 0; i < posData.size(); i++) {
			if (posData.get(i).getTerminalId() == id) {
				return posData.get(i).getTerminalName();
			}
		}
		return "";
	}

	public void update(){
		UserData userData = new UserData(getActivity());
		if (userData.getUserInfo() != null) {
			new GetTerminalThread(handler, Integer.parseInt(userData
					.getUserInfo().get("userId").toString()), userData
					.getUserInfo().get("certificate").toString()).start();
		}
	}
	
	public void initList() {
		listView.setAdapter(new CommonAdapter<PosBean>(getActivity(), posData,
				R.layout.plants_item) {
			@Override
			public void convert(ViewHolder holder, PosBean t) {
				holder.setText(R.id.plant_name, t.getTerminalName());
				holder.setImageResource(R.id.plant_img, R.drawable.head_image);
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(getActivity(),
						PlantInfoActivity.class);
				intent.putExtra("id", posData.get(arg2 - 1).getTerminalId());
				startActivity(intent);
			}
		});

	}

	@SuppressLint("NewApi")
	public void initPlants() {
		for (i = 0; i < posData.size(); i++) {
			ImageView iv = new ImageView(context);
			iv.setBackground(getResources().getDrawable(R.drawable.head_image));
			iv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					System.out.println("i--------->" + i);
					Intent intent = new Intent(context, PlantInfoActivity.class);
					intent.putExtra("id", posData.get(i).getTerminalId());
					startActivityForResult(intent, 0);

				}
			});
			TextView tv = new TextView(context);
			tv.setText(posData.get(i).getTerminalName());
			tv.setTextColor(getResources().getColor(R.color.white));
			LayoutParams params = new LayoutParams(dip2px(context, 80), dip2px(
					context, 80));
			iv.setId(i + 1);
			params.addRule(RelativeLayout.CENTER_HORIZONTAL,
					RelativeLayout.TRUE);
			params.topMargin = dip2px(context, 30);
			RelativeLayout layout = new RelativeLayout(context);

			layout.addView(iv, params);
			LayoutParams params1 = new LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			params1.addRule(RelativeLayout.CENTER_HORIZONTAL,
					RelativeLayout.TRUE);
			params1.addRule(RelativeLayout.BELOW, iv.getId());
			layout.addView(tv, params1);
			android.widget.LinearLayout.LayoutParams layoutParams = new android.widget.LinearLayout.LayoutParams(
					android.widget.LinearLayout.LayoutParams.FILL_PARENT,
					android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
			plantView.addView(layout, layoutParams);
		}
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {

		switch (requestCode) {
		case 30:
			if (resultCode == Activity.RESULT_OK) {
				String contents = intent.getStringExtra("SCAN_RESULT");
				Toast.makeText(getActivity(), contents, Toast.LENGTH_SHORT)
						.show();
				System.out.println(contents);
			} else if (resultCode == Activity.RESULT_CANCELED) {
				// Handle cancel
				Toast.makeText(getActivity(), "扫描失败", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		}

	}

	public void initListView() {
		// listView.setPullLoadEnable(false);
		listView.setAdapter(mAdapter);
		listView.setPullLoadEnable(false);//设置是否可以上拉加载
		// listView.setPullRefreshEnable(true);//设置是否可以下拉刷新
		listView.setXListViewListener(MyPlantsFragment.this);
		mHandler = new Handler();
	}

	private void geneItems() {
		UserData userData = new UserData(getActivity());
		new GetTerminalThread(handler, Integer.parseInt(userData.getUserInfo()
				.get("userId").toString()), userData.getUserInfo()
				.get("certificate").toString()).start();
	}

	private void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime("刚刚");
	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				posData.clear();
				geneItems();
				// mAdapter.notifyDataSetChanged();
				listView.setAdapter(new CommonAdapter<PosBean>(getActivity(),
						posData, R.layout.plants_item) {
					@Override
					public void convert(ViewHolder holder, PosBean t) {
						holder.setText(R.id.plant_name, t.getTerminalName());
						holder.setImageResource(R.id.plant_img,
								R.drawable.head_image);
					}
				});
				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				geneItems();
				mAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 500);
	}

}

package com.qlf.plants.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.qlf.plants.MainActivity;
import com.qlf.plants.R;
import com.qlf.plants.bean.PosBean;
import com.qlf.plants.scan.ScanCaptureAct;
import com.qlf.plants.thread.GetTerminalThread;
import com.qlf.plants.userdata.UserData;
import com.qlf.plants.utils.Bean;
import com.qlf.plants.utils.CommonAdapter;
import com.qlf.plants.utils.ViewHolder;

public class MyPosFragment extends Fragment {

	private View rootView;
	private ImageView back;
	private ListView posList;
	private List<PosBean> posData;
	private RelativeLayout pos_saomiao;

	Context me = this.getActivity();

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
					initData();
				} else {
					Toast.makeText(getActivity(), data.get("data").toString(),
							Toast.LENGTH_SHORT).show();
				}
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater.inflate(R.layout.mypos_main, container, false);
		back = (ImageView) rootView.findViewById(R.id.action_back);
		posList = (ListView) rootView.findViewById(R.id.pos_list);
		pos_saomiao = (RelativeLayout) rootView.findViewById(R.id.pos_saomiao);
		UserData userData = new UserData(getActivity());
		new GetTerminalThread(handler, Integer.parseInt(userData.getUserInfo()
				.get("userId").toString()),userData.getUserInfo().get("certificate").toString()).start();
		pos_saomiao.setOnClickListener(new OnClickListener() {
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
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.mLeftMenu.toggle();
			}
		});
		return rootView;
	}

	public void initData() {
		posList.setAdapter(new CommonAdapter<PosBean>(getActivity(), posData,
				R.layout.pos_item) {
			@Override
			public void convert(ViewHolder holder, PosBean t) {
				holder.setText(R.id.pos_name, t.getTerminalName());
				System.out.println( t.getTerminalName());
			}
		});
	}

	public void getData() {

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
}

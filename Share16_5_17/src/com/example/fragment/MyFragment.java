package com.example.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.bean.User;
import com.data.Queue;
import com.data.UserData;
import com.example.activity.ChangePassActivity;
import com.example.activity.MyInfoActivity;
import com.example.activity.MyRecomActivity;
import com.example.activity.MyUpLoadActivity;
import com.example.activity.SheZhiActivity;
import com.example.share.MainActivity;
import com.example.share4_15.R;
import com.example.utils.SDHelper;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyFragment extends Fragment implements OnClickListener {

	private View rootView;
	private RelativeLayout shuangchuan;
	private RelativeLayout xinxi;
	private RelativeLayout tuijian;
	private RelativeLayout yuanxi;
	private RelativeLayout shezhi;
	private RelativeLayout back;
	private ImageView my_image;
	private TextView my_name;
	private TextView my_classify;
	private TextView my_detail;
	public List<User> listUser = new ArrayList<User>();
	public Map<String, Bitmap> headImg = new HashMap<String, Bitmap>();
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			listUser =  (List<User>) msg.obj;
			ChangePassActivity.objectId = listUser.get(0).getObjectId();
			saveHeadImg();
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		MainActivity.rg_main_menu.setVisibility(View.VISIBLE);
		rootView = inflater.inflate(R.layout.myfragment, container, false);
		findViews();
		
		//获取数据
		geneItems();

		return rootView;
	}
	
	Handler imgHandler = new Handler() {
		final Map<String, Bitmap> bitmp = new HashMap<String, Bitmap>();
		final Map<String, String> path = new HashMap<String, String>();

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1001:
				path.put("0", (String)msg.obj);
				break;
			case 1002:
				bitmp.put(path.get("0"), (Bitmap) msg.obj);
				SDHelper helper = new SDHelper(getActivity());
				helper.createSDCardDir();
				helper.setMap(bitmp, path);
				try {
					helper.saveHeadBitmap();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		};
	};
	
	public void saveHeadImg() {
		SDHelper helper = new SDHelper(getActivity());
		try {
			headImg = helper.readHeadBitmap();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (!headImg.isEmpty() && headImg.size() > 0) {
			setHeadData();
		} else {
			initUserInfo();
			getHeadImg();
		}
	}
	
	
	
	public void getHeadImg() {
		User u = listUser.get(0);
		String url = u.getHeadImg().getFileUrl(getActivity());
		RequestQueue mQueue = Queue.getInstance();
		ImageRequest imageRequest = new ImageRequest(url,
				new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap response) {

						Message msg = new Message();
						msg.obj = "head";
						msg.what = 1001;
						imgHandler.sendMessage(msg);

						Message msg2 = new Message();
						msg2.obj = response;
						msg2.what = 1002;
						imgHandler.sendMessage(msg2);
					}
				}, 0, 0, Config.RGB_565, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
					}
				});
		mQueue.add(imageRequest);
	}
	
	public void setHeadData() {
		my_name.setText(UserData.getUserInfo(getActivity()).get("username").toString());
		my_classify.setText(listUser.get(0).getClassify());
		my_detail.setText(listUser.get(0).getSignature());
		if (headImg.get("head" + ".jpg") != null) {
			Bitmap bp = headImg.get("head" + ".jpg");
			my_image.setImageBitmap(bp);
		}
	}

	//得到数据后初始化信息
	public void initUserInfo() {
		my_name.setText(UserData.getUserInfo(getActivity()).get("username").toString());
		my_classify.setText(listUser.get(0).getClassify());
		my_detail.setText(listUser.get(0).getSignature());
		// 下载图片
		String url = listUser.get(0).getHeadImg().getFileUrl(getActivity());
		RequestQueue mQueue = Queue.getInstance();
		ImageRequest imageRequest = new ImageRequest(url,new Response.Listener<Bitmap>() {
			@Override
			public void onResponse(Bitmap response) {
				my_image.setImageBitmap(response);
			}
		}, 80, 80, Config.ARGB_8888, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				System.out.println(error.getMessage());
			}
		});
		mQueue.add(imageRequest);
	}

	// 获取数据
	public void geneItems() {
		BmobQuery<User> query = new BmobQuery<User>();
		// findObjects是通过内部代码线程实现的，不能在主线程中得到结果！
				query.findObjects(getActivity(), new FindListener<User>() {
					@Override
					public void onError(int arg0, String arg1) {
						System.out.println("查询失败"+arg1);
					}
					@Override
					public void onSuccess(List<User> object) {
						Message m = new Message();
						m.what = 1;
						m.obj = object;
						handler.sendMessage(m);
					}
				});
	}

	private void findViews() {
		my_image = (ImageView) rootView.findViewById(R.id.my_image);
		my_name = (TextView) rootView.findViewById(R.id.my_name);
		my_classify = (TextView) rootView.findViewById(R.id.my_classify);
		my_detail = (TextView) rootView.findViewById(R.id.my_detail);

		shuangchuan = (RelativeLayout) rootView
				.findViewById(R.id.my_shangchuan);
		xinxi = (RelativeLayout) rootView.findViewById(R.id.my_xinxi);
		tuijian = (RelativeLayout) rootView.findViewById(R.id.my_tuijian);
		yuanxi = (RelativeLayout) rootView.findViewById(R.id.my_yuanxi);
		shezhi = (RelativeLayout) rootView.findViewById(R.id.my_shezhi);
		back = (RelativeLayout) rootView.findViewById(R.id.my_back_ll);

		shuangchuan.setOnClickListener(this);
		xinxi.setOnClickListener(this);
		tuijian.setOnClickListener(this);
		yuanxi.setOnClickListener(this);
		shezhi.setOnClickListener(this);
		back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		// 我的上传
		case R.id.my_shangchuan:
			intent.setClass(getActivity(), MyUpLoadActivity.class);
			startActivity(intent);
			break;
		// 我的信息
		case R.id.my_xinxi:
			intent.setClass(getActivity(), MyInfoActivity.class);
			startActivity(intent);
			break;
		// 我推荐的
		case R.id.my_tuijian:
			intent.setClass(getActivity(), MyRecomActivity.class);
			startActivity(intent);
			break;
		// 院系通知
		case R.id.my_yuanxi:
			Toast.makeText(getActivity(), "暂无通知", Toast.LENGTH_SHORT).show();
			break;
		// 设置
		case R.id.my_shezhi:
			intent.setClass(getActivity(), SheZhiActivity.class);
			startActivity(intent);
			break;
		// 退出
		case R.id.my_back_ll:
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
					.setTitle("提示")
					.setMessage("确认退出?")
					.setPositiveButton("确认",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int arg1) {
									getActivity().finish();
									dialog.dismiss();
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int arg1) {
									dialog.dismiss();
								}
							});
			builder.create().show();
			break;

		}
	}
}

package com.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.example.share.HomeActivity1;
import com.example.share.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

public class HomeFragment extends Fragment {
	private View rootview;
	private ListView myList;
	private String[] str_name = new String[] { "假日", "国外画册欣赏","collection扁平设计", "版式整理术：高效解决多风格要求", "国外画册欣赏", "collection扁平设计","版式整理术：高效解决多风格要求" };
	private String[] str_author = new String[] { "share小白", "share小王","share小吕", "share小王", "share小王", "share小吕", "share小王" };
	private String[] str_design = new String[] { "原创-插画-练习习作", "平面设计-画册设计","平面设计-海报设计", "原创-摄影-练习习作", "平面设计-画册设计", "平面设计-海报设计", "原创-摄影-练习习作" };
	private String[] str_time = new String[] { "10分钟前", "16分钟前", "17分钟前","19分钟前", "20分钟前", "26分钟前", "50分钟前" };
	private int[] img = new int[] { R.drawable.list_img1, R.drawable.list_img2,R.drawable.list_img3, R.drawable.list_img4, R.drawable.list_img2,R.drawable.list_img3, R.drawable.list_img4 };
	private ImageView imageView;
	private TextView title;
	private TextView author;
	private TextView design;
	private TextView time;
	private ImageButton zan;
	private TextView zan_num;
	private ImageButton concern;
	private TextView concern_num;
	private ImageButton share;
	private TextView share_num;
	// 广告
	private ViewPager vp_main_ad = null;
	private List<View> adList;
	private ImageView iv1, iv2, iv3, iv4;
	private ImageView[] iv_circles;
	private ImageView iv_ad_circle_point;
	private ViewGroup viewGroup;
	// 并发
	private AtomicInteger atomicInteger = new AtomicInteger(0);
	private boolean isContinue = true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootview = inflater.inflate(R.layout.fragment_home, container, false);
		myList = (ListView) rootview.findViewById(R.id.myList);
		viewGroup = (ViewGroup) rootview.findViewById(R.id.ll_ad_main_group);
		vp_main_ad = (ViewPager) rootview.findViewById(R.id.vp_main_ad);
		myList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), HomeActivity1.class);
				startActivity(intent);
			}
		});
		myList.setAdapter(new MyBaseAdapter(getActivity()));
		adList = new ArrayList<View>();
		initImageList();
		initCirclePoint();
		return rootview;
	}

	class MyBaseAdapter extends BaseAdapter {

		LayoutInflater ll;

		public MyBaseAdapter(Context c) {
			// TODO Auto-generated constructor stub
			ll = LayoutInflater.from(c);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return str_name.length;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View view, ViewGroup arg2) {
			view = ll.inflate(R.layout.mylist_adapter, null);

			imageView = (ImageView) view.findViewById(R.id.imageView);
			title = (TextView) view.findViewById(R.id.title);
			author = (TextView) view.findViewById(R.id.author);
			design = (TextView) view.findViewById(R.id.design);
			time = (TextView) view.findViewById(R.id.time);
			zan = (ImageButton) view.findViewById(R.id.zan);
			zan_num = (TextView) view.findViewById(R.id.zan_num);
			concern = (ImageButton) view.findViewById(R.id.concern);
			concern_num = (TextView) view.findViewById(R.id.concern_num);
			share = (ImageButton) view.findViewById(R.id.share);
			share_num = (TextView) view.findViewById(R.id.share_num);

			imageView.setImageDrawable(getResources().getDrawable(img[position]));
			title.setText(str_name[position]);
			author.setText(str_author[position]);
			design.setText(str_design[position]);
			time.setText(str_time[position]);
			zan.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int i = Integer.parseInt(zan_num.getText().toString());
					i++;
					zan_num.setText(Integer.toString(i));
				}
			});
			concern.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int i = Integer.parseInt(concern_num.getText().toString());
					i++;
					concern_num.setText(Integer.toString(i));
				}
			});
			share.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int i = Integer.parseInt(share_num.getText().toString());
					i++;
					share_num.setText(Integer.toString(i));
				}
			});

			return view;
		}

	}

	/**
	 * （并发）更新当前位置
	 */
	private void atomicOption() {
		atomicInteger.incrementAndGet();
		if (atomicInteger.get() > iv_circles.length - 1) {
			atomicInteger.getAndAdd(-4);
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
	}

	// 圆点滚动
	public class imageListOnListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int position) {
			atomicInteger.getAndSet(position);
			for (int i = 0; i < iv_circles.length; i++) {
				iv_circles[position]
						.setBackgroundResource(R.drawable.point_pressed);
				if (position != i) {
					iv_circles[i]
							.setBackgroundResource(R.drawable.point_unpressed);
				}
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

	}

	// 初始化广告List
	private void initImageList() {
		iv1 = new ImageView(getActivity());
		iv1.setBackgroundResource(R.drawable.main_img1);
		adList.add(iv1);
		iv2 = new ImageView(getActivity());
		iv2.setBackgroundResource(R.drawable.main_img2);
		adList.add(iv2);
		iv3 = new ImageView(getActivity());
		iv3.setBackgroundResource(R.drawable.main_img3);
		adList.add(iv3);
		iv4 = new ImageView(getActivity());
		iv4.setBackgroundResource(R.drawable.main_img4);
		adList.add(iv4);
		vp_main_ad.setAdapter(new AdPagerAdapter());
		vp_main_ad.setOnPageChangeListener(new imageListOnListener());
	}

	private void initCirclePoint() {
		LayoutParams params;
		System.out.println(adList.size());
		iv_circles = new ImageView[adList.size()];//对iv_circles进行填充
		for (int i = 0; i < iv_circles.length; i++) {
			iv_ad_circle_point = new ImageView(getActivity());
			iv_ad_circle_point.setLayoutParams(new LayoutParams(20, 20));

			iv_circles[i] = iv_ad_circle_point;
			if (i == 0) {
				iv_circles[i].setBackgroundResource(R.drawable.point_pressed);
			} else {
				iv_circles[i].setBackgroundResource(R.drawable.point_unpressed);
			}
			viewGroup.addView(iv_circles[i]);
			params = (LayoutParams) iv_circles[i].getLayoutParams();
			params.leftMargin = 20;
			params.bottomMargin = 10;
			iv_circles[i].setLayoutParams(params);
		}

		new Thread(new RefreshAdCircleTask()).start();
	}

	public class RefreshAdCircleTask implements Runnable {

		@Override
		public void run() {
			while (true) {
				if (isContinue) {
					imageHandler.sendEmptyMessage(atomicInteger.get());
					atomicOption();
				}
			}
		}
	}

	// 广告list的Adapter
	public class AdPagerAdapter extends PagerAdapter {
		// 获得页面总数
		@Override
		public int getCount() {
			return 4;
		}

		// 判断 view和object的对应关系
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		/*
		 * 获得相应位置上的view container view的容器 其实就是viewpager自身 position 相应的位置
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(adList.get(position));
			return adList.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(adList.get(position));
		}
	}

	// 页面滑动
	private Handler imageHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (isContinue) {
				vp_main_ad.setCurrentItem(msg.what);
			}
		};
	};

}

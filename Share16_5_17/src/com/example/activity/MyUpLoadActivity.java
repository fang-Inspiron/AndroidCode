package com.example.activity;

import com.example.share4_15.R;
import com.example.utils.ScreenUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class MyUpLoadActivity extends Activity implements OnClickListener{

	
	private ListView list;
	
	private Button toTopBtn;// 返回顶部的按钮
	private boolean scrollFlag = false;// 标记是否滑动
	private int lastVisibleItemPosition = 0;// 标记上次滑动位置
	
	private ImageView imageView;
	private ImageButton zan;
	private ImageButton guanzhu;
	private ImageButton share;
	private ImageButton back;
	private TextView name;
	private TextView author;
	private TextView detail;
	private TextView time;
	private TextView zCount;
	private TextView gCount;
	private TextView sCount;
	
	private TextView loadtime;
	private TextView tuijian;
	private TextView sharemax;
	
	
	private String[] str_name = new String[]{"假日","国外画册欣赏","collection扁平设计","版式整理术：高效解决多风格要求","国外画册欣赏","collection扁平设计","版式整理术：高效解决多风格要求"};
	private String[] str_author = new String[]{"share小白","share小王","share小吕","share小王","share小王","share小吕","share小王"};
	private String[] str_detail = new String[]{"原创-插画-练习习作","平面设计-画册设计","平面设计-海报设计","原创-摄影-练习习作","平面设计-画册设计","平面设计-海报设计","原创-摄影-练习习作"};
	private String[] str_time = new String[]{"10分钟前","16分钟前","17分钟前","19分钟前","20分钟前","26分钟前","50分钟前"};
	private int[] img = new int[]{R.drawable.list_img1,R.drawable.list_img2,R.drawable.list_img3,R.drawable.list_img4,R.drawable.list_img2,R.drawable.list_img3,R.drawable.list_img4};
	
	private String[] str_name1 = new String[]{"假日","版式整理术：高效解决多风格要求","国外画册欣赏","collection扁平设计","国外画册欣赏","collection扁平设计","版式整理术：高效解决多风格要求"};
	private String[] str_author1 = new String[]{"share小白","share小王","share小王","share小吕","share小王","share小吕","share小王"};
	private String[] str_detail1 = new String[]{"原创-插画-练习习作","原创-摄影-练习习作","平面设计-画册设计","平面设计-海报设计","平面设计-画册设计","平面设计-海报设计","原创-摄影-练习习作"};
	private String[] str_time1 = new String[]{"10分钟前","16分钟前","17分钟前","19分钟前","20分钟前","26分钟前","50分钟前"};
	private int[] img1 = new int[]{R.drawable.list_img1,R.drawable.list_img4,R.drawable.list_img2,R.drawable.list_img3,R.drawable.list_img2,R.drawable.list_img3,R.drawable.list_img4};
	
	private String[] str_name2 = new String[]{"collection扁平设计","假日","国外画册欣赏","版式整理术：高效解决多风格要求","国外画册欣赏","collection扁平设计","版式整理术：高效解决多风格要求"};
	private String[] str_author2 = new String[]{"share小吕","share小白","share小王","share小王","share小王","share小吕","share小王"};
	private String[] str_detail2 = new String[]{"平面设计-海报设计","原创-插画-练习习作","平面设计-画册设计","原创-摄影-练习习作","平面设计-画册设计","平面设计-海报设计","原创-摄影-练习习作"};
	private String[] str_time2 = new String[]{"10分钟前","16分钟前","17分钟前","19分钟前","20分钟前","26分钟前","50分钟前"};
	private int[] img2 = new int[]{R.drawable.list_img3,R.drawable.list_img1,R.drawable.list_img2,R.drawable.list_img4,R.drawable.list_img2,R.drawable.list_img3,R.drawable.list_img4};
	int a=102,b=20,c=26;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 无标题
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload_main);
		loadtime = (TextView) findViewById(R.id.uploadtime);
		tuijian = (TextView) findViewById(R.id.tuijian);
		sharemax = (TextView) findViewById(R.id.sharemax);
		back = (ImageButton) findViewById(R.id.upload_back);
		toTopBtn = (Button) findViewById(R.id.up_top_btn);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		loadtime.setTextColor(Color.GRAY);
		loadtime.setOnClickListener(this);
		tuijian.setOnClickListener(this);
		sharemax.setOnClickListener(this);
		list = (ListView) findViewById(R.id.upload_list);
		list.setAdapter(new MyAdapter(this,str_name,str_author,str_detail,str_time,img));
		initView();
	}
	
	/**
	 * 初始化视图
	 */
	private void initView() {
		
		toTopBtn.setOnClickListener(this);
		list.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				switch (scrollState) {
				// 当不滚动时
				case OnScrollListener.SCROLL_STATE_IDLE:// 是当屏幕停止滚动时
					scrollFlag = false;
					// 判断滚动到底部
					if (list.getLastVisiblePosition() == (list
							.getCount() - 1)) {
						toTopBtn.setVisibility(View.VISIBLE);
					}
					// 判断滚动到顶部
					if (list.getFirstVisiblePosition() == 0) {
						toTopBtn.setVisibility(View.GONE);
					}

					break;
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动时
					scrollFlag = true;
					break;
				case OnScrollListener.SCROLL_STATE_FLING:// 是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时
					scrollFlag = false;
					break;
				}
			}

			/**
			 * firstVisibleItem：当前能看见的第一个列表项ID（从0开始）
			 * visibleItemCount：当前能看见的列表项个数（小半个也算） totalItemCount：列表项共数
			 */
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// 当开始滑动且ListView底部的Y轴点超出屏幕最大范围时，显示或隐藏顶部按钮
				if (scrollFlag
						&& ScreenUtil.getScreenViewBottomHeight(list) >= ScreenUtil
								.getScreenHeight(getApplicationContext())) {
					if (firstVisibleItem > lastVisibleItemPosition) {// 上滑
						toTopBtn.setVisibility(View.VISIBLE);
					} else if (firstVisibleItem < lastVisibleItemPosition) {// 下滑
						toTopBtn.setVisibility(View.GONE);
					} else {
						return;
					}
					lastVisibleItemPosition = firstVisibleItem;
				}
			}
		});
	}

	/**
	 * 滚动ListView到指定位置
	 * 
	 * @param pos
	 */
	private void setListViewPos(int pos) {
		if (android.os.Build.VERSION.SDK_INT >= 8) {
			list.smoothScrollToPosition(pos);
		} else {
			list.setSelection(pos);
		}
	}

	
	
	class MyAdapter extends BaseAdapter{

		String[] name1;
		String[] author1;
		String[] detail1;
		String[] time1;
		int[] img1;
		LayoutInflater ll;

		public MyAdapter(Context context,String[] name,String[] author,String[] detail,String[] time,int[] img) {
			// TODO Auto-generated constructor stub
			this.ll = LayoutInflater.from(context);
			this.name1 = name;
			this.author1 = author;
			this.detail1 = detail;
			this.time1 = time;
			this.img1 = img;
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
			return arg0;
		}

		@SuppressLint({ "ViewHolder", "InflateParams" }) @Override
		public View getView(int arg0, View convertView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			convertView = (LinearLayout) ll.inflate(R.layout.home_list_item,null);
			imageView = (ImageView) convertView.findViewById(R.id.item_image);
			name = (TextView) convertView.findViewById(R.id.item_name);
			author = (TextView) convertView.findViewById(R.id.item_author);
			detail = (TextView) convertView.findViewById(R.id.item_detail);
			time = (TextView) convertView.findViewById(R.id.item_time);
			zCount = (TextView) convertView.findViewById(R.id.item_zanCount);
			gCount = (TextView) convertView.findViewById(R.id.item_guanzhuCount);
			sCount = (TextView) convertView.findViewById(R.id.item_shareCount);
		
			zan = (ImageButton) convertView.findViewById(R.id.item_zan);
			guanzhu = (ImageButton) convertView.findViewById(R.id.item_guanzhu);
			share = (ImageButton) convertView.findViewById(R.id.item_share);
			
			imageView.setImageDrawable(getResources().getDrawable(img1[arg0]));
			name.setText(str_name1[arg0]);
			author.setText(str_author1[arg0]);
			detail.setText(str_detail1[arg0]);
			time.setText(str_time1[arg0]);
			
			zan.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					View view = (View) v.getParent();
					zCount = (TextView) view.findViewById(R.id.item_zanCount);
					String str = zCount.getText().toString();
					int i = Integer.parseInt(str);
					String s = Integer.toString(i+1);
					zCount.setText(s);
				}

				
			});
			guanzhu.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					View view = (View) v.getParent();
					gCount = (TextView) view.findViewById(R.id.item_guanzhuCount);
					String str = gCount.getText().toString();
					int i = Integer.parseInt(str);
					String s = Integer.toString(i+1);
					gCount.setText(s);
				}
			});
			share.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					View view = (View) v.getParent();
					sCount = (TextView) view.findViewById(R.id.item_shareCount);
					String str = sCount.getText().toString();
					int i = Integer.parseInt(str);
					String s = Integer.toString(i+1);
					sCount.setText(s);
				}
			});
			return convertView;
		}
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.uploadtime:
			loadtime.setTextColor(Color.GRAY);
			tuijian.setTextColor(Color.WHITE);
			sharemax.setTextColor(Color.WHITE);
			list.setAdapter(new MyAdapter(this, str_name1, str_author1, str_detail1, str_time1, img1));
			break;
		case R.id.tuijian:
			tuijian.setTextColor(Color.GRAY);
			sharemax.setTextColor(Color.WHITE);
			loadtime.setTextColor(Color.WHITE);
			list.setAdapter(new MyAdapter(this, str_name2, str_author2, str_detail2, str_time2, img2));
			break;
		case R.id.sharemax:
			sharemax.setTextColor(Color.GRAY);
			loadtime.setTextColor(Color.WHITE);
			tuijian.setTextColor(Color.WHITE);
			list.setAdapter(new MyAdapter(this, str_name, str_author, str_detail, str_time, img));
			break;
		case R.id.up_top_btn:// 点击按钮返回到ListView的第一项
			setListViewPos(0);
			break;
		}
	}
}

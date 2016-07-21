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

public class MyRecomActivity extends Activity implements OnClickListener{

	private ListView list;
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
	
	private Button toTopBtn;// ���ض����İ�ť
	private boolean scrollFlag = false;// ����Ƿ񻬶�
	private int lastVisibleItemPosition = 0;// ����ϴλ���λ��
	
	private String[] str_name = new String[]{"����","���⻭������","collection��ƽ���","��ʽ����������Ч�������Ҫ��","���⻭������","collection��ƽ���","��ʽ����������Ч�������Ҫ��"};
	private String[] str_author = new String[]{"shareС��","shareС��","shareС��","shareС��","shareС��","shareС��","shareС��"};
	private String[] str_detail = new String[]{"ԭ��-�廭-��ϰϰ��","ƽ�����-�������","ƽ�����-�������","ԭ��-��Ӱ-��ϰϰ��","ƽ�����-�������","ƽ�����-�������","ԭ��-��Ӱ-��ϰϰ��"};
	private String[] str_time = new String[]{"10����ǰ","16����ǰ","17����ǰ","19����ǰ","20����ǰ","26����ǰ","50����ǰ"};
	private int[] img = new int[]{R.drawable.list_img1,R.drawable.list_img2,R.drawable.list_img3,R.drawable.list_img4,R.drawable.list_img2,R.drawable.list_img3,R.drawable.list_img4};
	
	int a=102,b=20,c=26;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE); // �ޱ���
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recommend_main);
		list = (ListView) findViewById(R.id.recommend_list);
		back = (ImageButton) findViewById(R.id.recom_back);
		toTopBtn = (Button) findViewById(R.id.recom_top_btn);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		list.setAdapter(new MyAdapter(getApplicationContext()));
		initView();
	}
	
	/**
	 * ��ʼ����ͼ
	 */
	private void initView() {
		
		toTopBtn.setOnClickListener(this);
		list.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				switch (scrollState) {
				// ��������ʱ
				case OnScrollListener.SCROLL_STATE_IDLE:// �ǵ���Ļֹͣ����ʱ
					scrollFlag = false;
					// �жϹ������ײ�
					if (list.getLastVisiblePosition() == (list
							.getCount() - 1)) {
						toTopBtn.setVisibility(View.VISIBLE);
					}
					// �жϹ���������
					if (list.getFirstVisiblePosition() == 0) {
						toTopBtn.setVisibility(View.GONE);
					}

					break;
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// ����ʱ
					scrollFlag = true;
					break;
				case OnScrollListener.SCROLL_STATE_FLING:// �ǵ��û�����֮ǰ������Ļ��̧����ָ����Ļ�������Ի���ʱ
					scrollFlag = false;
					break;
				}
			}

			/**
			 * firstVisibleItem����ǰ�ܿ����ĵ�һ���б���ID����0��ʼ��
			 * visibleItemCount����ǰ�ܿ������б��������С���Ҳ�㣩 totalItemCount���б����
			 */
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// ����ʼ������ListView�ײ���Y��㳬����Ļ���Χʱ����ʾ�����ض�����ť
				if (scrollFlag
						&& ScreenUtil.getScreenViewBottomHeight(list) >= ScreenUtil
								.getScreenHeight(getApplicationContext())) {
					if (firstVisibleItem > lastVisibleItemPosition) {// �ϻ�
						toTopBtn.setVisibility(View.VISIBLE);
					} else if (firstVisibleItem < lastVisibleItemPosition) {// �»�
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
	 * ����ListView��ָ��λ��
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.recom_top_btn:// �����ť���ص�ListView�ĵ�һ��
			setListViewPos(0);
			break;
		}
	}
	
	class MyAdapter extends BaseAdapter{

		LayoutInflater ll;
		public MyAdapter(Context context) {
			// TODO Auto-generated constructor stub
			this.ll = LayoutInflater.from(context);
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
			
			name.setTextColor(Color.BLACK);
			author.setTextColor(Color.BLACK);
			detail.setTextColor(Color.BLACK);
			time.setTextColor(Color.BLACK);
			
			zCount = (TextView) convertView.findViewById(R.id.item_zanCount);
			gCount = (TextView) convertView.findViewById(R.id.item_guanzhuCount);
			sCount = (TextView) convertView.findViewById(R.id.item_shareCount);
		
			zan = (ImageButton) convertView.findViewById(R.id.item_zan);
			guanzhu = (ImageButton) convertView.findViewById(R.id.item_guanzhu);
			share = (ImageButton) convertView.findViewById(R.id.item_share);
			
			imageView.setImageDrawable(getResources().getDrawable(img[arg0]));
			name.setText(str_name[arg0]);
			author.setText(str_author[arg0]);
			detail.setText(str_detail[arg0]);
			time.setText(str_time[arg0]);
			
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
	
}

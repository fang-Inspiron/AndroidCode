package com.example.share;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class UploadActivity extends Activity implements OnClickListener {
	private ListView myList;
	private String[] str_name = new String[] { "匆匆那年", "关于设计反馈的五件事","用户设计PK战！脸书VS人人", "字体故事", "关于设计反馈的五件事", "用户设计PK战！脸书VS人人","字体故事" };
	private String[] str_author = new String[] { "share小白", "share小王","share小吕", "share小王", "share小王", "share小吕", "share小王" };
	private String[] str_design = new String[] { "原创-插画-练习习作", "设计文章-原创-理论","平面设计-海报设计", "原创-摄影-练习习作", "设计文章-原创-理论", "平面设计-海报设计", "原创-摄影-练习习作" };
	private String[] str_time = new String[] { "10分钟前", "16分钟前", "17分钟前","19分钟前", "20分钟前", "26分钟前", "50分钟前" };
	private int[] img = new int[] { R.drawable.list_img1, R.drawable.note_img1,R.drawable.note_img2, R.drawable.note_img3, R.drawable.note_img1,R.drawable.note_img2, R.drawable.list_img4 };
	private String[] str_name2 = new String[] { "字体故事","用户设计PK战！脸书VS人人", "关于设计反馈的五件事", "匆匆那年", "关于设计反馈的五件事", "用户设计PK战！脸书VS人人","关于设计反馈的五件事" };
	private String[] str_author2 = new String[] { "share小王", "share小吕","share小王", "share小白", "share小王", "share小吕", "share小王" };
	private String[] str_design2 = new String[] { "原创-摄影-练习习作", "平面设计-海报设计","设计文章-原创-理论", "原创-插画-练习习作", "设计文章-原创-理论", "平面设计-海报设计", "设计文章-原创-理论" };
	private String[] str_time2 = new String[] { "10分钟前", "16分钟前", "17分钟前","19分钟前", "20分钟前", "26分钟前", "50分钟前" };
	private int[] img2 = new int[] { R.drawable.list_img4,R.drawable.note_img2, R.drawable.note_img1, R.drawable.list_img1,R.drawable.note_img1, R.drawable.note_img2, R.drawable.note_img3 };
	private String[] str_name3 = new String[] { "用户设计PK战！脸书VS人人", "关于设计反馈的五件事","匆匆那年", "字体故事", "关于设计反馈的五件事", "匆匆那年", "字体故事" };
	private String[] str_author3 = new String[] { "share小吕", "share小王","share小白", "share小王", "share小王", "share小白", "share小王" };
	private String[] str_design3 = new String[] { "平面设计-海报设计", "设计文章-原创-理论","原创-插画-练习习作", "原创-摄影-练习习作", "设计文章-原创-理论", "原创-插画-练习习作", "原创-摄影-练习习作" };
	private String[] str_time3 = new String[] { "10分钟前", "16分钟前", "17分钟前","19分钟前", "20分钟前", "26分钟前", "50分钟前" };
	private int[] img3 = new int[] { R.drawable.note_img2,R.drawable.note_img1, R.drawable.note_img2, R.drawable.list_img4,R.drawable.note_img3, R.drawable.list_img1, R.drawable.list_img4 };
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
	private TextView uploadTime;
	private TextView recommandMost;
	private TextView shareMost;
	private MyBaseAdapter myAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_upload);
		
		uploadTime = (TextView) findViewById(R.id.uploadTime);
		recommandMost = (TextView) findViewById(R.id.recommandMost);
		shareMost = (TextView) findViewById(R.id.shareMost);
		uploadTime.setOnClickListener(this);
		recommandMost.setOnClickListener(this);
		shareMost.setOnClickListener(this);
		
		myList = (ListView) findViewById(R.id.myList);
		myAdapter = new MyBaseAdapter(this, str_name, str_author,str_design, str_time, img);
		myList.setAdapter(myAdapter);
	}
	
	class MyBaseAdapter extends BaseAdapter {
		LayoutInflater ll;
		private String[] str_name1;
		private String[] str_author1;
		private String[] str_design1;
		private String[] str_time1;
		private int[] img1;

		public MyBaseAdapter(Context c, String[] str_name1,
				String[] str_author1, String[] str_design1, String[] str_time1,
				int[] img1) {
			super();
			ll = LayoutInflater.from(c);
			this.str_name1 = str_name1;
			this.str_author1 = str_author1;
			this.str_design1 = str_design1;
			this.str_time1 = str_time1;
			this.img1 = img1;
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
			// TODO Auto-generated method stub
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
			imageView.setImageDrawable(getResources().getDrawable(
					img1[position]));
			title.setText(str_name1[position]);
			author.setText(str_author1[position]);
			design.setText(str_design1[position]);
			time.setText(str_time1[position]);

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

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.uploadTime:
			uploadTime.setTextColor(Color.GRAY);
			recommandMost.setTextColor(Color.WHITE);
			shareMost.setTextColor(Color.WHITE);
			myList.setAdapter(new MyBaseAdapter(this, str_name2,
					str_author2, str_design2, str_time2, img2));
			break;
		case R.id.recommandMost:
			uploadTime.setTextColor(Color.WHITE);
			recommandMost.setTextColor(Color.GRAY);
			shareMost.setTextColor(Color.WHITE);
			myList.setAdapter(new MyBaseAdapter(this, str_name3,
					str_author3, str_design3, str_time3, img3));
			break;
		case R.id.shareMost:
			uploadTime.setTextColor(Color.WHITE);
			recommandMost.setTextColor(Color.WHITE);
			shareMost.setTextColor(Color.GRAY);
			myList.setAdapter(new MyBaseAdapter(this, str_name,
					str_author, str_design, str_time, img));
			break;

		default:
			break;
		}
	}
	
}

package com.example.share;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class RecommendActivity extends Activity{
	private ListView myList;
	private String[] str_name = new String[] { "匆匆那年", "国外画册欣赏","collection扁平设计", "版式整理术：高效解决多风格要求", "国外画册欣赏", "collection扁平设计","版式整理术：高效解决多风格要求" };
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_recommend);
		
		myList = (ListView) findViewById(R.id.myList);
		myList.setAdapter(new MyBaseAdapter(this));
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

			imageView.setImageDrawable(getResources()
					.getDrawable(img[position]));
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
}

package com.fragment;

import com.example.share.R;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class EssayFragment extends Fragment implements OnClickListener {
	private View rootview;
	private ListView myList;
	private String[] str_name = new String[] { "假日", "国外画册欣赏","collection扁平设计", "版式整理术：高效解决多风格要求", "国外画册欣赏", "collection扁平设计","版式整理术：高效解决多风格要求" };
	private String[] str_author = new String[] { "share小白", "share小王","share小吕", "share小王", "share小王", "share小吕", "share小王" };
	private String[] str_design = new String[] { "原创-插画-练习习作", "平面设计-画册设计","平面设计-海报设计", "原创-摄影-练习习作", "平面设计-画册设计", "平面设计-海报设计", "原创-摄影-练习习作" };
	private String[] str_time = new String[] { "10分钟前", "16分钟前", "17分钟前","19分钟前", "20分钟前", "26分钟前", "50分钟前" };
	private int[] img = new int[] { R.drawable.list_img1, R.drawable.list_img2,R.drawable.list_img3, R.drawable.list_img4, R.drawable.list_img2,R.drawable.list_img3, R.drawable.list_img4 };
	private String[] str_name2 = new String[] { "版式整理术：高效解决多风格要求","collection扁平设计", "国外画册欣赏", "假日", "国外画册欣赏", "collection扁平设计","国外画册欣赏" };
	private String[] str_author2 = new String[] { "share小王", "share小吕","share小王", "share小白", "share小王", "share小吕", "share小王" };
	private String[] str_design2 = new String[] { "原创-摄影-练习习作", "平面设计-海报设计","平面设计-画册设计", "原创-插画-练习习作", "平面设计-画册设计", "平面设计-海报设计", "平面设计-画册设计" };
	private String[] str_time2 = new String[] { "10分钟前", "16分钟前", "17分钟前","19分钟前", "20分钟前", "26分钟前", "50分钟前" };
	private int[] img2 = new int[] { R.drawable.list_img4,R.drawable.list_img3, R.drawable.list_img2, R.drawable.list_img1,R.drawable.list_img2, R.drawable.list_img3, R.drawable.list_img2 };
	private String[] str_name3 = new String[] { "collection扁平设计", "国外画册欣赏","假日", "版式整理术：高效解决多风格要求", "国外画册欣赏", "假日", "版式整理术：高效解决多风格要求" };
	private String[] str_author3 = new String[] { "share小吕", "share小王","share小白", "share小王", "share小王", "share小白", "share小王" };
	private String[] str_design3 = new String[] { "平面设计-海报设计", "平面设计-画册设计","原创-插画-练习习作", "原创-摄影-练习习作", "平面设计-画册设计", "原创-插画-练习习作", "原创-摄影-练习习作" };
	private String[] str_time3 = new String[] { "10分钟前", "16分钟前", "17分钟前","19分钟前", "20分钟前", "26分钟前", "50分钟前" };
	private int[] img3 = new int[] { R.drawable.list_img3,R.drawable.list_img2, R.drawable.list_img1, R.drawable.list_img4,R.drawable.list_img2, R.drawable.list_img1, R.drawable.list_img4 };
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
	private TextView sift;
	private TextView hot;
	private TextView all;
	private MyBaseAdapter myAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootview = inflater.inflate(R.layout.fragment_essay, container, false);
		sift = (TextView) rootview.findViewById(R.id.sift);
		hot = (TextView) rootview.findViewById(R.id.hot);
		all = (TextView) rootview.findViewById(R.id.all);
		sift.setOnClickListener(this);
		hot.setOnClickListener(this);
		all.setOnClickListener(this);

		myList = (ListView) rootview.findViewById(R.id.myList);
		myAdapter = new MyBaseAdapter(getActivity(), str_name, str_author,
				str_design, str_time, img);
		myList.setAdapter(myAdapter);

		return rootview;
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
		case R.id.sift:
			sift.setTextColor(Color.GRAY);
			hot.setTextColor(Color.WHITE);
			all.setTextColor(Color.WHITE);
			myList.setAdapter(new MyBaseAdapter(getActivity(), str_name2,
					str_author2, str_design2, str_time2, img2));
			break;
		case R.id.hot:
			sift.setTextColor(Color.WHITE);
			hot.setTextColor(Color.GRAY);
			all.setTextColor(Color.WHITE);
			myList.setAdapter(new MyBaseAdapter(getActivity(), str_name3,
					str_author3, str_design3, str_time3, img3));
			break;
		case R.id.all:
			sift.setTextColor(Color.WHITE);
			hot.setTextColor(Color.WHITE);
			all.setTextColor(Color.GRAY);
			myList.setAdapter(new MyBaseAdapter(getActivity(), str_name,
					str_author, str_design, str_time, img));
			break;

		default:
			break;
		}
	}

}

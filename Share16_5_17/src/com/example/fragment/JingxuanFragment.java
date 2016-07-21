package com.example.fragment;

import com.example.share4_15.R;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class JingxuanFragment extends Fragment implements OnClickListener {

	private View rootView;
	private ListView list;
	private ImageView imageView;
	private ImageButton zan;
	private ImageButton guanzhu;
	private ImageButton share;
	private TextView name;
	private TextView author;
	private TextView detail;
	private TextView time;
	private TextView zCount;
	private TextView gCount;
	private TextView sCount;

	private String[] str_name = new String[] { "关于设计反馈的5件事", "用户设计PK战!脸书VS人人",
			"字体故事", "版式整理术：高效解决多风格要求" };
	private String[] str_author = new String[] { "share小白", "share小王",
			"share小吕", "share小王" };
	private String[] str_detail = new String[] { "设计文章-原创-理论",
			"设计文章-原创-Web/Flash", "设计文章-经验-设计观点", "设计文章-经验-案例分析" };
	private String[] str_time = new String[] { "10分钟前", "16分钟前", "17分钟前",
			"50分钟前" };
	private int[] img = new int[] { R.drawable.note_img1, R.drawable.note_img2,
			R.drawable.note_img3, R.drawable.note_img4 };

	int a = 102, b = 20, c = 26;

	private TextView jingxuan;
	private TextView remen;
	private TextView quanbu;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater.inflate(R.layout.note_fragment, null);
		list = (ListView) rootView.findViewById(R.id.note_list);
		jingxuan = (TextView) rootView.findViewById(R.id.jingxuan);
		remen = (TextView) rootView.findViewById(R.id.remen);
		quanbu = (TextView) rootView.findViewById(R.id.quanbu);
		quanbu.setTextColor(Color.GRAY);
		jingxuan.setOnClickListener(this);
		remen.setOnClickListener(this);
		quanbu.setOnClickListener(this);
		list.setAdapter(new MyAdapter(getActivity()));
		return rootView;
	}

	// 监听器

	// listview的适配
	class MyAdapter extends BaseAdapter {

		LayoutInflater ll;

		public MyAdapter(Context context) {
			// TODO Auto-generated constructor stub
			this.ll = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 4;
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

		@Override
		public View getView(int arg0, View convertView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			convertView = (LinearLayout) ll.inflate(R.layout.home_list_item,
					null);
			imageView = (ImageView) convertView.findViewById(R.id.item_image);
			name = (TextView) convertView.findViewById(R.id.item_name);
			author = (TextView) convertView.findViewById(R.id.item_author);
			detail = (TextView) convertView.findViewById(R.id.item_detail);
			time = (TextView) convertView.findViewById(R.id.item_time);
			zCount = (TextView) convertView.findViewById(R.id.item_zanCount);
			gCount = (TextView) convertView
					.findViewById(R.id.item_guanzhuCount);
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
					String s = Integer.toString(i + 1);
					zCount.setText(s);
				}

			});
			guanzhu.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					View view = (View) v.getParent();
					gCount = (TextView) view
							.findViewById(R.id.item_guanzhuCount);
					String str = gCount.getText().toString();
					int i = Integer.parseInt(str);
					String s = Integer.toString(i + 1);
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
					String s = Integer.toString(i + 1);
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
		case R.id.jingxuan:
			jingxuan.setTextColor(Color.GRAY);
			remen.setTextColor(Color.WHITE);
			quanbu.setTextColor(Color.WHITE);
			break;
		case R.id.remen:
			remen.setTextColor(Color.GRAY);
			jingxuan.setTextColor(Color.WHITE);
			quanbu.setTextColor(Color.WHITE);
			break;
		case R.id.quanbu:
			quanbu.setTextColor(Color.GRAY);
			remen.setTextColor(Color.WHITE);
			jingxuan.setTextColor(Color.WHITE);
			break;
		}
	}

}

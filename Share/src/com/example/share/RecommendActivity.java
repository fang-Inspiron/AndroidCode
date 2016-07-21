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
	private String[] str_name = new String[] { "�Ҵ�����", "���⻭������","collection��ƽ���", "��ʽ����������Ч�������Ҫ��", "���⻭������", "collection��ƽ���","��ʽ����������Ч�������Ҫ��" };
	private String[] str_author = new String[] { "shareС��", "shareС��","shareС��", "shareС��", "shareС��", "shareС��", "shareС��" };
	private String[] str_design = new String[] { "ԭ��-�廭-��ϰϰ��", "ƽ�����-�������","ƽ�����-�������", "ԭ��-��Ӱ-��ϰϰ��", "ƽ�����-�������", "ƽ�����-�������", "ԭ��-��Ӱ-��ϰϰ��" };
	private String[] str_time = new String[] { "10����ǰ", "16����ǰ", "17����ǰ","19����ǰ", "20����ǰ", "26����ǰ", "50����ǰ" };
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

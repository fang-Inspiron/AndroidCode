package com.example.activity;

import com.example.share4_15.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NewGuanZhuActivity extends Activity{

	ImageView new_head;
	ImageButton new_back;
	TextView new_name;
	Button new_is;
	ListView listView;
	String[] str_name=new String[]{"Share小格","Share小兰","Share小明","share小雪","share萌萌","shareLity"};
	int[] img = new int[]{R.drawable.sixin_img1,R.drawable.sixin_img2,R.drawable.sixin_img3,R.drawable.sixin_img4,R.drawable.guanzhu_img5,R.drawable.guanzhu_img6};
	int[] isguanzhu = new int[]{R.drawable.guanzhu_pressed,R.drawable.guanzhu_normal,R.drawable.guanzhu_pressed,R.drawable.guanzhu_pressed,R.drawable.guanzhu_normal,R.drawable.guanzhu_normal};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newguanzhu_activity);
		new_back = (ImageButton) findViewById(R.id.newguanzhu_back);
		
		new_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		listView = (ListView) findViewById(R.id.newguanzhu_list);
		listView.setAdapter(new MyAdapter(getApplicationContext()));
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

		@SuppressLint("NewApi") @Override
		public View getView(int position, View convertView	, ViewGroup arg2) {
			// TODO Auto-generated method stub
			convertView = (RelativeLayout) ll.inflate(R.layout.newguanzhu_list_item,
					null);
			new_head = (ImageView) convertView.findViewById(R.id.newguanzhu_head);
			new_name = (TextView) convertView.findViewById(R.id.newguanzhu_name);
			new_is = (Button) convertView.findViewById(R.id.is_guanzhu);
			new_head.setImageDrawable(getResources().getDrawable(img[position]));
			new_name.setText(str_name[position]);
			new_is.setBackground(getResources().getDrawable(isguanzhu[position]));
			if(position>3){
				new_is.setTag(0);
			} else{
				new_is.setTag(1);
			}
			new_is.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(Integer.parseInt(v.getTag().toString()) == 1){
						v.setBackground(getResources().getDrawable(R.drawable.guanzhu_normal));
						v.setTag(0);
					} else{
						v.setBackground(getResources().getDrawable(R.drawable.guanzhu_pressed));
						v.setTag(1);
					}
				}
			});
			return convertView;
		}
		
	}
}

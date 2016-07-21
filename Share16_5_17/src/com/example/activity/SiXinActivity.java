package com.example.activity;

import com.example.share4_15.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SiXinActivity extends Activity {

	ListView listView;
	ImageView sixin_head;
	TextView sixin_name;
	TextView sixin_message;
	TextView sixin_time;
	ImageButton sixin_back;
	String[] str_name = new String[] { "Share小格", "Share小兰", "Share小明",
			"Share小雪" };
	String[] str_message = new String[] { "你的作品我很喜欢!!!", "谢谢，已关注你", "为你点赞!",
			"你好可以问问你是怎么拍的吗" };
	String[] str_time = new String[] { "11-03 09:45", "11-03 10:00",
			"11-03 10:23", "11-03 11:20" };
	int[] img = new int[] { R.drawable.sixin_img1, R.drawable.sixin_img2,
			R.drawable.sixin_img3, R.drawable.sixin_img4 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sixin_activity);
		listView = (ListView) findViewById(R.id.sixin_list);
		sixin_back = (ImageButton) findViewById(R.id.sixin_back);
		sixin_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		listView.setAdapter(new MyAdapter(this));
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int arg2,
					long arg3) {
				// 跳转到私信聊天界面
				TextView sixin_name = (TextView) v.findViewById(R.id.sixin_name);
				Intent intent = new Intent(SiXinActivity.this,	ChatActivity.class);
				System.out.println("name-->" + sixin_name.getText().toString());
				intent.putExtra("name", sixin_name.getText().toString());
				startActivity(intent);
			}
		});
	}

	class MyAdapter extends BaseAdapter {

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
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			convertView = (RelativeLayout) ll.inflate(R.layout.sixin_list_item,
					null);
			sixin_head = (ImageView) convertView.findViewById(R.id.sixin_head);
			sixin_name = (TextView) convertView.findViewById(R.id.sixin_name);
			sixin_message = (TextView) convertView
					.findViewById(R.id.sixin_message);
			sixin_time = (TextView) convertView.findViewById(R.id.sixin_time);

			sixin_head.setImageDrawable(getResources().getDrawable(
					img[position]));
			sixin_name.setText(str_name[position]);
			sixin_message.setText(str_message[position]);
			sixin_time.setText(str_time[position]);
			return convertView;
		}

	}
}

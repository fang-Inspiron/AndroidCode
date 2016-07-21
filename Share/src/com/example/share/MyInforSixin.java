package com.example.share;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MyInforSixin extends Activity{
	private ListView myList;
	private String[] name = new String[] {"share小格", "share小兰", "share小明", "share小雪"};
	private String[] desp = new String[] {"你的作品我很喜欢！！！", "谢谢，已关注你", "为你点赞！", "你好，可以问问你是怎么拍的吗？"};
	private String[] time = new String[] {"11--03 09:45", "11--03 10:00", "11--03 10:23", "11--03 11:20"};
	private int[] img = new int[] {R.drawable.concern_img1, R.drawable.concern_img2, R.drawable.concern_img3, R.drawable.concern_img4};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myinfor_sixin);
		
		myList = (ListView) findViewById(R.id.mylistView);
		//创建一个List集合，List集合的元素是Map
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i=0; i<name.length; i++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("Imgs", img[i]);
			listItem.put("Names", name[i]);
			listItem.put("Description", desp[i]);
			listItem.put("Times", time[i]);
			listItems.add(listItem);
		}
		//创建一个SimpleAdapter
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.mylist_simple, 
				new String[] {"Imgs", "Names", "Description", "Times"}, 
				new int[] {R.id.iv_simpleList, R.id.tv_name, R.id.tv_desp, R.id.tv_time});
		//为ListView设置Adapter
		myList.setAdapter(simpleAdapter);
		myList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("position", position);
				intent.putExtra("name", name[position]);
				intent.setClass(MyInforSixin.this, MyInforDialog.class);
				startActivity(intent);
			}
		});
	}
}

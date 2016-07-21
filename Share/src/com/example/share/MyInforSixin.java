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
	private String[] name = new String[] {"shareС��", "shareС��", "shareС��", "shareСѩ"};
	private String[] desp = new String[] {"�����Ʒ�Һ�ϲ��������", "лл���ѹ�ע��", "Ϊ����ޣ�", "��ã���������������ô�ĵ���"};
	private String[] time = new String[] {"11--03 09:45", "11--03 10:00", "11--03 10:23", "11--03 11:20"};
	private int[] img = new int[] {R.drawable.concern_img1, R.drawable.concern_img2, R.drawable.concern_img3, R.drawable.concern_img4};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myinfor_sixin);
		
		myList = (ListView) findViewById(R.id.mylistView);
		//����һ��List���ϣ�List���ϵ�Ԫ����Map
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i=0; i<name.length; i++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("Imgs", img[i]);
			listItem.put("Names", name[i]);
			listItem.put("Description", desp[i]);
			listItem.put("Times", time[i]);
			listItems.add(listItem);
		}
		//����һ��SimpleAdapter
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.mylist_simple, 
				new String[] {"Imgs", "Names", "Description", "Times"}, 
				new int[] {R.id.iv_simpleList, R.id.tv_name, R.id.tv_desp, R.id.tv_time});
		//ΪListView����Adapter
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

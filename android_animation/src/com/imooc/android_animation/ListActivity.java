package com.imooc.android_animation;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListActivity extends Activity{
	
	private ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_layout);
		listView=(ListView) findViewById(R.id.listView);
		List<String>list=new ArrayList<String>();
		for(int i=0;i<20;i++)
		{
			list.add("慕课网"+i);
		}
		ArrayAdapter<String>adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
	    listView.setAdapter(adapter);
	    LayoutAnimationController lac=new LayoutAnimationController(AnimationUtils.loadAnimation(this, R.anim.zoom_in));
	    lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
	    listView.setLayoutAnimation(lac);
	    listView.startLayoutAnimation();
	}

}

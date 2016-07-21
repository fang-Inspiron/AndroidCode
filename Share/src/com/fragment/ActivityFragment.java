package com.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.share.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ActivityFragment extends Fragment {
	private View rootview;
	private ListView myList;
	private int[] img = new int[] { R.drawable.activity_list1,
			R.drawable.activity_list2, R.drawable.activity_list3,
			R.drawable.activity_list1 };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootview = inflater.inflate(R.layout.fragment_activity, container,
				false);
		myList = (ListView) rootview.findViewById(R.id.myList);
		
		// 创建一个List集合，List集合的元素是Map
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < img.length; i++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("img", img[i]);
			listItems.add(listItem);
		}
		// 创建一个SimpleAdapter
		//fragment中的this不是context，若要得到上下文需写成getActivity()
		SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), listItems,
				R.layout.simple_item, new String[] { "img" },
				new int[] { R.id.imageview });
		//为ListView设置Adapter
		myList.setAdapter(simpleAdapter);
	
		return rootview;
	}
}

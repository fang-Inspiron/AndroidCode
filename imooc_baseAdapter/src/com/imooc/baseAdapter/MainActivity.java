package com.imooc.baseAdapter;

import java.util.ArrayList;
import java.util.List;
import com.imooc.baseAdapter.bean.Bean;
import com.imooc.baseAdapter.utils.CommonAdapter;
import com.imooc.baseAdapter.utils.ViewHolder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

	private ListView mListView;
	private List<Bean> mDatas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initDatas();
		initView();
	}

	private void initView() {
		mListView = (ListView) findViewById(R.id.id_listview);
		mListView.setAdapter(new CommonAdapter<Bean>(MainActivity.this, mDatas,
				R.layout.item_listview) {
			@Override
			public void convert(ViewHolder holder, Bean bean) {
				holder.setText(R.id.id_title, bean.getTitle())
						.setText(R.id.id_desc, bean.getDesc())
						.setText(R.id.id_time, bean.getTime())
						.setText(R.id.id_phone, bean.getPhone());
			}
		});

	}

	private void initDatas() {
		mDatas = new ArrayList<Bean>();

		Bean bean = new Bean("Android新技能1", "Android打造万能的ListView和GridView适配器",
				"2015-5-19", "10086");
		mDatas.add(bean);
		bean = new Bean("Android新技能2", "Android打造万能的ListView和GridView适配器",
				"2015-5-19", "10086");
		mDatas.add(bean);
		bean = new Bean("Android新技能3", "Android打造万能的ListView和GridView适配器",
				"2015-5-19", "10086");
		mDatas.add(bean);
		bean = new Bean("Android新技能4", "Android打造万能的ListView和GridView适配器",
				"2015-5-19", "10086");
		mDatas.add(bean);
		bean = new Bean("Android新技能5", "Android打造万能的ListView和GridView适配器",
				"2015-5-19", "10086");
		mDatas.add(bean);
		bean = new Bean("Android新技能6", "Android打造万能的ListView和GridView适配器",
				"2015-5-19", "10086");
		mDatas.add(bean);
		bean = new Bean("Android新技能7", "Android打造万能的ListView和GridView适配器",
				"2015-5-19", "10086");
		mDatas.add(bean);
		bean = new Bean("Android新技能8", "Android打造万能的ListView和GridView适配器",
				"2015-5-19", "10086");
		mDatas.add(bean);
		bean = new Bean("Android新技能9", "Android打造万能的ListView和GridView适配器",
				"2015-5-19", "10086");
		mDatas.add(bean);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

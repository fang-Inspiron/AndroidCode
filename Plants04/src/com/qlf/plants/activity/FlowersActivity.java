package com.qlf.plants.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.qlf.plants.MainActivity;
import com.qlf.plants.R;
import com.qlf.plants.bean.PlantBean;
import com.qlf.plants.thread.PlantInfoThread;
import com.qlf.plants.utils.CommonAdapter;
import com.qlf.plants.utils.ViewHolder;
import com.qlf.plants.view.XListView;
import com.qlf.plants.view.XListView.IXListViewListener;
/**
 * 我的宝典
 * @author yxw19_000
 *
 */
public class FlowersActivity extends Activity implements IXListViewListener {
	private XListView mListView;
	private CommonAdapter<PlantBean> mAdapter;
	private ArrayList<PlantBean> items = new ArrayList<PlantBean>();
	private Handler mHandler;
	private int started = 1;
	private int ended = 31;
	private EditText search_et;
	private ImageView search_iv;
	private ImageView action_back;

	/** Called when the activity is first created. */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flower_main);
		geneItems();
		initViews();
		initListView();
		initSearch();
	}
	
	public void initSearch(){
		search_iv.setOnClickListener(new OnClickListener() {
			String str = null;
			@Override
			public void onClick(View v) {
				if((str=search_et.getText().toString())!=null){
					search(str);
				}
			}
		});
	}
	
	public void search(String str) {
		
	}

	public void initViews(){
		mListView = (XListView) findViewById(R.id.flower_list);
		action_back = (ImageView) findViewById(R.id.action_back);
		
		action_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.mLeftMenu.toggle();
			}
		});
	}
	
	public void initListView(){
		mListView.setPullLoadEnable(true);
		mAdapter = new CommonAdapter<PlantBean>(getApplicationContext(), items,
				R.layout.flower_item) {
			@Override
			public void convert(ViewHolder holder, PlantBean t) {
				holder.setText(R.id.flower_name, t.getCommon_name());
				//holder.setImageBitmap(R.id.flower_img,t.getImage());
			}
		};
		mListView.setAdapter(mAdapter);
		// mListView.setPullLoadEnable(false);//设置是否可以上拉加载
		mListView.setPullRefreshEnable(false);//设置是否可以下拉刷新
		mListView.setXListViewListener(this);
		mHandler = new Handler();
	}

	private void geneItems() {
		if (ended < 10360) {
			PlantInfoThread infoThread = new PlantInfoThread(
					getApplicationContext(), started, ended);
			infoThread.execute();
			try {
				List<PlantBean> list = infoThread.get();
				for (int i = 0; i < list.size(); i++) {
					PlantBean bean = new PlantBean();
					bean.setCommon_name(list.get(i).getCommon_name());
					bean.setImage(list.get(i).getImage());
					items.add(bean);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");
	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
//				start = ++refreshCnt;
//				items.clear();
//				geneItems();
//				// mAdapter.notifyDataSetChanged();
//				mAdapter = new CommonAdapter<String>(getApplicationContext(),
//						items, R.layout.flower_item) {
//					@Override
//					public void convert(ViewHolder holder, String t) {
//						holder.setText(R.id.flower_name, t);
//					}
//				};
//				mListView.setAdapter(mAdapter);
//				onLoad();
			}
		}, 500);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				started += 31;
				ended += 31;
				geneItems();
				mAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 500);
	}
	
	
}

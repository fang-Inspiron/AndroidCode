package com.qlf.plants.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.qlf.plants.MainActivity;
import com.qlf.plants.R;
import com.qlf.plants.bean.PlantBean;
import com.qlf.plants.sort.CharacterParser;
import com.qlf.plants.sort.ClearEditText;
import com.qlf.plants.sort.PinyinComparator;
import com.qlf.plants.sort.SideBar;
import com.qlf.plants.sort.SideBar.OnTouchingLetterChangedListener;
import com.qlf.plants.sort.SortAdapter;
import com.qlf.plants.sort.SortModel;
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
public class MyFlowersFragment extends Fragment implements IXListViewListener {
	private XListView sortListView;
	private List<PlantBean> items = new ArrayList<PlantBean>();
	private Handler mHandler;
	private int started = 1;
	private int ended = 31;
	private EditText search_et;
	private ImageView search_iv;

	/** Called when the activity is first created. */

	private View rootView;
	private ImageView action_back;
	private SideBar sideBar;
	private TextView dialog;
	private SortAdapter adapter;
	private ClearEditText mClearEditText;
	
	private String[] data;
	
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;
	
	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater.inflate(R.layout.flower_main, container, false);
		search_iv = (ImageView) rootView.findViewById(R.id.search_bt);
		action_back = (ImageView) rootView.findViewById(R.id.action_back);
		action_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.mLeftMenu.toggle();
			}
		});
		geneItems();
//		initViews();
		initListView();
		initView();
		initSearch();
		return rootView;
	}
	private void initView() {
		//实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		
		pinyinComparator = new PinyinComparator();
		
		sideBar = (SideBar) rootView.findViewById(R.id.sidrbar);
		dialog = (TextView) rootView.findViewById(R.id.dialog);
		sideBar.setTextView(dialog);
		
		//设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
			
			@Override
			public void onTouchingLetterChanged(String s) {
				//该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if(position != -1){
					sortListView.setSelection(position);
				}
				
			}
		});
		
		sortListView = (XListView) rootView.findViewById(R.id.flower_list);
		sortListView.setPullRefreshEnable(false);//设置是否可以下拉刷新
		sortListView.setPullLoadEnable(true);
		sortListView.setXListViewListener(this);
		mHandler = new Handler();
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//这里要利用adapter.getItem(position)来获取当前position所对应的对象
				Toast.makeText(getActivity(),SourceDateList.get(position-1).getName(), Toast.LENGTH_SHORT).show();
			}
		});
		
			SourceDateList = filledData(items);
			System.out.println(SourceDateList);
		// 根据a-z进行排序源数据
		Collections.sort(SourceDateList, pinyinComparator);
		adapter = new SortAdapter(getActivity(), SourceDateList);
		sortListView.setAdapter(adapter);
		
		
		mClearEditText = (ClearEditText) rootView.findViewById(R.id.filter_edit);
		
		//根据输入框输入值的改变来过滤搜索
		mClearEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
				filterData(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}


	/**
	 * 为ListView填充数据
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(List<PlantBean> date){
		List<SortModel> mSortList = new ArrayList<SortModel>();
		
		for(int i=0; i<date.size(); i++){
			SortModel sortModel = new SortModel();
			sortModel.setName(date.get(i).getCommon_name());
			sortModel.setImg(date.get(i).getImage());
			sortModel.setGenus_name(date.get(i).getGenus_name());
			//汉字转换成拼音
			String pinyin = characterParser.getSelling(date.get(i).getCommon_name());
			String sortString = pinyin.substring(0, 1).toUpperCase();
			
			// 正则表达式，判断首字母是否是英文字母
			if(sortString.matches("[A-Z]")){
				sortModel.setSortLetters(sortString.toUpperCase());
			}else{
				sortModel.setSortLetters("#");
			}
			
			mSortList.add(sortModel);
		}
		return mSortList;
		
	}
	
	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * @param filterStr
	 */
	private void filterData(String filterStr){
		List<SortModel> filterDateList = new ArrayList<SortModel>();
		
		if(TextUtils.isEmpty(filterStr)){
			filterDateList = SourceDateList;
		}else{
			filterDateList.clear();
			for(SortModel sortModel : SourceDateList){
				String name = sortModel.getName();
				if(name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
					filterDateList.add(sortModel);
				}
			}
		}
		
		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}
	
	
	
	public void initSearch(){
		search_iv.setOnClickListener(new OnClickListener() {
			String str = null;
			@Override
			public void onClick(View v) {
				if((str=mClearEditText.getText().toString())!=null){
					search(str);
				}
			}
		});
	}
	
	public void search(String str) {
		//启动线程从服务器获取
	}

	public void initViews(){
	}
	
	public void initListView(){
		
//		mAdapter = new CommonAdapter<PlantBean>(getActivity(), items,
//				R.layout.flower_item) {
//			@Override
//			public void convert(ViewHolder holder, PlantBean t) {
//				holder.setText(R.id.flower_name, t.getCommon_name());
//				holder.setImageBitmap(R.id.flower_img,t.getImage());
//			}
//		};
		// mListView.setPullLoadEnable(false);//设置是否可以上拉加载
		
	}

	private void geneItems() {
		//items = PlantInfoThread.data;
			PlantInfoThread infoThread = new PlantInfoThread(
					getActivity(),started,ended);
			infoThread.execute();
			try {
				List<PlantBean> list = infoThread.get();
				for (int i = 0; i < list.size(); i++) {
					PlantBean bean = new PlantBean();
					bean.setCommon_name(list.get(i).getCommon_name());
					bean.setImage(list.get(i).getImage());
					bean.setGenus_name(list.get(i).getGenus_name());
					items.add(bean);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
	}
	

	private void onLoad() {
		sortListView.stopRefresh();
		sortListView.stopLoadMore();
		sortListView.setRefreshTime("刚刚");
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
				SourceDateList = filledData(items);
				System.out.println(SourceDateList);
				// 根据a-z进行排序源数据
				Collections.sort(SourceDateList, pinyinComparator);
				adapter = new SortAdapter(getActivity(), SourceDateList);
				sortListView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				onLoad();
			}
		}, 500);
	}
	
	
}

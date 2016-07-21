package com.example.share;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.example.activity.ShuangChuanActivity;
import com.example.fragment.CupFragment;
import com.example.fragment.HomeFragment;
import com.example.fragment.MyFragment;
import com.example.fragment.NoteFragment;
import com.example.fragment.SearchFragment;
import com.example.share4_15.R;
import com.example.utils.ActionBarUtil;

public class MainActivity extends FragmentActivity {

	public final static String TAG = "FarmerMainActivity";
	private long mExitTime;
	/**
	 * 主界面的viewpager
	 */
	private ViewPager vp_main_tab = null;
	private FragmentPagerAdapter mAdapter = null;
	private List<Fragment> mFragments = new ArrayList<Fragment>();

	/**
	 * 底部的四个radiobutton
	 */
	private RadioButton rb_main_tab_menu1 = null;
	private RadioButton rb_main_tab_menu2 = null;
	private RadioButton rb_main_tab_menu3 = null;
	private RadioButton rb_main_tab_menu4 = null;
	private RadioButton rb_main_tab_menu5 = null;

	public static RadioGroup rg_main_menu = null;

	private HomeFragment homeFragment;
	private MyFragment myFragment;
	private NoteFragment noteFragment;
	private CupFragment cupFragment;
	private SearchFragment searchFragment;
	
	public static Map<String,Object> data = new HashMap<String, Object>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBarUtil.initMainActionBar(MainActivity.this, getActionBar(),
				"SHARE", 0);
		findViews();
		initAdapter();
		initViewPager();
	}

	private void findViews() {
		rb_main_tab_menu1 = (RadioButton) findViewById(R.id.rb_main_tab_menu1);
		rb_main_tab_menu2 = (RadioButton) findViewById(R.id.rb_main_tab_menu2);
		rb_main_tab_menu3 = (RadioButton) findViewById(R.id.rb_main_tab_menu3);
		rb_main_tab_menu4 = (RadioButton) findViewById(R.id.rb_main_tab_menu4);
		rb_main_tab_menu5 = (RadioButton) findViewById(R.id.rb_main_tab_menu5);
		rg_main_menu = (RadioGroup) findViewById(R.id.rg_main_menu);
		vp_main_tab = (ViewPager) findViewById(R.id.vp_main_tab);

		homeFragment = new HomeFragment();
		myFragment = new MyFragment();
		noteFragment = new NoteFragment();
		cupFragment = new CupFragment();
		searchFragment = new SearchFragment();
		
		mFragments.add(homeFragment);
		mFragments.add(searchFragment);
		mFragments.add(noteFragment);
		mFragments.add(cupFragment);
		mFragments.add(myFragment);
	}

	
	private void initAdapter() {
		android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
		mAdapter = new FragmentPagerAdapter(fm) {

			@Override
			public int getCount() {
				return mFragments.size();
			}

			@Override
			public android.support.v4.app.Fragment getItem(int arg0) {
				return mFragments.get(arg0);
			}

			// 初始化每个页卡选项
			@Override
			public Object instantiateItem(ViewGroup arg0, int arg1) {
				return super.instantiateItem(arg0, arg1);
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				super.destroyItem(container, position, object);
			}
		};
	}

	private void initViewPager() {
		vp_main_tab.setAdapter(mAdapter);
		vp_main_tab.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				switch (position) {
				case 0:
					rb_main_tab_menu1.setChecked(true);
					break;
				case 1:
					rb_main_tab_menu2.setChecked(true);
					break;
				case 2:
					rb_main_tab_menu3.setChecked(true);
					break;
				case 3:
					rb_main_tab_menu4.setChecked(true);
					break;
				case 4:
					rb_main_tab_menu5.setChecked(true);
					break;
				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		rg_main_menu.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup rg, int id) {
				switch (id) {
				case R.id.rb_main_tab_menu1:
					vp_main_tab.setCurrentItem(0);
					ActionBarUtil.initMainActionBar(MainActivity.this,
							getActionBar(), "SHARE", 0);
					break;
				case R.id.rb_main_tab_menu2:
					vp_main_tab.setCurrentItem(1);
					ActionBarUtil.initMainActionBar(MainActivity.this,
							getActionBar(), "搜索", 1);
					ImageView shang = (ImageView) getActionBar()
							.getCustomView().findViewById(
									R.id.action_shuangchuan);
					shang.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// 上传页面
							Intent intent = new Intent(MainActivity.this,
									ShuangChuanActivity.class);
							startActivity(intent);
						}
					});
					ImageView search_back = (ImageView)getActionBar().getCustomView()
							.findViewById(R.id.action_back);

					search_back.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// 返回搜索页面
							searchFragment.search_result.setVisibility(View.GONE);
							searchFragment.search.setVisibility(View.VISIBLE);
						}
					});
					break;
				case R.id.rb_main_tab_menu3:
					vp_main_tab.setCurrentItem(2);
					ActionBarUtil.initMainActionBar(MainActivity.this,
							getActionBar(), "文章", 2);
					break;
				case R.id.rb_main_tab_menu4:
					vp_main_tab.setCurrentItem(3);
					ActionBarUtil.initMainActionBar(MainActivity.this,
							getActionBar(), "活动", 3);
					break;
				case R.id.rb_main_tab_menu5:
					vp_main_tab.setCurrentItem(4);
					ActionBarUtil.initMainActionBar(MainActivity.this,
							getActionBar(), "个人信息", 4);
					break;
				default:
					break;
				}

			}
		});

	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();

			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	// 查找的监听
	public void searchOnClick(View v) {
		searchFragment.et_search.setText(((Button)v).getText());
	}
}

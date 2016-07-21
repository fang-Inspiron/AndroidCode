package com.fastfood.mine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.data.UserData;
import com.fastfood.R;
import com.fastfood.activity.LoginActivity;
import com.fastfood.activity.MainActivity;
import com.fastfood.utils.ActionBarUtil;
import com.fragment.MyOrderOneFragment;
import com.fragment.MyOrderTwoFragment;
import com.fragment.MyOrderZeroFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MyOrderActivityTest extends FragmentActivity {

	private TextView zero, one, two;
	private ViewPager viewPager;
	private List<Fragment> fragmentList;
	private MyOrderZeroFragment myOrderZeroFragment;
	private MyOrderOneFragment myOrderOneFragment;
	private MyOrderTwoFragment myOrderTwoFragment;
	private TextView home;
	public Map<String, Object> mapHasNotReceived = null;
	public Map<String, Object> mapHasReceived = null;
	public Map<String, Object> mapHasGetedGoods = null;
	boolean OKHasNotReceived[], OKHasReceived[], OKHasGetedGoods[];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_order_test);
		ActionBarUtil.initActionBar(MyOrderActivityTest.this, getActionBar(), "我的订单", 0);
		
	//	initUserInfo();
		
		findViewAndInit();
		setListener();
	}
	
	public void initUserInfo() {
			Map<String, Object> map = UserData.getUserInfo(MyOrderActivityTest.this);
			System.out.println(map);
	}
	
	public void findViewAndInit() {
		viewPager = (ViewPager) findViewById(R.id.myOrderViewpager);
		zero = (TextView) findViewById(R.id.textView_order0);
		one = (TextView) findViewById(R.id.textView_order1);
		two = (TextView) findViewById(R.id.textView_order2);
		home = (TextView) getActionBar().getCustomView().findViewById(R.id.actionbar_title);
		
		fragmentList = new ArrayList<Fragment>();
		myOrderZeroFragment = new MyOrderZeroFragment();
		myOrderOneFragment = new MyOrderOneFragment();
		myOrderTwoFragment = new MyOrderTwoFragment();
		fragmentList.add(myOrderZeroFragment); // 未接单---0
		fragmentList.add(myOrderOneFragment);  // 已接单---1
		fragmentList.add(myOrderTwoFragment);  // 已收货---2
	}

	public void setListener() {
		home.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		viewPager.setAdapter(new MyOrderFragmentPagerAdapter(this.getSupportFragmentManager(), fragmentList));
		viewPager.setCurrentItem(0);
		OrderOnClickListener clickListener = new OrderOnClickListener();
		zero.setOnClickListener(clickListener);
		one.setOnClickListener(clickListener);
		two.setOnClickListener(clickListener);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if (arg0 == 0) {
					viewPager.setCurrentItem(0);
					zero.setTextColor(getResources().getColor(R.color.orange_light));
					zero.setBackgroundColor(getResources().getColor(R.color.white));
					one.setTextColor(getResources().getColor(R.color.black));
					one.setBackgroundColor(getResources().getColor(R.color.darkgrey));
					two.setTextColor(getResources().getColor(R.color.black));
					two.setBackgroundColor(getResources().getColor(R.color.darkgrey));
				} else if (arg0 == 1) {
					viewPager.setCurrentItem(1);
					one.setTextColor(getResources().getColor(R.color.orange_light));
					one.setBackgroundColor(getResources().getColor(R.color.white));
					zero.setTextColor(getResources().getColor(R.color.black));
					zero.setBackgroundColor(getResources().getColor(R.color.darkgrey));
					two.setTextColor(getResources().getColor(R.color.black));
					two.setBackgroundColor(getResources().getColor(R.color.darkgrey));
				} else if (arg0 == 2) {
					viewPager.setCurrentItem(2);
					two.setTextColor(getResources().getColor(R.color.orange_light));
					two.setBackgroundColor(getResources().getColor(R.color.white));
					zero.setTextColor(getResources().getColor(R.color.black));
					zero.setBackgroundColor(getResources().getColor(R.color.darkgrey));
					one.setTextColor(getResources().getColor(R.color.black));
					one.setBackgroundColor(getResources().getColor(R.color.darkgrey));
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	class OrderOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			TextView textView = (TextView) v;
			System.out.println(textView.getText());
			if (textView.getId() == R.id.textView_order0) {
				viewPager.setCurrentItem(0);
				zero.setTextColor(getResources().getColor(R.color.orange_light));
				zero.setBackgroundColor(getResources().getColor(R.color.white));
				one.setTextColor(getResources().getColor(R.color.black));
				one.setBackgroundColor(getResources().getColor(R.color.darkgrey));
				two.setTextColor(getResources().getColor(R.color.black));
				two.setBackgroundColor(getResources().getColor(R.color.darkgrey));
			} else if (textView.getId() == R.id.textView_order1) {
				viewPager.setCurrentItem(1);
				one.setTextColor(getResources().getColor(R.color.orange_light));
				one.setBackgroundColor(getResources().getColor(R.color.white));
				zero.setTextColor(getResources().getColor(R.color.black));
				zero.setBackgroundColor(getResources().getColor(R.color.darkgrey));
				two.setTextColor(getResources().getColor(R.color.black));
				two.setBackgroundColor(getResources().getColor(R.color.darkgrey));
			} else if (textView.getId() == R.id.textView_order2) {
				viewPager.setCurrentItem(2);
				two.setTextColor(getResources().getColor(R.color.orange_light));
				two.setBackgroundColor(getResources().getColor(R.color.white));
				zero.setTextColor(getResources().getColor(R.color.black));
				zero.setBackgroundColor(getResources().getColor(R.color.darkgrey));
				one.setTextColor(getResources().getColor(R.color.black));
				one.setBackgroundColor(getResources().getColor(R.color.darkgrey));
			}
		}
		
	}
	
	class MyOrderFragmentPagerAdapter extends FragmentPagerAdapter {

		List<Fragment> fragmentList;
		
		public MyOrderFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
			super(fm);
			this.fragmentList = fragmentList;
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			return fragmentList.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fragmentList.size();
		}
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
	
}

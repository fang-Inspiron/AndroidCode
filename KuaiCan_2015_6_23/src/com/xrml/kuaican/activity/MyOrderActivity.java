package com.xrml.kuaican.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xrml.kuaican.R;
import com.xrml.kuaican.fragment.MyOrderOneFragment;
import com.xrml.kuaican.fragment.MyOrderTwoFragment;
import com.xrml.kuaican.fragment.MyOrderZeroFragment;
import com.xrml.kuaican.util.ActionBarUtil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MyOrderActivity extends FragmentActivity {
	TextView zero, one, two;
	ViewPager viewpager;
	List<Fragment> fragmentList;
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
		super.onCreate(savedInstanceState);
		ActionBarUtil.initActionBar(getApplicationContext(), getActionBar(),
				"我的订单");
		setContentView(R.layout.activity_my_order);
		findViewAndInit();
		setListener();

	}

	void findViewAndInit() {
		viewpager = (ViewPager) findViewById(R.id.myOrderViewpager);
		zero = (TextView) findViewById(R.id.ordertextview0);
		one = (TextView) findViewById(R.id.ordertextview1);
		two = (TextView) findViewById(R.id.ordertextview2);
		home = (TextView) getActionBar().getCustomView().findViewById(
				R.id.actionbar_user_state);
		home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		fragmentList = new ArrayList<Fragment>();
		myOrderZeroFragment = new MyOrderZeroFragment();
		myOrderOneFragment = new MyOrderOneFragment();
		myOrderTwoFragment = new MyOrderTwoFragment();
		fragmentList.add(myOrderZeroFragment); // 未接单---0
		fragmentList.add(myOrderOneFragment); // 已接单---1
		fragmentList.add(myOrderTwoFragment); // 已收货---2
	}

	void setListener() {
		viewpager.setAdapter(new MyOrderFragmentPagerAdapter(this
				.getSupportFragmentManager(), fragmentList));
		viewpager.setCurrentItem(0);
		OrderOnClicklistener listener = new OrderOnClicklistener();
		zero.setOnClickListener(listener);
		one.setOnClickListener(listener);
		two.setOnClickListener(listener);
		viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageSelected(int arg0) {
				if (arg0 == 0) {
					zero.setBackgroundColor(getResources().getColor(
							R.color.white));
					zero.setTextColor(getResources().getColor(
							R.color.orange_light));
					one.setBackgroundColor(getResources().getColor(
							R.color.darkgray));
					one.setTextColor(getResources().getColor(R.color.black));
					two.setBackgroundColor(getResources().getColor(
							R.color.darkgray));
					two.setTextColor(getResources().getColor(R.color.black));
					viewpager.setCurrentItem(0);
				} else if (arg0 == 1) {
					one.setBackgroundColor(getResources().getColor(
							R.color.white));
					one.setTextColor(getResources().getColor(
							R.color.orange_light));
					zero.setBackgroundColor(getResources().getColor(
							R.color.darkgray));
					zero.setTextColor(getResources().getColor(R.color.black));
					two.setBackgroundColor(getResources().getColor(
							R.color.darkgray));
					two.setTextColor(getResources().getColor(R.color.black));
					viewpager.setCurrentItem(1);
				} else {
					two.setBackgroundColor(getResources().getColor(
							R.color.white));
					two.setTextColor(getResources().getColor(
							R.color.orange_light));
					one.setBackgroundColor(getResources().getColor(
							R.color.darkgray));
					one.setTextColor(getResources().getColor(R.color.black));
					zero.setBackgroundColor(getResources().getColor(
							R.color.darkgray));
					zero.setTextColor(getResources().getColor(R.color.black));
					viewpager.setCurrentItem(2);
					myOrderTwoFragment.upDateListView();
				}

			}

		});

	}

	class OrderOnClicklistener implements OnClickListener {

		@Override
		public void onClick(View view) {
			TextView textView = (TextView) view;
			System.out.println(textView.getText());
			if (textView.getId() == R.id.ordertextview0) {
				zero.setBackgroundColor(getResources().getColor(R.color.white));
				zero.setTextColor(getResources().getColor(R.color.orange_light));
				one.setBackgroundColor(getResources()
						.getColor(R.color.darkgray));
				one.setTextColor(getResources().getColor(R.color.black));
				two.setBackgroundColor(getResources()
						.getColor(R.color.darkgray));
				two.setTextColor(getResources().getColor(R.color.black));
				viewpager.setCurrentItem(0);
			} else if (textView.getId() == R.id.ordertextview1) {
				one.setBackgroundColor(getResources().getColor(R.color.white));
				one.setTextColor(getResources().getColor(R.color.orange_light));
				zero.setBackgroundColor(getResources().getColor(
						R.color.darkgray));
				zero.setTextColor(getResources().getColor(R.color.black));
				two.setBackgroundColor(getResources()
						.getColor(R.color.darkgray));
				two.setTextColor(getResources().getColor(R.color.black));
				viewpager.setCurrentItem(1);
			} else {
				two.setBackgroundColor(getResources().getColor(R.color.white));
				two.setTextColor(getResources().getColor(R.color.orange_light));
				one.setBackgroundColor(getResources()
						.getColor(R.color.darkgray));
				one.setTextColor(getResources().getColor(R.color.black));
				zero.setBackgroundColor(getResources().getColor(
						R.color.darkgray));
				zero.setTextColor(getResources().getColor(R.color.black));
				viewpager.setCurrentItem(2);
			}

		}

	}

	class MyOrderFragmentPagerAdapter extends FragmentPagerAdapter {
		List<Fragment> fragmentList;

		public MyOrderFragmentPagerAdapter(FragmentManager fragmentManager,
				List<Fragment> fragmentList) {
			super(fragmentManager);
			this.fragmentList = fragmentList;
		}

		@Override
		public Fragment getItem(int arg0) {
			return fragmentList.get(arg0);
		}

		@Override
		public int getCount() {
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

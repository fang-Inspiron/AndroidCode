package com.fastfood.activity;

import java.util.ArrayList;
import java.util.List;
import com.bean.GoodsBean;
import com.data.App;
import com.fastfood.R;
import com.fastfood.mine.AddressActivity;
import com.fastfood.mine.AdviceActivity;
import com.fastfood.mine.AlterPasswordActivity;
import com.fastfood.mine.DeclareActivity;
import com.fastfood.mine.MyOrderActivity;
import com.fastfood.mine.MyOrderActivityTest;
import com.fastfood.utils.ActionBarUtil;
import com.fragment.CartFragment;
import com.fragment.HomeFragment;
import com.fragment.MineFragment;
import com.fragment.PhoneFragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	public static App app;
	private RadioGroup rg_main;
	private RadioButton rb_home;
	private RadioButton rb_cart;
	private RadioButton rb_mine;
	private RadioButton rb_phone;
	private ViewPager viewPager_main;
	private List<Fragment> mFragments = new ArrayList<Fragment>();
	private Fragment fragment_home;
	public static CartFragment fragment_cart;
	private ProgressDialog pd;
	private Fragment fragment_mine;
	private Fragment fragment_phone;
	private FragmentPagerAdapter mAdapter;
	public static Spinner spinnerCity;
	public static List<GoodsBean> cartData = new ArrayList<GoodsBean>();
	public static TextView tv_isLogin;
	public static MainActivity mActivity;
	
	public static CartFragment getCartFragment() {
		if (fragment_cart == null) {
			return new CartFragment();
		}
		return fragment_cart;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mActivity = this;
		app = (App) getApplication();
		System.out.println("App.name:"+app.getPackageName());
		ActionBarUtil.initActionBar(getApplicationContext(), getActionBar(),"已登录", 1);
		spinnerCity = (Spinner)getActionBar().getCustomView().findViewById(R.id.spinnerCity);
		
		tv_isLogin = (TextView) getActionBar().getCustomView().findViewById(R.id.actionbar_title);
		
		findID();
		initAdapter();
		initViewPager();
	}

	public void findID() {
		rg_main = (RadioGroup) findViewById(R.id.rg_main);
		rb_home = (RadioButton) findViewById(R.id.rb_home);
		rb_cart = (RadioButton) findViewById(R.id.rb_cart);
		rb_mine = (RadioButton) findViewById(R.id.rb_mine);
		rb_phone = (RadioButton) findViewById(R.id.rb_phone);
		viewPager_main = (ViewPager) findViewById(R.id.viewPager_main);

		fragment_home = new HomeFragment();
		fragment_cart = new CartFragment();
		fragment_mine = new MineFragment();
		fragment_phone = new PhoneFragment();

		mFragments.add(fragment_home);
		mFragments.add(fragment_cart);
		mFragments.add(fragment_mine);
		mFragments.add(fragment_phone);
	}

	public void initAdapter() {
		FragmentManager fm = getSupportFragmentManager();
		mAdapter = new FragmentPagerAdapter(fm) {

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mFragments.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				return mFragments.get(arg0);
			}

			// 初始化每个选项卡
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

	public void initViewPager() {
		viewPager_main.setAdapter(mAdapter);
		viewPager_main.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				switch (position) {
				case 0:
					rb_home.setChecked(true);
					break;
				case 1:
					rb_cart.setChecked(true);
					break;
				case 2:
					rb_mine.setChecked(true);
					break;
				case 3:
					rb_phone.setChecked(true);
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

		rg_main.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup rg, int id) {
				switch (id) {
				case R.id.rb_home:
					viewPager_main.setCurrentItem(0);
					ActionBarUtil.initActionBar(getApplicationContext(), getActionBar(), "未登录", 1);
					spinnerCity = (Spinner)getActionBar().getCustomView().findViewById(R.id.spinnerCity);
					break;
				case R.id.rb_cart:
					viewPager_main.setCurrentItem(1);
					ActionBarUtil.initActionBar(MainActivity.this, getActionBar(), "购物车", 0);
					break;
				case R.id.rb_mine:
					viewPager_main.setCurrentItem(2);
					ActionBarUtil.initActionBar(MainActivity.this, getActionBar(), "我的", 0);
					break;
				case R.id.rb_phone:
					viewPager_main.setCurrentItem(3);
					ActionBarUtil.initActionBar(MainActivity.this, getActionBar(), "电话订", 0);
					
					break;
				default:
					break;
				}
			}
		});
	}
	
	public void MyInforLayoutOnClick(View view) {
		Intent intent = new Intent();
		switch (view.getId()) {
		case R.id.layout_alterPassword:
			intent.setClass(MainActivity.this, AlterPasswordActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_myOrder:
			intent.setClass(MainActivity.this, MyOrderActivityTest.class);
			startActivity(intent);
			break;
		case R.id.layout_address:
			intent.setClass(MainActivity.this, AddressActivity.class);
			startActivity(intent);
			break;
			
		case R.id.layout_declare:
			intent.setClass(MainActivity.this, DeclareActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_advice:
			intent.setClass(MainActivity.this, AdviceActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_check_update:
			showSpinner();
			break;

		default:
			break;
		}
	}

	public void showSpinner() {
		// 调用静态方法显示环形进度条
		pd = ProgressDialog.show(MainActivity.this, "提示", "检查中......", false, true); 

		new Thread() {
			public void run() {
				try {
					sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					pd.dismiss();
				}
			};
		}.start();
	}
}

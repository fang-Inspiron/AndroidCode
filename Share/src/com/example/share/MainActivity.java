package com.example.share;

import java.util.ArrayList;
import java.util.List;

import com.fragment.ActivityFragment;
import com.fragment.EssayFragment;
import com.fragment.HomeFragment;
import com.fragment.PersonFragment;
import com.fragment.SearchFragment;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.style.BulletSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends FragmentActivity {
	private RadioGroup rg_main;
	private RadioButton rb_home;
	private RadioButton rb_search;
	private RadioButton rb_essay;
	private RadioButton rb_activity;
	private RadioButton rb_person;
	private ViewPager vp_main_tab;
	private List<Fragment> mFragments = new ArrayList<Fragment>();
	private Fragment fragment_home;
	private Fragment fragment_search;
	private Fragment fragment_essay;
	private Fragment fragment_activity;
	private Fragment fragment_person;
	private FragmentPagerAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findID();
		initAdapter();
		initViewPager();
	}

	public void findID() {
		rg_main = (RadioGroup) findViewById(R.id.rg_main);
		rb_home = (RadioButton) findViewById(R.id.rb_home);
		rb_search = (RadioButton) findViewById(R.id.rb_search);
		rb_essay = (RadioButton) findViewById(R.id.rb_essay);
		rb_activity = (RadioButton) findViewById(R.id.rb_activity);
		rb_person = (RadioButton) findViewById(R.id.rb_person);
		vp_main_tab = (ViewPager) findViewById(R.id.vp_main_tab);

		fragment_home = new HomeFragment();
		fragment_search = new SearchFragment();
		fragment_essay = new EssayFragment();
		fragment_activity = new ActivityFragment();
		fragment_person = new PersonFragment();

		mFragments.add(fragment_home);
		mFragments.add(fragment_search);
		mFragments.add(fragment_essay);
		mFragments.add(fragment_activity);
		mFragments.add(fragment_person);
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
		vp_main_tab.setAdapter(mAdapter);
		vp_main_tab.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				switch (position) {
				case 1:
					rb_search.setChecked(true);
					break;
				case 2:
					rb_essay.setChecked(true);
					break;
				case 3:
					rb_activity.setChecked(true);
					break;
				case 4:
					rb_person.setChecked(true);
					break;
				default:
					break;
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

		rg_main.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup rg, int id) {
				// TODO Auto-generated method stub
				switch (id) {
				case R.id.rb_home:
					vp_main_tab.setCurrentItem(0);
					break;
				case R.id.rb_search:
					vp_main_tab.setCurrentItem(1);
					break;
				case R.id.rb_essay:
					vp_main_tab.setCurrentItem(2);
					break;
				case R.id.rb_activity:
					vp_main_tab.setCurrentItem(3);
					break;
				case R.id.rb_person:
					vp_main_tab.setCurrentItem(4);
					break;
				default:
					break;
				}
			}
		});
	}

	public void MyLayoutOnClick(View view) {
		Intent intent = new Intent();
		switch (view.getId()) {
		case R.id.layout_upload:
			intent.setClass(MainActivity.this, UploadActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_information:
			intent.setClass(MainActivity.this, InformationActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_recommend:
			intent.setClass(MainActivity.this, RecommendActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_notify:
			intent.setClass(MainActivity.this, NotifyActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_setting:
			intent.setClass(MainActivity.this, SettingActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_exit:
			MyDialog();
			break;
		default:
			break;
		}
	}

	public void MyTextViewOnClick(View v) {
		SearchFragment.et_search.setText(((Button) v).getText().toString());
		if (Integer.parseInt(v.getTag().toString()) == 0) {
			((Button) v).setTextColor(Color.WHITE);
			((Button) v).setBackgroundColor(Color.parseColor("#358fcb"));
			v.setTag("1");
		} else {
			((Button) v).setTextColor(Color.BLACK);
			((Button) v).setBackgroundColor(Color.WHITE);
			v.setTag("0");
		}
	}
	
	protected void MyDialog() {
		Builder builder = new Builder(MainActivity.this);
		builder.setMessage("确认退出吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				MainActivity.this.finish();
				
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
}

package com.xrml.kuaican;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xrml.kuaican.customer.toast.CustomToast;
import com.xrml.kuaican.data.App;
import com.xrml.kuaican.fragment.MainTabFragment;
import com.xrml.kuaican.fragment.MyFragment;
import com.xrml.kuaican.fragment.PhoneOrderFragment;
import com.xrml.kuaican.fragment.ShoppingCartFragment;
import com.xrml.kuaican.util.ActionBarUtil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends FragmentActivity {

	public final static String TAG = "FarmerMainActivity";
	public int[] endLocat = new int[2];
	public RelativeLayout animLayout;
	private long mExitTime;
	/**
	 * �������viewpager
	 */
	public static ViewPager vp_main_tab = null;
	private FragmentPagerAdapter mAdapter = null;
	private List<Fragment> mFragments = new ArrayList<Fragment>();

	/**
	 * �ײ����ĸ�radiobutton
	 */
	private RadioButton rb_main_tab_menu1 = null;
	public RadioButton rb_main_tab_menu2 = null;
	private RadioButton rb_main_tab_menu3 = null;
	private RadioButton rb_main_tab_menu4 = null;

	private RadioGroup rg_main_menu = null;
	public Map<String, Bitmap> adPhoto;

	private MainTabFragment mainTabFragment;
	private MyFragment myFragment;
	private PhoneOrderFragment phoneOrderFragment;
	private static ShoppingCartFragment shoppingCartFragment;

	public static Map<String, Object> data = new HashMap<String, Object>();
	public static App app;

	public static ShoppingCartFragment getShoppingCartFragment() {
		if(shoppingCartFragment == null){
			return new ShoppingCartFragment();
		}
		return shoppingCartFragment;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = new App();
		setContentView(R.layout.activity_main);
		initActionBar();
		findViews();
		initAdapter();
		initViewPager();
	}

	private void findViews() {
		rb_main_tab_menu1 = (RadioButton) findViewById(R.id.rb_main_tab_menu1);
		rb_main_tab_menu2 = (RadioButton) findViewById(R.id.rb_main_tab_menu2);
		rb_main_tab_menu3 = (RadioButton) findViewById(R.id.rb_main_tab_menu3);
		rb_main_tab_menu4 = (RadioButton) findViewById(R.id.rb_main_tab_menu4);
		rg_main_menu = (RadioGroup) findViewById(R.id.rg_main_menu);
		vp_main_tab = (ViewPager) findViewById(R.id.vp_main_tab);
		animLayout = (RelativeLayout) findViewById(R.id.anim);
		mainTabFragment = new MainTabFragment();
		myFragment = new MyFragment();
		phoneOrderFragment = new PhoneOrderFragment();
		shoppingCartFragment = new ShoppingCartFragment();
		mainTabFragment.setMainactivity(this);
		mFragments.add(mainTabFragment);
		mFragments.add(shoppingCartFragment);
		mFragments.add(myFragment);
		mFragments.add(phoneOrderFragment);
	}

	private void initAdapter() {
		android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
		// android.support.v4.app.FragmentPagerAdapter.FragmentPagerAdapter
		mAdapter = new FragmentPagerAdapter(fm) {

			@Override
			public int getCount() {
				return mFragments.size();
			}

			@Override
			public android.support.v4.app.Fragment getItem(int arg0) {
				return mFragments.get(arg0);
			}

			// ��ʼ��ÿ��ҳ��ѡ��
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
					initActionBar();
					try {
						mainTabFragment.initActionBar();
					} catch (Exception e) {
						// e.printStackTrace();
					}
					break;
				case 1:
					rb_main_tab_menu2.setChecked(true);
					ActionBarUtil.initMainActionBar(getApplicationContext(),
							getActionBar(), "���ﳵ");
					break;
				case 2:
					rb_main_tab_menu3.setChecked(true);
					ActionBarUtil.initMainActionBar(getApplicationContext(),
							getActionBar(), "�ҵ�");
					break;
				case 3:
					rb_main_tab_menu4.setChecked(true);
					ActionBarUtil.initMainActionBar(getApplicationContext(),
							getActionBar(), "�绰��");
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
					break;
				case R.id.rb_main_tab_menu2:
					vp_main_tab.setCurrentItem(1);
					break;
				case R.id.rb_main_tab_menu3:
					vp_main_tab.setCurrentItem(2);
					break;
				case R.id.rb_main_tab_menu4:
					vp_main_tab.setCurrentItem(3);
					break;
				default:
					break;
				}

			}
		});

	}

	// ��ʼ����ҳ��actionBar
	private void initActionBar() {
		app = (App) getApplication();
		if (app.getUser() == null && app.getArea() == null) {
			ActionBarUtil.initMainActionBar(MainActivity.this, getActionBar(),
					"δ��¼", "��ѡ������");
		} else if (app.getUser() != null && app.getArea() == null) {
			ActionBarUtil.initMainActionBar(MainActivity.this, getActionBar(),
					"�ѵ�¼", "��ѡ������");
		} else if (app.getUser() != null && app.getArea() != null
				&& app.getArea().getCurrAreaName() != null) {
			ActionBarUtil.initMainActionBar(MainActivity.this, getActionBar(),
					"�ѵ�¼", app.getArea().getCurrAreaName());
		} else if (app.getUser() == null && app.getArea() != null
				&& app.getArea().getCurrAreaName() != null) {
			ActionBarUtil.initMainActionBar(MainActivity.this, getActionBar(),
					"δ��¼", app.getArea().getCurrAreaName());
		} else if (app.getUser() != null && app.getArea() != null
				&& app.getArea().getCurrAreaName() == null) {
			ActionBarUtil.initMainActionBar(MainActivity.this, getActionBar(),
					"�ѵ�¼", "��ѡ������");
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (mainTabFragment.rl_search.getVisibility() == 0) {
			mainTabFragment.rl_search.setVisibility(View.INVISIBLE);
			return false;
		}
		if (myFragment.isDownLoad) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					MainActivity.this);
			builder.setTitle("У��");
			builder.setMessage("���ں�̨�����°汾,�˳���ֹͣ����?");
			builder.setPositiveButton("ȷ��",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int arg1) {
							finish();
							System.exit(0);
						}
					});
			builder.setNegativeButton("ȡ��",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int arg1) {
							dialog.dismiss();
						}
					});
			builder.create().show();
		} else {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				if ((System.currentTimeMillis() - mExitTime) > 2000) {
					CustomToast.showToast(this, "�ٰ�һ���˳�����", 2000);
					mExitTime = System.currentTimeMillis();
				} else {
					finish();
					System.exit(0);
				}
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}

package com.qlf.plants;

import java.util.ArrayList;
import java.util.List;

import com.qlf.plants.fragment.MyFlowersFragment;
import com.qlf.plants.fragment.MyPlantsFragment;
import com.qlf.plants.fragment.MyPosFragment;
import com.qlf.plants.fragment.MyWarningFragment;
import com.qlf.plants.fragment.SettingFragment;
import com.qlf.plants.fragment.UserInfoFragment;
import com.qlf.plants.thread.PlantInfoThread;
import com.qlf.plants.userdata.UserData;
import com.qlf.plants.utils.ActionBarUtil;
import com.qlf.plants.view.CircleImageView;
import com.qlf.plants.view.SlidingMenu;
import com.zcw.togglebutton.ToggleButton;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode.VmPolicy;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 主界面
 * 
 * @author yxw19_000
 * 
 */
public class MainActivity extends FragmentActivity implements OnClickListener {

	public static SlidingMenu mLeftMenu;
	private CircleImageView user_img;

	ToggleButton button;
	private long mExitTime;

	private List<Fragment> mFragments = new ArrayList<Fragment>();
	public android.support.v4.app.FragmentManager fm;
	public FragmentTransaction transaction;
	private MyPlantsFragment myPlantsFragment;

	private RelativeLayout my_flower, my_pos, my_plants, my_yujing;
	private ImageView setting;
	private ProgressDialog progressDialog;

	public static LinearLayout main_background;
	
	public static  TextView textViewUserName;
	public static  TextView textViewSign;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 100) {
				progressDialog.dismiss();
				mLeftMenu.toggle();
			}else if(msg.what == 0x123){
				Toast.makeText(MainActivity.this, "资源加载完成！", Toast.LENGTH_SHORT).show();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		mLeftMenu = (SlidingMenu) findViewById(R.id.id_menu);
		user_img = (CircleImageView) findViewById(R.id.user_img);

		user_img.setOnClickListener(this);
		initView();
		myPlantsFragment = new MyPlantsFragment();
		mFragments.add(myPlantsFragment);
		initAdapter();
//		PlantInfoThread thread = new PlantInfoThread(getApplicationContext(),handler);
//		thread.execute();
	}

	@SuppressLint("NewApi") 
	public void initView() {
		my_flower = (RelativeLayout) findViewById(R.id.my_flower);
		my_plants = (RelativeLayout) findViewById(R.id.my_plants);
	//	my_pos = (RelativeLayout) findViewById(R.id.my_pos);
		my_yujing = (RelativeLayout) findViewById(R.id.yujing);
		setting = (ImageView) findViewById(R.id.setting_img);
		main_background = (LinearLayout) findViewById(R.id.main_background);

		textViewSign =  (TextView) findViewById(R.id.user_qianming);
		textViewUserName = (TextView) findViewById(R.id.user_id);
		
		UserData userData = new UserData(this);
		
		if(userData.getUserNameAndSign()!=null){
			textViewSign.setText(userData.getUserNameAndSign().get("sign").toString());
			textViewUserName.setText(userData.getUserNameAndSign().get("username").toString());
		}
		
		
		if(userData.getBackground()!=null){
			main_background.setBackground(getResources().getDrawable(Integer.parseInt(userData.getBackground().get("backgroundid"))));
		}
		
		my_flower.setOnClickListener(this);
		my_plants.setOnClickListener(this);
		//my_pos.setOnClickListener(this);
		my_yujing.setOnClickListener(this);
		setting.setOnClickListener(this);
	}

	private void initAdapter() {
		fm = getSupportFragmentManager();
		// 步骤一：添加一个FragmentTransaction的实例
		transaction = fm.beginTransaction();
		// 步骤二：用add()方法加上Fragment的对象rightFragment
		MyPlantsFragment rightFragment = new MyPlantsFragment();
		transaction.add(R.id.frame, rightFragment);
		// 步骤三：调用commit()方法使得FragmentTransaction实例的改变生效
		transaction.commit();
	}

	public static void toggleMenu(View view) {
		mLeftMenu.toggle();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.user_img:
			mLeftMenu.toggle();
			transaction = fm.beginTransaction();
			UserInfoFragment userInfoFragment = new UserInfoFragment();
			transaction.replace(R.id.frame, userInfoFragment);
			transaction.commit();
			break;
//		case R.id.my_pos:
//			mLeftMenu.toggle();
//			transaction = fm.beginTransaction();
//			MyPosFragment myPosFragment = new MyPosFragment();
//			transaction.replace(R.id.frame, myPosFragment);
//			transaction.commit();
//			break;
		case R.id.my_plants:
			mLeftMenu.toggle();
			transaction = fm.beginTransaction();
			MyPlantsFragment myPlantsFragment = new MyPlantsFragment();
			transaction.replace(R.id.frame, myPlantsFragment);
			transaction.commit();
			break;
		case R.id.my_flower:
//			if(PlantInfoThread.data == null){
//				Toast.makeText(getApplicationContext(), "资源正在加载...请稍候", Toast.LENGTH_SHORT).show();
//				break;
//			}
			progressDialog = new ProgressDialog(MainActivity.this);
			progressDialog.setTitle("提示");
			progressDialog.setMessage("加载中...");
			progressDialog.show();
			new Thread() {
				@Override
				public void run() {
					try {
						Thread.sleep(2000);
						handler.sendEmptyMessage(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}.start();
			transaction = fm.beginTransaction();
			MyFlowersFragment myFlowersFragment = new MyFlowersFragment();
			transaction.replace(R.id.frame, myFlowersFragment);
			transaction.commit();
			break;
		case R.id.yujing:
			mLeftMenu.toggle();
			transaction = fm.beginTransaction();
			MyWarningFragment mWarningFragment = new MyWarningFragment();
			transaction.replace(R.id.frame, mWarningFragment);
			transaction.commit();
			break;
		case R.id.setting_img:
			mLeftMenu.toggle();
			transaction = fm.beginTransaction();
			SettingFragment settingFragment = new SettingFragment();
			transaction.replace(R.id.frame, settingFragment);
			transaction.commit();
			break;
		}
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
}

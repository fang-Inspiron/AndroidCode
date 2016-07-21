package com.xrml.kuaican.activity;

import com.xrml.kuaican.MainActivity;
import com.xrml.kuaican.R;
import com.xrml.kuaican.customer.toast.CustomToast;
import com.xrml.kuaican.data.App;
import com.xrml.kuaican.model.Area;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash_activity);
		ImageView iv = (ImageView) findViewById(R.id.splash_iv);
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
		alphaAnimation.setDuration(1500);
		iv.setAnimation(alphaAnimation);
		alphaAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				checkIsFirstLogin();
			}
		});
	}

	private void checkIsFirstLogin() {
		Intent intent = new Intent();
		SharedPreferences sp = getSharedPreferences("userinfo",
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		String state = sp.getString("isFirstLogin", "");
		if (state.equals("") || state == null) {
			// 第一次登陆
			firstLogin(intent, editor);
		} else {
			// 不是第一次登陆
			Area area = new Area();
			area.setCurrAreaName(sp.getString("CURRAREANAME", ""));
			if (area.getCurrAreaName() == null
					|| area.getCurrAreaName().equals("")) {
				firstLogin(intent, editor);
			} else {
				area.setComplainPhone(sp.getString("COMPLAINPHONE", ""));
				area.setCurrAreaId(sp.getString("CURRAREAID", ""));
				area.setCurrCity(sp.getString("CURRCITY", ""));
				area.setDes(sp.getString("DES", ""));
				area.setOrderConsumeTime(sp.getString("ORDERCONSUMETIME", ""));
				App app = (App) getApplication();
				app.setArea(area);
				intent.setClass(SplashActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		}
		editor.commit();
	}

	private void firstLogin(Intent intent, Editor editor) {
		CustomToast.showToast(SplashActivity.this, "第一次进入,请先选择城市",
				2000);
		intent.setClass(SplashActivity.this, ChooseCity.class);
		intent.putExtra("isFirstLogin", "yes");
		startActivity(intent);
		finish();
	}
}

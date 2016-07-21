package com.fastfood.activity;

import java.util.Timer;
import java.util.TimerTask;
import com.fastfood.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class WelcomeActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		
		final Intent intent = new Intent();
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				intent.setClass(WelcomeActivity.this, MainActivity.class);
				WelcomeActivity.this.startActivity(intent);
				finish();
			}
		};
		timer.schedule(task, 2 * 1000);
	}
}

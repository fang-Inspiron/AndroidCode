package com.example.share;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class Welcome extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);

		final Intent intent = new Intent();
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				intent.setClass(Welcome.this, Login.class);
				Welcome.this.startActivity(intent);
				finish();
			}
		};
		timer.schedule(task, 2 * 1000);
	}
}

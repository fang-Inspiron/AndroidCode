package com.example.share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class SettingActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_setting);
	}
	
	public void MySettingLayoutOnClick(View view) {
		Intent intent = new Intent();
		switch (view.getId()) {
		case R.id.layout_basics:
			intent.setClass(SettingActivity.this, MySetBasics.class);
			startActivity(intent);
			break;
		case R.id.layout_alterPswd:
			intent.setClass(SettingActivity.this, MySetAlterPswd.class);
			startActivity(intent);
			break;
		case R.id.layout_messageSet:
			intent.setClass(SettingActivity.this, MySetMessageSet.class);
			startActivity(intent);
			break;
		case R.id.layout_clearCache:
			Toast toast = Toast.makeText(getApplicationContext(), "»º´æÒÑÇå³ý",Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 100);
			toast.show();
			break;
		default:
			break;
		}
	}
}

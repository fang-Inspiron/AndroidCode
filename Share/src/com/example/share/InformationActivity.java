package com.example.share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class InformationActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_information);
	}

	public void MyInforLayoutOnClick(View view) {
		Intent intent = new Intent();
		switch (view.getId()) {
		case R.id.layout_newConcern:
			intent.setClass(InformationActivity.this, MyInforNewConcern.class);
			startActivity(intent);
			break;
		case R.id.layout_privateLetter:
			intent.setClass(InformationActivity.this, MyInforSixin.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

}

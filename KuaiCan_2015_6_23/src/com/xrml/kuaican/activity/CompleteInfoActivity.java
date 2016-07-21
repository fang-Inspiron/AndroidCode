package com.xrml.kuaican.activity;

import com.xrml.kuaican.R;
import com.xrml.kuaican.util.ActionBarUtil;

import android.app.Activity;
import android.os.Bundle;

public class CompleteInfoActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complete_info_activity);
		ActionBarUtil.initActionBar(getApplicationContext(), getActionBar(),
				"²¹È«ÐÅÏ¢");
	}
}

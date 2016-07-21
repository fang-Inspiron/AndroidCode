package com.fastfood.mine;

import com.fastfood.R;
import com.fastfood.utils.ActionBarUtil;
import android.app.Activity;
import android.os.Bundle;

public class AlterPasswordActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_alter_password);
		ActionBarUtil.initActionBar(AlterPasswordActivity.this, getActionBar(), "ÐÞ¸ÄÃÜÂë", 0);
	}

}

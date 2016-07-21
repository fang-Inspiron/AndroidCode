package com.fastfood.mine;

import com.fastfood.R;
import com.fastfood.utils.ActionBarUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AddressActivity extends Activity {

	private Button btn_newAddress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_address);
		ActionBarUtil.initActionBar(getApplication(), getActionBar(), " ’ªıµÿ÷∑", 0);
		btn_newAddress = (Button) findViewById(R.id.btn_newAddress);
		btn_newAddress.setOnClickListener(new MyBtnAddressListener());
	}

	class MyBtnAddressListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(AddressActivity.this, NewAddressActivity.class);
			startActivity(intent);
		}
	}
	
}

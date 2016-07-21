package com.qlf.plants.activity;

import com.qlf.plants.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class Pop extends Activity {
	//
	// /**
	// * 底部的四个radiobutton
	// */
	// private RadioButton radioButton0 = null;
	// private RadioButton radioButton1 = null;
	// private RadioButton radioButton2 = null;
	// private RadioButton radioButton3 = null;
	// private RadioButton radioButton4 = null;
	// private RadioButton radioButton5 = null;

	public static RadioGroup radioGroup = null;
	private TextView title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pop_main);
		findViews();
		initView();
		initIntent();
	}

	public void initIntent() {
		Intent intent = getIntent();
		int i = intent.getIntExtra("id", 0);
		switch (i) {
		case 1:
			title.setText("补光时长（min）");
			break;
		case 2:
			title.setText("浇水时长（min）");
			break;
		case 3:
			title.setText("加湿时长（min）");
			break;
		case 4:
			title.setText("通风时长（min）");
			break;
			default:break;
		}
	}

	private void findViews() {
		// radioButton0 = (RadioButton) findViewById(R.id.btn_0);
		// radioButton1 = (RadioButton) findViewById(R.id.btn_1);
		// radioButton2 = (RadioButton) findViewById(R.id.btn_2);
		// radioButton3 = (RadioButton) findViewById(R.id.btn_3);
		// radioButton4 = (RadioButton) findViewById(R.id.btn_4);
		// radioButton5 = (RadioButton) findViewById(R.id.btn_5);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		title = (TextView) findViewById(R.id.pop_title);
	}

	public void initView() {
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup rg, int id) {
				switch (id) {
				case R.id.btn_0:
					PlantInfoActivity.time = 5;
					break;
				case R.id.btn_1:
					PlantInfoActivity.time = 10;
					break;
				case R.id.btn_2:
					PlantInfoActivity.time = 15;
					break;
				case R.id.btn_3:
					PlantInfoActivity.time = 20;
					break;
				case R.id.btn_4:
					PlantInfoActivity.time = 25;
					break;
				case R.id.btn_5:
					PlantInfoActivity.time = 30;
					break;
				}
				finish();
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

	public void close(View v) {
		finish();
	}
}
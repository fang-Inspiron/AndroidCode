package com.example.share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class MyInforDialog extends Activity{
	private int position;
	private String name="与share小格对话";
	private TextView dialogName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		position = intent.getIntExtra("position", 0);
		name = "与"+intent.getStringExtra("name")+"对话";
		
		System.out.println(name);
		switch (position) {
		case 0:
			setContentView(R.layout.myinfor_dialognull);
			dialogName = (TextView) findViewById(R.id.tv_dialogName);
			dialogName.setText(name);
			break;
		case 1:
			setContentView(R.layout.myinfor_dialog);
			break;
		case 2:
			setContentView(R.layout.myinfor_dialognull);
			dialogName = (TextView) findViewById(R.id.tv_dialogName);
			dialogName.setText(name);
			break;
		case 3:
			setContentView(R.layout.myinfor_dialognull);
			dialogName = (TextView) findViewById(R.id.tv_dialogName);
			dialogName.setText(name);
			break;
		default:
			break;
		}
	}
}

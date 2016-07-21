package com.qlf.plants.activity;

import com.qlf.plants.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

public class AddPosActivity extends Activity{

	TextView pos_id;
	private EditText editTextTerminalName;
	private EditText editTextTerminalPos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pos_result);
		pos_id = (TextView) findViewById(R.id.actionbar_title);
		Intent intent = getIntent();
		String id = intent.getStringExtra("id");
		pos_id.setText("ID:"+id);
	}
}

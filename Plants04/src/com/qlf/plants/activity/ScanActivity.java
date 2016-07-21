package com.qlf.plants.activity;


import com.qlf.plants.R;
import com.qlf.plants.scan.ScanCaptureAct;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ScanActivity extends Activity{

	Context me = ScanActivity.this;

	Button btnScan;
	TextView tvScanResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	//	btnScan = (Button) findViewById(R.id.btnScan);
	//	tvScanResult = (TextView) findViewById(R.id.tvScanResult);

		btnScan.setOnClickListener(ScanOcl);

	}

	public Button.OnClickListener ScanOcl = new Button.OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			try {

				Intent intent = new Intent();
				intent.setClass(me, ScanCaptureAct.class);
				startActivityForResult(intent, 30);

			} catch (Exception e) {
				Toast.makeText(me, "相机打开失败,请检查相机是否可正常使用", Toast.LENGTH_LONG).show();
			}

		}

	};

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {

		switch (requestCode) {
		case 30:
			if (resultCode == RESULT_OK) {
				String contents = intent.getStringExtra("SCAN_RESULT");

				tvScanResult.setText("扫描结果:" + contents);

			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancel
				Toast.makeText(me, "扫描失败", Toast.LENGTH_SHORT).show();
			}
			break;
		}

	}
}

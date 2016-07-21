package com.qlf.plants.activity;

import java.util.Map;

import com.qlf.plants.R;
import com.qlf.plants.fragment.MyPlantsFragment;
import com.qlf.plants.thread.AddTerminalThread;
import com.qlf.plants.thread.GetTerminalInfoThread;
import com.qlf.plants.thread.GetTerminalThread;
import com.qlf.plants.userdata.UserData;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class AddPlantActivity extends Activity {

	public static ImageView img;
	private EditText editText;
	private EditText editTextName;
	private EditText editTextPosition;

	private Button addPlantOk;
	private Map<String,Object> data;
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what == AddTerminalThread.SUCCESS){
				data = (Map<String, Object>) msg.obj;
				System.out.println("code-------------"+data.get("code").toString());
				if(data.get("code").toString().equals("1")){
					Toast.makeText(getApplicationContext(), "添加成功!", Toast.LENGTH_LONG).show();
					MyPlantsFragment.my.update();
					finish();
				}
				else
					Toast.makeText(getApplicationContext(), data.get("data").toString(), Toast.LENGTH_LONG).show();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_plant_main);

		editText = (EditText) findViewById(R.id.add_plant_id);
		addPlantOk = (Button) findViewById(R.id.add_plant_ok);
		
		editTextName = (EditText) findViewById(R.id.add_plant_name);
		editTextPosition = (EditText) findViewById(R.id.add_plant_position);

		Intent in = getIntent();
		final String s = in.getStringExtra("id");

		editText.setText(" ID : " + s);
		img = (ImageView) findViewById(R.id.add_plant_img);
		img.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						SelectPicPopupWindow.class);
				intent.putExtra("type", "2");
				startActivity(intent);
			}
		});

		UserData data = new UserData(getApplicationContext());
		final int userId = Integer.parseInt(data.getUserInfo().get("userId").toString());
		final String certificate = data.getUserInfo().get("certificate").toString();
		
		addPlantOk.setOnClickListener(new OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				System.out.println("OKOK");
				new AddTerminalThread(handler, 1, editTextName.getText().toString(), editTextPosition.getText().toString(), s, userId,certificate).start();
			}
		});
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}

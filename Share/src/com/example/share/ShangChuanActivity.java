package com.example.share;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ShangChuanActivity extends Activity implements OnClickListener{
	private Spinner shang_spinner;
	private String[] array = { "原创作品", "设计资料", "设计师观点", "设计教程" };
	private Button bt_sub;
	private Button didian;
	private ImageView shang_photo;
	private ImageButton back;
	private ProgressDialog progressDialog;

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 100) {
				progressDialog.dismiss();
				Toast.makeText(ShangChuanActivity.this, "上传成功",Toast.LENGTH_SHORT).show();
				finish();
			}
			if (msg.what == 200) {
				didian.setText("陕西省,西安市");
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shangchuan);
		
		bt_sub = (Button) findViewById(R.id.shang_bt_sub);
		didian = (Button) findViewById(R.id.shang_didian);
		shang_photo = (ImageView) findViewById(R.id.shang_photo);
		back = (ImageButton) findViewById(R.id.back);
		
		shang_photo.setOnClickListener( this);
		back.setOnClickListener( this);
		bt_sub.setOnClickListener( this);
		handler.sendEmptyMessageDelayed(200, 2000);//第一个参数：msg.what;第二个参数：延迟时间（2s）

		shang_spinner = (Spinner) findViewById(R.id.shang_spinner);
		// 创建ArrayAdapter对象
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, array);
		//为Spinner设置Adapter
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		shang_spinner.setAdapter(adapter);
	}

	public void searchOnClick(View v) {
		if (Integer.parseInt(v.getTag().toString()) == 0) {
			v.setBackgroundResource(R.drawable.yinying2);
			v.setTag("1");
			((Button) v).setTextColor(Color.WHITE);
		} else {
			v.setBackgroundResource(R.drawable.yinying);
			v.setTag("0");
			((Button) v).setTextColor(Color.BLACK);
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.shang_photo:
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
			startActivityForResult(intent, 999999);
			break;
		case R.id.back:
			finish();
			break;
		case R.id.shang_spinner:
			shang_spinner.setBackgroundResource(R.drawable.shang_xuanze_press);
			break;
		case R.id.shang_bt_sub:
			AlertDialog.Builder builder = new AlertDialog.Builder(
					ShangChuanActivity.this)
					.setTitle("提示")
					.setMessage("确认发布?")
					.setPositiveButton("确认",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int arg1) {
									dialog.dismiss();
									progressDialog = new ProgressDialog(ShangChuanActivity.this);
									progressDialog.setTitle("提示");
									progressDialog.setMessage("内容发布中...");
									progressDialog.show();
									new Thread() {
										public void run() {
											try {
												Thread.sleep(2000);
												handler.sendEmptyMessage(100);
											} catch (InterruptedException e) {
												e.printStackTrace();
											}
										};
									}.start();
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,int arg1) {
									dialog.dismiss();
								}
							});
			builder.create().show();
			break;
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 999999:
			if (resultCode == RESULT_OK) {
				ContentResolver resolver = getContentResolver();
				Uri uri = data.getData();
				Bitmap bm;
				try {
					bm = MediaStore.Images.Media.getBitmap(resolver, uri);
					Bitmap bitmap = Bitmap.createScaledBitmap(bm, 200, 100, true);
					shang_photo.setImageBitmap(bitmap);
				} catch (Exception e) {
					Toast.makeText(ShangChuanActivity.this, "选择图片失败", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		}
	}
}

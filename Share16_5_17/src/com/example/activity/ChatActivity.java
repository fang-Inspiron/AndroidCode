package com.example.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.share4_15.R;

public class ChatActivity extends Activity {

	private ImageButton back;
	private TextView chatTitle;
	private LinearLayout ll_message1;
	private LinearLayout ll_message2;
	private Button chat_sub;
	private EditText et_content;
	private ScrollView sl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chat_activity);
		back = (ImageButton) findViewById(R.id.chat_back);
		chat_sub = (Button) findViewById(R.id.chat_bt_sub);
		et_content = (EditText) findViewById(R.id.chat_content);
		et_content.clearFocus();
		chatTitle = (TextView) findViewById(R.id.chat_title);
		ll_message1 = (LinearLayout) findViewById(R.id.chat_ll_message1);
		ll_message2 = (LinearLayout) findViewById(R.id.chat_ll_message2);
		sl = (ScrollView) findViewById(R.id.chat_sl);
		Intent intent = getIntent();
		if (!intent.getStringExtra("name").equals("Share小兰")) {
			ll_message1.setVisibility(View.GONE);
			chatTitle.setText("与" + intent.getStringExtra("name") + "对话");
		}
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		chat_sub.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!et_content.getText().toString().equals("")) {
					sendContent(et_content.getText().toString());
					et_content.setText("");
				}
			}
		});
		et_content.setOnTouchListener(new OnTouchListener() {

			@SuppressLint("ClickableViewAccessibility") @Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				et_content.setFocusable(true);
				et_content.requestFocus();
				sl.post(new Runnable() {

					@Override
					public void run() {
						sl.scrollTo(0, 1000);
					}
				});
				return false;
			}
		});
	}

	protected void sendContent(String message) {
		RelativeLayout relativeLayout = new RelativeLayout(ChatActivity.this);
		@SuppressWarnings("deprecation")
		RelativeLayout.LayoutParams relatineParams = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, dipToPx(70));
		relativeLayout.setLayoutParams(relatineParams);

		// 添加图像
		ImageView photo = new ImageView(ChatActivity.this);
		photo.setId(1000);
		photo.setBackgroundResource(R.drawable.my);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				dipToPx(50), dipToPx(50));
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		params.addRule(RelativeLayout.CENTER_VERTICAL);
		params.rightMargin = dipToPx(10);
		photo.setLayoutParams(params);

		// 添加消息内容
		TextView content = new TextView(ChatActivity.this);
		content.setBackgroundResource(R.drawable.chat_img1);
		content.setText(message);
		content.setTextSize(18);
		content.setPadding(dipToPx(10), 0, dipToPx(10), 0);
		content.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
		RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, dipToPx(50));
		params2.leftMargin = dipToPx(10);
		params2.addRule(RelativeLayout.CENTER_VERTICAL);
		params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		params2.rightMargin = dipToPx(70);
		content.setLayoutParams(params2);

		relativeLayout.addView(photo, params);
		relativeLayout.addView(content, params2);
		ll_message2.addView(relativeLayout);
	}

	// 将dp转换成px
	public int dipToPx(float dp) {
		float px = getResources().getDisplayMetrics().density;
		return (int) (dp * px + 0.5f);
	}
}

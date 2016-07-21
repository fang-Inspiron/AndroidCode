package com.xrml.kuaican.util;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.xrml.kuaican.R;

public class ActionBarUtil {
	public static void initMainActionBar(Context context, ActionBar actionBar,
			String title, String location) {
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.orange_line_bg);
		@SuppressWarnings("deprecation")
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		// 自定义标题栏
		actionBar.setBackgroundDrawable(bd);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setTitle(title);
		actionBar.setCustomView(R.layout.actionbar_location);
		TextView choose_location = (TextView) actionBar.getCustomView()
				.findViewById(R.id.actionBar_text);
		choose_location.setText(location);
		choose_location.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
				return false;
			}
		});
		TextView tv_title = (TextView) actionBar.getCustomView().findViewById(
				R.id.user_state);
		tv_title.setText(title);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
	}

	public static void initMainActionBar(Context context, ActionBar actionBar,
			String title) {
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.orange_line_bg);
		@SuppressWarnings("deprecation")
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		// 自定义标题栏
		actionBar.setBackgroundDrawable(bd);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setTitle(title);
		actionBar.setCustomView(R.layout.actionbar_location);
		RelativeLayout rl = (RelativeLayout) actionBar.getCustomView()
				.findViewById(R.id.actionbar_rl);
		rl.setVisibility(View.INVISIBLE);
		TextView tv_title = (TextView) actionBar.getCustomView().findViewById(
				R.id.user_state);
		tv_title.setText(title);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
	}

	public static void initActionBar(Context context, ActionBar actionBar,
			String title) {
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.orange_line_bg);
		@SuppressWarnings("deprecation")
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		// 自定义标题栏
		actionBar.setBackgroundDrawable(bd);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setTitle(title);
		actionBar.setCustomView(R.layout.actionbar);
		TextView tv_title = (TextView) actionBar.getCustomView().findViewById(
				R.id.actionbar_user_state);
		tv_title.setText(title);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
	}
}
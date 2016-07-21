package com.fastfood.utils;

import com.fastfood.R;
import android.app.ActionBar;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class ActionBarUtil {

	public static void initActionBar(Context context, ActionBar actionBar,
			String title, int curr) {
		if (actionBar==null) {
			System.out.println("null");
		}
		actionBar.setTitle(title);
		actionBar.setCustomView(R.layout.action_bar);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		if (curr == 0) {
			TextView tv_title = (TextView) actionBar.getCustomView().findViewById(R.id.actionbar_title);
			tv_title.setText(title);
			tv_title.setVisibility(View.VISIBLE);
			
			ImageView iv_back = (ImageView) actionBar.getCustomView().findViewById(R.id.action_back);
			iv_back.setVisibility(View.VISIBLE);
		}
		if (curr == 1) {
			TextView tv_title = (TextView) actionBar.getCustomView().findViewById(R.id.actionbar_title);
			tv_title.setText(title);
			tv_title.setVisibility(View.VISIBLE);
			
			Spinner spinner = (Spinner) actionBar.getCustomView().findViewById(
					R.id.spinnerCity);
			spinner.setVisibility(View.VISIBLE);
			ImageView iv_locate = (ImageView) actionBar.getCustomView()
					.findViewById(R.id.iv_locate);
			iv_locate.setVisibility(View.VISIBLE);
		}
	}

}

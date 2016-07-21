package com.qlf.plants.utils;


import com.qlf.plants.R;

import android.app.ActionBar;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ActionBarUtil {
	public static void initMainActionBar(Context context, ActionBar actionBar,
			String title, int curr) {
		actionBar.setTitle(title);
		actionBar.setCustomView(R.layout.action_bar);
		TextView tv_title = (TextView) actionBar.getCustomView().findViewById(
				R.id.actionbar_title);
		tv_title.setText(title);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		if (curr == 0) {
			ImageView iv_back = (ImageView) actionBar.getCustomView()
					.findViewById(R.id.action_back);
			iv_back.setVisibility(View.INVISIBLE);
		}
	}
}
package com.example.share;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

public class MySetBasics extends Activity {
	private ImageView iv_man;
	private ImageView iv_woman;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myset_basics);

		iv_man = (ImageView) findViewById(R.id.iv_man);
		iv_woman = (ImageView) findViewById(R.id.iv_woman);

		iv_man.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (Integer.parseInt(iv_man.getTag().toString()) == 0) {
					iv_man.setBackgroundResource(R.drawable.man);
					iv_man.setTag(1);
					iv_woman.setBackgroundResource(R.drawable.woman);
					iv_woman.setTag(0);
				} else {
					iv_man.setBackgroundResource(R.drawable.man1);
					iv_man.setTag(0);
					iv_woman.setBackgroundResource(R.drawable.woman1);
					iv_woman.setTag(1);
				}
			}
		});

		iv_woman.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (Integer.parseInt(iv_woman.getTag().toString()) == 0) {
					iv_man.setBackgroundResource(R.drawable.man1);
					iv_man.setTag(0);
					iv_woman.setBackgroundResource(R.drawable.woman1);
					iv_woman.setTag(1);
				} else {
					iv_man.setBackgroundResource(R.drawable.man);
					iv_man.setTag(1);
					iv_woman.setBackgroundResource(R.drawable.woman);
					iv_woman.setTag(0);
				}
			}
		});
	}

}

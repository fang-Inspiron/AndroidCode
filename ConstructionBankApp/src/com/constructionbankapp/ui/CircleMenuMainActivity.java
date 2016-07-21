package com.constructionbankapp.ui;

/*
 * Copyright 2013 Csaba Szugyiczki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.constructionbankapp.ui.R;
import com.duguang.baseanimation.ui.base.BaseActivity;
import com.duguang.baseanimation.ui.customview.circlemenu.CircleLayout;
import com.duguang.baseanimation.ui.customview.circlemenu.CircleLayout.OnItemClickListener;
import com.duguang.baseanimation.ui.customview.circlemenu.CircleLayout.OnItemSelectedListener;

/**
 * 圆形旋转菜单(自定义Layout+ImageView)
 * 
 * @author Administrator
 * 
 */
public class CircleMenuMainActivity extends BaseActivity implements
		OnItemSelectedListener, OnItemClickListener, OnClickListener {
	private RelativeLayout relbtn;
	private CircleLayout circleMenu;

	@Override
	public void setView() {
		setContentView(R.layout.construction_bank_main);

	}

	@Override
	public void initView() {
		circleMenu = (CircleLayout) findViewById(R.id.main_circle_layout);
		relbtn = (RelativeLayout) findViewById(R.id.relbtn);
	}

	@Override
	public void setListener() {
		circleMenu.setOnItemSelectedListener(this);
		circleMenu.setOnItemClickListener(this);

		relbtn.setOnClickListener(this);
	}

	@Override
	public void onItemSelected(View view, int position, long id, String name) {
		// selectedTextView.setText(name);
	}

	@Override
	public void onItemClick(View view, int position, long id, String name) {
		Toast.makeText(getApplicationContext(), " " + name, Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.relbtn:
			Toast.makeText(getApplicationContext(), "更换头像", Toast.LENGTH_SHORT)
					.show();
			break;
		default:
			break;
		}
	}

}

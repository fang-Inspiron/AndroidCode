package com.qlf.plants.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.qlf.plants.MainActivity;
import com.qlf.plants.R;
import com.qlf.plants.activity.PopBackground;
import com.qlf.plants.userdata.UserData;
import com.zcw.togglebutton.ToggleButton;
import com.zcw.togglebutton.ToggleButton.OnToggleChanged;

public class SettingFragment extends Fragment{

	View rootView;
	ImageView back;
	RelativeLayout setBackground;
	
	private ToggleButton message_setting;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater.inflate(R.layout.setting_main, container, false);
		back = (ImageView) rootView.findViewById(R.id.action_back);
		setBackground = (RelativeLayout) rootView.findViewById(R.id.set_background);
		setBackground.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), PopBackground.class);
				startActivity(intent);
			}
		});
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.mLeftMenu.toggle();
			}
		});
		initButton();
		return rootView;
	}
	
	public void initButton(){
		message_setting = (ToggleButton) rootView.findViewById(R.id.message_setting);
		
		final UserData userData = new UserData(getActivity());
		
		if(userData.getMessage()!=null){
			if(userData.getMessage().get("message").booleanValue()){
				message_setting.toggleOn();
			}
		}
		
		message_setting.setOnToggleChanged(new OnToggleChanged() {
			@Override
			public void onToggle(boolean on) {
				userData.saveMessage(on);
			}
		});
	}
}

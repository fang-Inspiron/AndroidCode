package com.qlf.plants.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.qlf.plants.MainActivity;
import com.qlf.plants.R;
import com.qlf.plants.userdata.UserData;
import com.zcw.togglebutton.ToggleButton;
import com.zcw.togglebutton.ToggleButton.OnToggleChanged;

public class MyWarningFragment extends Fragment {

	View rootView;
	ImageView back;

	ToggleButton warn1;
	ToggleButton warn2;
	ToggleButton warn3;
	ToggleButton warn4;
	ToggleButton warn5;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater.inflate(R.layout.mywarning_main, container, false);
		back = (ImageView) rootView.findViewById(R.id.action_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.mLeftMenu.toggle();
			}
		});
		initView();
		return rootView;
	}

	public void initView() {
		warn1 = (ToggleButton) rootView.findViewById(R.id.warning_kongqiwendu);
		warn2 = (ToggleButton) rootView.findViewById(R.id.warning_kongqishidu);
		warn3 = (ToggleButton) rootView.findViewById(R.id.warning_turangshidu);
		warn4 = (ToggleButton) rootView.findViewById(R.id.warning_guangzhao);
		warn5 = (ToggleButton) rootView.findViewById(R.id.warning_water);
		

		final UserData userData = new UserData(getActivity());
		
			if(userData.getWarning1()!=null){
				System.out.println("warn1:"+userData.getWarning1().get("warn1").booleanValue());
				if(userData.getWarning1().get("warn1").booleanValue()){
					warn1.toggleOn();
				}
			}
			if(userData.getWarning2()!=null){
				if(userData.getWarning2().get("warn2").booleanValue()){
					warn2.toggleOn();
				}
			}
			if(userData.getWarning3()!=null){
				if(userData.getWarning3().get("warn3").booleanValue()){
					warn3.toggleOn();
				}
			}
			if(userData.getWarning4()!=null){
				if(userData.getWarning4().get("warn4").booleanValue()){
					warn4.toggleOn();
				}
			}
			if(userData.getWarning5()!=null){
				if(userData.getWarning5().get("warn5").booleanValue()){
					warn5.toggleOn();
				}
			}

		warn1.setOnToggleChanged(new OnToggleChanged() {
			@Override
			public void onToggle(boolean on) {
				userData.saveWarning1(on);
				System.out.println(on);
			}
		});
		warn2.setOnToggleChanged(new OnToggleChanged() {
			@Override
			public void onToggle(boolean on) {
				userData.saveWarning2(on);
			}
		});
		warn3.setOnToggleChanged(new OnToggleChanged() {
			@Override
			public void onToggle(boolean on) {
				userData.saveWarning3(on);
			}
		});
		warn4.setOnToggleChanged(new OnToggleChanged() {
			@Override
			public void onToggle(boolean on) {
				userData.saveWarning4(on);
			}
		});
		warn5.setOnToggleChanged(new OnToggleChanged() {
			@Override
			public void onToggle(boolean on) {
				userData.saveWarning5(on);
			}
		});

	}
}

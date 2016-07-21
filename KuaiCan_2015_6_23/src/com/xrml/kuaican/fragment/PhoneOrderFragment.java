package com.xrml.kuaican.fragment;

import com.xrml.kuaican.R;
import com.xrml.kuaican.activity.ChooseArea;
import com.xrml.kuaican.activity.ChooseCity;
import com.xrml.kuaican.customer.toast.CustomToast;
import com.xrml.kuaican.data.App;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class PhoneOrderFragment extends Fragment {
	private App app;
	private View rootView;
	private ImageButton callPhone;
	private String phoneNumbStr;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = (View) inflater.inflate(R.layout.call, container, false);
		initInfo();
		findViews();
		return rootView;
	}

	private void findViews() {
		callPhone = (ImageButton) rootView.findViewById(R.id.call_button);
		callPhone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (app.getArea() == null) {
					CustomToast.showToast(getActivity(), "请先选择城市",
							2000);
					Intent intent = new Intent(getActivity(), ChooseCity.class);
					startActivity(intent);
				} else if (app.getArea().getComplainPhone() == null
						|| app.getArea().getComplainPhone().equals("")) {
					CustomToast.showToast(getActivity(), "请选择区域",
							2000);
					Intent intent = new Intent(getActivity(), ChooseArea.class);
					startActivity(intent);
				} else {
					phoneNumbStr = app.getArea().getComplainPhone();
					Intent intent = new Intent();
					intent.setAction("android.intent.action.DIAL");
					intent.setData(Uri.parse("tel:" + phoneNumbStr));
					startActivity(intent);
				}
			}
		});
	}

	private void initInfo() {
		app = (App) getActivity().getApplication();
	}
}
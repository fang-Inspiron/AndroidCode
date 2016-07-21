package com.fragment;

import com.fastfood.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;

public class PhoneFragment extends Fragment {

	private View rootView;
	private ImageView imageView_phone;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 rootView = inflater.inflate(R.layout.fragment_phone, container, false);
		 imageView_phone = (ImageView) rootView.findViewById(R.id.imageView_phone);
		 imageView_phone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				start();
			}
		});
		 
		return rootView;
	}
	
	public void start() {
		String phoneNumbStr = "18829291821";
		Intent intent = new Intent();
		intent.setAction("android.intent.action.DIAL");
		intent.setData(Uri.parse("tel:" + phoneNumbStr));
		startActivity(intent);
	}
}

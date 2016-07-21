package com.data;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.app.Application;
import android.content.Context;

public class Queue extends Application{

	public static RequestQueue queue;
	public static Context mContext;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		queue = Volley.newRequestQueue(getApplicationContext());
		mContext = getApplicationContext();
	}
	
	public static RequestQueue getInstance() {
		if (queue != null) {
			return queue;
		} 
		return Volley.newRequestQueue(mContext);
	}
}

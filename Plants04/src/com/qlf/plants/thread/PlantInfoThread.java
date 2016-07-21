package com.qlf.plants.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qlf.plants.bean.PlantBean;
import com.qlf.plants.utils.DBHelper;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;



public class PlantInfoThread extends AsyncTask<Void, Integer, List<PlantBean>>{

	private Context context;
	private int start;
	private int end=0;
	public static List<PlantBean> data;
	private Handler handler;
	public PlantInfoThread(Context context,int start,int end) {
		this.context = context;
		this.start = start;
		this.end = end;
	}
	
	public PlantInfoThread(Context context,Handler handler) {
		this.context = context;
		this.handler = handler;
	}
	
	public PlantInfoThread(Context context){
		this.context = context;
	}
	
	protected List<PlantBean> doInBackground(Void... arg0) {
		List<PlantBean> list = new ArrayList<PlantBean>();
		DBHelper dbHelper = new DBHelper(context);
		if(end!=0)
			list = dbHelper.getPlantInfo(start, end);
		else{
			list = dbHelper.getPlantInfo();
			data = list;
		}
		return list;
	}

	@Override
	protected void onPostExecute(List<PlantBean> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		System.out.println(result.size());
	//	Toast.makeText(context, "资源加载完成！", Toast.LENGTH_LONG).show();
	}
	
	
}

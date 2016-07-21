package com.fastfood.mine;

import com.data.App;
import com.fastfood.R;
import com.fastfood.activity.LoginActivity;
import com.fastfood.activity.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DeclareActivity extends Activity {

	private App app;
	private String orderExplainDes;
	private String orderExplainPhone;
	private TextView order_explain_callPhone;// 拨打的电话
	private TextView order_explain_desc;// 订餐说明

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_declare);
		
//		orderExplainDes = app.getArea().getDes();
//		orderExplainPhone = app.getArea().getComplainPhone();
//		System.out.println(orderExplainDes + ",详情");
//		System.out.println(orderExplainPhone);
		
	}

}

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
	private TextView order_explain_callPhone;// ����ĵ绰
	private TextView order_explain_desc;// ����˵��

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_declare);
		
//		orderExplainDes = app.getArea().getDes();
//		orderExplainPhone = app.getArea().getComplainPhone();
//		System.out.println(orderExplainDes + ",����");
//		System.out.println(orderExplainPhone);
		
	}

}

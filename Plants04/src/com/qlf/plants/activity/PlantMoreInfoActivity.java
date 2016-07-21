package com.qlf.plants.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.achartengine.GraphicalView;

import com.qlf.plants.R;
import com.qlf.plants.graph.GraphUtils;
import com.qlf.plants.graph.StudentGradeMessage;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

public class PlantMoreInfoActivity extends Activity{

	
	private StudentGradeMessage sgm ;
	private Map<String, StudentGradeMessage> stuGradeMap ;
	private List<HashMap<String, StudentGradeMessage>> studentGradeList = new ArrayList<HashMap<String,StudentGradeMessage>>();
	private Button button1;
	private GraphicalView charView;
	private LinearLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.plants_info_main_second);
		initData();
		layout = (LinearLayout) findViewById(R.id.down_info);
		charView = (GraphicalView) GraphUtils.getInstance().getLineChartView(PlantMoreInfoActivity.this, studentGradeList , "B");
		//layout.removeAllViews();
		layout.addView(charView);
	}
	
	private void initData() {
		// TODO Auto-generated method stub
		stuGradeMap = new HashMap<String, StudentGradeMessage>();
		sgm = new StudentGradeMessage();
		sgm.setName("1.1");
		sgm.setMath(80);
		sgm.setChinese(87);
		sgm.setEnglish(78);
		sgm.setTotal(248);
		sgm.setNumChinese(10);
		sgm.setNumEnglish(25);
		sgm.setNumMath(9);
		sgm.setNumTotal(12);
		stuGradeMap.put("name",sgm );
		studentGradeList.add((HashMap<String, StudentGradeMessage>) stuGradeMap);
		stuGradeMap = new HashMap<String, StudentGradeMessage>();
		sgm = new StudentGradeMessage();
		sgm.setName("1.2");
		sgm.setMath(67);
		sgm.setChinese(89);
		sgm.setEnglish(80);
		sgm.setTotal(236);
		sgm.setNumChinese(5);
		sgm.setNumEnglish(21);
		sgm.setNumMath(23);
		sgm.setNumTotal(16);
		stuGradeMap.put("name",sgm );
		studentGradeList.add((HashMap<String, StudentGradeMessage>) stuGradeMap);
		stuGradeMap = new HashMap<String, StudentGradeMessage>();
		sgm = new StudentGradeMessage();
		sgm.setName("1.3");
		sgm.setMath(50);
		sgm.setChinese(80);
		sgm.setEnglish(70);
		sgm.setTotal(200);
		sgm.setNumChinese(10);
		sgm.setNumEnglish(35);
		sgm.setNumMath(39);
		sgm.setNumTotal(29);
		stuGradeMap.put("name",sgm );
		studentGradeList.add((HashMap<String, StudentGradeMessage>) stuGradeMap);
		stuGradeMap = new HashMap<String, StudentGradeMessage>();
		sgm = new StudentGradeMessage();
		sgm.setName("1.4");
		sgm.setMath(60);
		sgm.setChinese(67);
		sgm.setEnglish(60);
		sgm.setTotal(187);
		sgm.setNumChinese(40);
		sgm.setNumEnglish(30);
		sgm.setNumMath(30);
		sgm.setNumTotal(40);
		stuGradeMap.put("name",sgm );
		studentGradeList.add((HashMap<String, StudentGradeMessage>) stuGradeMap);
		stuGradeMap = new HashMap<String, StudentGradeMessage>();
		sgm = new StudentGradeMessage();
		sgm.setName("1.5");
		sgm.setMath(80);
		sgm.setChinese(87);
		sgm.setEnglish(88);
		sgm.setTotal(258);
		sgm.setNumChinese(9);
		sgm.setNumEnglish(7);
		sgm.setNumMath(13);
		sgm.setNumTotal(14);
		stuGradeMap.put("name",sgm );
		studentGradeList.add((HashMap<String, StudentGradeMessage>) stuGradeMap);
		stuGradeMap = new HashMap<String, StudentGradeMessage>();
		sgm = new StudentGradeMessage();
		sgm.setName("1.6");
		sgm.setMath(90);
		sgm.setChinese(80);
		sgm.setEnglish(50);
		sgm.setTotal(220);
		sgm.setNumChinese(10);
		sgm.setNumEnglish(35);
		sgm.setNumMath(2);
		sgm.setNumTotal(21);
		stuGradeMap.put("name",sgm );
		studentGradeList.add((HashMap<String, StudentGradeMessage>) stuGradeMap);
	}
}

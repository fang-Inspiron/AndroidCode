package com.qlf.plants.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.achartengine.GraphicalView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.qlf.plants.R;
import com.qlf.plants.bean.PosBean;
import com.qlf.plants.fragment.MyPlantsFragment;
import com.qlf.plants.graph.GraphUtils;
import com.qlf.plants.graph.StudentGradeMessage;
import com.qlf.plants.thread.BUGTerminalThread;
import com.qlf.plants.thread.DeleteTerminalThread;
import com.qlf.plants.thread.GetTerminalInfoThread;
import com.qlf.plants.thread.GetTerminalThread;
import com.qlf.plants.userdata.UserData;
import com.qlf.plants.view.DefinedScrollView;
import com.qlf.plants.view.SelectPicPopupWindow;
import com.qlf.plants.view.VerticalPager;

/**
 * 利用自定义的ScrollView加载视图来实现翻页效果，下面用页码和总页数标识当前的视图是第几屏
 * 
 * @author WANGXIAOHONG
 * 
 */
public class PlantInfoActivity extends Activity implements OnClickListener {

	public static RelativeLayout up;
	private LinearLayout down_info;
	private RadioGroup rg_main_menu;

	private ImageView back;
	// 上下两个布局
	public View addview1 = null, addview2 = null;
	// 补光、浇水、加湿、通风
	private ImageView buguang, jiaoshui, jiashi, tongfeng;
	private Button change;

	SelectPicPopupWindow menuWindow;

	private StudentGradeMessage sgm;
	private Map<String, StudentGradeMessage> stuGradeMap;
	private List<HashMap<String, StudentGradeMessage>> studentGradeList = new ArrayList<HashMap<String, StudentGradeMessage>>();
	private GraphicalView charView;
	private static Map<String, Object> data;
	private static TextView temperature;
	private static TextView airhumidity;
	private static TextView soilmoisture;
	private static TextView illumination;
	private TextView title;
	private ImageView update, delete;
	public static Context context;
	public int type = 0;
	public static int time = 0;
	static ProgressDialog dialog;
	int terId;
	UserData userDate;
	public static Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == GetTerminalInfoThread.SUCCESS) {
				data = (Map<String, Object>) msg.obj;
				if (Integer.parseInt(data.get("code").toString()) == 1) {
					if (dialog != null)
						dialog.dismiss();
					if (data.get("temperature") != null) {
						String temp = ((Map<String, Object>) data.get("data"))
								.get("temperature").toString();
						String air = ((Map<String, Object>) data.get("data"))
								.get("airhumidity").toString();
						String soil = ((Map<String, Object>) data.get("data"))
								.get("soilmoisture").toString();
						String ill = ((Map<String, Object>) data.get("data"))
								.get("waterlevel").toString();
						System.out.println("OK------------------");
						temperature.setText(temp);
						airhumidity.setText(air);
						soilmoisture.setText(soil);
						illumination.setText(ill);
					} else {
						Toast.makeText(PlantInfoActivity.context, "与终端未连接...",
								Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(PlantInfoActivity.context,
							data.get("data").toString(), Toast.LENGTH_SHORT)
							.show();
				}
			} else if (msg.what == 0x123) {
				Toast.makeText(PlantInfoActivity.context, "请检查网络",
						Toast.LENGTH_LONG).show();
			} else if(msg.what == DeleteTerminalThread.DELETE_SUCCESS){
				data = (Map<String, Object>) msg.obj;
				Toast.makeText(PlantInfoActivity.context,
						data.get("data").toString(), Toast.LENGTH_SHORT)
						.show();
				MyPlantsFragment.my.update();
			}
		};
	};
	

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.plants_info_main);
		// setupView();
		context = this;
		Intent intent = getIntent();
		terId = intent.getIntExtra("id", 0);
		System.out.println("i=" + terId);

		initView1();
		LayoutParams params = new LayoutParams(dip2px(getApplicationContext(),
				300), dip2px(getApplicationContext(), 280));
		params.gravity = Gravity.CENTER_HORIZONTAL;
		params.topMargin = dip2px(getApplicationContext(), 20);
		charView = (GraphicalView) GraphUtils.getInstance().getLineChartView(
				PlantInfoActivity.this, studentGradeList, "B");
		down_info.addView(charView, params);

		RelativeLayout relativeLayout = new RelativeLayout(
				getApplicationContext());
		TextView textView = new TextView(getApplicationContext());
		textView.setText("今日趋势");
		textView.setTextColor(Color.WHITE);
		android.widget.RelativeLayout.LayoutParams layoutParams = new android.widget.RelativeLayout.LayoutParams(
				android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT,
				android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL,
				RelativeLayout.TRUE);
		relativeLayout.addView(textView, layoutParams);
		Button button = new Button(getApplicationContext());
		button.setBackground(getResources()
				.getDrawable(R.drawable.history));
		android.widget.RelativeLayout.LayoutParams layoutParams1 = new android.widget.RelativeLayout.LayoutParams(
				dip2px(getApplicationContext(), 40),
				dip2px(getApplicationContext(), 25));
		layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
				RelativeLayout.TRUE);
		relativeLayout.addView(button, layoutParams1);
		
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), HistoryInfoActivity.class);
				startActivity(intent);
			}
		});
		
		LayoutParams pParams = new LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		pParams.gravity = Gravity.CENTER_HORIZONTAL;
		pParams.topMargin = dip2px(getApplicationContext(), 5);
		down_info.addView(relativeLayout, pParams);
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
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
		stuGradeMap.put("name", sgm);
		studentGradeList
				.add((HashMap<String, StudentGradeMessage>) stuGradeMap);
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
		stuGradeMap.put("name", sgm);
		studentGradeList
				.add((HashMap<String, StudentGradeMessage>) stuGradeMap);
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
		stuGradeMap.put("name", sgm);
		studentGradeList
				.add((HashMap<String, StudentGradeMessage>) stuGradeMap);
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
		stuGradeMap.put("name", sgm);
		studentGradeList
				.add((HashMap<String, StudentGradeMessage>) stuGradeMap);
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
		stuGradeMap.put("name", sgm);
		studentGradeList
				.add((HashMap<String, StudentGradeMessage>) stuGradeMap);
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
		stuGradeMap.put("name", sgm);
		studentGradeList
				.add((HashMap<String, StudentGradeMessage>) stuGradeMap);
	}

	private void addAchart() {
		// init example series data
		Random rand = new Random();
		int size = 20;
		GraphViewData[] data = new GraphViewData[size];
		for (int i = 0; i < size; i++) {
			data[i] = new GraphViewData(i, rand.nextInt(20));
		}
		GraphViewSeries exampleSeries = new GraphViewSeries(data);

		GraphView graphView;
		graphView = new LineGraphView(this // context
				, "GraphViewDemo" // heading
		);
		// graphView.setHorizontalLabels(new String[] { "small", "middle",
		// "big"});
		// AseoZdpAseo.initType(this, AseoZdpAseo.INSERT_TYPE);
		graphView.addSeries(exampleSeries); // data
		graphView.setCustomLabelFormatter(new CustomLabelFormatter() {
			@Override
			public String formatLabel(double value, boolean isValueX) {
				if (isValueX) {
					if (value < 5) {
						return "small";
					} else if (value < 15) {
						return "middle";
					} else {
						return "big";
					}
				}
				return null; // let graphview generate Y-axis label for us
			}
		});

		/*
		 * use Date as x axis label
		 */
		long now = new Date().getTime();
		data = new GraphViewData[size];
		for (int i = 0; i < size; i++) {
			data[i] = new GraphViewData(now + (i * 60 * 60 * 24 * 1000),
					rand.nextInt(20)); // next day
		}
		exampleSeries = new GraphViewSeries(data);

		graphView = new LineGraphView(this, "GraphViewDemo");
		((LineGraphView) graphView).setDrawBackground(true);
		graphView.addSeries(exampleSeries); // data

		/*
		 * date as label formatter
		 */
		final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d");
		graphView.setCustomLabelFormatter(new CustomLabelFormatter() {
			@Override
			public String formatLabel(double value, boolean isValueX) {
				if (isValueX) {
					Date d = new Date((long) value);
					return dateFormat.format(d);
				}
				return null; // let graphview generate Y-axis label for us
			}
		});
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				1200, 1200);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		params.leftMargin = 80;
		graphView.setLayoutParams(params);
		down_info.addView(graphView);
	}

	private void initView1() {
		up = (RelativeLayout) findViewById(R.id.up_info);
		down_info = (LinearLayout) findViewById(R.id.down_info);
		rg_main_menu = (RadioGroup) findViewById(R.id.rg_main_menu);
		temperature = (TextView) findViewById(R.id.temperature);
		airhumidity = (TextView) findViewById(R.id.airhumidity);
		soilmoisture = (TextView) findViewById(R.id.soilmoisture);
		illumination = (TextView) findViewById(R.id.illumination);
		title = (TextView) findViewById(R.id.actionbar_title);
		title.setText(MyPlantsFragment.getName(terId));
		userDate = new UserData(getApplicationContext());
		new GetTerminalInfoThread(handler, terId, userDate.getUserInfo()
				.get("certificate").toString()).start();
		// addAchart();
		initData();
		back = (ImageView) findViewById(R.id.action_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		buguang = (ImageView) findViewById(R.id.buguang);
		jiaoshui = (ImageView) findViewById(R.id.jiaoshui);
		jiashi = (ImageView) findViewById(R.id.jiashi);
		tongfeng = (ImageView) findViewById(R.id.tongfeng);
		change = (Button) findViewById(R.id.button_change);
		update = (ImageView) findViewById(R.id.update);
		delete = (ImageView) findViewById(R.id.action_delete);

		update.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog = new ProgressDialog(PlantInfoActivity.this);
				dialog.setTitle("提示");
				dialog.setMessage("刷新中...");
				dialog.show();
				new Thread() {
					@Override
					public void run() {
						try {
							Thread.sleep(1000);
							new GetTerminalInfoThread(handler, terId, userDate
									.getUserInfo().get("certificate")
									.toString()).start();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						super.run();
					}
				}.start();
			}
		});

		delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog();
			}
		});

		change.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Intent intent2 = new Intent(getApplicationContext(),
//						PlantMoreInfoActivity.class);
//				startActivity(intent2);
//				overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
			}
		});
		buguang.setOnClickListener(this);
		jiaoshui.setOnClickListener(this);
		jiashi.setOnClickListener(this);
		tongfeng.setOnClickListener(this);
	}

	protected void dialog() {
		AlertDialog.Builder builder = new Builder(PlantInfoActivity.this);
		builder.setMessage("确认删除吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new AlertDialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				new DeleteTerminalThread(handler,terId,userDate.getUserInfo().get("certificate").toString()).start();
				PlantInfoActivity.this.finish();
			}
		});
		builder.setNegativeButton("取消", new AlertDialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getApplicationContext(), Pop.class);
		switch (v.getId()) {
		case R.id.buguang:
			type = 2;
			intent.putExtra("id", 1);
			startActivity(intent);
			break;
		case R.id.jiaoshui:
			type = 3;
			intent.putExtra("id", 2);
			startActivity(intent);
			break;
		case R.id.jiashi:
			type = 5;
			intent.putExtra("id", 3);
			startActivity(intent);
			break;
		case R.id.tongfeng:
			type = 4;
			intent.putExtra("id", 4);
			startActivity(intent);
			break;
		}
		 new BUGTerminalThread(handler,terId, type, time,userDate.getUserInfo().get("certificate").toString()).start();
	}
}
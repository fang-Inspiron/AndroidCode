package com.fastfood.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.fastfood.R;
import com.fastfood.utils.ActionBarUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fastfood.utils.AbstractSpinerAdapter;
import com.fastfood.utils.SpinerPopWindow;

public class SubmitOrderActivity extends Activity implements OnClickListener,
		AbstractSpinerAdapter.IOnItemSelectListener {

	private SpinerPopWindow mSpinerPopWindow;
	private TextView showTime;
	private ImageButton mBtnDropDown;
	private List<String> timeList = new ArrayList<String>();
	private int add_time_length = 8;
	private Button button_submit_order;
	private EditText et_address;
	private EditText et_name;
	private EditText et_phone;
	private EditText et_mark;
	private RelativeLayout ll_lowwarn;
	private FrameLayout fl_layout;
	private TextView tv_show_time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submit_order);
		ActionBarUtil.initActionBar(getApplicationContext(), getActionBar(),	"�ύ����", 0);

		getId();
		setupViews();
		
	}

	private void setupViews() {
		ll_lowwarn.setOnClickListener(this);
		mBtnDropDown.setOnClickListener(this);

		String[] times = new String[add_time_length];
		int temp = 0;
		for (int i = 0; i < add_time_length; i++) {
			temp += 15;
			times[i] = transformTime(temp);	//��ʼ��times����
			timeList.add(times[i]);						//��times������ӵ�timeList��
		}

		mSpinerPopWindow = new SpinerPopWindow(this);
		mSpinerPopWindow.refreshData(timeList, 0);
		mSpinerPopWindow.setItemListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.ll_lowwarn:
			showSpinWindow();
			break;
		case R.id.ib_dropdown:
			showSpinWindow();
			break;
		case R.id.button_submit_order:
			myDialog();
			break;
		}
	}
	
	@Override
	public void onItemClick(int pos) {
		setTime(pos);
	}
	
	public void myDialog() {
		AlertDialog.Builder builder = new Builder(SubmitOrderActivity.this);
		builder.setMessage("ȷ���ύ��");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				SubmitOrderActivity.this.finish();
			}
		});
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private void setTime(int pos) {
		if (pos >= 0 && pos <= timeList.size()) {
			String value = timeList.get(pos);

			showTime.setText(value);
		}
	}

	private void showSpinWindow() {
		Log.e("", "showSpinWindow");
		mSpinerPopWindow.setWidth(showTime.getWidth());
		mSpinerPopWindow.showAsDropDown(showTime);
	}
	
	public void getId() {
		showTime = (TextView) findViewById(R.id.tv_show_time);
		mBtnDropDown = (ImageButton) findViewById(R.id.ib_dropdown);
		button_submit_order = (Button) findViewById(R.id.button_submit_order);
		et_address = (EditText) findViewById(R.id.et_address);
		et_name = (EditText) findViewById(R.id.et_name);
		et_phone = (EditText) findViewById(R.id.et_phone);
		et_mark = (EditText) findViewById(R.id.et_mark);
		ll_lowwarn = (RelativeLayout) findViewById(R.id.ll_lowwarn);
	}
	
	public String transformTime(int addTime) {
		long currentTime = System.currentTimeMillis();// 1����ȡ��ǰʱ�䣬��ȡ����ʱ��������long���͵ģ���λ�Ǻ���
		currentTime += addTime * 60 * 1000;// 2������������ϼ���addTime���ӣ�
		Date date = new Date(currentTime);// 3����ʽ��ʱ�䣬��ȡ���ľ��ǵ�ǰʱ��addTime֮���ʱ��
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");// 4������ʱ���ʽ������
		System.out.println(dateFormat.format(date));
		return (dateFormat.format(date));
	}
}

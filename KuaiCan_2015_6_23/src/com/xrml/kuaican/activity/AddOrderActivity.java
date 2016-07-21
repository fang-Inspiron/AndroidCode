package com.xrml.kuaican.activity;

import java.util.Map;

import com.xrml.kuaican.R;
import com.xrml.kuaican.customer.toast.CustomToast;
import com.xrml.kuaican.data.App;
import com.xrml.kuaican.net.AddOrderThread;
import com.xrml.kuaican.util.ActionBarUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddOrderActivity extends Activity {
	private App app;
	private TextView home;// actionbar���ؼ�
	private EditText orderName;// ��Ʒ��
	private EditText orderPrice;// ��Ʒ�۸�
	private EditText orderCount;// ��Ʒ����
	private EditText orderCategory;// ��Ʒ���
	private EditText orderKey;// �ؼ���
	private EditText orderInfo;// ��Ʒ����
	private ImageView orderPhoto;// ��ƷͼƬ
	private Button addOrder;// ���
	private LinearLayout add_order_ll;// ��Ƭѡ��
	private TextView add_order_paizhao;// ����
	private TextView add_order_xiangce;// ���
	private TextView add_order_cancle;// ȡ��
	private Map<String, Object> data;
	private ProgressDialog progressDialog;
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AddOrderThread.NETWORK_ERROR:
				CustomToast.showToast(AddOrderActivity.this, "�������Ӵ���", 2000);
				break;
			case AddOrderThread.SUCCESS:
				data = (Map<String, Object>) msg.obj;
				checkResult();
				break;
			}
		}
	};

	private void checkResult() {
		String response = data.get("response").toString();
		progressDialog.dismiss();
		if (response.equals("update_ok")) {
			CustomToast.showToast(AddOrderActivity.this, "��ӳɹ�", 2000);
		} else if (response.equals("update_error")) {
			CustomToast.showToast(AddOrderActivity.this, "���ʧ��", 2000);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_order_activirt);
		ActionBarUtil.initActionBar(getApplicationContext(), getActionBar(),
				"������Ʒ");
		app = (App) getApplication();
		progressDialog = new ProgressDialog(AddOrderActivity.this);
		progressDialog.setTitle("У��");
		progressDialog.setMessage("�����,���Ժ�...");
		findViews();
	}

	private void findViews() {
		AddOrderListener addOrderListener = new AddOrderListener();
		orderName = (EditText) findViewById(R.id.add_order_orderName);
		orderPrice = (EditText) findViewById(R.id.add_order_orderPrice);
		orderCount = (EditText) findViewById(R.id.add_order_orderCount);
		orderCategory = (EditText) findViewById(R.id.add_order_orderCategory);
		orderKey = (EditText) findViewById(R.id.add_order_orderKey);
		orderInfo = (EditText) findViewById(R.id.add_order_orderInfo);
		orderPhoto = (ImageView) findViewById(R.id.add_order_addPhoto);
		home = (TextView) getActionBar().getCustomView().findViewById(
				R.id.user_state);
		// ѡ����Ƭ
		add_order_ll = (LinearLayout) findViewById(R.id.add_order_ll);
		// ����
		add_order_paizhao = (TextView) findViewById(R.id.add_order_paizhao);
		add_order_paizhao.setOnClickListener(addOrderListener);
		// ������ȡͼƬ
		add_order_xiangce = (TextView) findViewById(R.id.add_order_xiangce);
		add_order_xiangce.setOnClickListener(addOrderListener);
		// ȡ��
		add_order_cancle = (TextView) findViewById(R.id.add_order_quxiao);
		add_order_cancle.setOnClickListener(addOrderListener);
		// ����
		home.setOnClickListener(addOrderListener);
		// �����ƷͼƬ
		orderPhoto.setOnClickListener(addOrderListener);
		// �����Ʒ
		addOrder = (Button) findViewById(R.id.add_order_addOk);
		addOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String goodsName = orderName.getText().toString();
				String goodsCategory = orderCategory.getText().toString();
				String goodsPrice = orderPrice.getText().toString();
				String goodsContent = orderInfo.getText().toString();
				String goodsCount = orderCount.getText().toString();
				String keyWords = orderKey.getText().toString();
				if (goodsName.equals("") || goodsName == null) {
					CustomToast
							.showToast(AddOrderActivity.this, "��������Ʒ��", 2000);
				} else if (goodsCategory.equals("") || goodsCategory == null) {
					CustomToast.showToast(AddOrderActivity.this, "��������Ʒ���",
							2000);
				} else if (goodsPrice.equals("") || goodsPrice == null) {
					CustomToast.showToast(AddOrderActivity.this, "��������Ʒ�۸�",
							2000);
				} else if (goodsContent.equals("") || goodsContent == null) {
					CustomToast.showToast(AddOrderActivity.this, "��������Ʒ����",
							2000);
				} else if (goodsCount.equals("") || goodsCount == null) {
					CustomToast.showToast(AddOrderActivity.this, "��������Ʒ����",
							2000);
				} else if (keyWords.equals("") || keyWords == null) {
					CustomToast.showToast(AddOrderActivity.this, "��������Ʒ�ؼ���",
							2000);
				} else {
					new AddOrderThread(handler, app.getUser().getUserId(),
							1 + "", goodsName, goodsPrice, goodsContent,
							goodsCount, keyWords, app.getUser().getCheckStr())
							.start();
				}
			}
		});

	}

	class AddOrderListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			// �����ƷͼƬ
			case R.id.add_order_addPhoto:
			//	TranslateAnimation animation = new TranslateAnimation(AddOrderActivity.this, R.)
				add_order_ll.setVisibility(View.VISIBLE);
				break;
			// ����
			case R.id.actionbar_user_state:
				finish();
				break;
			// ����ȡ
			case R.id.add_order_xiangce:
				break;
			// ����
			case R.id.add_order_paizhao:
				break;
			// ȡ��
			case R.id.add_order_quxiao:
				add_order_ll.setVisibility(View.GONE);
				break;
			}
		}
	}
}

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
	private TextView home;// actionbar返回键
	private EditText orderName;// 商品名
	private EditText orderPrice;// 商品价格
	private EditText orderCount;// 商品数量
	private EditText orderCategory;// 商品类别
	private EditText orderKey;// 关键字
	private EditText orderInfo;// 商品详情
	private ImageView orderPhoto;// 商品图片
	private Button addOrder;// 添加
	private LinearLayout add_order_ll;// 照片选择
	private TextView add_order_paizhao;// 拍照
	private TextView add_order_xiangce;// 相册
	private TextView add_order_cancle;// 取消
	private Map<String, Object> data;
	private ProgressDialog progressDialog;
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AddOrderThread.NETWORK_ERROR:
				CustomToast.showToast(AddOrderActivity.this, "网络连接错误", 2000);
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
			CustomToast.showToast(AddOrderActivity.this, "添加成功", 2000);
		} else if (response.equals("update_error")) {
			CustomToast.showToast(AddOrderActivity.this, "添加失败", 2000);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_order_activirt);
		ActionBarUtil.initActionBar(getApplicationContext(), getActionBar(),
				"新增商品");
		app = (App) getApplication();
		progressDialog = new ProgressDialog(AddOrderActivity.this);
		progressDialog.setTitle("校帮");
		progressDialog.setMessage("添加中,请稍后...");
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
		// 选择照片
		add_order_ll = (LinearLayout) findViewById(R.id.add_order_ll);
		// 拍照
		add_order_paizhao = (TextView) findViewById(R.id.add_order_paizhao);
		add_order_paizhao.setOnClickListener(addOrderListener);
		// 从相册获取图片
		add_order_xiangce = (TextView) findViewById(R.id.add_order_xiangce);
		add_order_xiangce.setOnClickListener(addOrderListener);
		// 取消
		add_order_cancle = (TextView) findViewById(R.id.add_order_quxiao);
		add_order_cancle.setOnClickListener(addOrderListener);
		// 返回
		home.setOnClickListener(addOrderListener);
		// 添加商品图片
		orderPhoto.setOnClickListener(addOrderListener);
		// 添加商品
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
							.showToast(AddOrderActivity.this, "请输入商品名", 2000);
				} else if (goodsCategory.equals("") || goodsCategory == null) {
					CustomToast.showToast(AddOrderActivity.this, "请输入商品类别",
							2000);
				} else if (goodsPrice.equals("") || goodsPrice == null) {
					CustomToast.showToast(AddOrderActivity.this, "请输入商品价格",
							2000);
				} else if (goodsContent.equals("") || goodsContent == null) {
					CustomToast.showToast(AddOrderActivity.this, "请输入商品详情",
							2000);
				} else if (goodsCount.equals("") || goodsCount == null) {
					CustomToast.showToast(AddOrderActivity.this, "请输入商品数量",
							2000);
				} else if (keyWords.equals("") || keyWords == null) {
					CustomToast.showToast(AddOrderActivity.this, "请输入商品关键字",
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
			// 添加商品图片
			case R.id.add_order_addPhoto:
			//	TranslateAnimation animation = new TranslateAnimation(AddOrderActivity.this, R.)
				add_order_ll.setVisibility(View.VISIBLE);
				break;
			// 返回
			case R.id.actionbar_user_state:
				finish();
				break;
			// 相册获取
			case R.id.add_order_xiangce:
				break;
			// 拍照
			case R.id.add_order_paizhao:
				break;
			// 取消
			case R.id.add_order_quxiao:
				add_order_ll.setVisibility(View.GONE);
				break;
			}
		}
	}
}

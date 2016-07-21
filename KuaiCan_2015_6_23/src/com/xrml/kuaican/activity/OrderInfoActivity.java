package com.xrml.kuaican.activity;

import java.util.HashMap;
import java.util.Map;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xrml.kuaican.MainActivity;
import com.xrml.kuaican.R;
import com.xrml.kuaican.customer.toast.CustomToast;
import com.xrml.kuaican.data.App;
import com.xrml.kuaican.fragment.MainTabFragment;
import com.xrml.kuaican.fragment.ShoppingCartFragment;
import com.xrml.kuaican.net.DownLoadImage;
import com.xrml.kuaican.util.ActionBarUtil;
import com.xrml.kuaican.util.JSONUtil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderInfoActivity extends Activity {

	private TextView home;
	private String businseePhone;
	private String gmList;
	private String goodsContent;
	private Bitmap goodsImage;
	private String goodsId;
	private String goodsName;
	private String goodsSales;
	private String goodsPrice;
	private App app;
	private Map<String, String> data;
	private ImageView no_image;
	private int[] imgListId = new int[] { R.id.gmList1, R.id.gmList2,
			R.id.gmList3, R.id.gmList4, R.id.gmList5, R.id.gmList6 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_info);
		ActionBarUtil.initActionBar(getApplicationContext(), getActionBar(),
				"商品详情");
		app = (App) getApplication();
		no_image = (ImageView) findViewById(R.id.order_info_noGmList);
		initData();
		try {
			findViews();
		} catch (Exception e) {
			// e.printStackTrace();
		}

	}

	private void initData() {
		Intent intent = getIntent();
		byte buff[] = intent.getByteArrayExtra("itemImage");
		goodsImage = BitmapFactory.decodeByteArray(buff, 0, buff.length);
		businseePhone = intent.getStringExtra("businessPhone");
		gmList = intent.getStringExtra("gmList");
		goodsContent = intent.getStringExtra("goodsContent");
		goodsId = intent.getStringExtra("goodsId");
		goodsName = intent.getStringExtra("goodsName");
		goodsPrice = intent.getStringExtra("goodsPrice");
		goodsSales = intent.getStringExtra("goodsSales");
		if (gmList == null || gmList.equals("") || gmList.equals("null")) {
			no_image.setVisibility(View.VISIBLE);
		} else {
			no_image.setVisibility(View.GONE);
			data = JSONUtil.getGmList(gmList);
			initImage();
		}
	}

	private void initImage() {
		for (int i = 0; i < data.size(); i++) {
			final String imgPath = data.get("goodsImg" + i).toString();
			final ImageView iv_gmList = (ImageView) findViewById(imgListId[i]);
			if (MainTabFragment.photoData.get(imgPath) == null) {
				ImageLoader.getInstance().loadImage(
						DownLoadImage.IMAGE_URL + imgPath,
						new ImageLoadingListener() {
							@Override
							public void onLoadingStarted(String imageUri,
									View view) {

							}

							@Override
							public void onLoadingFailed(String imageUri,
									View view, FailReason failReason) {

							}

							@Override
							public void onLoadingComplete(String imageUri,
									View view, Bitmap loadedImage) {
								@SuppressWarnings("deprecation")
								BitmapDrawable bd = new BitmapDrawable(
										loadedImage);
								iv_gmList.setBackground(bd);
								app.getPhotoData().put(imgPath, loadedImage);
							}

							@Override
							public void onLoadingCancelled(String imageUri,
									View view) {
							}
						});
			} else {
				@SuppressWarnings("deprecation")
				BitmapDrawable bd = new BitmapDrawable(
						MainTabFragment.photoData.get(imgPath));
				iv_gmList.setBackground(bd);
			}
		}
	}

	private void findViews() throws Exception {
		TextView tv_goodsName = (TextView) findViewById(R.id.order_info_goodsName);
		tv_goodsName.setText(goodsName);
		TextView tv_goodsContent = (TextView) findViewById(R.id.order_info_goodsContent);
		tv_goodsContent.setText(goodsContent);
		ImageView iv_goodsImage = (ImageView) findViewById(R.id.order_info_iv);
		iv_goodsImage.setImageBitmap(goodsImage);
		TextView tv_goodsSales = (TextView) findViewById(R.id.order_info_goodsSales);
		tv_goodsSales.setText("月销量: " + goodsSales);
		TextView tv_goodsPrice = (TextView) findViewById(R.id.order_info_goodsPrice);
		tv_goodsPrice.setText("￥ " + goodsPrice);
		TextView tv_goodsXiangqing = (TextView) findViewById(R.id.order_info_xiangqing);
		tv_goodsXiangqing.setText("商品详情：" + goodsContent);
		TextView tv_businessPhone = (TextView) findViewById(R.id.order_info_businessPhonecall);
		tv_businessPhone.setText("咨询电话：" + businseePhone);
		TextView tv_goodsId = (TextView) findViewById(R.id.order_info_goodsId);
		tv_goodsId.setText(goodsId);
		ImageButton playBusiness = (ImageButton) findViewById(R.id.order_info_playPhone);
		playBusiness.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (app.getArea() == null) {
					CustomToast.showToast(OrderInfoActivity.this, "请先选择城市",
							2000);
					Intent intent = new Intent(OrderInfoActivity.this,
							ChooseCity.class);
					startActivity(intent);
				} else if (app.getArea().getComplainPhone() == null
						|| app.getArea().getComplainPhone().equals("")) {
					CustomToast
							.showToast(OrderInfoActivity.this, "请选择区域", 2000);
					Intent intent = new Intent(OrderInfoActivity.this,
							ChooseArea.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent();
					intent.setAction("android.intent.action.DIAL");
					intent.setData(Uri.parse("tel:" + businseePhone));
					startActivity(intent);
				}
			}
		});
		Button submit = (Button) findViewById(R.id.order_info_submit);
		submit.setOnClickListener(new OnClickListener() {

			int c = 1;

			@Override
			public void onClick(View arg0) {
				// addShoppingCart();
				boolean ishad = false;
				ShoppingCartFragment sf = MainActivity
						.getShoppingCartFragment();
				Map<String, Object> map = new HashMap<String, Object>();
				if (MainTabFragment.isFirst == true) {
					for (int i = 0; i < app.goodsList.size(); i++) {
						if (goodsName.equals(app.goodsList.get(i).get(
								"goodsName"))) {
							ishad = true;
							c++;
							Map<String, Object> m = new HashMap<String, Object>();
							m.put("goodsId", goodsId);
							m.put("goodsName", goodsName);
							m.put("goodsPrice", goodsPrice);
							m.put("goodsCount", c);
							MainActivity.app.goodsList.set(i, m);
						}
					}
				}
				if (!ishad) {
					map.put("goodsId", goodsId);
					map.put("goodsName", goodsName);
					map.put("goodsPrice", goodsPrice);
					map.put("goodsCount", 1);
					MainActivity.app.goodsList.add(map);
				}
				try {
					sf.change(OrderInfoActivity.this);
				} catch (Exception e) {
					System.out.println("OrderInfoActivity--->212");
				}
			}
		});
		home = (TextView) getActionBar().getCustomView().findViewById(
				R.id.actionbar_user_state);
		home.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

}

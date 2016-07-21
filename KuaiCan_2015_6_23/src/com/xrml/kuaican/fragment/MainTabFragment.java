package com.xrml.kuaican.fragment;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xrml.kuaican.MainActivity;
import com.xrml.kuaican.R;
import com.xrml.kuaican.activity.ChooseArea;
import com.xrml.kuaican.activity.ChooseCity;
import com.xrml.kuaican.activity.LoginActivity;
import com.xrml.kuaican.activity.OrderInfoActivity;
import com.xrml.kuaican.customer.toast.CustomToast;
import com.xrml.kuaican.data.App;
import com.xrml.kuaican.model.Area;
import com.xrml.kuaican.net.DownLoadImage;
import com.xrml.kuaican.net.GetAdPageByCus;
import com.xrml.kuaican.net.GetGoodsCategory;
import com.xrml.kuaican.net.GetGoodsListThread;
import com.xrml.kuaican.net.IsConnection;
import com.xrml.kuaican.net.SearchGoodsThread;
import com.xrml.kuaican.util.ReFlashListView;
import com.xrml.kuaican.util.ReFlashListView.IReflashListener;

/**
 * ��ҳFragment
 * 
 * @author Administrator
 * 
 */
public class MainTabFragment extends Fragment implements OnClickListener,
		IReflashListener {

	public static String TAG = "MainTabFragment";
	private MainActivity mainActivity;
	public static boolean isFirst = true;// �Ƿ��һ���ж�����
	public static boolean isLoadOrderList = false;
	private View rootView;

	// ���
	private ViewPager vp_main_ad = null;
	private List<View> adList = null;
	private ImageView iv1, iv2, iv3;
	private ImageView[] iv_circles;
	private ImageView iv_ad_circle_point;
	private ViewGroup viewGroup;
	// ����
	private AtomicInteger atomicInteger = new AtomicInteger(0);
	private boolean isContinue = true;

	private Button btn_main_choose_good_type = null;
	private Button btn_main_choose_order_type = null;
	private Button btn_main_choose_price_type = null;
	public static Button btn_main_tab_chooseLocat = null;
	public TextView choose_area = null;
	public TextView user_state = null;
	public ImageView actionBarBack = null;
	public ImageView chooseAreaPhoto = null;
	private TextView ll_main_loading = null;

	// ����
	private EditText mEditTextSearch = null;
	private ImageView mImageViewSearchBack = null;
	private Button mButtonSearch = null;
	private Button mButtonMainSearch = null;
	public RelativeLayout rl_search = null;

	private PopupMenu pm_choose_good_type = null;
	private PopupMenu pm_choose_price_type = null;
	private PopupMenu pm_choose_order_type = null;
	private PopupMenu pm_choose_locat_type = null;
	// ĳ������Ʒ�б�
	private ReFlashListView lv_main_content = null;
	private List<Map<String, Object>> mData = null;
	private static Map<String, Object> data = null;
	// ��ƷͼƬ
	public static Map<String, Bitmap> photoData;
	private Map<String, String> adImageUrl;
	// ĳ������Ʒ���
	private List<Map<String, Object>> cateDataList = null;
	private Map<String, Object> cateData = null;
	// ��ǰĳ������Ʒ���
	private int currGoodsCategoryId = 1;
	private int currGoodsCateSortWay = 1;
	private App app;
	// ������Ʒ��handler
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GetAdPageByCus.NETWORK_ERROR:
			case GetGoodsListThread.NETWORK_ERROR:
			case SearchGoodsThread.NETWORK_ERROR:
				ll_main_loading.setText("��ȡ��Ʒ��Ϣʧ��");
				CustomToast.showToast(getActivity(), "��ȡ��Ʒ��Ϣʧ��", 2000);
				break;
			case GetAdPageByCus.SUCCESS:
				adImageUrl = (Map<String, String>) msg.obj;
				// ��ʼ���ӷ�������ȡ�Ĺ��ͼƬ
				initImagePgoto();
				break;
			case GetGoodsListThread.NO_GOODS:
			case SearchGoodsThread.NO_GOODS:
				lv_main_content.reflashComplete();
				currGoodsCategoryId = 1;
				ll_main_loading.setText("û�и�����Ʒ");
				CustomToast.showToast(getActivity(), "û�и�����Ʒ", 2000);
				break;
			case GetGoodsListThread.SUCCESS:
			case SearchGoodsThread.SUCCESS:
				lv_main_content.reflashComplete();
				ll_main_loading.setVisibility(View.GONE);
				lv_main_content.setVisibility(View.VISIBLE);
				data = (Map<String, Object>) msg.obj;
				app.setData(data);
				mData = (List<Map<String, Object>>) data.get("list");
				GoodsAdapter goodsAdapter = new GoodsAdapter(getActivity());
				lv_main_content.setAdapter(goodsAdapter);
				break;
			case GetGoodsCategory.NETWORK_ERROR:
				ll_main_loading.setText("��ȡ��Ʒ��Ϣʧ��");
				CustomToast.showToast(getActivity(), "��ȡ��Ʒ���ʧ��", 2000);
				break;
			case GetGoodsCategory.SUCCESS:
				cateData = null;
				cateDataList = null;
				cateData = (Map<String, Object>) msg.obj;
				app.setCateData(cateData);
				try {
					cateDataList = (List<Map<String, Object>>) cateData
							.get("list");
					ll_main_loading.setVisibility(View.GONE);
					lv_main_content.setVisibility(View.VISIBLE);
					initGoodsCate();
					initGoodsList();
				} catch (Exception e) {
					ll_main_loading.setVisibility(View.VISIBLE);
					lv_main_content.setVisibility(View.GONE);
					System.out.println("������������Ʒ����\n����ѡ������");
					ll_main_loading.setText("������������Ʒ����\n����ѡ������");
					ll_main_loading.setTextSize(20);
					pm_choose_good_type = null;
					app.setData(null);
				}
				break;
			}
		}
	};

	/**
	 * ��⵱���ӵ������Ǹ�����Ʒ������Ʒ
	 */
	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1001:
				if (IsConnection.checkNet(getActivity())) {
					ll_main_loading.setText("������...");
					// ������Ʒ���
					new GetGoodsCategory(handler,
							app.getArea().getCurrAreaId(), "meal").start();
					myHandler.removeMessages(1001);
					myHandler = null;
				} else {
					myHandler.sendEmptyMessageDelayed(1001, 1500);
				}
				break;
			}
		};
	};

	// ��ʼ����Ʒ����б�
	private void initGoodsCate() {
		Map<String, Object> oMap;
		Menu menu = pm_choose_good_type.getMenu();
		for (int i = 0; i < cateDataList.size(); i++) {
			oMap = cateDataList.get(i);
			menu.add(Menu.NONE, Menu.FIRST + 0, 0, oMap
					.get("goodsCategoryName").toString());
		}
		setPopupListener();
	};

	/**
	 * ��ʼ���ӷ�������ȡ�Ĺ��ͼƬ
	 */
	protected void initImagePgoto() {
		if (mainActivity.adPhoto == null) {
			mainActivity.adPhoto = new HashMap<String, Bitmap>();
		}
		for (int i = 0; i < adImageUrl.size(); i++) {
			if (mainActivity.adPhoto
					.get(adImageUrl.get("adImg" + i).toString()) == null) {
				ImageLoader.getInstance().loadImage(
						DownLoadImage.IMAGE_URL
								+ adImageUrl.get("adImg" + i).toString(),
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
								System.out.println("���URL--->" + imageUri);
								app.getPhotoData().put(imageUri, loadedImage);
							}

							@Override
							public void onLoadingCancelled(String imageUri,
									View view) {
							}
						});
			} else {

			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("MainTabFragment-->onCreateView()");
		rootView = inflater.inflate(R.layout.fragment_main, null);
		app = (App) getActivity().getApplication();
		// ��ʼ���ؼ�
		findViews();
		// ��ʼ���˵�
		initPopupMenu();
		// ��ʼ�����
		initImageList();
		initCirclePoint();
		if (app.getPhotoData() == null) {
			photoData = new HashMap<String, Bitmap>();
			app.setPhotoData(photoData);
		} else {
			photoData = app.getPhotoData();
		}
		// ������Ʒ�б�
		if (IsConnection.checkNet(getActivity())) {
			if (app.getArea() != null && app.getArea().getCurrAreaId() != null
					&& app.getCateData() == null) {
				new GetGoodsCategory(handler, app.getArea().getCurrAreaId(),
						"meal").start();
			} else if (app.getCateData() != null) {
				cateData = app.getCateData();
				cateDataList = (List<Map<String, Object>>) cateData.get("list");
				initGoodsCate();
			}
			if (app.getData() != null) {
				ll_main_loading.setVisibility(View.GONE);
				lv_main_content.setVisibility(View.VISIBLE);
				data = app.getData();
				mData = (List<Map<String, Object>>) data.get("list");
				GoodsAdapter goodsAdapter = new GoodsAdapter(getActivity());
				lv_main_content.setAdapter(goodsAdapter);
			}
			isFirst = true;
		} else {
			if (isFirst) {
				ll_main_loading.setText("����ʧ��...");
				myHandler.sendEmptyMessage(1001);
				isFirst = false;
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity())
						.setTitle("У��")
						.setMessage("�������Ӵ���,�Ƿ������������?")
						.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int arg1) {
										dialog.dismiss();
										startActivity(new Intent(
												Settings.ACTION_WIRELESS_SETTINGS));
									}
								})
						.setNegativeButton("ȡ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int arg1) {
										dialog.dismiss();
									}
								});
				builder.setCancelable(false);
				builder.create().show();
				CustomToast.showToast(getActivity(), "��ǰ������", 2000);
			}
		}

		return rootView;
	}

	@SuppressWarnings("unchecked")
	private void initGoodsList() {
		if (app.getData() == null) {
			new GetGoodsListThread(handler, currGoodsCategoryId, "user", 1, 1)
					.start();
		} else {
			ll_main_loading.setVisibility(View.GONE);
			lv_main_content.setVisibility(View.VISIBLE);
			data = app.getData();
			mData = (List<Map<String, Object>>) data.get("list");
			GoodsAdapter goodsAdapter = new GoodsAdapter(getActivity());
			lv_main_content.setAdapter(goodsAdapter);
		}
	}

	private void initArea(App app) {
		if (app.getArea() == null) {
			try {
				SharedPreferences spData = getActivity().getSharedPreferences(
						"userinfo", Context.MODE_PRIVATE);
				Area area = new Area();
				area.setComplainPhone(spData.getString("COMPLAINPHONE", ""));
				area.setCurrAreaId(spData.getString("CURRAREAID", ""));
				area.setCurrAreaName(spData.getString("CURRAREANAME", ""));
				area.setCurrCity(spData.getString("CURRCITY", ""));
				area.setDes(spData.getString("DES", ""));
				area.setOrderConsumeTime(spData.getString("ORDERCONSUMETIME",
						""));
				if (area.getCurrAreaName().equals("")) {
					app.setArea(null);
				} else {
					app.setArea(area);
					CustomToast.showToast(getActivity(), "��ʼ������ɹ�", 2000);
				}
			} catch (Exception e) {
				CustomToast.showToast(getActivity(), "��ʼ������ʧ��,������ѡ������", 2000);
			}
		}
	}

	@Override
	public void onResume() {
		app = (App) getActivity().getApplication();
		if (ll_main_loading == null)
			ll_main_loading = (TextView) rootView
					.findViewById(R.id.ll_main_loading);
		if (lv_main_content == null)
			lv_main_content = (ReFlashListView) rootView
					.findViewById(R.id.lv_main_content);
		try {
			if (MainActivity.vp_main_tab != null
					&& MainActivity.vp_main_tab.getCurrentItem() == 0) {
				initActionBar();
			}
			if (app.getArea() == null) {
				CustomToast.showToast(getActivity(), "��ѡ�����", 2000);
				startActivity(new Intent(getActivity(), ChooseCity.class));
			}
			if (app.getArea().getCurrAreaName() == null
					|| app.getArea().getCurrAreaName().equals("")) {
				ll_main_loading.setText("����ѡ������");
				ll_main_loading.setVisibility(View.VISIBLE);
				lv_main_content.setVisibility(View.GONE);
				CustomToast.showToast(getActivity(), "��ѡ������", 2000);
				startActivity(new Intent(getActivity(), ChooseArea.class));
			}

		} catch (Exception e) {
		}
		if (isLoadOrderList) {
			ll_main_loading.setVisibility(View.VISIBLE);
			lv_main_content.setVisibility(View.GONE);
			pm_choose_good_type = new PopupMenu(getActivity(),
					btn_main_choose_good_type);
			new GetGoodsCategory(handler, app.getArea().getCurrAreaId(), "meal")
					.start();
			System.out.println("onResume()");
			isLoadOrderList = false;
		}
		super.onResume();
	}

	// ��ʼ����ҳ��actionBar
	public void initActionBar() throws Exception {
		app = (App) getActivity().getApplication();
		initArea(app);

		chooseAreaPhoto = (ImageView) getActivity().getActionBar()
				.getCustomView().findViewById(R.id.choose_area_photo);
		chooseAreaPhoto.setBackgroundResource(R.drawable.shouye_06);

		actionBarBack = (ImageView) getActivity().getActionBar()
				.getCustomView().findViewById(R.id.actionbar_location_back);
		// actionBarBack.setBackground(null);
		actionBarBack.setVisibility(View.INVISIBLE);

		choose_area = (TextView) getActivity().getActionBar().getCustomView()
				.findViewById(R.id.actionBar_text);
		choose_area.setOnClickListener(this);

		user_state = (TextView) getActivity().getActionBar().getCustomView()
				.findViewById(R.id.user_state);
		user_state.setOnClickListener(this);

		mButtonMainSearch = (Button) getActivity().getActionBar()
				.getCustomView().findViewById(R.id.actionBar_search);
		mButtonMainSearch.setOnClickListener(this);

		mButtonSearch = (Button) getActivity().getActionBar().getCustomView()
				.findViewById(R.id.bt_search);
		mButtonSearch.setOnClickListener(this);

		mImageViewSearchBack = (ImageView) getActivity().getActionBar()
				.getCustomView().findViewById(R.id.search_back);
		mImageViewSearchBack.setOnClickListener(this);

		mEditTextSearch = (EditText) getActivity().getActionBar()
				.getCustomView().findViewById(R.id.et_search);

		rl_search = (RelativeLayout) getActivity().getActionBar()
				.getCustomView().findViewById(R.id.rl_search);

		try {
			if (app.getUser() == null && app.getArea() == null) {
				user_state.setText("δ��¼");
				choose_area.setText("��ѡ������");
			} else if (app.getUser() != null && app.getArea() != null
					&& app.getArea().getCurrAreaName() != null) {
				user_state.setText("�ѵ�¼");
				choose_area.setText(app.getArea().getCurrAreaName());
			} else if (app.getUser() == null && app.getArea() != null
					&& app.getArea().getCurrAreaName() != null) {
				user_state.setText("δ��¼");
				choose_area.setText(app.getArea().getCurrAreaName());
			} else if (app.getUser() != null
					&& (app.getArea() == null || app.getArea()
							.getCurrAreaName() == null)) {
				user_state.setText("�ѵ�¼");
				choose_area.setText("��ѡ������");
			}
		} catch (Exception e) {
			user_state.setText("δ��¼");
			choose_area.setText("��ѡ������");
		}
	}

	// ��ʼ�����List
	private void initImageList() {
		adList = new ArrayList<View>();
		iv1 = new ImageView(getActivity());
		iv1.setBackgroundResource(R.drawable.advertisement_1);
		adList.add(iv1);
		iv2 = new ImageView(getActivity());
		iv2.setBackgroundResource(R.drawable.advertisement_2);
		adList.add(iv2);
		iv3 = new ImageView(getActivity());
		iv3.setBackgroundResource(R.drawable.advertisement_3);
		adList.add(iv3);
		vp_main_ad.setAdapter(new AdPagerAdapter());
		vp_main_ad.setOnPageChangeListener(new imageListOnListener());
	}

	private void findViews() {

		vp_main_ad = (ViewPager) rootView.findViewById(R.id.vp_main_ad);
		viewGroup = (ViewGroup) rootView.findViewById(R.id.ll_ad_main_group);

		btn_main_choose_good_type = (Button) rootView
				.findViewById(R.id.btn_main_choose_good_type);
		btn_main_choose_good_type.setOnClickListener(this);

		btn_main_choose_order_type = (Button) rootView
				.findViewById(R.id.btn_main_choose_order_type);
		btn_main_choose_order_type.setOnClickListener(this);

		btn_main_choose_price_type = (Button) rootView
				.findViewById(R.id.btn_main_choose_price_type);
		btn_main_choose_price_type.setOnClickListener(this);

		lv_main_content = (ReFlashListView) rootView
				.findViewById(R.id.lv_main_content);
		lv_main_content.setInterface(this);

		ll_main_loading = (TextView) rootView
				.findViewById(R.id.ll_main_loading);

	}

	// ��ͼƬת���ɶ�������
	private byte[] bitmap2Bytes(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * �����������µ�ǰλ��
	 */
	private void atomicOption() {
		atomicInteger.incrementAndGet();
		if (atomicInteger.get() > iv_circles.length - 1) {
			atomicInteger.getAndAdd(-3);
		}
		try {
			Thread.sleep(3500);
		} catch (InterruptedException e) {
		}
	}

	// Բ�����
	public class imageListOnListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int position) {
			atomicInteger.getAndSet(position);
			for (int i = 0; i < iv_circles.length; i++) {
				iv_circles[position]
						.setBackgroundResource(R.drawable.point_focused);
				if (position != i) {
					iv_circles[i]
							.setBackgroundResource(R.drawable.point_unfocused);
				}
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

	}

	private void initCirclePoint() {
		iv_circles = new ImageView[adList.size()];
		for (int i = 0; i < iv_circles.length; i++) {
			iv_ad_circle_point = new ImageView(getActivity());
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					15, 15);
			params.setMargins(5, 0, 5, 0);
			iv_ad_circle_point.setLayoutParams(params);
			iv_circles[i] = iv_ad_circle_point;
			if (i == 0) {
				iv_circles[i].setBackgroundResource(R.drawable.point_focused);
			} else {
				iv_circles[i].setBackgroundResource(R.drawable.point_unfocused);
			}
			viewGroup.addView(iv_circles[i]);
		}
		new Thread(new RefreshAdCircleTask()).start();
	}

	public class RefreshAdCircleTask implements Runnable {

		@Override
		public void run() {
			while (true) {
				if (isContinue) {
					imageHandler.sendEmptyMessage(atomicInteger.get());
					atomicOption();
				}
			}
		}
	}

	// ҳ�滬��
	private Handler imageHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (isContinue) {
				vp_main_ad.setCurrentItem(msg.what);
			}
		};
	};

	// �˵��ĳ�ʼ��
	private void initPopupMenu() {
		// ������Ʒ���
		pm_choose_good_type = new PopupMenu(getActivity(),
				btn_main_choose_good_type);
		setPopupListener();

		// ���õ�ǰλ��
		pm_choose_locat_type = new PopupMenu(getActivity(),
				btn_main_tab_chooseLocat);
		pm_choose_locat_type.inflate(R.menu.btn_menu_choose_locat);
		pm_choose_locat_type
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {
						btn_main_tab_chooseLocat.setText(item.getTitle());
						return false;
					}
				});

		// ������������ʽ
		pm_choose_order_type = new PopupMenu(getActivity(),
				btn_main_choose_order_type);
		pm_choose_order_type.inflate(R.menu.btn_menu_choose_order);
		pm_choose_order_type
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {
						if (item.getItemId() == R.id.order_high_to_low) {
							currGoodsCateSortWay = 3;
							new GetGoodsListThread(handler,
									currGoodsCategoryId, "user",
									currGoodsCateSortWay, 1).start();
						} else if (item.getItemId() == R.id.order_low_to_high) {
							currGoodsCateSortWay = 2;
							new GetGoodsListThread(handler,
									currGoodsCategoryId, "user",
									currGoodsCateSortWay, 1).start();
						}
						return false;
					}
				});

		// ���ü۸�����ʽ
		pm_choose_price_type = new PopupMenu(getActivity(),
				btn_main_choose_price_type);
		pm_choose_price_type.inflate(R.menu.btn_menu_choose_price);
		pm_choose_price_type
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {
						switch (item.getItemId()) {
						case R.id.price_high_to_low:
							currGoodsCateSortWay = 1;
							new GetGoodsListThread(handler,
									currGoodsCategoryId, "user",
									currGoodsCateSortWay, 1).start();
							break;
						case R.id.price_low_to_high:
							currGoodsCateSortWay = 0;
							new GetGoodsListThread(handler,
									currGoodsCategoryId, "user",
									currGoodsCateSortWay, 1).start();
							break;
						}
						return false;
					}
				});
	}

	private void setPopupListener() {
		pm_choose_good_type
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {
						setGoodsCateId(item.getTitle());
						return false;
					}

					private void setGoodsCateId(CharSequence title) {
						Map<String, Object> oMap;
						for (int i = 0; i < cateDataList.size(); i++) {
							oMap = cateDataList.get(i);
							if (oMap.get("goodsCategoryName").toString()
									.equals(title)) {
								currGoodsCategoryId = (Integer) oMap
										.get("goodsCategoryId");
								new GetGoodsListThread(handler,
										currGoodsCategoryId, "user", 1, 1)
										.start();
							}
						}
					}
				});
	}

	// ���list��Adapter
	public class AdPagerAdapter extends PagerAdapter {
		// ���ҳ������
		@Override
		public int getCount() {
			return 3;
		}

		// �ж� view��object�Ķ�Ӧ��ϵ
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		/*
		 * �����Ӧλ���ϵ�view container view������ ��ʵ����viewpager���� position ��Ӧ��λ��
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(adList.get(position));
			return adList.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(adList.get(position));
		}
	}

	// Button�ļ���
	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.btn_main_choose_good_type:
			if (pm_choose_good_type == null) {
				CustomToast.showToast(getActivity(), "������������Ʒ\n������ѡ������", 2000);
			} else {
				Log.i(TAG, "pm_choose_good_type.show()");
				pm_choose_good_type.show();
			}
			break;
		case R.id.btn_main_choose_order_type:
			if (pm_choose_good_type == null) {
				CustomToast.showToast(getActivity(), "������������Ʒ\n������ѡ������", 2000);
			} else {
				Log.i(TAG, "pm_choose_order_type.show()");
				pm_choose_order_type.show();
			}
			break;
		case R.id.btn_main_choose_price_type:
			if (pm_choose_good_type == null) {
				CustomToast.showToast(getActivity(), "������������Ʒ\n������ѡ������", 2000);
			} else {
				Log.i(TAG, "pm_choose_price_type.show()");
				pm_choose_price_type.show();
			}
			break;
		case R.id.user_state:
			if (app.getUser() == null) {
				Intent intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.actionBar_text:
			isLoadOrderList = true;
			Intent intent = new Intent(getActivity(), ChooseCity.class);
			startActivity(intent);
			break;
		// ������Ʒ
		case R.id.actionBar_search:
			rl_search.setVisibility(View.VISIBLE);
			break;
		case R.id.search_back:
			rl_search.setVisibility(View.INVISIBLE);
			break;
		case R.id.bt_search:
			try {
				searchGoods();
			} catch (Exception e) {
				System.out.println("MainTabFragment-->805");
			}
			break;
		}

	}

	private void searchGoods() {
		String searchKey = mEditTextSearch.getText().toString();
		if (searchKey.equals("") || searchKey == null) {
			CustomToast.showToast(getActivity(), "������ؼ���", 2000);
		} else {
			if (app == null)
				app = (App) getActivity().getApplication();
			if (app.getArea() != null && app.getArea().getCurrAreaId() != null) {
				new SearchGoodsThread(handler, app.getArea().getCurrAreaId(),
						"search", mEditTextSearch.getText().toString()).start();
			} else {
				CustomToast.showToast(getActivity(), "����ѡ�����", 2000);
			}
		}

	}

	// private RelativeLayout mAnimLayout;
	private boolean isAniming = false;

	// ��Ʒ��Adapter
	public class GoodsAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public GoodsAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
		}

		// ����adapter���й����ٸ�����item
		@Override
		public int getCount() {
			return mData == null ? 0 : mData.size();
		}

		// ����λ�ã��õ���Ӧ��item����
		@Override
		public Object getItem(int position) {
			return mData.get(position);
		}

		// ����λ�ã��õ���Ӧ��item�����id
		@Override
		public long getItemId(int position) {
			return position;
		}

		// ͨ������getView()�������õ���Ӧ��view���󣬲�������ʾ��Activity��
		@Override
		public View getView(int position, View arg1, ViewGroup arg2) {
			final Map<String, Object> data = mData.get(position);
			RelativeLayout ll = (RelativeLayout) mInflater.inflate(
					R.layout.item_main_tab_good, null);

			TextView tv_main_item_goodsName = (TextView) ll
					.findViewById(R.id.tv_main_item_goodsName);
			tv_main_item_goodsName.setText(data.get("goodsName").toString());

			TextView tv_main_item_goodsSales = (TextView) ll
					.findViewById(R.id.tv_main_item_goodsSales);
			tv_main_item_goodsSales.setText("������: "
					+ data.get("goodsSales").toString());

			TextView tv_main_item_goodsId = (TextView) ll
					.findViewById(R.id.tv_main_item_goodsId);
			tv_main_item_goodsId.setText("" + data.get("goodsId").toString());

			TextView tv_main_item_goodsContent = (TextView) ll
					.findViewById(R.id.tv_main_item_goodsContent);
			tv_main_item_goodsContent.setText(""
					+ data.get("goodsContent").toString());
			TextView tv_main_itme_gmList = (TextView) ll
					.findViewById(R.id.tv_main_item_gmList);
			tv_main_itme_gmList.setText("" + data.get("gmList").toString());

			TextView tv_main_item_businessPhone = (TextView) ll
					.findViewById(R.id.tv_main_item_businessPhone);
			tv_main_item_businessPhone.setText(""
					+ data.get("businessPhone").toString());

			TextView tv_main_item_goodsPrice = (TextView) ll
					.findViewById(R.id.tv_main_item_goodsPrice);
			tv_main_item_goodsPrice.setText("�� "
					+ data.get("goodsPrice").toString());
			// ������ƷͼƬ
			final ImageView iv_main_tab_item = (ImageView) ll
					.findViewById(R.id.iv_main_tab_item);
			iv_main_tab_item.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// ��Ʒ����
					Intent intent = new Intent(getActivity(),
							OrderInfoActivity.class);
					BitmapDrawable bd = (BitmapDrawable) iv_main_tab_item
							.getBackground();
					Bitmap bm = bd.getBitmap();
					byte bitmapBtye[] = new byte[1024];
					bitmapBtye = bitmap2Bytes(bm);

					intent.putExtra("goodsId", data.get("goodsId").toString());
					intent.putExtra("goodsName", data.get("goodsName")
							.toString());
					intent.putExtra("goodsContent", data.get("goodsContent")
							.toString());
					intent.putExtra("goodsPrice", data.get("goodsPrice")
							.toString());
					intent.putExtra("goodsSales", data.get("goodsSales")
							.toString());
					intent.putExtra("businessPhone", data.get("businessPhone")
							.toString());
					intent.putExtra("gmList", data.get("gmList").toString());
					intent.putExtra("itemImage", bitmapBtye);
					startActivity(intent);
				}
			});

			if (photoData.get(data.get("goodsImg").toString()) == null) {
				ImageLoader.getInstance().loadImage(
						DownLoadImage.IMAGE_URL
								+ data.get("goodsImg").toString(),
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
								iv_main_tab_item.setBackground(bd);
								app.getPhotoData().put(
										data.get("goodsImg").toString(),
										loadedImage);
							}

							@Override
							public void onLoadingCancelled(String imageUri,
									View view) {
							}
						});
			} else {
				@SuppressWarnings("deprecation")
				BitmapDrawable bd = new BitmapDrawable(photoData.get(data.get(
						"goodsImg").toString()));
				iv_main_tab_item.setBackground(bd);
			}

			final Button btn_main_item_submit = (Button) ll
					.findViewById(R.id.btn_main_item_submit);
			btn_main_item_submit.setOnClickListener(new OnClickListener() {

				int c = 1;

				@Override
				public void onClick(View v) {
					boolean ishad = false;
					try {
						if (isAniming) {
							return;
						}
						int startLocat[] = new int[2];
						btn_main_item_submit.getLocationInWindow(startLocat);
						setUpAnimLayout(startLocat[0], startLocat[1],
								btn_main_item_submit.getWidth(),
								btn_main_item_submit.getHeight());
					} catch (Exception e) {
					}
					@SuppressWarnings("static-access")
					ShoppingCartFragment sf = ((MainActivity) getActivity())
							.getShoppingCartFragment();
					Map<String, Object> map = new HashMap<String, Object>();
					if (isFirst == true) {
						for (int i = 0; i < MainActivity.app.goodsList.size(); i++) {
							if (data.get("goodsName").equals(
									MainActivity.app.goodsList.get(i).get(
											"goodsName"))) {
								ishad = true;
								c++;
								Map<String, Object> m = new HashMap<String, Object>();
								m.put("goodsId", data.get("goodsId"));
								m.put("goodsName", data.get("goodsName"));
								m.put("goodsPrice", data.get("goodsPrice"));
								m.put("goodsCount", c);
								MainActivity.app.goodsList.set(i, m);
							}
						}
					}
					if (!ishad) {
						map.put("goodsId", data.get("goodsId"));
						map.put("goodsName", data.get("goodsName"));
						map.put("goodsPrice", data.get("goodsPrice"));
						map.put("goodsCount", 1);
						c = Integer.parseInt(map.get("goodsCount").toString());
						MainActivity.app.goodsList.add(map);
					}
					try {
						sf.change(getActivity());
					} catch (Exception e) {
						System.out.println("MainTabFragment--->964");
					}
				}
			});
			return ll;
		}

		/**
		 * ���춯����
		 * 
		 * @param right
		 * @param top
		 * @param width
		 * @param height
		 */
		protected void setUpAnimLayout(int right, int top, int width, int height) {

			mainActivity.rb_main_tab_menu2
					.getLocationOnScreen(mainActivity.endLocat);
			// ����ͼƬ
			final ImageView gou = new ImageView(getActivity());
			gou.setImageBitmap(BitmapFactory.decodeResource(getResources(),
					R.drawable.main_tab2_pressed));
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					width, width);
			params.leftMargin = right;
			params.topMargin = top - getActivity().getActionBar().getHeight()
					- 20;
			gou.setLayoutParams(params);
			mainActivity.animLayout.addView(gou);
			// ��Ӷ���
			AnimationSet animationSet = new AnimationSet(true);
			animationSet.setDuration(500);
			animationSet.setFillAfter(true);
			// ƽ�ƶ���
			TranslateAnimation animation = new TranslateAnimation(
					0,
					-(right - mainActivity.endLocat[0] - mainActivity.rb_main_tab_menu2
							.getWidth() / 4), 0, mainActivity.endLocat[1] - top
							- mainActivity.rb_main_tab_menu2.getHeight() / 5);
			// ����Ч��
			ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0.5f, 1,
					0.5f, Animation.RELATIVE_TO_SELF, 1f,
					Animation.RELATIVE_TO_SELF, 1f);
			// ��������ӵ�animationSet��
			animationSet.addAnimation(scaleAnimation);
			animationSet.addAnimation(animation);
			gou.setAnimation(animationSet);
			animation.start();
			animation.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation arg0) {
					isAniming = true;
				}

				@Override
				public void onAnimationRepeat(Animation arg0) {

				}

				@Override
				public void onAnimationEnd(Animation arg0) {
					isAniming = false;
					mainActivity.animLayout.removeView(gou);
				}
			});
		}

	}

	public void setMainactivity(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

	@Override
	public void onReflash() {
		// ��������
		new GetGoodsListThread(handler, currGoodsCategoryId, "user", 1, 1)
				.start();
	}

}

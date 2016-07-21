package com.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import com.bean.GoodsBean;
import com.data.UserData;
import com.fastfood.R;
import com.fastfood.activity.MainActivity;
import com.fastfood.thread.GetAdPage;
import com.fastfood.thread.GetGoodsListThread;
import com.fastfood.thread.GetUserLocationThread;
import com.fastfood.utils.CommonAdapter;
import com.fastfood.utils.DownLoadImage;
import com.fastfood.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class HomeFragment extends Fragment {

	private View rootView;
	private Spinner spinner_classify;
	private Spinner spinner_sales;
	private Spinner spinner_price;
	private String[] arr1 = { "掌上餐厅", "零食特产", "技能交换" };
	private String[] arr2 = { "销量从高到低", "销量从低到高" };
	private String[] arr3 = { "价格从高到低", "价格从低到高" };

	// 广告
	private ViewPager vp_main_ad = null;
	private List<View> adList;
	private ImageView iv1, iv2, iv3, iv4;
	private ImageView[] iv_circles;
	private ImageView iv_ad_circle_point;
	private ViewGroup viewGroup;
	// 并发
	private AtomicInteger atomicInteger = new AtomicInteger(0);
	private boolean isContinue = true;
	
	private ListView home_list;
	private ImageView cate_picture;
	private TextView title;
	private TextView sales_value;
	private TextView content;
	private TextView price;
	private Button btn_purchase;
	public static ArrayList<String> spinnerData = new ArrayList<String>();
	public static boolean hasChoose;
	
	Map<String, Object> data_goodsList;
	Map<String, Object> data_ad;
	Map<String, Object> data_location;
	Map<String, Object> data_cart;
	
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GetGoodsListThread.NETWORK_ERROR:
				Toast.makeText(getActivity(), "网络连接错误", Toast.LENGTH_SHORT).show();
				break;
			case GetGoodsListThread.SUCCESS:
				data_goodsList = (Map<String, Object>) msg.obj;
				if (data_goodsList.containsKey("response")) {
					Toast.makeText(getActivity(), "此类商品不存在", Toast.LENGTH_LONG).show();
				} else {
					initList();
				}
				break;
			case GetAdPage.NETWORK_ERROR:
				Toast.makeText(getActivity(), "网络连接错误", Toast.LENGTH_SHORT).show();
				break;
			case GetAdPage.SUCCESS:
				data_ad = (Map<String, Object>) msg.obj;
				if (data_ad.containsKey("response")) {
					Toast.makeText(getActivity(), "没有广告图片", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getActivity(), "获取广告图片成功", Toast.LENGTH_SHORT).show();
					//initImageList();
				}
				break;
			case GetUserLocationThread.NETWORK_ERROR:
				Toast.makeText(getActivity(), "网络连接错误", Toast.LENGTH_SHORT).show();
				break;
			case GetUserLocationThread.SUCCESS:
				data_location = (Map<String, Object>) msg.obj;
				if (data_location.containsKey("response")) {
					Toast.makeText(getActivity(), "对不起，此城市位开放", Toast.LENGTH_LONG).show();
				} else {
					//Toast.makeText(getActivity(), "获取城市信息成功", Toast.LENGTH_SHORT).show();
					setUserLocation();
				}
				break;
		
			default:
				break;
			}

		};
	};
	

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_home, container, false);
		findID();
		spinnerSetAdapter();
		//广告
		adList = new ArrayList<View>();
		initImageList();
		initCirclePoint();
		if (spinnerData!= null) {
			MainActivity.spinnerCity.setAdapter(new CommonAdapter<String>(getActivity(), spinnerData, R.layout.item_city) {
				@Override
				public void convert(ViewHolder holder, String t) {
					holder.setText(R.id.textView_city, t);
				}
			});
		}
		//new GetAdPage(handler, 1).start();
		new GetUserLocationThread("西安", 1, handler).start();
		String userId = UserData.getUserInfo(getActivity()).get("userId").toString();
		new GetGoodsListThread(handler, 1, userId, "user", 1, "", 1, 1).start();
		
		//创建默认的ImageLoader配置参数  
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(getActivity());  
          
        //Initialize ImageLoader with configuration.  
        ImageLoader.getInstance().init(configuration);
        
		return rootView;
	}
	
	public void setUserLocation() {
		List<Map<String, Object>> list = (List<Map<String, Object>>) data_location.get("list");
		if (list != null) {
			for (int i=0; i<list.size(); i++) {
				String areaName = (String) list.get(i).get("areaName");
				spinnerData.add(areaName);
			}
		} else {
			System.out.println("现无法获取地区");
		}
		MainActivity.spinnerCity.setAdapter(new CommonAdapter<String>(getActivity(), spinnerData, R.layout.item_city) {
			@Override
			public void convert(ViewHolder holder, String t) {
				holder.setText(R.id.textView_city, t);
			}
		});
	}

	public void initList() {
		if (data_goodsList.get("foodList") == null) {
			System.out.println("gai区域暂无商品");
			Toast.makeText(getActivity(), "该区域暂无商品", Toast.LENGTH_LONG).show();
		} else {
			final List<GoodsBean> goodsList = (List<GoodsBean>)data_goodsList.get("foodList");
			home_list.setAdapter(new CommonAdapter<GoodsBean>(getActivity(), (List<GoodsBean>)(data_goodsList.get("foodList")), R.layout.homelist_item) {

				@Override
				public void convert(final ViewHolder holder, GoodsBean t) {
					ImageLoader.getInstance().loadImage(
							DownLoadImage.IMAGE_URL + goodsList.get(holder.getPosition()).getGoodsImg(),
							new ImageLoadingListener() {
	
								@Override
								public void onLoadingStarted(String imageUri, View view) {
								}
	
								@Override
								public void onLoadingFailed(String imageUri, View view,
										FailReason failReason) {
									System.out.println(failReason.getCause().getMessage());
								}
	
								@Override
								public void onLoadingComplete(String imageUri,
										View view, Bitmap loadedImage) {
									((ImageView)holder.getView(R.id.cate_picture)).setImageBitmap(loadedImage);
								}
	
								@Override
								public void onLoadingCancelled(String imageUri,
										View view) {
	
								}
							});

					((TextView)holder.getView(R.id.title)).setText(t.getGoodsName());
					((TextView)holder.getView(R.id.sales_value)).setText(String.valueOf(t.getGoodsSales()));
					((TextView)holder.getView(R.id.content)).setText(t.getGoodsContent());
					((TextView)holder.getView(R.id.price)).setText("¥ "+String.valueOf(t.getGoodsPrice()));
					((Button)holder.getView(R.id.btn_purchase)).setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							int position = holder.getPosition();
							boolean flag = false;
							
							
							for (int i=0; i<MainActivity.cartData.size(); i++) {
								if (MainActivity.cartData.get(i).getGoodsId()==goodsList.get(position).getGoodsId()) {
									goodsList.get(position).addGoodsCount();
									MainActivity.fragment_cart= MainActivity.getCartFragment();
									MainActivity.fragment_cart.change();
									flag = true;
								} 
							}
							if (!flag) {
								goodsList.get(position).addGoodsCount();
								MainActivity.cartData.add(goodsList.get(position));
								MainActivity.fragment_cart= MainActivity.getCartFragment();
								MainActivity.fragment_cart.change();
							}
						}
					});
				}
			});
		}
	}
	
	public void findID() {
		spinner_classify = (Spinner) rootView.findViewById(R.id.spinner_classify);
		spinner_sales = (Spinner) rootView.findViewById(R.id.spinner_sales);
		spinner_price = (Spinner) rootView.findViewById(R.id.spinner_price);
		viewGroup = (ViewGroup) rootView.findViewById(R.id.ll_ad_main_group);
		vp_main_ad = (ViewPager) rootView.findViewById(R.id.vp_main_ad);
		home_list = (ListView) rootView.findViewById(R.id.home_list);
	}

	public void spinnerSetAdapter() {
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),
				R.layout.my_spinner, arr1);
		spinner_classify.setAdapter(adapter1);

		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),
				R.layout.my_spinner, arr2);
		spinner_sales.setAdapter(adapter2);

		ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getActivity(),
				R.layout.my_spinner, arr3);
		spinner_price.setAdapter(adapter3);
	}

	// 初始化广告List
	private void initImageList() {
		iv1 = new ImageView(getActivity());
		iv1.setBackgroundResource(R.drawable.advertisement_3);
		adList.add(iv1);
		iv2 = new ImageView(getActivity());
		iv2.setBackgroundResource(R.drawable.advertisement_2);
		adList.add(iv2);
		iv3 = new ImageView(getActivity());
		iv3.setBackgroundResource(R.drawable.advertisement_1);
		adList.add(iv3);
		iv4 = new ImageView(getActivity());
		iv4.setBackgroundResource(R.drawable.advertisement_4);
		adList.add(iv4);
		vp_main_ad.setAdapter(new AdPagerAdapter());
		vp_main_ad.setOnPageChangeListener(new imageListOnListener());
	}
	
	//广告List的Adapter
	public class AdPagerAdapter extends PagerAdapter{

		// 获得页面总数
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 4;
		}

		// 判断 view和object的对应关系
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1;
		}
		
		//获得相应位置上的view；container，view的容器，其实就是viewPager自身；position，相应的位置
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(adList.get(position));
			return adList.get(position);
		}
		
		public void destroyItem (ViewGroup container, int position, Object object) {
			container.removeView(adList.get(position));
		}
	}
	
	//圆点滚动
	public class imageListOnListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			atomicInteger.getAndSet(position);
			for (int i=0; i<iv_circles.length; i++) {
				iv_circles[position].setBackgroundResource(R.drawable.point_pressed);
				if (position != i) {
					iv_circles[i].setBackgroundResource(R.drawable.point_unpressed);
				}
			}
		}
		
	}

	
	private void initCirclePoint() {
		LayoutParams params;
		System.out.println(adList.size());
		iv_circles = new ImageView[adList.size()];//对iv_circles进行填充
		for (int i=0; i<iv_circles.length; i++) {
			iv_ad_circle_point = new ImageView(getActivity());
			iv_ad_circle_point.setLayoutParams(new LayoutParams(20, 20));
			iv_circles[i] = iv_ad_circle_point;
			if (i == 0) {
				iv_circles[i].setBackgroundResource(R.drawable.point_pressed);
			} else {
				iv_circles[i].setBackgroundResource(R.drawable.point_unpressed);
			}
			viewGroup.addView(iv_circles[i]);
			params = (LayoutParams) iv_circles[i].getLayoutParams();
			params.leftMargin = 20;
			params.bottomMargin = 10;
			iv_circles[i].setLayoutParams(params);
		}
		new Thread(new RefreshAdCircleTask()).start();
	}
	
	public class RefreshAdCircleTask implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				if (isContinue) {
					imageHandler.sendEmptyMessage(atomicInteger.get());
					atomicOption();
				}
			}
		}
	}
	
	//页面滑动
	private Handler imageHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (isContinue) {
				vp_main_ad.setCurrentItem(msg.what);
			}
		};
	};
	
	//(并发)更新当前位置
	private void atomicOption(){
		atomicInteger.incrementAndGet();
		if (atomicInteger.get() > iv_circles.length-1) {
			atomicInteger.addAndGet(-4);
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

}

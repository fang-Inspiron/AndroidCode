package com.example.fragment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.bean.Essay;
import com.bshare.BShare;
import com.bshare.core.BSShareItem;
import com.bshare.core.PlatformType;
import com.data.Queue;
import com.data.UserData;
import com.example.activity.ItemContentActivity;
import com.example.share.MainActivity;
import com.example.share4_15.R;
import com.example.utils.CommonAdapter;
import com.example.utils.SDHelper;
import com.example.utils.SQLiteUtil;
import com.example.utils.ScreenUtil;
import com.example.utils.ViewHolder;
import com.view.XListView;
import com.view.XListView.IXListViewListener;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class HomeFragment extends Fragment implements OnClickListener,
		IXListViewListener {

	private View rootView;
	private XListView list;
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
	private CommonAdapter<Essay> adapter;
	private Button toTopBtn;// 返回顶部的按钮
	private boolean scrollFlag = false;// 标记是否滑动
	private int lastVisibleItemPosition = 0;// 标记上次滑动位置
	public BShare bshare = null;
	public int iflag;
	public List<Essay> listEssay = new ArrayList<Essay>();
	public List<Map<String, Bitmap>> fileTheme = new ArrayList<Map<String, Bitmap>>();
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			listEssay = (List<Essay>) msg.obj;
			saveImg();
			initView();
		}
	};

	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		System.setProperty("debug", "true");
		com.bshare.core.Config.configObject().setAutoCloseShareList(true);
		com.bshare.core.Config.configObject().setShouldTrackBackClick(true);
		com.bshare.core.Config.configObject().setPublisherUUID("dfdfkjdfkjdkfj");
		List<PlatformType> platforms = new ArrayList<PlatformType>(
				com.bshare.core.Config.configObject().getShareList());
		com.bshare.core.Config.configObject().setShareList(platforms);
		bshare = BShare.instance(getActivity());

		bshare.addCredential(getActivity(), PlatformType.QQMB, "abc", "123",null);
		List<PlatformType> bindedAccounts = bshare.getBindedAccount(getActivity());

		rootView = inflater.inflate(R.layout.home_fragment, null);
		list = (XListView) rootView.findViewById(R.id.home_list);
		adList = new ArrayList<View>();
		vp_main_ad = (ViewPager) rootView.findViewById(R.id.vp_main_ad);
		viewGroup = (ViewGroup) rootView.findViewById(R.id.ll_ad_main_group);
		toTopBtn = (Button) rootView.findViewById(R.id.top_btn);
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(), ItemContentActivity.class);
				intent.putExtra("position", position);
				startActivity(intent);
			}
		});
		geneItems();

		// 初始化广告
		initImageList();
		initCirclePoint();

		return rootView;
	}

	public void initList() {
		list.setXListViewListener(this);
		adapter = new CommonAdapter<Essay>(getActivity(), listEssay,R.layout.home_list_item) {
			@Override
			public void convert(final ViewHolder holder, final Essay t) {
				holder.setText(R.id.item_name, t.getTitle());
				holder.setText(R.id.item_author, t.getAuthor());
				holder.setText(R.id.item_detail, t.getClassify());
				holder.setText(R.id.item_time, t.getTime());
				holder.setText(R.id.item_zanCount,String.valueOf(t.getZanNum()));
				holder.setText(R.id.item_guanzhuCount,String.valueOf(t.getCareNum()));
				holder.setText(R.id.item_shareCount,String.valueOf(t.getShareNum()));
				// 下载图片
				String url = t.getThemeImg().getFileUrl(getActivity());
				RequestQueue mQueue = Queue.getInstance();
				ImageRequest imageRequest = new ImageRequest(url,new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap response) {
						holder.setImageBitmap(R.id.item_image, response);
					}
				}, 200, 200, Config.ARGB_8888, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						System.out.println(error.getMessage());
					}
				});
				mQueue.add(imageRequest);

				if (t.getListImg() != null) {
					String[] urlArr = new String[t.getListImg().size()];
					RequestQueue mQueue2 = Queue.getInstance();

					for (int i = 0; i < urlArr.length; i++) {
						urlArr[i]=t.getListImg().get(i).getFileUrl(getActivity());
						ImageRequest imageRequest2 = new ImageRequest(urlArr[i],new Response.Listener<Bitmap>() {
							@Override
							public void onResponse(Bitmap response) {
							//	holder.setImageBitmap(R.id.item_image, response);
							}
						}, 200, 200, Config.ARGB_8888, new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
								System.out.println(error.getMessage());
							}
						});
						mQueue2.add(imageRequest2);
					}
				}

				// 给“赞”设置监听器
				holder.getView(R.id.item_zan).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						boolean flag_zan = true;
						if (flag_zan) {
							t.increment("zanNum");
							t.update(getActivity(),
							new UpdateListener() {
								@Override
								public void onSuccess() {
									((TextView) holder.getView(R.id.item_zanCount)).setText(String.valueOf(t	.getZanNum() + 1));
									geneItems();
									adapter.notifyDataSetChanged();
									onLoad();
									Toast.makeText(getActivity(),	"修改成功",Toast.LENGTH_SHORT).show();
								}
								@Override
								public void onFailure(int arg0,String arg1) {
									Toast.makeText(getActivity(),	"修改失败",Toast.LENGTH_SHORT).show();
								}
							});
							flag_zan = false;
						}
					}
				});
				// 给“关注”设置监听器
				holder.getView(R.id.item_guanzhu).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						boolean flag_care = true;
						if (flag_care) {
							t.increment("careNum");
							t.update(getActivity(),
							new UpdateListener() {
								@Override
								public void onSuccess() {
									((TextView) holder.getView(R.id.item_guanzhuCount)).setText(String.valueOf(t.getCareNum() + 1));
									geneItems();
									adapter.notifyDataSetChanged();
									onLoad();
									Toast.makeText(getActivity(),"修改成功",Toast.LENGTH_SHORT)	.show();
								}
								@Override
								public void onFailure(int arg0,String arg1) {
									Toast.makeText(getActivity(),	"修改失败",Toast.LENGTH_SHORT).show();
								}
							});
							flag_care = false;
						}
					}
				});
				// 给“分享”设置监听器
				holder.getView(R.id.item_share).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						boolean flag_share = true;
						BSShareItem shareItem = new BSShareItem(PlatformType.SOHUMINIBLOG, "标题", "1","2");
						bshare.showMoreList(getActivity(), shareItem);
						if (flag_share) {
							t.increment("shareNum");
							t.update(getActivity(),
								new UpdateListener() {
								@Override
								public void onSuccess() {
								//	((TextView) holder.getView(R.id.item_shareCount)).setText(String.valueOf(t.getShareNum() + 1));
								//	geneItems();
								//	adapter.notifyDataSetChanged();
								//	onLoad();
								//	Toast.makeText(getActivity(),	"修改成功",Toast.LENGTH_SHORT).show();
								}
								@Override
								public void onFailure(int arg0,String arg1) {
									Toast.makeText(getActivity(),	"修改失败",Toast.LENGTH_SHORT).show();
								}
							});
							flag_share = false;
						}
					}
				});
			}
		};
		list.setAdapter(adapter);
		list.setPullLoadEnable(true);
		list.setPullRefreshEnable(true);
	}

	/**
	 * 初始化视图
	 */
	private void initView() {
		// toTopBtn回到顶部的按钮
		toTopBtn.setOnClickListener(this);
		list.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				// 当不滚动时
				case OnScrollListener.SCROLL_STATE_IDLE:// 是当屏幕停止滚动时
					scrollFlag = false;
					// 判断滚动到底部
					if (list.getLastVisiblePosition() == (list.getCount() - 1)) {
						toTopBtn.setVisibility(View.VISIBLE);
					}
					// 判断滚动到顶部
					if (list.getFirstVisiblePosition() == 0) {
						toTopBtn.setVisibility(View.GONE);
						MainActivity.rg_main_menu.setVisibility(View.VISIBLE);
					}
					break;
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动时
					MainActivity.rg_main_menu.setVisibility(View.GONE);
					scrollFlag = true;
					break;
				case OnScrollListener.SCROLL_STATE_FLING:// 是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时
					scrollFlag = false;
					break;
				}
			}

			/**
			 * firstVisibleItem：当前能看见的第一个列表项ID（从0开始）
			 * visibleItemCount：当前能看见的列表项个数（小半个也算） totalItemCount：列表项共数
			 */
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// 当开始滑动且ListView底部的Y轴点超出屏幕最大范围时，显示或隐藏顶部按钮
				if (scrollFlag
						&& ScreenUtil.getScreenViewBottomHeight(list) >= ScreenUtil
								.getScreenHeight(getActivity())) {
					if (firstVisibleItem > lastVisibleItemPosition) {// 上滑
						toTopBtn.setVisibility(View.VISIBLE);
					} else if (firstVisibleItem < lastVisibleItemPosition) {// 下滑
						toTopBtn.setVisibility(View.GONE);
					} else {
						return;
					}
					lastVisibleItemPosition = firstVisibleItem;
				}
			}
		});
	}

	/**
	 * 滚动ListView到指定位置
	 * 
	 * @param pos
	 */
	private void setListViewPos(int pos) {
		if (android.os.Build.VERSION.SDK_INT >= 8) {
			list.smoothScrollToPosition(pos);
		} else {
			list.setSelection(pos);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_btn:// 点击按钮返回到ListView的第一项
			setListViewPos(0);
			break;
		}
	}


	/**
	 * （并发）更新当前位置
	 */
	private void atomicOption() {
		atomicInteger.incrementAndGet();
		if (atomicInteger.get() > iv_circles.length - 1) {
			atomicInteger.getAndAdd(-4);
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
	}

	// 圆点滚动
	public class imageListOnListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int position) {
			atomicInteger.getAndSet(position);
			for (int i = 0; i < iv_circles.length; i++) {
				iv_circles[position].setBackgroundResource(R.drawable.point_pressed);
				if (position != i) {
					iv_circles[i].setBackgroundResource(R.drawable.point_unpressed);
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

	// 初始化广告List
	private void initImageList() {
		iv1 = new ImageView(getActivity());
		iv1.setBackgroundResource(R.drawable.main_img1);
		adList.add(iv1);
		iv2 = new ImageView(getActivity());
		iv2.setBackgroundResource(R.drawable.main_img2);
		adList.add(iv2);
		iv3 = new ImageView(getActivity());
		iv3.setBackgroundResource(R.drawable.main_img3);
		adList.add(iv3);
		iv4 = new ImageView(getActivity());
		iv4.setBackgroundResource(R.drawable.main_img4);
		adList.add(iv4);
		vp_main_ad.setAdapter(new AdPagerAdapter());
		vp_main_ad.setOnPageChangeListener(new imageListOnListener());
	}

	private void initCirclePoint() {
		LayoutParams params;
		iv_circles = new ImageView[adList.size()];
		for (int i = 0; i < iv_circles.length; i++) {
			iv_ad_circle_point = new ImageView(getActivity());
			iv_ad_circle_point.setLayoutParams(new LayoutParams(30, 30));

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
			while (true) {
				if (isContinue) {
					imageHandler.sendEmptyMessage(atomicInteger.get());
					atomicOption();
				}
			}
		}
	}

	// 广告list的Adapter
	public class AdPagerAdapter extends PagerAdapter {
		// 获得页面总数
		@Override
		public int getCount() {
			return 4;
		}

		// 判断 view和object的对应关系
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		/*
		 * 获得相应位置上的view container view的容器 其实就是viewpager自身 position 相应的位置
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

	// 页面滑动
	private Handler imageHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (isContinue) {
				vp_main_ad.setCurrentItem(msg.what);
			}
		};
	};

	@Override
	public void onRefresh() {
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				geneItems();
				list.setAdapter(new CommonAdapter<Essay>(getActivity(),listEssay, R.layout.home_list_item) {
					@Override
					public void convert(final ViewHolder holder, Essay t) {
						holder.setText(R.id.item_name, t.getTitle());
						holder.setText(R.id.item_author, t.getAuthor());
						holder.setText(R.id.item_detail, t.getClassify());
						holder.setText(R.id.item_time, t.getTime());
						holder.setText(R.id.item_zanCount,String.valueOf(t.getZanNum()));
						holder.setText(R.id.item_guanzhuCount,String.valueOf(t.getCareNum()));
						holder.setText(R.id.item_shareCount,String.valueOf(t.getShareNum()));
						String url = t.getThemeImg().getFileUrl(getActivity());
						RequestQueue mQueue = Volley.newRequestQueue(getActivity());
						ImageRequest imageRequest = new ImageRequest(url,
								new Response.Listener<Bitmap>() {
									@Override
									public void onResponse(Bitmap response) {
										holder.setImageBitmap(R.id.item_image,
												response);
									}
								}, 200, 200, Config.ARGB_8888,
								new Response.ErrorListener() {
									@Override
									public void onErrorResponse(VolleyError error) {
										System.out.println("出错了");
									}
								});
						mQueue.add(imageRequest);
					}
				});
				onLoad();
			}
		}, 2000);

	}

	@Override
	public void onLoadMore() {
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				geneItems();
				adapter.notifyDataSetChanged();
				onLoad();
			}
		}, 500);

	}

	private void geneItems() {
		// 重新获取数据
		BmobQuery<Essay> query = new BmobQuery<Essay>();
		// findObjects是通过内部代码线程实现的，不能在主线程中得到结果！
		query.findObjects(getActivity(), new FindListener<Essay>() {
			@Override
			public void onError(int arg0, String arg1) {
				System.out.println("查询失败"+arg1);
			}
			@Override
			public void onSuccess(List<Essay> object) {
				System.out.println("查询成功：共"+ object.size() + "条数据");
				
				SQLiteUtil squ = new SQLiteUtil(getActivity(), "essayData");
				squ.update(object);
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("essayId", String.valueOf(object.size()));
				UserData.saveEssayIdInfo(getActivity(), map);
				
				Message m = new Message();
				m.what = 1;
				m.obj = object;
				handler.sendMessage(m);
			}
		});

	}
	
	private void onLoad() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(new Date());
		list.stopRefresh();
		list.stopLoadMore();
		list.setRefreshTime(time);
	}

	Handler imgHandler = new Handler() {
		final List<Map<String, Bitmap>> listBmp = new ArrayList<Map<String, Bitmap>>();
		final List<String> listPath = new ArrayList<String>();
		int i = 0;

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1001:
				listPath.add(null);
				listPath.set(i, (String) msg.obj);
				i++;
				break;
			case 1002:
				Map<String, Bitmap> map = new HashMap<String, Bitmap>();
				map.put(listPath.get(i - 1), (Bitmap) msg.obj);
				listBmp.add(map);
				break;
			case 1003:
				listPath.add(null);
				listPath.set(i, (String) msg.obj + i);
				i++;
				break;
			case 1004:
				Map<String, Bitmap> map2 = new HashMap<String, Bitmap>();
				map2.put(listPath.get(i - 1), (Bitmap) msg.obj);
				listBmp.add(map2);
				break;
			case 1005:
				SDHelper helper = new SDHelper(getActivity());
				helper.createSDCardDir();
				helper.setList(listBmp, listPath);
				try {
					helper.saveMyBitmap();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		};
	};
	int i;
	public void setListData() {
		list.setXListViewListener(this);
		adapter = new CommonAdapter<Essay>(getActivity(), listEssay, R.layout.home_list_item) {
			@SuppressLint("NewApi")
			@Override
			public void convert(final ViewHolder holder, final Essay t) {
				holder.setText(R.id.item_name, t.getTitle());
				holder.setText(R.id.item_author, t.getAuthor());
				holder.setText(R.id.item_detail, t.getClassify());
				holder.setText(R.id.item_time, t.getTime());
				holder.setText(R.id.item_zanCount,String.valueOf(t.getZanNum()));
				holder.setText(R.id.item_guanzhuCount,String.valueOf(t.getCareNum()));
				holder.setText(R.id.item_shareCount,String.valueOf(t.getShareNum()));
				for (int i = 0; i < fileTheme.size(); i++) {
					if (fileTheme.get(i).get("theme" + t.getEssayId() + ".jpg") != null) {
						Bitmap bp = fileTheme.get(i).get("theme" + t.getEssayId() + ".jpg");
						holder.setImageBitmap(R.id.item_image, bp);
					}
				}

				// 给“赞”设置监听器
				holder.getView(R.id.item_zan).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						boolean flag_zan = true;
						if (flag_zan) {
							t.increment("zanNum");
							t.update(getActivity(),	new UpdateListener() {
								@Override
								public void onSuccess() {
									((TextView) holder.getView(R.id.item_zanCount)).setText(String.valueOf(t	.getZanNum() + 1));
									geneItems();
									adapter.notifyDataSetChanged();
									onLoad();
									Toast.makeText(getActivity(), "修改成功",Toast.LENGTH_SHORT).show();
								}

								@Override
								public void onFailure(int arg0,String arg1) {
									Toast.makeText(getActivity(),"修改失败",Toast.LENGTH_SHORT)	.show();
								}
							});
							flag_zan = false;
						}
					}
				});
				// 给“关注”设置监听器
				holder.getView(R.id.item_guanzhu).setOnClickListener(	new OnClickListener() {
					@Override
					public void onClick(View v) {
						boolean flag_care = true;
						if (flag_care) {
							t.increment("careNum");
							t.update(getActivity(),new UpdateListener() {
								@Override
								public void onSuccess() {
									((TextView) holder.getView(R.id.item_guanzhuCount)).setText(String.valueOf(t.getCareNum() + 1));
									geneItems();
									adapter.notifyDataSetChanged();
									onLoad();
									Toast.makeText(getActivity(),	"修改成功",Toast.LENGTH_SHORT).show();
								}

								@Override
								public void onFailure(int arg0,String arg1) {
									Toast.makeText(getActivity(),"修改失败",Toast.LENGTH_SHORT)	.show();
								}
							});
							flag_care = false;
						}
					}
				});
				// 给“分享”设置监听器
				holder.getView(R.id.item_share).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						boolean flag_share = true;
						BSShareItem shareItem = new BSShareItem(PlatformType.SOHUMINIBLOG, "标题", "1","2");
						bshare.showMoreList(getActivity(), shareItem);
						
						if (flag_share) {
							t.increment("shareNum");
							t.update(getActivity(),
							new UpdateListener() {
								@Override
								public void onSuccess() {
								//	((TextView) holder.getView(R.id.item_shareCount)).setText(String.valueOf(t.getShareNum() + 1));
								//	geneItems();
								//	adapter.notifyDataSetChanged();
								//	onLoad();
								//	Toast.makeText(getActivity(),	"修改成功",Toast.LENGTH_SHORT).show();
								}

								@Override
								public void onFailure(int arg0,String arg1) {
									Toast.makeText(getActivity(),	"修改失败",Toast.LENGTH_SHORT).show();
								}
							});
							flag_share = false;
						}
					}
				});
			}
		};
		list.setAdapter(adapter);
		list.setPullLoadEnable(true);
		list.setPullRefreshEnable(true);
	}

	public void saveImg() {
		SDHelper helper = new SDHelper(getActivity());
		try {
			fileTheme = helper.readThemeBitmap();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (fileTheme != null && fileTheme.size() > 0) {
			setListData();
		} else {
			initList();
			getAllImg();
		}
	}

	public void getAllImg() {
		// 下载图片
		for (i = 0; i < listEssay.size(); i++) {
			final Essay t = listEssay.get(i);
			String url = t.getThemeImg().getFileUrl(getActivity());
			RequestQueue mQueue = Queue.getInstance();
			ImageRequest imageRequest = new ImageRequest(url,
					new Response.Listener<Bitmap>() {
						@Override
						public void onResponse(Bitmap response) {

							Message msg = new Message();
							msg.obj = "theme" + String.valueOf(t.getEssayId());
							msg.what = 1001;
							imgHandler.sendMessage(msg);

							Message msg2 = new Message();
							msg2.obj = response;
							msg2.what = 1002;
							imgHandler.sendMessage(msg2);
						}
					}, 0, 0, Config.RGB_565, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
						}
					});
			mQueue.add(imageRequest);

			if (t.getListImg() != null) {
				String[] urlArr = new String[t.getListImg().size()];
				RequestQueue mQueue2 = Queue.getInstance();
				for (iflag = 0; iflag < urlArr.length; iflag++) {

					urlArr[iflag] = t.getListImg().get(iflag)
							.getFileUrl(getActivity());

					ImageRequest imageRequest2 = new ImageRequest(
							urlArr[iflag], new Response.Listener<Bitmap>() {
								@Override
								public void onResponse(Bitmap response) {
									Message msg = new Message();
									msg.obj = "Img" + t.getEssayId();
									msg.what = 1003;
									imgHandler.sendMessage(msg);

									Message msg2 = new Message();
									msg2.obj = response;
									msg2.what = 1004;
									imgHandler.sendMessage(msg2);
									if (i >= listEssay.size() - 1) {
										Message msg3 = new Message();
										msg3.what = 1005;
										imgHandler.sendMessage(msg3);
									}

								}
							}, 0, 0, Config.RGB_565,
							new Response.ErrorListener() {
								@Override
								public void onErrorResponse(VolleyError error) {
								}
							});
					mQueue2.add(imageRequest2);
				}

			}
		}

	}
}

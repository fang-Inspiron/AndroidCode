package com.example.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.bmob.v3.listener.UploadFileListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.bean.Essay;
import com.data.UserData;
import com.example.share4_15.R;
import com.photo.activity.AlbumActivity;
import com.photo.utils.Bimp;
import com.photo.utils.PublicWay;
import com.photo.utils.Res;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupMenu.OnMenuItemClickListener;

public class ShuangChuanActivity extends Activity implements OnClickListener {
	
	private ImageView iv_select_photo;
	private ImageButton back;
	private EditText et_title;
	private EditText et_content;
	public Button bt_xuanze;
	private Button bt_loca;
	private Button bt_sub;
	private Button bt_pingmian;
	private Button bt_wangye;
	private Button bt_ui;
	private Button bt_chahua;
	private Button bt_xuni;
	private Button bt_yingshi;
	private Button bt_sheying;
	private Button bt_qita;
	private PopupMenu pm_bt_xuanze = null;
	private ProgressDialog progressDialog;
	public String popMenu_selected;
	public static String path;
	public List<Button> listButton = new ArrayList<Button>();
	private LocationClient mLocationClient;
	
	private GridView noScrollgridview;
	private GridAdapter adapter;
	private View parentView;
	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	public static Bitmap bimap ;
	
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 100) {
				progressDialog.dismiss();
				Toast.makeText(ShuangChuanActivity.this, "上传成功",
						Toast.LENGTH_SHORT).show();
				Bimp.tempSelectBitmap.clear();
				finish();
			
			}
			if (msg.what == 200) {
				getAddr();
			}
			if (msg.what == 1) {
				adapter.notifyDataSetChanged();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.shangchuan_activity);
		
		handler.sendEmptyMessageDelayed(200, 500);
		
		Res.init(this);
		bimap = BitmapFactory.decodeResource(getResources(),R.drawable.icon_addpic_unfocused);
		PublicWay.activityList.add(this);
		parentView = getLayoutInflater().inflate(R.layout.shangchuan_activity, null);
		setContentView(parentView);
		
		findId();
		initPopupMenu();
		setListener();
		Init();
	}
	
	public void getAddr() {
		mLocationClient = new LocationClient(this.getApplicationContext());
		mLocationClient.registerLocationListener(new MyLocationListener());
		InitLocation();
		mLocationClient.start();
	}
	
	//停止定位
	@Override
	protected void onStop() {
		 mLocationClient.stop();
		super.onStop();
	}

	private void InitLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);//设置定位模式
		//option.setCoorType(tempcoor);//返回的定位结果是百度经纬度，默认值gcj02
		int span=1000;
		option.setScanSpan(span);//设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}
	
	/**
	 * 实现定位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			String addr = null;
			if (location.getLocType() == BDLocation.TypeGpsLocation){
				addr = location.getAddrStr();
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
				addr = location.getAddrStr();
			}
			mLocationClient.stop();
			String str = null;
			for (int i = 0; i<addr.length(); i++) {
				if (addr.charAt(i) == '市') {
					str = addr.substring(0, i+1);
					break;
				}
			}
			bt_loca.setText(str);
		}
	}
	
	public void setListener() {
		iv_select_photo.setOnClickListener(this);
		back.setOnClickListener(this);
		bt_sub.setOnClickListener(this);
		bt_xuanze.setOnClickListener(this);
	}
	
	public void findId() {
		iv_select_photo = (ImageView) findViewById(R.id.shang_photo);
		back = (ImageButton) findViewById(R.id.upload_back);
		bt_sub = (Button) findViewById(R.id.shang_bt_sub);
		bt_xuanze = (Button) findViewById(R.id.shang_bt_xunaze);
		bt_loca = (Button) findViewById(R.id.shang_bt_loca);
		et_title = (EditText) findViewById(R.id.et_title);
		et_content = (EditText) findViewById(R.id.et_content);
		bt_pingmian = (Button) findViewById(R.id.btn_pingmian);
		bt_wangye = (Button) findViewById(R.id.btn_wangye);
		bt_ui = (Button) findViewById(R.id.btn_ui);
		bt_chahua = (Button) findViewById(R.id.btn_chahua);
		bt_xuni = (Button) findViewById(R.id.btn_xuni);
		bt_yingshi = (Button) findViewById(R.id.btn_yingshi);
		bt_sheying = (Button) findViewById(R.id.btn_sheying);
		bt_qita = (Button) findViewById(R.id.btn_qita);
		listButton.add(bt_pingmian);
		listButton.add(bt_wangye);
		listButton.add(bt_ui);
		listButton.add(bt_chahua);
		listButton.add(bt_xuni);
		listButton.add(bt_yingshi);
		listButton.add(bt_sheying);
		listButton.add(bt_qita);
	}

	public void shangOnClick(View v) {
		if (Integer.parseInt(v.getTag().toString()) == 0) {
			v.setBackgroundResource(R.drawable.yinying);
			v.setTag("1");
			((Button)v).setTextColor(Color.WHITE);
		} else {
			v.setBackgroundResource(R.drawable.yinying);
			v.setTag("0");
			((Button)v).setTextColor(Color.BLACK);
		}
	}

	public void searchOnClick(View v) {
		if (Integer.parseInt(v.getTag().toString()) == 0) {
			v.setBackgroundResource(R.drawable.yinying2);
			v.setTag("1");
			((Button)v).setTextColor(Color.WHITE);
		} else {
			v.setBackgroundResource(R.drawable.yinying);
			v.setTag("0");
			((Button)v).setTextColor(Color.BLACK);
		}
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.shang_photo:
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
			startActivityForResult(intent, 999999);
			break;
		case R.id.upload_back:
			finish();
			break;
		case R.id.shang_bt_xunaze:
			bt_xuanze.setBackgroundResource(R.drawable.shang_xuanze_press);
			pm_bt_xuanze.show();
			break;
		case R.id.shang_bt_sub:
			AlertDialog.Builder builder = new AlertDialog.Builder(
					ShuangChuanActivity.this).setTitle("提示").setMessage("确认发布?")
					.setPositiveButton("确认",	new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									arg0.dismiss();
									progressDialog = new ProgressDialog(ShuangChuanActivity.this);
									progressDialog.setTitle("提示");
									progressDialog.setMessage("内容发布中...");
									progressDialog.show();
									new Thread() {
										public void run() {
											try {
												Thread.sleep(500);
												uploadData();
											} catch (InterruptedException e) {
												e.printStackTrace();
											}
										};
									}.start();
								}
							})
					.setNegativeButton("取消",new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,int arg1) {
									arg0.dismiss();
								}
							});
			builder.create().show();

			break;
		}
	}
	
	
	public void uploadData() {
		//获取数据
		final int essayId = Integer.valueOf(UserData.getEssayIdInfo(getApplicationContext()).get("essayId"))+1;
		
		String title = et_title.getText().toString();
		String content = et_content.getText().toString();
		String author = UserData.getUserInfo(getApplicationContext()).get("username").toString();
		StringBuilder classify = new StringBuilder(popMenu_selected);
		for (Button b:listButton) {
			if (Integer.parseInt(b.getTag().toString())==1) {
				classify.append("-"+b.getText().toString());
			}
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(new Date());
		final BmobFile themeImg =  new BmobFile(new File(getPath()));
		
		List<BmobFile> bmobFiles = new ArrayList<BmobFile>();
		for (int i=0; i<imgPathList.size(); i++) {
			BmobFile b = new BmobFile(new File(imgPathList.get(i)));
			bmobFiles.add(b);
		}
		
		//添加数据
		final Essay essay = new Essay();
		essay.setEssayId(essayId);
		essay.setTitle(title);
		essay.setContent(content);
		essay.setAuthor(author);
		essay.setClassify(classify.toString());
		essay.setTime(time);
		essay.setZanNum(0);
		essay.setCareNum(0);
		essay.setShareNum(0);
		essay.setThemeImg(themeImg);
		
		//先上传选中的几张图片imgPathList
		int i;
		final String[] files = new String[imgPathList.size()+1];
		for (i=0; i<imgPathList.size(); i++) {
			files[i] = imgPathList.get(i);
		}
		files[i] = getPath();
		
		Bmob.uploadBatch(getApplicationContext(), files, new UploadBatchListener() {
			@Override
			public void onError(int arg0, String arg1) {
			}
			@Override
			public void onProgress(int arg0, int arg1, int arg2, int arg3) {
			}

			@Override
			public void onSuccess(final List<BmobFile> arg0, List<String> arg1) {
				if(arg0.size()==files.length) {
				themeImg.uploadblock(getApplicationContext(), new UploadFileListener() {
					@Override
					public void onSuccess() {
						essay.setListImg(arg0);//将上传成功的list保存到Essay表
						essay.save(getApplicationContext(), new SaveListener() {
							@Override
							public void onSuccess() {
								//将数据更新保存在本地
								Map<String, String> map = new HashMap<String, String>();
								map.put("essayId", String.valueOf(essayId));
								UserData.saveEssayIdInfo(getApplicationContext(), map);
								Toast.makeText(getApplicationContext() , "上传成功", Toast.LENGTH_SHORT).show();
								handler.sendEmptyMessage(100);
							}
							@Override
							public void onFailure(int arg0, String arg1) {
								Toast.makeText(getApplicationContext(), arg1, Toast.LENGTH_SHORT).show();
							}
						});
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {
						Toast.makeText(getApplicationContext() , arg1, Toast.LENGTH_SHORT).show();
					}
				});
				}
			}
		});
		
	}
	
	/**
	 * imgPathList表示选中的几张图片的路径
	 * onRestart用来接收Bimp.tempSelectBitmap(图片路径);更新数据
	 */
	ArrayList<String> imgPathList;
	protected void onRestart() {
		imgPathList = new ArrayList<String>();
		for (int i=0; i<Bimp.tempSelectBitmap.size(); i++) {
			imgPathList.add(Bimp.tempSelectBitmap.get(i).getImagePath());
		}
		adapter.update();
		super.onRestart();
	}

	public ArrayList<String> getImgPathList() {
		return imgPathList;
	}

	public void setImgPathList(ArrayList<String> imgPathList) {
		this.imgPathList = imgPathList;
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 999999:
			if (resultCode == RESULT_OK) {
				ContentResolver resolver = getContentResolver();
				Uri uri = data.getData();
				Bitmap bm;
				try {
					bm = MediaStore.Images.Media.getBitmap(resolver, uri);

					//这里开始的第二部分，获取图片的路径：
		            String[] proj = {MediaStore.Images.Media.DATA};
		            //好像是android多媒体数据库的封装接口，具体的看Android文档
		            Cursor cursor = managedQuery(uri, proj, null, null, null); 
		            //按我个人理解 这个是获得用户选择的图片的索引值
		            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		            //将光标移至开头 ，这个很重要，不小心很容易引起越界
		            cursor.moveToFirst();
		            //最后根据索引值获取图片路径
		            String path = cursor.getString(column_index);
		            setPath(path);
		            
					Bitmap bitmap = Bitmap.createScaledBitmap(bm,200, 200,true);
					iv_select_photo.setImageBitmap(bitmap);
					
				} catch (Exception e) {
					Toast.makeText(ShuangChuanActivity.this, "选择图片失败",Toast.LENGTH_SHORT).show();
				}
			}
			break;
		}
	}

	public void setPath(String p) {
		path = p;
	}
	
	public static String getPath() {
		return path;
	}
	

	private void initPopupMenu() {
		// 设置商品类别
		pm_bt_xuanze = new PopupMenu(ShuangChuanActivity.this, bt_xuanze);
		pm_bt_xuanze.inflate(R.menu.shang_xuanze);
		popMenu_selected = "原创";
		pm_bt_xuanze.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				popMenu_selected = item.getTitle().toString();
				bt_xuanze.setText(item.getTitle());
				bt_xuanze.setBackgroundResource(R.drawable.shang_xuanzen_normal);
				return false;
			}

		});
	}
	
	
	
	//photo
	public void Init() {
		
		pop = new PopupWindow(ShuangChuanActivity.this);
		View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);
		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
		
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);
		
		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
		Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
		Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
		parent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				photo();
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ShuangChuanActivity.this, AlbumActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		
		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);	
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		adapter.update();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == Bimp.tempSelectBitmap.size()) {
					ll_popup.startAnimation(AnimationUtils.loadAnimation(ShuangChuanActivity.this,R.anim.activity_translate_in));
					pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
				} else {
					Intent intent = new Intent(ShuangChuanActivity.this, AlbumActivity.class);
					intent.putExtra("position", "1");
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});
	}

	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private int selectedPosition = -1;
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			if(Bimp.tempSelectBitmap.size() == 9){
				return 9;
			}
			return (Bimp.tempSelectBitmap.size() + 1);
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_published_grida,parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position ==Bimp.tempSelectBitmap.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
				if (position == 9) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}


		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.tempSelectBitmap.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							Bimp.max += 1;
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						}
					}
				}
			}).start();
		}
	}

	public String getString(String s) {
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++) {
			s.charAt(i);
		}
		return path;
	}

	private static final int TAKE_PICTURE = 0x000001;

	public void photo() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}
}

package com.example.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.bean.User;
import com.data.Queue;
import com.example.share4_15.R;
import com.example.utils.CommonAdapter;
import com.example.utils.SDHelper;
import com.example.utils.SQLiteUtil;
import com.example.utils.Utility;
import com.example.utils.ViewHolder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ItemContentActivity extends Activity {

	private ListView listWorks;
	private ImageView head;
	private TextView works_name;
	private TextView works_time;
	private TextView works_author;
	private ImageButton zan;
	private ImageButton guanzhu;
	private ImageButton share;
	private ImageButton back;
	private TextView zCount;
	private TextView gCount;
	private TextView sCount;
	private TextView works_content;
	public List<Map<String, Bitmap>> fileImg = new ArrayList<Map<String, Bitmap>>();
	public Map<String, Bitmap> headImg = new HashMap<String, Bitmap>();
	public List<User> listUser = new ArrayList<User>();
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			listUser = (List<User>) msg.obj;
			saveHeadImg();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.works_main);
		findId();
		geneItems();

		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		Intent intent = getIntent();
		final int essayId = intent.getIntExtra("position", 1);
		SQLiteUtil squ = new SQLiteUtil(this, "essayData");
		final Map<String, Object> map = squ.find(essayId);
		works_name.setText(map.get("title").toString());
		works_author.setText(map.get("author").toString());
		works_time.setText(map.get("time").toString());
		zCount.setText(map.get("zanNum").toString());
		gCount.setText(map.get("careNum").toString());
		sCount.setText(map.get("shareNum").toString());
		works_content.setText(map.get("content").toString());
		
		SDHelper helper = new SDHelper(getApplicationContext());
		try {
			fileImg = helper.readImgBitmap("Img" + essayId);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (fileImg != null && fileImg.size() > 0) {
			listWorks.setAdapter(new CommonAdapter<Map<String, Bitmap>>(
				getApplicationContext(), fileImg,R.layout.works_list_item) {
						@Override
						public void convert(ViewHolder holder, Map<String, Bitmap> t) {
							holder.setImageBitmap(R.id.works_img,t.get("Img" + essayId));
						}
					});
			/*
			 * 问题：ScrollView与ListView嵌套导致ListView显示不全面，只能显示一项Item的图片
			 * 滑动冲突：滑动listView没效果，只能滑动ScrollView
			 * 
			 * 解决方法：主动计算和设置ListView的高度 （
			 * 在设置完ListView的Adapter后，根据ListView的子项目重新计算ListView的高度，
			 * 然后把高度再作为LayoutParams设置给ListView，这样它的高度就正确了）
			 */
			Utility.setListViewHeightBasedOnChildren(listWorks);
		}
	}

	Handler imgHandler = new Handler() {
		final Map<String, Bitmap> bitmp = new HashMap<String, Bitmap>();
		final Map<String, String> path = new HashMap<String, String>();

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1001:
				path.put("0", (String) msg.obj);
				break;
			case 1002:
				bitmp.put(path.get("0"), (Bitmap) msg.obj);
				SDHelper helper = new SDHelper(getApplicationContext());
				helper.createSDCardDir();
				helper.setMap(bitmp, path);
				try {
					helper.saveHeadBitmap();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		};
	};

	public void saveHeadImg() {
		SDHelper helper = new SDHelper(getApplicationContext());
		try {
			headImg = helper.readHeadBitmap();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (!headImg.isEmpty() && headImg.size() > 0) {
			setHeadData();
		} else {
			initHeadImg();
			getHeadImg();
		}
	}

	public void getHeadImg() {
		User u = listUser.get(0);
		String url = u.getHeadImg().getFileUrl(getApplicationContext());
		RequestQueue mQueue = Queue.getInstance();
		ImageRequest imageRequest = new ImageRequest(url,
				new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap response) {
						Message msg = new Message();
						msg.obj = "head";
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
	}

	public void setHeadData() {
		if (headImg.get("head" + ".jpg") != null) {
			Bitmap bp = headImg.get("head" + ".jpg");
			head.setImageBitmap(bp);
		}
	}

	public void initHeadImg() {
		// 下载图片
		String url = listUser.get(0).getHeadImg()
				.getFileUrl(getApplicationContext());
		RequestQueue mQueue = Queue.getInstance();
		ImageRequest imageRequest = new ImageRequest(url,
				new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap response) {
						head.setImageBitmap(response);
					}
				}, 80, 80, Config.ARGB_8888, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						System.out.println(error.getMessage());
					}
				});
		mQueue.add(imageRequest);
	}

	// 获取数据
	public void geneItems() {
		BmobQuery<User> query = new BmobQuery<User>();
		// findObjects是通过内部代码线程实现的，不能在主线程中得到结果！
		query.findObjects(getApplicationContext(), new FindListener<User>() {
			@Override
			public void onError(int arg0, String arg1) {
				System.out.println("查询失败" + arg1);
			}

			@Override
			public void onSuccess(List<User> object) {
				Message m = new Message();
				m.what = 1;
				m.obj = object;
				handler.sendMessage(m);
			}
		});
	}

	public void findId() {
		listWorks = (ListView) findViewById(R.id.works_list);
		head = (ImageView) findViewById(R.id.head);
		works_name = (TextView) findViewById(R.id.works_name);
		works_author = (TextView) findViewById(R.id.works_author);
		works_time = (TextView) findViewById(R.id.works_time);
		zan = (ImageButton) findViewById(R.id.item_zan);
		guanzhu = (ImageButton) findViewById(R.id.item_guanzhu);
		share = (ImageButton) findViewById(R.id.item_share);
		back = (ImageButton) findViewById(R.id.works_back);
		zCount = (TextView) findViewById(R.id.item_zanCount);
		gCount = (TextView) findViewById(R.id.item_guanzhuCount);
		sCount = (TextView) findViewById(R.id.item_shareCount);
		works_content = (TextView) findViewById(R.id.works_content);
	}

}

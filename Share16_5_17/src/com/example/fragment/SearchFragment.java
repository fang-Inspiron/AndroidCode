package com.example.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.bean.Content;
import com.bean.Essay;
import com.data.UserData;
import com.example.share4_15.R;
import com.example.utils.CommonAdapter;
import com.example.utils.MyDateFormat;
import com.example.utils.SDHelper;
import com.example.utils.SQLiteUtil;
import com.example.utils.ViewHolder;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class SearchFragment extends Fragment {
	private View rootView;
	public EditText et_search;
	private ImageView search_bt;
	public RelativeLayout search;
	public LinearLayout search_result;
	private ProgressDialog progressDialog;
	public List<Content> listContent = new ArrayList<Content>();
	public ListView listResult;

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			progressDialog.dismiss();
			search.setVisibility(View.GONE);
			search_result.setVisibility(View.VISIBLE);
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.search_fragment, container, false);
		et_search = (EditText) rootView.findViewById(R.id.search_et_search);
		search_bt = (ImageView) rootView.findViewById(R.id.search_bt);
		search = (RelativeLayout) rootView.findViewById(R.id.search);
		search_result = (LinearLayout) rootView
				.findViewById(R.id.search_result);
		listResult = (ListView) rootView.findViewById(R.id.list_result);
		search_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 得到要搜索的关键词
				String searchStr = et_search.getText().toString();
				// 拿到本机缓存的列表项
				SQLiteUtil squ = new SQLiteUtil(getActivity(), "essayData");
				List<Essay> list = squ.findItem();
				SDHelper helper = new SDHelper(getActivity());
				// 读取主题图片
				List<Map<String, Bitmap>> themeList = new ArrayList<Map<String, Bitmap>>();
				try {
					themeList = helper.readThemeBitmap();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				// 按“分类”搜索
				if (searchStr.equals("平面设计") || searchStr.equals("网页设计")
						|| searchStr.equals("UI/icon")	|| searchStr.equals("插画/手绘")
						|| searchStr.equals("虚拟与设计") || searchStr.equals("影视")
						|| searchStr.equals("摄影") || searchStr.equals("其他")) {
					searchClassify(list, searchStr, themeList);
				}
				// 按“推荐”搜索
				else if (searchStr.equals("人气最高")) {		//选取关注数最高的
					searchPopularity(list,  themeList);
				} else if (searchStr.equals("收藏最多")) {	//赞
					searchCollection(list, themeList);
				} else if (searchStr.equals("评论最多")) {	//分享
					searchComment(list, themeList);
				} else if (searchStr.equals("编辑精选")) {	//随机
					searchGoodEdit(list, themeList);
				}
				// 按“时间”搜索
				else if (searchStr.equals("30分钟前")) {
					searchMinute(list, searchStr, themeList);
				} else if (searchStr.equals("1小时前")) {
					searchHour(list, searchStr, themeList);
				} else if (searchStr.equals("1月前")) {
					searchMonth(list, searchStr, themeList);
				} else if (searchStr.equals("1年前")) {
					searchYear(list, searchStr, themeList);
				}
				//模糊搜索
				else {
					searchClassify(list, searchStr, themeList);
				}

				//将搜索的结果显示出来
				listResult.setAdapter(new CommonAdapter<Content>(getActivity(),
						listContent, R.layout.home_list_item) {

					@Override
					public void convert(ViewHolder holder, Content t) {
						holder.setText(R.id.item_name, t.getEssay().getTitle());
						holder.setText(R.id.item_author, t.getEssay().getAuthor());
						holder.setText(R.id.item_detail, t.getEssay()	.getClassify());
						holder.setText(R.id.item_time, t.getEssay().getTime());
						holder.setText(R.id.item_zanCount,String.valueOf(t.getEssay().getZanNum()));
						holder.setText(R.id.item_guanzhuCount,String.valueOf(t.getEssay().getCareNum()));
						holder.setText(R.id.item_shareCount,String.valueOf(t.getEssay().getShareNum()));
						holder.setImageBitmap(R.id.item_image, t.getBitmap());
					}
				});
				progressDialog = new ProgressDialog(getActivity());
				progressDialog.setTitle("提示");
				progressDialog.setMessage("搜索中...");
				progressDialog.show();
				new Thread() {
					@Override
					public void run() {
						try {
							Thread.sleep(1000);
							handler.sendEmptyMessage(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}.start();
			}
		});
		et_search.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				et_search.setText("");
				return false;
			}
		});
		return rootView;
	}

	// 按“分类”中的关键词搜索
	public void searchClassify(List<Essay> list, String searchStr,
			List<Map<String, Bitmap>> themeList) {
		listContent.clear();
		for (Essay essay : list) {
			if (essay.getClassify().contains(searchStr)) {
				String filename = "theme" + essay.getEssayId() + ".jpg";
				Content c = new Content();
				c.setEssay(essay);
				for (int i = 0; i < themeList.size(); i++) {
					if (themeList.get(i).get(filename) != null) {
						c.setBitmap(themeList.get(i).get(filename));
					}
				}
				listContent.add(c);
			}
		}
	}
	
//	// 模糊搜索
//		public void searchDim(List<Essay> list, String searchStr,
//				List<Map<String, Bitmap>> themeList) {
//			listContent.clear();
//			for (Essay essay : list) {
//				if (essay.getClassify().contains(searchStr)) {
//					String filename = "theme" + essay.getEssayId() + ".jpg";
//					Content c = new Content();
//					c.setEssay(essay);
//					for (int i = 0; i < themeList.size(); i++) {
//						if (themeList.get(i).get(filename) != null) {
//							c.setBitmap(themeList.get(i).get(filename));
//						}
//					}
//					listContent.add(c);
//				}
//			}
//		}
	
	// 按“推荐”中的“人气最高”搜索
	public void searchPopularity(List<Essay> list, List<Map<String, Bitmap>> themeList) {
		listContent.clear();
		
		int maxCareNum=0;
		Essay e = new Essay();
		for (Essay essay : list) {
			if (essay.getCareNum() > maxCareNum) {
				maxCareNum = essay.getCareNum();
				e = essay;
			}
		}
		if (maxCareNum != 0) {
			String filename = "theme" + e.getEssayId() + ".jpg";
			Content c = new Content();
			c.setEssay(e);
			for (int i = 0; i < themeList.size(); i++) {
				if (themeList.get(i).get(filename) != null) {
					c.setBitmap(themeList.get(i).get(filename));
				}
			}
			listContent.add(c);
		}
	}
	
	// 按“推荐”中的“收藏最高”搜索
		public void searchCollection(List<Essay> list, List<Map<String, Bitmap>> themeList) {
			listContent.clear();
			int maxCollectNum=0;
			Essay e = new Essay();
			for (Essay essay : list) {
				if (essay.getZanNum() > maxCollectNum) {
					maxCollectNum = essay.getZanNum();
					e = essay;
				}
			}
			if (maxCollectNum != 0) {
				String filename = "theme" + e.getEssayId() + ".jpg";
				Content c = new Content();
				c.setEssay(e);
				for (int i = 0; i < themeList.size(); i++) {
					if (themeList.get(i).get(filename) != null) {
						c.setBitmap(themeList.get(i).get(filename));
					}
				}
				listContent.add(c);
			}
		}
		
		// 按“推荐”中的“评论最高”搜索
		public void searchComment(List<Essay> list, List<Map<String, Bitmap>> themeList) {
			listContent.clear();
			int maxCommentNum=0;
			Essay e = new Essay();
			for (Essay essay : list) {
				if (essay.getShareNum() > maxCommentNum) {
					maxCommentNum = essay.getShareNum();
					e = essay;
				}
			}
			if (maxCommentNum != 0) {
				String filename = "theme" + e.getEssayId() + ".jpg";
				Content c = new Content();
				c.setEssay(e);
				for (int i = 0; i < themeList.size(); i++) {
					if (themeList.get(i).get(filename) != null) {
						c.setBitmap(themeList.get(i).get(filename));
					}
				}
				listContent.add(c);
			}
		}
		
		// 按“推荐”中的“编辑精选”搜索
		public void searchGoodEdit(List<Essay> list, List<Map<String, Bitmap>> themeList) {
			listContent.clear();
			int itemSize =  Integer.valueOf(UserData.getEssayIdInfo(getActivity()).get("essayId"));
			Random r = new Random();
			int random = r.nextInt(itemSize)+1;
			System.out.println("编辑精选random："+random);
			
			for (Essay essay : list) {
				if (essay.getEssayId() == random) {
					String filename = "theme" + essay.getEssayId() + ".jpg";
					Content c = new Content();
					c.setEssay(essay);
					for (int i = 0; i < themeList.size(); i++) {
						if (themeList.get(i).get(filename) != null) {
							c.setBitmap(themeList.get(i).get(filename));
						}
					}
					listContent.add(c);
					break;
				}
			}
		}

	// 按“时间”中的“30分钟前”搜索
	public void searchMinute(List<Essay> list, String searchStr,
			List<Map<String, Bitmap>> themeList) {
		listContent.clear();

		for (Essay essay : list) {
			String timeFormat = MyDateFormat.dateFormat(essay.getTime());
			if (timeFormat.contains("分钟")) {
				for (int i = 0; i < timeFormat.length(); i++) {
					if (timeFormat.charAt(i) == '分') {
						int time = Integer.valueOf(timeFormat.substring(0, i));
						if (time >= 30) {
							String filename = "theme" + essay.getEssayId()	+ ".jpg";
							Content c = new Content();
							c.setEssay(essay);
							for (int j = 0; j < themeList.size(); j++) {
								if (themeList.get(j).get(filename) != null) {
									c.setBitmap(themeList.get(j).get(filename));
								}
							}
							listContent.add(c);
							break;
						}
					}
				}
			}
		}

	}

	// 按“时间”中的“1小时前”搜索
	public void searchHour(List<Essay> list, String searchStr,
			List<Map<String, Bitmap>> themeList) {
		listContent.clear();

		for (Essay essay : list) {
			String timeFormat = MyDateFormat.dateFormat(essay.getTime());
			if (timeFormat.contains("小时")) {
				String filename = "theme" + essay.getEssayId() + ".jpg";
				Content c = new Content();
				c.setEssay(essay);
				for (int j = 0; j < themeList.size(); j++) {
					if (themeList.get(j).get(filename) != null) {
						c.setBitmap(themeList.get(j).get(filename));
					}
				}
				listContent.add(c);
			}
		}

	}

	// 按“时间”中的“1月前”搜索
	public void searchMonth(List<Essay> list, String searchStr,
			List<Map<String, Bitmap>> themeList) {
		listContent.clear();

		for (Essay essay : list) {
			String timeFormat = MyDateFormat.dateFormat(essay.getTime());
			if (timeFormat.contains("月")) {
				String filename = "theme" + essay.getEssayId() + ".jpg";
				Content c = new Content();
				c.setEssay(essay);
				for (int j = 0; j < themeList.size(); j++) {
					if (themeList.get(j).get(filename) != null) {
						c.setBitmap(themeList.get(j).get(filename));
					}
				}
				listContent.add(c);
			}
		}

	}

	// 按“时间”中的“1年前”搜索
	public void searchYear(List<Essay> list, String searchStr,
			List<Map<String, Bitmap>> themeList) {
		listContent.clear();

		for (Essay essay : list) {
			String timeFormat = MyDateFormat.dateFormat(essay.getTime());
			if (timeFormat.contains("年")) {
				String filename = "theme" + essay.getEssayId() + ".jpg";
				Content c = new Content();
				c.setEssay(essay);
				for (int j = 0; j < themeList.size(); j++) {
					if (themeList.get(j).get(filename) != null) {
						c.setBitmap(themeList.get(j).get(filename));
					}
				}
				listContent.add(c);
			}
		}

	}
	
	
}
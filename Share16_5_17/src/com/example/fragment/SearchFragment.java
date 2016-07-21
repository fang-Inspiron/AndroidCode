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
				// �õ�Ҫ�����Ĺؼ���
				String searchStr = et_search.getText().toString();
				// �õ�����������б���
				SQLiteUtil squ = new SQLiteUtil(getActivity(), "essayData");
				List<Essay> list = squ.findItem();
				SDHelper helper = new SDHelper(getActivity());
				// ��ȡ����ͼƬ
				List<Map<String, Bitmap>> themeList = new ArrayList<Map<String, Bitmap>>();
				try {
					themeList = helper.readThemeBitmap();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				// �������ࡱ����
				if (searchStr.equals("ƽ�����") || searchStr.equals("��ҳ���")
						|| searchStr.equals("UI/icon")	|| searchStr.equals("�廭/�ֻ�")
						|| searchStr.equals("���������") || searchStr.equals("Ӱ��")
						|| searchStr.equals("��Ӱ") || searchStr.equals("����")) {
					searchClassify(list, searchStr, themeList);
				}
				// �����Ƽ�������
				else if (searchStr.equals("�������")) {		//ѡȡ��ע����ߵ�
					searchPopularity(list,  themeList);
				} else if (searchStr.equals("�ղ����")) {	//��
					searchCollection(list, themeList);
				} else if (searchStr.equals("�������")) {	//����
					searchComment(list, themeList);
				} else if (searchStr.equals("�༭��ѡ")) {	//���
					searchGoodEdit(list, themeList);
				}
				// ����ʱ�䡱����
				else if (searchStr.equals("30����ǰ")) {
					searchMinute(list, searchStr, themeList);
				} else if (searchStr.equals("1Сʱǰ")) {
					searchHour(list, searchStr, themeList);
				} else if (searchStr.equals("1��ǰ")) {
					searchMonth(list, searchStr, themeList);
				} else if (searchStr.equals("1��ǰ")) {
					searchYear(list, searchStr, themeList);
				}
				//ģ������
				else {
					searchClassify(list, searchStr, themeList);
				}

				//�������Ľ����ʾ����
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
				progressDialog.setTitle("��ʾ");
				progressDialog.setMessage("������...");
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

	// �������ࡱ�еĹؼ�������
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
	
//	// ģ������
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
	
	// �����Ƽ����еġ�������ߡ�����
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
	
	// �����Ƽ����еġ��ղ���ߡ�����
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
		
		// �����Ƽ����еġ�������ߡ�����
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
		
		// �����Ƽ����еġ��༭��ѡ������
		public void searchGoodEdit(List<Essay> list, List<Map<String, Bitmap>> themeList) {
			listContent.clear();
			int itemSize =  Integer.valueOf(UserData.getEssayIdInfo(getActivity()).get("essayId"));
			Random r = new Random();
			int random = r.nextInt(itemSize)+1;
			System.out.println("�༭��ѡrandom��"+random);
			
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

	// ����ʱ�䡱�еġ�30����ǰ������
	public void searchMinute(List<Essay> list, String searchStr,
			List<Map<String, Bitmap>> themeList) {
		listContent.clear();

		for (Essay essay : list) {
			String timeFormat = MyDateFormat.dateFormat(essay.getTime());
			if (timeFormat.contains("����")) {
				for (int i = 0; i < timeFormat.length(); i++) {
					if (timeFormat.charAt(i) == '��') {
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

	// ����ʱ�䡱�еġ�1Сʱǰ������
	public void searchHour(List<Essay> list, String searchStr,
			List<Map<String, Bitmap>> themeList) {
		listContent.clear();

		for (Essay essay : list) {
			String timeFormat = MyDateFormat.dateFormat(essay.getTime());
			if (timeFormat.contains("Сʱ")) {
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

	// ����ʱ�䡱�еġ�1��ǰ������
	public void searchMonth(List<Essay> list, String searchStr,
			List<Map<String, Bitmap>> themeList) {
		listContent.clear();

		for (Essay essay : list) {
			String timeFormat = MyDateFormat.dateFormat(essay.getTime());
			if (timeFormat.contains("��")) {
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

	// ����ʱ�䡱�еġ�1��ǰ������
	public void searchYear(List<Essay> list, String searchStr,
			List<Map<String, Bitmap>> themeList) {
		listContent.clear();

		for (Essay essay : list) {
			String timeFormat = MyDateFormat.dateFormat(essay.getTime());
			if (timeFormat.contains("��")) {
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
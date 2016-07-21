package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.share.MainActivity;
import com.example.share4_15.R;
import com.example.utils.ScreenUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class NoteFragment extends Fragment implements OnClickListener {

	private List<Fragment> mFragments = new ArrayList<Fragment>();
	private RemenFragment remenFragment;
	private JingxuanFragment jingxuanFragment;
	private Button toTopBtn;// ���ض����İ�ť
	private boolean scrollFlag = false;// ����Ƿ񻬶�
	private int lastVisibleItemPosition = 0;// ����ϴλ���λ��

	private View rootView;
	private ListView list;
	private ImageView imageView;
	private ImageButton zan;
	private ImageButton guanzhu;
	private ImageButton share;
	private TextView name;
	private TextView author;
	private TextView detail;
	private TextView time;
	private TextView zCount;
	private TextView gCount;
	private TextView sCount;

	private String[] str_name = new String[] { "����", "���⻭������",
			"collection��ƽ���", "��ʽ����������Ч�������Ҫ��", "���⻭������", "collection��ƽ���",
			"��ʽ����������Ч�������Ҫ��" };
	private String[] str_author = new String[] { "shareС��", "shareС��",
			"shareС��", "shareС��", "shareС��", "shareС��", "shareС��" };
	private String[] str_detail = new String[] { "ԭ��-�廭-��ϰϰ��", "ƽ�����-�������",
			"ƽ�����-�������", "ԭ��-��Ӱ-��ϰϰ��", "ƽ�����-�������", "ƽ�����-�������", "ԭ��-��Ӱ-��ϰϰ��" };
	private String[] str_time = new String[] { "10����ǰ", "16����ǰ", "17����ǰ",
			"19����ǰ", "20����ǰ", "26����ǰ", "50����ǰ" };
	private int[] img = new int[] { R.drawable.list_img1, R.drawable.list_img2,
			R.drawable.list_img3, R.drawable.list_img4, R.drawable.list_img2,
			R.drawable.list_img3, R.drawable.list_img4 };

	private String[] str_name1 = new String[] { "����", "��ʽ����������Ч�������Ҫ��",
			"���⻭������", "collection��ƽ���", "���⻭������", "collection��ƽ���",
			"��ʽ����������Ч�������Ҫ��" };
	private String[] str_author1 = new String[] { "shareС��", "shareС��",
			"shareС��", "shareС��", "shareС��", "shareС��", "shareС��" };
	private String[] str_detail1 = new String[] { "ԭ��-�廭-��ϰϰ��", "ԭ��-��Ӱ-��ϰϰ��",
			"ƽ�����-�������", "ƽ�����-�������", "ƽ�����-�������", "ƽ�����-�������", "ԭ��-��Ӱ-��ϰϰ��" };
	private String[] str_time1 = new String[] { "10����ǰ", "16����ǰ", "17����ǰ",
			"19����ǰ", "20����ǰ", "26����ǰ", "50����ǰ" };
	private int[] img1 = new int[] { R.drawable.list_img1,
			R.drawable.list_img4, R.drawable.list_img2, R.drawable.list_img3,
			R.drawable.list_img2, R.drawable.list_img3, R.drawable.list_img4 };

	private String[] str_name2 = new String[] { "collection��ƽ���", "����",
			"���⻭������", "��ʽ����������Ч�������Ҫ��", "���⻭������", "collection��ƽ���",
			"��ʽ����������Ч�������Ҫ��" };
	private String[] str_author2 = new String[] { "shareС��", "shareС��",
			"shareС��", "shareС��", "shareС��", "shareС��", "shareС��" };
	private String[] str_detail2 = new String[] { "ƽ�����-�������", "ԭ��-�廭-��ϰϰ��",
			"ƽ�����-�������", "ԭ��-��Ӱ-��ϰϰ��", "ƽ�����-�������", "ƽ�����-�������", "ԭ��-��Ӱ-��ϰϰ��" };
	private String[] str_time2 = new String[] { "10����ǰ", "16����ǰ", "17����ǰ",
			"19����ǰ", "20����ǰ", "26����ǰ", "50����ǰ" };
	private int[] img2 = new int[] { R.drawable.list_img3,
			R.drawable.list_img1, R.drawable.list_img2, R.drawable.list_img4,
			R.drawable.list_img2, R.drawable.list_img3, R.drawable.list_img4 };

	int a = 102, b = 20, c = 26;

	private TextView jingxuan;
	private TextView remen;
	private TextView quanbu;

	@SuppressLint("InflateParams") @Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		MainActivity.rg_main_menu.setVisibility(View.VISIBLE);
		rootView = inflater.inflate(R.layout.note_fragment, null);
		list = (ListView) rootView.findViewById(R.id.note_list);
		jingxuan = (TextView) rootView.findViewById(R.id.jingxuan);
		remen = (TextView) rootView.findViewById(R.id.remen);
		quanbu = (TextView) rootView.findViewById(R.id.quanbu);
		toTopBtn = (Button) rootView.findViewById(R.id.note_top_btn);
		quanbu.setTextColor(Color.GRAY);

		remenFragment = new RemenFragment();
		jingxuanFragment = new JingxuanFragment();
		mFragments.add(remenFragment);
		mFragments.add(jingxuanFragment);
		initAdapter();
		jingxuan.setOnClickListener(this);
		remen.setOnClickListener(this);
		quanbu.setOnClickListener(this);
		list.setAdapter(new MyAdapter(getActivity(), str_name, str_author,
				str_detail, str_time, img));
		initView();
		return rootView;
	}

	/**
	 * ��ʼ����ͼ
	 */
	private void initView() {

		toTopBtn.setOnClickListener(this);
		list.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				switch (scrollState) {
				// ��������ʱ
				case OnScrollListener.SCROLL_STATE_IDLE:// �ǵ���Ļֹͣ����ʱ
					scrollFlag = false;
					// �жϹ������ײ�
					if (list.getLastVisiblePosition() == (list.getCount() - 1)) {
						toTopBtn.setVisibility(View.VISIBLE);
					}
					// �жϹ���������
					if (list.getFirstVisiblePosition() == 0) {
						toTopBtn.setVisibility(View.GONE);
					}

					break;
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// ����ʱ
					scrollFlag = true;
					break;
				case OnScrollListener.SCROLL_STATE_FLING:// �ǵ��û�����֮ǰ������Ļ��̧����ָ����Ļ�������Ի���ʱ
					scrollFlag = false;
					break;
				}
			}

			/**
			 * firstVisibleItem����ǰ�ܿ����ĵ�һ���б���ID����0��ʼ��
			 * visibleItemCount����ǰ�ܿ������б��������С���Ҳ�㣩 totalItemCount���б����
			 */
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// ����ʼ������ListView�ײ���Y��㳬����Ļ���Χʱ����ʾ�����ض�����ť
				if (scrollFlag
						&& ScreenUtil.getScreenViewBottomHeight(list) >= ScreenUtil
								.getScreenHeight(getActivity())) {
					if (firstVisibleItem > lastVisibleItemPosition) {// �ϻ�
						toTopBtn.setVisibility(View.VISIBLE);
					} else if (firstVisibleItem < lastVisibleItemPosition) {// �»�
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
	 * ����ListView��ָ��λ��
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

	private void initAdapter() {
		android.support.v4.app.FragmentManager fm = getActivity()
				.getSupportFragmentManager();
		new FragmentPagerAdapter(fm) {

			@Override
			public int getCount() {
				return mFragments.size();
			}

			@Override
			public android.support.v4.app.Fragment getItem(int arg0) {
				return mFragments.get(arg0);
			}

			// ��ʼ��ÿ��ҳ��ѡ��
			@Override
			public Object instantiateItem(ViewGroup arg0, int arg1) {
				return super.instantiateItem(arg0, arg1);
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				super.destroyItem(container, position, object);
			}
		};
	}

	// ������

	// listview������
	class MyAdapter extends BaseAdapter {

		String[] name1;
		String[] author1;
		String[] detail1;
		String[] time1;
		int[] img1;
		LayoutInflater ll;

		public MyAdapter(Context context, String[] name, String[] author,
				String[] detail, String[] time, int[] img) {
			// TODO Auto-generated constructor stub
			this.ll = LayoutInflater.from(context);
			this.name1 = name;
			this.author1 = author;
			this.detail1 = detail;
			this.time1 = time;
			this.img1 = img;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return str_name.length;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@SuppressLint({ "InflateParams", "ViewHolder" }) @Override
		public View getView(int arg0, View convertView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			convertView = (LinearLayout) ll.inflate(R.layout.home_list_item,
					null);
			imageView = (ImageView) convertView.findViewById(R.id.item_image);
			name = (TextView) convertView.findViewById(R.id.item_name);
			author = (TextView) convertView.findViewById(R.id.item_author);
			detail = (TextView) convertView.findViewById(R.id.item_detail);
			time = (TextView) convertView.findViewById(R.id.item_time);
			zCount = (TextView) convertView.findViewById(R.id.item_zanCount);
			gCount = (TextView) convertView
					.findViewById(R.id.item_guanzhuCount);
			sCount = (TextView) convertView.findViewById(R.id.item_shareCount);

			zan = (ImageButton) convertView.findViewById(R.id.item_zan);
			guanzhu = (ImageButton) convertView.findViewById(R.id.item_guanzhu);
			share = (ImageButton) convertView.findViewById(R.id.item_share);

			imageView.setImageDrawable(getResources().getDrawable(img1[arg0]));
			name.setText(name1[arg0]);
			author.setText(author1[arg0]);
			detail.setText(detail1[arg0]);
			time.setText(time1[arg0]);

			zan.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					View view = (View) v.getParent();
					zCount = (TextView) view.findViewById(R.id.item_zanCount);
					String str = zCount.getText().toString();
					int i = Integer.parseInt(str);
					String s = Integer.toString(i + 1);
					zCount.setText(s);
				}

			});
			guanzhu.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					View view = (View) v.getParent();
					gCount = (TextView) view
							.findViewById(R.id.item_guanzhuCount);
					String str = gCount.getText().toString();
					int i = Integer.parseInt(str);
					String s = Integer.toString(i + 1);
					gCount.setText(s);
				}
			});
			share.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					View view = (View) v.getParent();
					sCount = (TextView) view.findViewById(R.id.item_shareCount);
					String str = sCount.getText().toString();
					int i = Integer.parseInt(str);
					String s = Integer.toString(i + 1);
					sCount.setText(s);
				}
			});
			return convertView;
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.jingxuan:
			jingxuan.setTextColor(Color.GRAY);
			remen.setTextColor(Color.WHITE);
			quanbu.setTextColor(Color.WHITE);
			list.setAdapter(new MyAdapter(getActivity(), str_name1,
					str_author1, str_detail1, str_time1, img1));
			break;
		case R.id.remen:
			remen.setTextColor(Color.GRAY);
			jingxuan.setTextColor(Color.WHITE);
			quanbu.setTextColor(Color.WHITE);
			list.setAdapter(new MyAdapter(getActivity(), str_name2,
					str_author2, str_detail2, str_time2, img2));
			break;
		case R.id.quanbu:
			quanbu.setTextColor(Color.GRAY);
			remen.setTextColor(Color.WHITE);
			jingxuan.setTextColor(Color.WHITE);
			list.setAdapter(new MyAdapter(getActivity(), str_name, str_author,
					str_detail, str_time, img));
			break;
		case R.id.note_top_btn:// �����ť���ص�ListView�ĵ�һ��
			setListViewPos(0);
			break;
		}
	}

}
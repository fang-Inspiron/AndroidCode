package com.fastfood.utils;

import com.fastfood.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class ReFlashListView extends ListView implements OnScrollListener {

	private View header;
	private int headerHeight;// ���������ļ��ĸ߶�
	private int firstVisibleItem;// ��ǰ��һ���ɼ���item��λ��
	private int scrollStste;// listview ��ǰ����״̬

	private boolean isRemark;// ��ǣ���ǰ����ListView����˰���
	private int startY;// ����ʱ��Yֵ

	private int state;// ��ǰ״̬
	private final int NONE = 0;// ����״̬
	private final int PULL = 1;// ��ʾ����״̬
	private final int RELSE = 2;// ��ʾ�ͷ�״̬
	private final int REFLASHING = 3;// ˢ��״̬

	private IReflashListener reflashListener;// ˢ�����ݵĽӿ�

	public ReFlashListView(Context context) {
		super(context);
		initView(context);
	}

	public ReFlashListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public ReFlashListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	/**
	 * ��ʼ�����棬��Ӷ��������ļ���listview����
	 * 
	 * @param context
	 */
	private void initView(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		header = inflater.inflate(R.layout.header_layout, null);
		measureView(header);
		headerHeight = header.getMeasuredHeight();
		topPadding(-headerHeight);
		this.addHeaderView(header);
		this.setOnScrollListener(this);
	}

	/**
	 * ֪ͨ�����֣�ռ�ö��ط�
	 * 
	 * @param v
	 */
	private void measureView(View v) {
		ViewGroup.LayoutParams p = v.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
		int height;
		int tempHeight = p.height;
		if (tempHeight > 0) {
			height = MeasureSpec.makeMeasureSpec(tempHeight,
					MeasureSpec.EXACTLY);
		} else {
			height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		v.measure(width, height);
	}

	/**
	 * ����header���ֵ��ϱ߾�
	 * 
	 * @param topPadding
	 */
	private void topPadding(int topPadding) {
		header.setPadding(header.getPaddingLeft(), topPadding,
				header.getPaddingRight(), header.getPaddingBottom());
		header.invalidate();
	}

	@Override
	public void onScroll(AbsListView arg0, int firstVisibleItem, int arg2,
			int arg3) {
		this.firstVisibleItem = firstVisibleItem;
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int scrollStste) {
		this.scrollStste = scrollStste;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (firstVisibleItem == 0) {
				isRemark = true;
				startY = (int) ev.getY();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			onMove(ev);
			break;

		case MotionEvent.ACTION_UP:
			if (state == RELSE) {
				state = REFLASHING;
				reflashViewByState();
				// ������������
				reflashListener.onReflash();
			} else if (state == PULL) {
				state = NONE;
				isRemark = false;
				reflashViewByState();
			}
			break;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * �ж��ƶ������еĲ���
	 * 
	 * @param ev
	 */
	private void onMove(MotionEvent ev) {
		if (!isRemark) {
			return;
		}
		int tempY = (int) ev.getY();
		int space = tempY - startY;
		int topPadding = space - headerHeight;
		switch (state) {
		case NONE:
			// ����״̬
			if (space > 0) {
				state = PULL;
				reflashViewByState();
			}
			break;

		case PULL:
			topPadding(topPadding);
			// �����ﵽһ���̶ȣ�����ListView��ǰ�ǹ���״̬
			if (space > headerHeight + 30
					&& scrollStste == SCROLL_STATE_TOUCH_SCROLL) {
				state = RELSE;
				reflashViewByState();
			}
			break;
		case RELSE:
			topPadding(topPadding);
			if (space < headerHeight + 30) {
				state = PULL;
				reflashViewByState();
			} else if (space <= 0) {
				state = NONE;
				isRemark = false;
				reflashViewByState();
			}
			break;
		}
	}

	/**
	 * ���ݵ�ǰ״̬�ı������ʾ
	 */
	private void reflashViewByState() {
		TextView tip = (TextView) header.findViewById(R.id.tip);
		ImageView arrow = (ImageView) header.findViewById(R.id.arrow);
		ProgressBar progress = (ProgressBar) header.findViewById(R.id.progress);

		RotateAnimation anim = new RotateAnimation(0, 180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		anim.setDuration(500);
		anim.setFillAfter(true);
		RotateAnimation anim1 = new RotateAnimation(180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		anim1.setDuration(500);
		anim1.setFillAfter(true);

		switch (state) {
		case NONE:
			topPadding(-headerHeight);
			arrow.clearAnimation();
			break;

		case PULL:
			arrow.setVisibility(View.VISIBLE);
			progress.setVisibility(View.GONE);
			tip.setText("��������ˢ��");
			arrow.clearAnimation();
			arrow.setAnimation(anim1);
			break;
		case RELSE:
			arrow.setVisibility(View.VISIBLE);
			progress.setVisibility(View.GONE);
			tip.setText("�ɿ�����ˢ��");
			arrow.clearAnimation();
			arrow.setAnimation(anim);
			break;
		case REFLASHING:
			arrow.clearAnimation();
			topPadding(0);// ˢ�µ�ʱ��߶ȹ̶�����
			arrow.setVisibility(View.GONE);
			progress.setVisibility(View.VISIBLE);
			tip.setText("����ˢ��");
			break;
		}
	}

	/**
	 * ��ȡ������
	 */
	public void reflashComplete() {
		state = NONE;
		isRemark = false;
		reflashViewByState();
	}

	public void setInterface(IReflashListener reflashListener) {
		this.reflashListener = reflashListener;
	}

	/**
	 * ˢ�����ݽӿ�
	 */
	public interface IReflashListener {
		public void onReflash();
	}
}

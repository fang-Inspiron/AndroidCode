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
	private int headerHeight;// 顶部布局文件的高度
	private int firstVisibleItem;// 当前第一个可见的item的位置
	private int scrollStste;// listview 当前滚动状态

	private boolean isRemark;// 标记，当前是在ListView的最顶端按下
	private int startY;// 按下时的Y值

	private int state;// 当前状态
	private final int NONE = 0;// 正常状态
	private final int PULL = 1;// 提示下拉状态
	private final int RELSE = 2;// 提示释放状态
	private final int REFLASHING = 3;// 刷新状态

	private IReflashListener reflashListener;// 刷新数据的接口

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
	 * 初始化界面，添加顶部布局文件到listview里面
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
	 * 通知父布局，占用多大地方
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
	 * 设置header布局的上边距
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
				// 加载最新数据
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
	 * 判断移动过程中的操作
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
			// 下拉状态
			if (space > 0) {
				state = PULL;
				reflashViewByState();
			}
			break;

		case PULL:
			topPadding(topPadding);
			// 下拉达到一定程度，并且ListView当前是滚动状态
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
	 * 根据当前状态改变界面显示
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
			tip.setText("下拉可以刷新");
			arrow.clearAnimation();
			arrow.setAnimation(anim1);
			break;
		case RELSE:
			arrow.setVisibility(View.VISIBLE);
			progress.setVisibility(View.GONE);
			tip.setText("松开可以刷新");
			arrow.clearAnimation();
			arrow.setAnimation(anim);
			break;
		case REFLASHING:
			arrow.clearAnimation();
			topPadding(0);// 刷新的时候高度固定不变
			arrow.setVisibility(View.GONE);
			progress.setVisibility(View.VISIBLE);
			tip.setText("正在刷新");
			break;
		}
	}

	/**
	 * 获取完数据
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
	 * 刷新数据接口
	 */
	public interface IReflashListener {
		public void onReflash();
	}
}

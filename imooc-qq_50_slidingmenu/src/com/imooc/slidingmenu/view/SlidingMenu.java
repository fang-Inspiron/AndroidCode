package com.imooc.slidingmenu.view;

import com.imooc.slidingmenu.R;
import com.nineoldandroids.view.ViewHelper;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class SlidingMenu extends HorizontalScrollView {

	private LinearLayout mWapper;
	private ViewGroup mMenu;
	private ViewGroup mContent;
	private int mScreenWidth;
	private int mMenuWidth;
	private int mMenuRightPadding = 50;// 单位：dp
	private boolean once;
	private boolean isOpen;

	public SlidingMenu(Context context) {
		// 一个参数的构造方法调用两个参数的构造方法
		this(context, null);
	}

	// 未使用自定义属性时调用
	public SlidingMenu(Context context, AttributeSet attrs) {
		// 两个参数的构造方法调用三个参数的构造方法
		this(context, attrs, 0);
	}

	// 当使用了自定义属性时，会调用此构造方法
	public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		// 获取我们定义的属性
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.SlidingMenu, defStyleAttr, 0);

		int n = a.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.SlidingMenu_rightPadding:
				mMenuRightPadding = a.getDimensionPixelSize(attr,
						(int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_DIP, 50, context
										.getResources().getDisplayMetrics()));
				break;
			}
		}
		// 用完要释放
		a.recycle();

		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		mScreenWidth = outMetrics.widthPixels;
	}

	// 设置子View的宽和高 设置自己的宽和高
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		if (!once) {
			mWapper = (LinearLayout) getChildAt(0);
			mMenu = (ViewGroup) mWapper.getChildAt(0);
			mContent = (ViewGroup) mWapper.getChildAt(1);
			mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth
					- mMenuRightPadding;
			mContent.getLayoutParams().width = mScreenWidth;
			once = true;
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	// 通过设置偏移量将Menu隐藏
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed) {
			this.scrollTo(mMenuWidth, 0);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_UP:
			// 隐藏在左边的宽度
			int scrollX = getScrollX();
			if (scrollX >= mMenuWidth / 2) {
				this.smoothScrollTo(mMenuWidth, 0);
				isOpen = false;
			} else {
				this.smoothScrollTo(0, 0);
				isOpen = true;
			}
			return true;
		}

		return super.onTouchEvent(ev);
	}

	// 打开菜单
	public void openMenu() {
		if (isOpen)
			return;
		this.smoothScrollTo(0, 0);
		isOpen = true;
	}

	// 关闭菜单
	public void closeMenu() {
		if (!isOpen)
			return;
		this.smoothScrollTo(mMenuWidth, 0);
		isOpen = false;
	}

	// 切换菜单
	public void toggle() {
		if (isOpen) {
			closeMenu();
		} else {
			openMenu();
		}
	}

	// 滚动发生时（抽屉式侧滑效果）
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		
		super.onScrollChanged(l, t, oldl, oldt);

		float scale = l * 1.0f / mMenuWidth;// 1~0动画梯度值

		/*
		 * 区别一：内容区域1.0~0.7缩放的效果 scale：1.0~0.0 0.7+0.3*scale
		 * 
		 * 区别二：菜单的偏移量需要修改
		 * 
		 * 区别三：菜单的显示时有缩放以及透明度变化 
		 * 				缩放：0.7~1.0 1.0-scale*0.3 
		 * 				透明度：0.6~1.0
		 * 							0.6+0.4*（1-scale）
		 */
		float rightScale = 0.7f + 0.3f * scale;
		float leftScale = 1.0f - scale * 0.3f;
		float leftAlpha = 0.6f + 0.4f * (1 - scale);

		// 调用属性动画，设置TranslationX
		ViewHelper.setTranslationX(mMenu, mMenuWidth * scale * 0.7f);//Menu设置偏移
		ViewHelper.setScaleX(mMenu, leftScale);//Menu设置缩放
		ViewHelper.setScaleY(mMenu, leftScale);
		ViewHelper.setAlpha(mMenu, leftAlpha);//Menu设置透明度

		// 设置content的缩放的中心点
		ViewHelper.setPivotX(mContent, 0);//content设置中心点
		ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);
		ViewHelper.setScaleX(mContent, rightScale);//content设置缩放
		ViewHelper.setScaleY(mContent, rightScale);

	}

}

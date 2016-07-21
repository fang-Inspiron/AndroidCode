package com.qlf.plants.activity;

import java.util.ArrayList;
import java.util.List;

import com.qlf.plants.MainActivity;
import com.qlf.plants.R;
import com.qlf.plants.userdata.UserData;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.Window;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class PopBackground extends Activity implements OnClickListener ,OnGestureListener{

	private ImageView pre_photo, next_photo, enter_photo;

	private ViewPager background;
	ViewFlipper flipper;
	// 定义手势检测器实例
	GestureDetector detector;
	// 定义一个动画数组，用于为ViewFlipper指定切换动画效果
	Animation[] animations = new Animation[4];
	// 定义手势动作两点之间的最小距离
	final int FLIP_DISTANCE = 50;

	private int[] imgs = new int[] { R.drawable.b1, R.drawable.b2,
			R.drawable.b3, R.drawable.b4, R.drawable.b5, R.drawable.b6 };
	int index = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.motify_background_main);
		
		detector = new GestureDetector(this, (OnGestureListener) this);
		// 获得ViewFlipper实例
		flipper = (ViewFlipper) this.findViewById(R.id.flipper);
		// 为ViewFlipper添加5个ImageView组件
		flipper.addView(addImageView(R.drawable.b1));
		flipper.addView(addImageView(R.drawable.b2));
		flipper.addView(addImageView(R.drawable.b3));
		flipper.addView(addImageView(R.drawable.b4));
		flipper.addView(addImageView(R.drawable.b5));
		flipper.addView(addImageView(R.drawable.b6));
		// 初始化Animation数组
		animations[0] = AnimationUtils.loadAnimation(this, R.anim.left_in);
		animations[1] = AnimationUtils.loadAnimation(this, R.anim.left_out);
		animations[2] = AnimationUtils.loadAnimation(this, R.anim.right_in);
		animations[3] = AnimationUtils.loadAnimation(this, R.anim.right_out);
		findViews();
	}

	private void findViews() {
		pre_photo = (ImageView) findViewById(R.id.pre_photo);
		next_photo = (ImageView) findViewById(R.id.next_photo);
		enter_photo = (ImageView) findViewById(R.id.enter_photo);

		pre_photo.setOnClickListener(this);
		next_photo.setOnClickListener(this);
		enter_photo.setOnClickListener(this);
	}

	@SuppressLint("NewApi") public void pre() {
		
		index--;
		if (index < 0) {
			index = imgs.length - 1;
		}
		flipper.showPrevious();
	}

	@SuppressLint("NewApi") public void next() {
		index++;
		if (index >= imgs.length) {
			index = 0;
		}
		flipper.showNext();
	}

	@SuppressLint("NewApi") 
	public void enter() {
		System.out.println(index);
		UserData data = new UserData(this);
		data.saveBackground(imgs[index]);
		MainActivity.main_background.setBackground(getResources().getDrawable(imgs[index]));
		if(PlantInfoActivity.up!=null)
		PlantInfoActivity.up.setBackground(getResources().getDrawable(imgs[index]));
		finish();
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pre_photo:
			pre();
			break;
		case R.id.next_photo:
			next();
			break;
		case R.id.enter_photo:
			enter();
			break;
		default:
			break;
		}
	}
	// 定义添加ImageView的工具方法
	private View addImageView(int resId) {
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(resId);
		imageView.setScaleType(ImageView.ScaleType.CENTER);
		return imageView;
	}
	
	@Override
	public boolean onFling(MotionEvent event1, MotionEvent event2,
			float velocityX, float velocityY) {
		// 如果第一个触点事件的X座标大于第二个触点事件的X座标超过FLIP_DISTANCE
		// 也就是手势从右向左滑。
		if (event1.getX() - event2.getX() > FLIP_DISTANCE) {
			// 为flipper设置切换的的动画效果
			flipper.setInAnimation(animations[0]);
			flipper.setOutAnimation(animations[1]);
			index++;
			flipper.showNext();
			return true;
		}
		// 如果第二个触点事件的X座标大于第一个触点事件的X座标超过FLIP_DISTANCE
		// 也就是手势从右向左滑。
		else if (event2.getX() - event1.getX() > FLIP_DISTANCE) {
			// 为flipper设置切换的的动画效果
			flipper.setInAnimation(animations[2]);
			flipper.setOutAnimation(animations[3]);
			index--;
			flipper.showPrevious();
			return true;
		}
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 将该Activity上的触碰事件交给GestureDetector处理
		return detector.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent event) {
	}

	@Override
	public boolean onScroll(MotionEvent event1, MotionEvent event2, float arg2,
			float arg3) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent event) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent event) {
		return false;
	}

}
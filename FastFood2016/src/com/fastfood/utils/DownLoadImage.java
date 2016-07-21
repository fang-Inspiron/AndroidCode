package com.fastfood.utils;

import java.net.URL;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

public class DownLoadImage {
	private String imag_path;
	public static final String IMAGE_URL = "http://115.159.94.74/Ordering";
	public DownLoadImage(String imag_path) {
		this.imag_path = imag_path;
	}

	public void loadImage(final ImageCallBack callBack) {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				callBack.getDrawable((Drawable) msg.obj);
			}
		};
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Drawable drawable = Drawable.createFromStream(new URL(
							IMAGE_URL+imag_path).openStream(), "");
					Message message = Message.obtain();
					message.obj = drawable;
					handler.sendMessage(message);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public interface ImageCallBack {
		public void getDrawable(Drawable drawable);
	}
}

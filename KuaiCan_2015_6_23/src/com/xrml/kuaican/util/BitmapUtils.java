package com.xrml.kuaican.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class BitmapUtils {

	public final static String TAG = "BitmapUtils";
	
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// 原始图片的高度
		final int height = options.outHeight;
		final int width = options.outWidth;
		Log.v(TAG, "Orginal Bitmap Size :height = "+height +" width = "+width);
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			final int halfHeight = height / 2;
			final int halfWidth = width / 2;
			// 在保证解析出的bitmap宽高分别大于目标尺寸宽高的前提下，取可能的inSampleSize的最大值
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}
		return inSampleSize;
	}

	/**
	 * @param res
	 * @param resId
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromResource(Resources res,
			int resId, int reqWidth, int reqHeight) {

		// 首先设置 inJustDecodeBounds=true 来获取图片尺寸
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);
		// 计算 inSampleSize 的值
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);
		Log.v(TAG, "inSampleSize = "+options.inSampleSize);
		// 根据计算出的 inSampleSize 来解码图片生成Bitmap
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	// mImageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(),
	// R.id.myimage, 100, 100));
}

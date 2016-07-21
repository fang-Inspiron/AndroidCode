package com.xrml.kuaican.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class BitmapUtils {

	public final static String TAG = "BitmapUtils";
	
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// ԭʼͼƬ�ĸ߶�
		final int height = options.outHeight;
		final int width = options.outWidth;
		Log.v(TAG, "Orginal Bitmap Size :height = "+height +" width = "+width);
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			final int halfHeight = height / 2;
			final int halfWidth = width / 2;
			// �ڱ�֤��������bitmap��߷ֱ����Ŀ��ߴ��ߵ�ǰ���£�ȡ���ܵ�inSampleSize�����ֵ
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

		// �������� inJustDecodeBounds=true ����ȡͼƬ�ߴ�
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);
		// ���� inSampleSize ��ֵ
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);
		Log.v(TAG, "inSampleSize = "+options.inSampleSize);
		// ���ݼ������ inSampleSize ������ͼƬ����Bitmap
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	// mImageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(),
	// R.id.myimage, 100, 100));
}

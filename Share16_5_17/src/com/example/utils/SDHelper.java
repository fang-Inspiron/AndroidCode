package com.example.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.widget.Toast;

public class SDHelper {

	public Context mContext;
	public String newPath;
	public List<Map<String, Bitmap>> listBmp;
	public List<String> listPath;
	public Map<String, Bitmap> bitmap;
	public Map<String, String> path;

	public SDHelper(Context context) {
		this.mContext = context;
	}

	public List<Map<String, Bitmap>> getList() {
		return listBmp;
	}

	public void setList(List<Map<String, Bitmap>> listBmp, List<String> listPath) {
		this.listBmp = listBmp;
		this.listPath = listPath;
	}
	
	public void setMap(Map<String, Bitmap> bitmap, Map<String, String> path) {
		this.bitmap = bitmap;
		this.path = path;
	}

	// 首先判断SD卡是否插入
	public String getSDPath() {
		File SDdir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		if (sdCardExist) {
			SDdir = Environment.getExternalStorageDirectory();
		}
		if (SDdir != null) {
			return SDdir.toString();
		} else {
			return null;
		}
	}

	// 然后创建文件夹
	public void createSDCardDir() {
		if (getSDPath() == null) {
			Toast.makeText(mContext, "未找到SD卡", Toast.LENGTH_SHORT).show();
		} else {
			if (Environment.MEDIA_MOUNTED.equals(Environment
					.getExternalStorageState())) {
				// 创建一个文件夹对象，赋值为外部存储器的目录
				File sdcardDir = Environment.getExternalStorageDirectory();
				// 得到一个路径，内容是sdcard的文件夹路径和名字
				newPath = sdcardDir.getPath() + "/shareApp/tempImages/";// newPath在程序中要声明
				File path1 = new File(newPath);
				if (!path1.exists()) {
					// 若不存在，创建目录，可以在应用启动的时候创建
					path1.mkdirs();
					System.out.println("paht ok,path:" + newPath);
				}
			} else {
				System.out.println("false");
			}
		}
	}

	// 创建好文件夹之后就可以保存图片了
	public void saveMyBitmap() throws IOException {

		FileOutputStream fOut = null;
		for (int i = 0; i < listBmp.size(); i++) {
			File f = new File(newPath + listPath.get(i) + ".jpg");
			f.createNewFile();
			try {
				fOut = new FileOutputStream(f);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			Bitmap bmp = listBmp.get(i).get(listPath.get(i));
			bmp.compress(Bitmap.CompressFormat.JPEG, 60, fOut);
			try {
				fOut.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void saveHeadBitmap() throws IOException {
		FileOutputStream fOut = null;
		File f = new File(newPath + path.get("0") + ".jpg");
		f.createNewFile();
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Bitmap bmp = bitmap.get(path.get("0"));
		bmp.compress(Bitmap.CompressFormat.JPEG, 60, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public Map<String, Bitmap> readHeadBitmap() throws IOException {
		File sdcardDir = Environment.getExternalStorageDirectory();
		// 得到一个路径，内容是sdcard的文件夹路径和名字
		String path = sdcardDir.getPath() + "/shareApp/tempImages";// newPath在程序中要声明

		File file = new File(path);

		newPath = sdcardDir.getPath() + "/shareApp/tempImages/";
		Map<String, Bitmap> map = new HashMap<String, Bitmap>();
		if (file.getName().contains("head")) {
			Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath());
			map.put(file.getName(), bmp);
		}
		return map;
	}

	// 读取图片了
	public List<Map<String, Bitmap>> readThemeBitmap() throws IOException {
		File sdcardDir = Environment.getExternalStorageDirectory();
		// 得到一个路径，内容是sdcard的文件夹路径和名字
		String path = sdcardDir.getPath() + "/shareApp/tempImages";// newPath在程序中要声明

		File file = new File(path);
		File[] files = file.listFiles();
		if (files == null || files.length <= 0) {
			return null;
		}

		newPath = sdcardDir.getPath() + "/shareApp/tempImages/";
		List<Map<String, Bitmap>> list = new ArrayList<Map<String, Bitmap>>();
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (f.getName().contains("theme")) {
				Bitmap themeBmp = BitmapFactory.decodeFile(f.getAbsolutePath());
				Map<String, Bitmap> map = new HashMap<String, Bitmap>();
				map.put(f.getName(), themeBmp);
				list.add(map);
			}
		}
		return list;
	}

	// 读取图片了
	public List<Map<String, Bitmap>> readImgBitmap(String pos)
			throws IOException {
		File sdcardDir = Environment.getExternalStorageDirectory();
		// 得到一个路径，内容是sdcard的文件夹路径和名字
		String path = sdcardDir.getPath() + "/shareApp/tempImages";// newPath在程序中要声明

		File file = new File(path);
		File[] files = file.listFiles();
		if (files == null || files.length <= 0) {
			return null;
		}

		newPath = sdcardDir.getPath() + "/shareApp/tempImages/";
		List<Map<String, Bitmap>> list = new ArrayList<Map<String, Bitmap>>();
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (f.getName().contains(pos)) {
				Bitmap imgBmp = BitmapFactory.decodeFile(f.getAbsolutePath());
				Map<String, Bitmap> map = new HashMap<String, Bitmap>();
				map.put(pos, imgBmp);
				list.add(map);
			}
		}
		return list;
	}

	// 附加drawable2Bitmap方法
	static Bitmap drawable2Bitmap(Drawable d) {
		int width = d.getIntrinsicWidth();
		int height = d.getIntrinsicHeight();
		Bitmap.Config config = d.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		Bitmap bitmap = Bitmap.createBitmap(width, height, config);
		Canvas canvas = new Canvas(bitmap);
		d.setBounds(0, 0, width, height);
		d.draw(canvas);
		return bitmap;
	}

}

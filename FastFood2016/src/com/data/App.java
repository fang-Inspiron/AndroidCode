package com.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;

import com.baidu.frontia.Frontia;
import com.baidu.frontia.FrontiaApplication;
import com.baidu.frontia.api.FrontiaStatistics;
import com.baidu.mobstat.SendStrategyEnum;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class App extends FrontiaApplication {

	public static final String BAIDU_REPORTID = "1451c1a60d";

	public boolean INTERNET_IS_OPEN = false; // ��ǰ״̬�Ƿ�����
	public static String IS_FIRST_INTO = "yes";
	private static User user;
	private static Area area;
	public List<Map<String, Object>> goodsList = new ArrayList<Map<String, Object>>();
	// ĳ������Ʒ���
	private Map<String, Object> cateData;
	// ĳ��Ʒ����µ���Ʒ�б�
	private Map<String, Object> data;
	// ��Ʒ��ͼƬ�͹��ͼƬ
	private Map<String, Bitmap> photoData;

	@Override
	public void onCreate() {
		super.onCreate();

		// ����Ĭ�ϵ�ImageLoader���ò���
		ImageLoaderConfiguration configuration = ImageLoaderConfiguration
				.createDefault(this);
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(configuration);
		//��ʼ��Ӧ��ͳ��
		initFrontia();

	}

	private void initFrontia() {
		if (Frontia.init(getApplicationContext(), BAIDU_REPORTID)) {
			FrontiaStatistics stat = Frontia.getStatistics();
			stat.setReportId(BAIDU_REPORTID);
			stat.setSessionTimeout(20);
			stat.enableExceptionLog();
			stat.start(SendStrategyEnum.SET_TIME_INTERVAL, 0, 10, false);
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		App.user = user;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		App.area = area;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public Map<String, Object> getCateData() {
		return cateData;
	}

	public void setCateData(Map<String, Object> cateData) {
		this.cateData = cateData;
	}

	public Map<String, Bitmap> getPhotoData() {
		return photoData;
	}

	public void setPhotoData(Map<String, Bitmap> photoData) {
		this.photoData = photoData;
	}

}

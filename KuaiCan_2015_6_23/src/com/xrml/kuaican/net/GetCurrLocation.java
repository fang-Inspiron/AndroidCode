package com.xrml.kuaican.net;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * @author Administrator �õ���ǰ��������
 */
public class GetCurrLocation {

	private LocationClient mLocationClient;
	private MyLocationListenner myListener;
	String cityName;
	private Handler handler;

	public GetCurrLocation(Context context, Handler handler) {
		this.handler = handler;
		mLocationClient = new LocationClient(context);

		/**
		 * ������������������������������������������������������������������������������������������������������������������������������������
		 * �����AK��Ӧ��ǩ�������󶨣����ʹ�����Լ��Ĺ�������Ҫ�滻Ϊ�Լ������Key
		 * ������������������������������������������������������������������������������������������������������������������������������������
		 */
		mLocationClient.setAK("uXPoEo7EN3i51dxvBsnCm2wi");
		myListener = new MyLocationListenner();
		mLocationClient.registerLocationListener(myListener);
		if (mLocationClient != null) {
			setLocationOption();
			mLocationClient.start();
			mLocationClient.requestLocation();
		}
	}

	// ������ز���
	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // ��gps
		option.setCoorType("bd09ll"); // ������������
		option.setServiceName("com.baidu.location.service_v2.9");
		option.setAddrType("all");
		option.setScanSpan(2000); // ���ö�λģʽ��С��1����һ�ζ�λ;���ڵ���1����ʱ��λ
		option.setPriority(LocationClientOption.NetWorkFirst); // ������������
		option.setPriority(LocationClientOption.GpsFirst); // �����ã�Ĭ����gps����

		option.disableCache(true);

		mLocationClient.setLocOption(option);
	}

	/**
	 * �����������и���λ�õ�ʱ�򣬸�ʽ�����ַ������������Ļ��
	 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			StringBuffer sb = new StringBuffer(256);
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				/**
				 * ��ʽ����ʾ��ַ��Ϣ
				 */
				cityName = location.getCity();
				if (cityName.equals("") || cityName == null) {
					handler.sendEmptyMessage(0);
				} else {
					Message message = Message.obtain();
					message.what = 1;
					message.obj = location.getCity();
					handler.sendMessage(message);
				}
			}

		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}

	public void stopListener() {
		mLocationClient.stop();
	}
}
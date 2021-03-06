package com.xrml.kuaican.net;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * @author Administrator 得到当前城市名称
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
		 * ——————————————————————————————————————————————————————————————————
		 * 这里的AK和应用签名包名绑定，如果使用在自己的工程中需要替换为自己申请的Key
		 * ——————————————————————————————————————————————————————————————————
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

	// 设置相关参数
	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setServiceName("com.baidu.location.service_v2.9");
		option.setAddrType("all");
		option.setScanSpan(2000); // 设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位
		option.setPriority(LocationClientOption.NetWorkFirst); // 设置网络优先
		option.setPriority(LocationClientOption.GpsFirst); // 不设置，默认是gps优先

		option.disableCache(true);

		mLocationClient.setLocOption(option);
	}

	/**
	 * 监听函数，有更新位置的时候，格式化成字符串，输出到屏幕中
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
				 * 格式化显示地址信息
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

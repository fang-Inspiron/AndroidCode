package com.xrml.kuaican.net;

import java.util.HashMap;
import java.util.Map;

import com.xrml.kuaican.util.HttpUtils;
import com.xrml.kuaican.util.JSONUtil;

import android.os.Handler;
import android.os.Message;

/**
 * @author Administrator
 *	根据区域ID查询商品类别
 */
public class GetGoodsCategory extends Thread {

	private String areaId;
	private Handler handler;// 区域ID
	private String meal;

	public static final int NETWORK_ERROR = 600;
	public static final int SUCCESS = 601;

	public GetGoodsCategory(Handler handler, String areaId,String meal) {
		super();
		this.handler = handler;
		this.areaId = areaId;
		this.meal = meal;
	}
	@Override
	public void run() {
		Map<String, String> rawParams = new HashMap<String, String>();
		rawParams.put("areaId", areaId );
		rawParams.put("searchWay", meal);
		String result = HttpUtils.queryStringForPost(HttpUtils.GET_GOODS_CATE,
				rawParams);
		if (result == null) {
			handler.sendEmptyMessage(NETWORK_ERROR);
		} else {
			Map<String, Object> data = JSONUtil.getGoodsCatePageByCus(result);
			Message msg = Message.obtain();
			msg.what = SUCCESS;
			msg.obj = data;
			handler.sendMessage(msg);
		}
	}
}

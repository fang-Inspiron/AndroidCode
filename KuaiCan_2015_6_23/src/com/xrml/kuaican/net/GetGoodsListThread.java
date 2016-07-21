package com.xrml.kuaican.net;

import java.util.HashMap;
import java.util.Map;

import android.os.Handler;
import android.os.Message;

import com.xrml.kuaican.util.HttpUtils;
import com.xrml.kuaican.util.JSONUtil;

/**
 * @author Administrator
 *	得到某商品类别下的所有商品
 */
public class GetGoodsListThread extends Thread {
	
	private Handler handler;
	private int goodsCategoryId;
	private String searchWay;
	private int sortWay;
	private int pageNo;
	
	public static final int NETWORK_ERROR = 700;
	public static final int SUCCESS = 701;
	public static final int NO_GOODS = 703;
	
	public GetGoodsListThread(Handler handler, int goodsCategoryId,
			String searchWay, int sortWay, int pageNo) {
		super();
		this.handler = handler;
		this.goodsCategoryId = goodsCategoryId;
		this.searchWay = searchWay;
		this.sortWay = sortWay;
		this.pageNo = pageNo;
	}

	@Override
	public void run() {
		Map<String,String> rawParams = new HashMap<String,String>();
		rawParams.put("goodsCategoryId", goodsCategoryId+"");
		rawParams.put("searchWay", searchWay);
		rawParams.put("sortWay", sortWay+"");
		rawParams.put("pageNo", pageNo+"");
		String result = HttpUtils.queryStringForPost(HttpUtils.GET_GOODS_LIST, rawParams);
		if(result == null){
			handler.sendEmptyMessage(NETWORK_ERROR);
		}else if(result.length() < 30){
			Message msg = Message.obtain();
			msg.what = NO_GOODS;
			handler.sendMessage(msg);
		}else{
			Map<String,Object> data = JSONUtil.getGoodsListByCus(result);
			Message msg = Message.obtain();
			msg.what = SUCCESS;
			msg.obj = data;
			handler.sendMessage(msg);
		}
	}
}

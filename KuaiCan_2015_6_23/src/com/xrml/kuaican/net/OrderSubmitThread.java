package com.xrml.kuaican.net;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import android.os.Handler;
import android.os.Message;

import com.xrml.kuaican.util.HttpUtils;
import com.xrml.kuaican.util.JSONUtil;

public class OrderSubmitThread extends Thread {

	public static final int NETWORK_ERROR = 0;
	public static final int SUCCESS = 1;
	// 提交成功
	public static final String SUBMIT_OK = "24D340D7072786998EE2BC4532B1834A";
	// 对不起，有些商品不能配送
	public static final String SOMEGOODSNOT_NOT = "A5D1CDD1906AB3EBA74F4CE1577F66B7";
	// 请提前半小时预定
	public static final String PLEASEEARLY_NOT = "CD49CE843BDB2E7DED123FE1775A783";
	// 登录过期，请重试
	public static final String LOGIN_NOT = "F1F5FF7206C408B26AA1A2B63EDBAEF3";
	//网络错误，请重试
	public static final String NET_ERROR = "C732B67F7AD59A691E15B328A501508D";
	
	private Handler handler;
	private String areaId;
	private List<Map<String, Object>> cartListStr;
	private String userId;
	private String orderAddress;
	private String orderPhone;
	private String orderConsumeTime;
	private String orderWay;
	private String orderMore;
	private String alipayWay;
	private String USER418C5509E2171D55B0AEE5C2EA4442B5;

	
	
	public OrderSubmitThread(Handler handler, String areaId,
			List<Map<String, Object>> cartListStr, String userId,
			String orderAddress, String orderPhone, String orderConsumeTime,
			String orderWay, String orderMore, String alipayWay, String xIAOYAN) {
		super();
		this.handler = handler;
		this.areaId = areaId;
		this.cartListStr = cartListStr;
		this.userId = userId;
		this.orderAddress = orderAddress;
		this.orderPhone = orderPhone;
		this.orderConsumeTime = orderConsumeTime;
		this.orderWay = orderWay;
		this.orderMore = orderMore;
		this.alipayWay = alipayWay;
		USER418C5509E2171D55B0AEE5C2EA4442B5 = xIAOYAN;
	}



	@SuppressWarnings("unused")
	@Override
	public void run() {
		Map<String, String> rawParams = new HashMap<String, String>();
		JSONArray jsonArray = new JSONArray(cartListStr);
		String strb = "";
		if(cartListStr.size()>1){
			int i;
			strb += "[";
			for(i=0;i<cartListStr.size()-1;i++){
		
				strb += "{"+"'goodsId':"+cartListStr.get(i).get("goodsId")+","+"'goodsCount':"+cartListStr.get(i).get("goodsCount")+"}"+",";
			
			}
			strb += "{"+"'goodsId':"+cartListStr.get(i).get("goodsId")+","+"'goodsCount':"+cartListStr.get(i).get("goodsCount")+"}"+"]";
		}
		else
			strb = "["+"{"+"'goodsId':"+cartListStr.get(0).get("goodsId")+","+"'goodsCount':"+cartListStr.get(0).get("goodsCount")+"}"+"]";
	
		System.out.println("Str:" + strb);
		
		rawParams.put("cartListStr", strb);
		rawParams.put("userId", userId+"");
		rawParams.put("areaId", areaId+"");
		rawParams.put("orderAddress", orderAddress+"");
		rawParams.put("orderPhone", orderPhone+"");
		rawParams.put("orderConsumeTime", orderConsumeTime+"");
		rawParams.put("orderWay", orderWay+"");
		rawParams.put("orderMore", orderMore+"");
		rawParams.put("alipayWay", alipayWay+"");
		rawParams.put("USER418C5509E2171D55B0AEE5C2EA4442B5", USER418C5509E2171D55B0AEE5C2EA4442B5+"");
		for(int i=0;i<rawParams.size();i++){
			System.out.println();
		}
		String result = HttpUtils.queryStringForPost(HttpUtils.SUBMIT_ORDER,
				rawParams);
		if (result == null) {
			System.out.println("ERROR");
			handler.sendEmptyMessage(NETWORK_ERROR);
		} else if (result == HttpUtils.NETWORK_ERROR) {
			System.out.println("ERROR");
			handler.sendEmptyMessage(NETWORK_ERROR);
		} else {
			Map<String, Object> data = JSONUtil.getCustomerData(result);
			Message msg = Message.obtain();
			msg.what = SUCCESS;
			msg.obj = data;
			handler.sendMessage(msg);
		}
	}
}

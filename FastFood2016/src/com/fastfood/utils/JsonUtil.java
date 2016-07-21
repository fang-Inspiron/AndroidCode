package com.fastfood.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bean.GoodsBean;

public class JsonUtil {

	/**
	 * 1.获取验证码
	 * @param jsonStr
	 * 			:服务器返回JSON数据格式
	 * 			获取成功: {"response":"update_ok"}
	 * 			获取失败: {"response":"update_error"}
	 * @return
	 */
	public static Map<String, Object> getCode(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject obj = new JSONObject(jsonStr);
			map.put("response", obj.getString("response"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 2.用户注册
	 * @param jsonStr
	 * 			服务器返回的JSON数据为
	 * 			1.注册成功 {"response":"CBEA0ABDF6E393C33C540E604C8F4E8C"}
	 * 			2.手机号码已经被注册 {"response":"A3A2BA02F08301C857AFCFDFC192DF04"}
	 * 			3.网络错误请重新注册 {"response":"C732B67F7AD59A691E15B328A501508D"}
	 * 			4.对不起你无注册权限 {"response":"F1F5FF7206C408B26AA1A2B63EDBAEF3"}
	 * @return
	 */
	public static Map<String, Object> getRegisterResult(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject obj = new JSONObject(jsonStr);
			map.put("response", obj.getString("response"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 3.用户登录
	 * @param jsonStr
	 * 			服务器返回JSON数据 
	 * 			成功返回 {
	 *						"USER418C5509E2171D55B0AEE5C2EA4442B5":"26223A5B90699DF51950E01FD5C10EAB",
     *						"USER8E44F0089B076E18A718EB9CA3D94674":999999
	 *					 }
	 * 			失败返回 {"response":"0DBA67280F3E685EAED2290EDBC5783B"}
	 * @return
	 */
	public final static String  CHECK_STR = "USER418C5509E2171D55B0AEE5C2EA4442B5";
	public final static String USER_ID = "USER8E44F0089B076E18A718EB9CA3D94674";
	
	public static Map<String, Object> getLoginResult(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject obj = new JSONObject(jsonStr);
			map.put("checkStr", obj.get(CHECK_STR));
			map.put("userId", obj.get(USER_ID));
		} catch (JSONException e) {
			try {
				JSONObject obj = new JSONObject(jsonStr);
				map = new HashMap<String, Object>();
				map.put("response", obj.get("response"));
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		return map;
	}
	
	/**
	 * 7.用户查看某类别下的商品
	 * 
	 * @param jsonStr
	 *            :服务器返回的JSON数据格式 {"endPage":1, "len":20, "list":[{
	 *            "action":null, "freight":0.0, "goodsAddtime":null,
	 *            "goodsCategoryId":2, "goodsContent":"白菜+鸡蛋", "goodsId":1,
	 *            "goodsImg":null, "goodsName":"一荤一素", "goodsPrice":18.0,
	 *            "goodsSales":20, "goodsState":1, "isPeisong":1,"pageNo":0,
	 *            "proChance":0.0, "proCount":0, "proFlag":0, "proTime":null,
	 *            "searchKey":null, "searchWay":null, "sortWay":0} ],
	 *            "pageNo":1, "startIndex":0, "startPage":1, "totalPage":1,
	 *            "totalRecord":2}
	 * @return
	 */
	public static Map<String, Object> getGoodsListByCus(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject obj;
		try {
			obj = new JSONObject(jsonStr);
			map.put("endPage", obj.getInt("endPage"));
			map.put("len", obj.getInt("len"));
			map.put("pageNo", obj.getInt("pageNo"));
			map.put("startIndex", obj.getInt("startIndex"));
			map.put("startPage", obj.getInt("startPage"));
			map.put("totalPage", obj.getInt("totalPage"));
			map.put("totalRecord", obj.getInt("totalRecord"));
			List<GoodsBean> list = new ArrayList<GoodsBean>();
			JSONArray jsonArray = obj.getJSONArray("list");
			for (int i=0; i<jsonArray.length(); i++) {
				JSONObject o = jsonArray.getJSONObject(i);
				GoodsBean bean = new GoodsBean();
				bean.setFreight(Float.parseFloat(o.get("freight").toString()));
//				bean.setGoodsAddtime(o.getString("goodsAddTime"));
				bean.setGoodsCategoryId(o.getInt("goodsCategoryId"));
				bean.setGoodsContent(o.getString("goodsContent"));
				bean.setGoodsId(o.getString("goodsId"));
				bean.setGoodsImg(o.getString("goodsImg"));
				bean.setGoodsName(o.getString("goodsName"));
				bean.setGoodsPrice(Float.parseFloat(o.get("goodsPrice").toString()));
				bean.setGoodsSales(o.getInt("goodsSales"));
//				bean.setGoodsState(o.getString("goodsState"));
//				bean.setIsPeisong(o.getInt("isPeisong"));
//				bean.setPageNo(o.getInt("pageNo"));
//				bean.setProChance(Float.parseFloat(o.get("proChance").toString()));
//				bean.setProCount(o.getInt("proCount"));
//				bean.setProFlag(o.getInt("proFlag"));
//				bean.setProTime(o.getString("proTime"));
//				bean.setSearchKey(o.getString("searchKey"));
//				bean.setSearchWay(o.getString("searchWay"));
				bean.setBusinessPhone(o.getString("businessPhone"));
//				bean.setKeyWords(o.getString("keyWords"));
				bean.setUserId(o.getString("userId"));
				
//				JSONArray array = o.getJSONArray("gmList");
//				List<Map<String, Object>> gmList = new ArrayList<Map<String, Object>>();
//				
//				for (int j=0; j<array.length(); j++) {
//					Map<String, Object> oMap = new HashMap<String, Object>();
//					oMap.put("goodsId", array.getJSONObject(j).get("goodsId"));
//					oMap.put("goodsImg", array.getJSONObject(j).get("goodsImg"));
//					oMap.put("goodsImgId", array.getJSONObject(j).get("goodsImgId"));
//					oMap.put("imgDes", array.getJSONObject(j).get("imgDes"));
//					oMap.put("imgName", array.getJSONObject(j).get("imgName"));
//				
//					gmList.add(oMap);
//				}
//				bean.setGmList(gmList);
//				bean.setSortWay(o.getInt("sortWay"));
				list.add(bean);
				
			}
			map.put("foodList", list);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	/**
	 * 12.查看广告图片
	 * 
	 * @param jsonStr
	 *            :服务器返回JSON数据格式 {"endPage":1, "len":6, "list":[{ "adId":1,
	 *            "adImg":"\/img\/advertisement_1.jpg", "adName":"第一张",
	 *            "areaId":1}], "pageNo":1, "startIndex":0, "startPage":1,
	 *            "totalPage":1, "totalRecord":6}
	 * @return
	 */
	public static Map<String, Object> getAdPageResult(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject obj = new JSONObject(jsonStr);
			map.put("endPage", obj.getInt("endPage"));
			map.put("len", obj.getInt("len"));
			map.put("pageNo", obj.getInt("pageNo"));
			map.put("startIndex", obj.getInt("startIndex"));
			map.put("startPage", obj.getInt("startPage"));
			map.put("totalPage", obj.getInt("totalPage"));
			map.put("totalRecord", obj.getInt("totalRecord"));
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			JSONArray array = obj.getJSONArray("list");
			for (int i=0; i<array.length(); i++) {
				JSONObject o = array.getJSONObject(i);
				Map<String, Object> oMap = new HashMap<String, Object>();
				oMap.put("adId", o.getInt("adId"));
				oMap.put("adImg", o.getString("adImg"));
				oMap.put("adName", o.getString("adName"));
				oMap.put("areaId", o.getInt("areaId"));
				list.add(oMap);
			}
			map.put("list", list);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	//5、用户查询区域
	public static Map<String, Object> getUserLocation(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			JSONObject obj = new JSONObject(jsonStr);
			map.put("endPage", obj.getInt("endPage"));
			map.put("len", obj.getInt("len"));
			map.put("pageNo", obj.getInt("pageNo"));
			map.put("startIndex", obj.getInt("startIndex"));
			map.put("startPage", obj.getInt("startPage"));
			map.put("totalPage", obj.getInt("totalPage"));
			map.put("totalRecord", obj.getInt("totalRecord"));
			
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			JSONArray array = obj.getJSONArray("list");
			for (int i=0; i<array.length(); i++) {
				JSONObject o = array.getJSONObject(i);
				Map<String, Object> oMap = new HashMap<String, Object>();
				oMap.put("advanceTime", o.getString("advanceTime"));
				oMap.put("areaId", o.getInt("areaId"));
				oMap.put("areaName", o.getString("areaName"));
				oMap.put("cityName", o.getString("cityName"));
				oMap.put("complainPhone", o.getString("complainPhone"));
				oMap.put("des", o.getString("des"));
				oMap.put("orderConsumeTime", o.getString("orderConsumeTime"));
				list.add(oMap);
			}
			map.put("list", list);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	//8、加入购物车，计算总价运费和总价
	public static Map<String, Object> getShoppingCart(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
	
		try {
			JSONObject obj = new JSONObject(jsonStr);
			map.put("allGoodsPrice", Float.parseFloat(obj.get("allGoodsPrice").toString()));
			//map.put("cartListStr", obj.getString("cartListStr"));
			map.put("freight", obj.getDouble("freight"));
			map.put("totalPrice", Float.parseFloat(obj.get("totalPrice").toString()));
			
//			if (obj.has("orderItems") ) { 
//				if (obj.getJSONObject("orderItems") != null) {
//					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//					JSONArray array = obj.getJSONArray("orderItems");
//					for (int i=0; i<array.length(); i++) {
//						JSONObject o = array.getJSONObject(i);
//						Map<String, Object> oMap = new HashMap<String, Object>();
//						oMap.put("goodsCount", o.getInt("goodsCount"));
//						oMap.put("goodsId", o.getInt("goodsId"));
//						oMap.put("orderId", o.getInt("orderId"));
//						oMap.put("orderItemId", o.getInt("orderItemId"));
//						oMap.put("orderItemState", o.getInt("orderItemState"));
//						
//						list.add(oMap);
//					}
//					map.put("list", list);
//				}
//			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	/**
	 * 10. 获取订单列表
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, Object> getOrderListResult(String jsonStr) {
		Map<String, Object> map = null;
		try {
			map = new HashMap<String, Object>();
			JSONObject obj = new JSONObject(jsonStr);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			JSONArray array = obj.getJSONArray("list");
			for (int i = 0; i < array.length(); i++) {
				JSONObject o = array.getJSONObject(i);
				Map<String, Object> oMap = new HashMap<String, Object>();
				oMap.put("orderId", o.get("orderId"));
				oMap.put("orderTime", o.get("orderTime"));
				oMap.put("totalPrice", o.get("totalPrice"));
				oMap.put("goodsName", o.get("goodsName"));
				oMap.put("goodsCount", o.get("goodsCount"));
				oMap.put("goodsPrice", o.get("goodsPrice"));
				oMap.put("orderState", o.get("orderState"));
				oMap.put("orderMore", o.get("orderMore"));
				oMap.put("consumeTime", o.get("consumeTime"));
				list.add(oMap);
			}
			map.put("list", list);
		} catch (Exception e) {
			try {
				map = new HashMap<String, Object>();
				JSONObject obj = new JSONObject(jsonStr);
				map.put("response", obj.get("response"));
			} catch (Exception e1) {
				return map;
			}
		}
		return map;
	}
	
	// 11.修改订单状态
		public static Map<String, Object> changeOrderState(String jsonStr) {
			Map<String, Object> map = null;
			try {
				map = new HashMap<String, Object>();
				JSONObject o = new JSONObject(jsonStr);
				map.put("response", o.getString("response"));
			} catch (JSONException e) {
				return map;
				// e.printStackTrace();
			}
			return map;
		}
}

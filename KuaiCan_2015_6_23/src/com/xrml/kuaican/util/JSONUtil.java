package com.xrml.kuaican.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Nemo
 *
 */
/**
 * @author Nemo
 * 
 */
public class JSONUtil {
	/**
	 * 1.��ȡ��֤��
	 * 
	 * @param jsonStr
	 *            :����������JSON���ݸ�ʽ ��ȡ�ɹ�: {"response":"update_ok"} ��ȡʧ��:
	 *            {"response":"update_error"}
	 * @return
	 */
	public static Map<String, Object> getCode(String jsonStr) {
		Map<String, Object> map = null;
		try {
			JSONObject obj = new JSONObject(jsonStr);
			map = new HashMap<String, Object>();
			map.put("response", obj.get("response"));
		} catch (Exception e) {
			map = new HashMap<String, Object>();
			map.put("response", "dontKnow");
			return map;
		}
		return map;
	}

	/**
	 * 2.�û�ע��
	 * 
	 * @param jsonStr
	 *            ���������ص�JSON����Ϊ 1.ע��ɹ�
	 *            {"response":"CBEA0ABDF6E393C33C540E604C8F4E8C"} 2.�ֻ������Ѿ���ע��
	 *            {"response":"A3A2BA02F08301C857AFCFDFC192DF04"} 3.�������������ע��
	 *            {"response":"C732B67F7AD59A691E15B328A501508D"} 4.�Բ�������ע��Ȩ��
	 *            {"response":"F1F5FF7206C408B26AA1A2B63EDBAEF3"}
	 * @return
	 */
	public static Map<String, Object> getRegistResult(String jsonStr) {
		Map<String, Object> map = null;
		try {
			JSONObject obj = new JSONObject(jsonStr);
			map = new HashMap<String, Object>();
			map.put("response", obj.get("response"));
		} catch (Exception e) {
			map = new HashMap<String, Object>();
			map.put("response", "dontKnow");
			return map;
		}
		return map;
	}

	/**
	 * 3.�û���¼
	 * 
	 * @param jsonStr
	 *            ����������JSON���� �ɹ����� { "USER418C5509E2171D55B0AEE5C2EA4442B5":
	 *            "26223A5B90699DF51950E01FD5C10EAB",
	 *            "USER8E44F0089B076E18A718EB9CA3D94674":999999 } ʧ�ܷ���
	 *            {"response":"0DBA67280F3E685EAED2290EDBC5783B"}
	 * @return
	 */
	private final static String CHECK_STR = "USER418C5509E2171D55B0AEE5C2EA4442B5";
	private final static String USER_ID = "USER8E44F0089B076E18A718EB9CA3D94674";

	// �����ɹ�����
	public static Map<String, Object> getLoginResult(String jsonStr) {
		Map<String, Object> map = null;
		try {
			JSONObject obj = new JSONObject(jsonStr);
			map = new HashMap<String, Object>();
			map.put("checkStr", obj.get(CHECK_STR));
			map.put("userId", obj.get(USER_ID));
		} catch (Exception e) {
			try {
				JSONObject obj = new JSONObject(jsonStr);
				map = new HashMap<String, Object>();
				map.put("response", obj.get("response"));
			} catch (Exception e2) {
				return map;
			}
		}
		return map;
	}

	/**
	 * 4.��ѯ���г���
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, Object> getAllCity(String jsonStr) {
		Map<String, Object> map = null;
		try {
			map = new HashMap<String, Object>();
			JSONObject obj = new JSONObject(jsonStr);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			JSONArray array = obj.getJSONArray("list");
			for (int i = 0; i < array.length(); i++) {
				Map<String, Object> oMap = new HashMap<String, Object>();
				JSONObject o = array.getJSONObject(i);
				oMap.put("cityId", o.get("cityId"));
				oMap.put("cityName", o.get("cityName"));
				list.add(oMap);
			}
			map.put("list", list);
		} catch (Exception e) {
			try {
				JSONObject obj = new JSONObject(jsonStr);
				map = new HashMap<String, Object>();
				map.put("response", obj.get("response"));
			} catch (JSONException e1) {
				return map;
			}
		}
		return map;
	}

	/**
	 * 5.��ѯ��������
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, Object> getAllArea(String jsonStr) {
		Map<String, Object> map = null;
		try {
			map = new HashMap<String, Object>();
			JSONObject obj = new JSONObject(jsonStr);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			JSONArray array = obj.getJSONArray("list");
			for (int i = 0; i < array.length(); i++) {
				Map<String, Object> oMap = new HashMap<String, Object>();
				JSONObject o = array.getJSONObject(i);
				oMap.put("areaId", o.get("areaId"));
				oMap.put("areaName", o.get("areaName"));
				oMap.put("cityName", o.get("cityName"));
				oMap.put("complainPhone", o.get("complainPhone"));
				oMap.put("des", o.get("des"));
				oMap.put("orderConsumeTime", o.get("orderConsumeTime"));
				list.add(oMap);
			}
			map.put("list", list);
		} catch (Exception e) {
			try {
				JSONObject obj = new JSONObject(jsonStr);
				map = new HashMap<String, Object>();
				map.put("response", obj.get("response"));
			} catch (JSONException e1) {
				return map;
			}
		}
		return map;
	}

	/**
	 * 6.�û�����������Ʒ���
	 * 
	 * @param jsonStr
	 *            :���������ص�JSON���ݸ�ʽ {"endPage":1, "len":10, "list":[{ "areaId":1,
	 *            "goodsCategoryId":1, "goodsCategoryName":"���ʲ�"} ], "pageNo":1,
	 *            "startIndex":0, "startPage":1, "totalPage":1, "totalRecord":2}
	 * @return
	 */
	public static Map<String, Object> getGoodsCatePageByCus(String jsonStr) {
		Map<String, Object> map = null;
		try {
			JSONObject obj = new JSONObject(jsonStr);
			map = new HashMap<String, Object>();
			map.put("pageNo", obj.getInt("pageNo"));
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			JSONArray arr = obj.getJSONArray("list");
			for (int i = 0; i < arr.length(); i++) {
				JSONObject o = arr.getJSONObject(i);
				Map<String, Object> oMap = new HashMap<String, Object>();
				oMap.put("areaId", o.get("areaId"));
				oMap.put("goodsCategoryId", o.get("goodsCategoryId"));
				oMap.put("goodsCategoryName", o.get("goodsCategoryName"));
				list.add(oMap);
			}
			map.put("list", list);

		} catch (Exception e) {
			// e.printStackTrace();
			return null;

		}
		return map;
	}

	/**
	 * 7.�û��鿴ĳ����µ���Ʒ
	 * 
	 * @param jsonStr
	 *            :���������ص�JSON���ݸ�ʽ {"endPage":1, "len":20, "list":[{
	 *            "action":null, "freight":0.0, "goodsAddtime":null,
	 *            "goodsCategoryId":2, "goodsContent":"�ײ�+����", "goodsId":1,
	 *            "goodsImg":null, "goodsName":"һ��һ��", "goodsPrice":18.0,
	 *            "goodsSales":20, "goodsState":1, "isPeisong":1,"pageNo":0,
	 *            "proChance":0.0, "proCount":0, "proFlag":0, "proTime":null,
	 *            "searchKey":null, "searchWay":null, "sortWay":0} ],
	 *            "pageNo":1, "startIndex":0, "startPage":1, "totalPage":1,
	 *            "totalRecord":2} ʧ�ܷ��� {"response":"no_goodsCate"}
	 * @return
	 */
	public static Map<String, Object> getGoodsListByCus(String jsonStr) {
		Map<String, Object> map = null;
		try {
			JSONObject obj = new JSONObject(jsonStr);
			map = new HashMap<String, Object>();
			map.put("pageNo", obj.getInt("pageNo"));
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			JSONArray array = obj.getJSONArray("list");
			for (int i = 0; i < array.length(); i++) {
				JSONObject o = array.getJSONObject(i);
				Map<String, Object> oMap = new HashMap<String, Object>();
				oMap.put("goodsCategoryId", o.getInt("goodsCategoryId"));
				oMap.put("goodsContent", o.get("goodsContent"));
				oMap.put("goodsId", o.get("goodsId"));
				oMap.put("goodsImg", o.get("goodsImg"));
				oMap.put("goodsName", o.get("goodsName"));
				oMap.put("goodsPrice", o.get("goodsPrice"));
				oMap.put("goodsSales", o.get("goodsSales"));
				oMap.put("goodsState", o.get("goodsState"));
				oMap.put("businessPhone", o.get("businessPhone"));
				oMap.put("gmList", o.get("gmList"));
				list.add(oMap);
			}
			map.put("list", list);
		} catch (Exception e) {
			try {
				map = new HashMap<String, Object>();
				JSONObject obj = new JSONObject(jsonStr);
				map.put("response", obj.get("response"));
			} catch (Exception e2) {
				return map;
				// e.printStackTrace();
			}
		}
		return map;
	}

	/**
	 * 8.�û��޸�����
	 * 
	 * @param jsonStr
	 *            :���������ص�JSON���ݸ�ʽ
	 *            1.ԭ�������{"response":"40885C152C1BA6F5D1D20A06CBAB7DFA"} 2.�޸ĳɹ�
	 *            ��{"response":"update_ok"} 3.��½ʧЧ�����µ�½
	 *            ��{"response":"F1F5FF7206C408B26AA1A2B63EDBAEF3"}
	 * @return
	 */
	public static Map<String, Object> getChangePasswordResult(String jsonStr) {
		Map<String, Object> map = null;
		try {
			map = new HashMap<String, Object>();
			JSONObject o = new JSONObject(jsonStr);
			map.put("response", o.get("response"));
		} catch (JSONException e) {
			return map;
			// e.printStackTrace();
		}
		return map;
	}

	/**
	 * 12.�鿴���ͼƬ
	 * 
	 * @param jsonStr
	 *            :����������JSON���ݸ�ʽ {"endPage":1, "len":6, "list":[{ "adId":1,
	 *            "adImg":"\/img\/advertisement_1.jpg", "adName":"��һ��",
	 *            "areaId":1}], "pageNo":1, "startIndex":0, "startPage":1,
	 *            "totalPage":1, "totalRecord":6}
	 * @return
	 */
	public static Map<String, Object> getAdPageResult(String jsonStr) {
		Map<String, Object> map = null;
		try {
			map = new HashMap<String, Object>();
			JSONObject obj = new JSONObject(jsonStr);
			// List<Map<String, Object>> list = new ArrayList<Map<String,
			// Object>>();
			JSONArray arry = obj.getJSONArray("list");
			for (int i = 0; i < arry.length(); i++) {
				JSONObject o = arry.getJSONObject(i);
				// Map<String, Object> oMap = new HashMap<String, Object>();
				map.put("adImg" + i, o.get("adImg"));
				// list.add(oMap);
			}
			// map.put("list", list);
		} catch (Exception e) {
			return map;
		}
		return map;
	}

	/**
	 * 10.���빺�ﳵ�������ܼ��˷Ѻ��ܼ�
	 * 
	 * @param jsonStr
	 *            �������ͣ�json {"allGoodsPrice":58.0, "cartListStr":" ",
	 *            "freight":5.800000000000001, "orderItems":[{ "goodsCount":1,
	 *            "goodsId":1, "orderId":0, "orderItemId":0,
	 *            "orderItemState":0}], "totalPrice":63.8}
	 * 
	 * @return
	 */
	public static Map<String, Object> getDataShoppingCart(String jsonStr) {
		Map<String, Object> map = null;
		try {
			map = new HashMap<String, Object>();
			JSONObject object = new JSONObject(jsonStr);
			map.put("freight", object.get("freight"));
			map.put("allGoodsPrice", object.get("allGoodsPrice"));
			map.put("totalPrice", object.get("totalPrice"));
			System.out
					.println("JsonUtils-->ShoppingCart()-->" + map.toString());
		} catch (JSONException e) {
			// e.printStackTrace();
			return null;

		}
		return map;
	}

	/**
	 * 11.�����û�����������ύ����
	 * 
	 * @param jsonStr
	 * 
	 * @return
	 */
	public static Map<String, Object> getCustomerData(String jsonStr) {
		Map<String, Object> map = null;
		try {
			map = new HashMap<String, Object>();
			JSONObject object = new JSONObject(jsonStr);
			map.put("response", object.get("response"));
		} catch (JSONException e) {
			// e.printStackTrace();
			return null;

		}
		return map;
	}

	/**
	 * 14.�鿴�û���ַ
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, Object> getUserAddressData(String jsonStr) {
		Map<String, Object> map = null;
		try {
			JSONObject object = new JSONObject(jsonStr);
			map = new HashMap<String, Object>();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			JSONArray array = object.getJSONArray("list");
			for (int i = 0; i < array.length(); i++) {
				JSONObject o = array.getJSONObject(i);
				Map<String, Object> oMap = new HashMap<String, Object>();
				oMap.put("flag", o.get("flag"));
				oMap.put("orderAddress", o.get("orderAddress"));
				oMap.put("orderPhone", o.get("orderPhone"));
				oMap.put("receiveId", o.get("receiveId"));
				oMap.put("userId", o.get("userId"));
				oMap.put("userName", o.get("userName"));
				list.add(oMap);
			}
			map.put("list", list);
		} catch (Exception e) {
			try {
				map = new HashMap<String, Object>();
				JSONObject obj = new JSONObject(jsonStr);
				map.put("response", obj.getString("response"));
			} catch (JSONException e1) {
				return map;
				// e1.printStackTrace();
			}

		}
		return map;
	}

	/**
	 * 16.�޸��û���ַ
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, Object> getChangeUserAddress(String jsonStr) {
		Map<String, Object> map = null;
		try {
			map = new HashMap<String, Object>();
			JSONObject object = new JSONObject(jsonStr);
			map.put("response", object.get("response"));
		} catch (JSONException e) {
			// e.printStackTrace();
			return null;

		}
		return map;
	}

	/**
	 * 17.ɾ���û���ַ
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, Object> getDeletedUserAddress(String jsonStr) {
		Map<String, Object> map = null;
		try {
			map = new HashMap<String, Object>();
			JSONObject object = new JSONObject(jsonStr);
			map.put("response", object.get("response"));
		} catch (JSONException e) {
			// e.printStackTrace();
			return null;

		}
		return map;
	}

	/**
	 * 15. ������ַ
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, Object> newAddressResult(String jsonStr) {
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

	/**
	 * 10. ��ȡ�����б�
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

	// 11.�޸Ķ���״̬
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

	/**
	 * 19.�û�����
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, Object> getSuggessed(String jsonStr) {
		Map<String, Object> map = null;
		try {
			map = new HashMap<String, Object>();
			JSONObject obj = new JSONObject(jsonStr);
			map.put("response", obj.get("response"));
		} catch (JSONException e) {
			return map;
			// e.printStackTrace();
		}
		return map;
	}

	/**
	 * ֻ����response������
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, Object> getResponseResult(String jsonStr) {
		Map<String, Object> map = null;
		try {
			map = new HashMap<String, Object>();
			JSONObject obj = new JSONObject(jsonStr);
			map.put("response", obj.get("response"));
		} catch (Exception e) {
			return map;
			// e.printStackTrace();
		}
		return map;
	}

	/**
	 * ����gmList�е�����
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, String> getGmList(String jsonStr) {
		Map<String, String> map = null;
		try {
			map = new HashMap<String, String>();
			JSONArray array = new JSONArray(jsonStr);
			for (int i = 0; i < array.length(); i++) {
				JSONObject o = array.getJSONObject(i);
				map.put("goodsImg" + i, o.get("goodsImg").toString());
			}

		} catch (Exception e) {
			return map;
		}
		return map;
	}
}

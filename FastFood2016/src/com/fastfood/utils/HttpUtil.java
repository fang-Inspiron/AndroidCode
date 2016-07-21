package com.fastfood.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpUtil {

	static String str = "http://222.24.63.104:8080";
	
	// �������
	public static final String NETWORK_ERROR = "network_error";
	// 1����ȡ��֤��
	public static final String VERIFICATION_CODE = str+"/Ordering/customer/User_getCode";
	// 2���û�ע��
	public static final String REGISTER_CODE = str+"/Ordering/customer/0226BF4A2BE77367E8153CA7109B4DB3";
	// 3���û���¼
	public static final String USER_LOGIN = str+"/Ordering/customer/D6488C8133052CAA3714E1DCB1E5F32F";
	// 18���û��޸�����
	public static final String CHANGE_PASSWORD = str+"Ordering/customer/D896F91BD8655FA6C997777F84B4247C";
	// 7���û��̻��鿴��Ʒ
	public static final String GET_GOODS = str+"/Ordering/customer/Goods_getGoodsPageByCus";
	// 12���鿴���ͼƬ
	public static final String GET_ADPAGE = str+"/Ordering/customer/Ad_getAdPageByCus";
	// 5���û���ѯ����
	public static final String SEARCH_LOCATION = str+"/Ordering/customer/Area_getAreaPageByCus";
	// 8�����빺�ﳵ�������ܼ��˷Ѻ��ܼ�
	public static final String SHOPPING_CART = str+"/Ordering/customer/Cart_add";
	// �鿴�����б�
	public static final String GEt_ORDER_LIST = str+"/Ordering/customer/03CE22C822F19D4056D2D363E4E690B3";
	// �޸Ķ���״̬
	public static final String CHANGE_ORDER_STATE = str+"/Ordering/customer/8133683BFB6A3C4818F587C6FC0B9A8A";
	
	
	/*
	 * ����POST���������ַ����ķ���
	 * 
	 * @param url (����ĵ�ַ)
	 * 
	 * @param params (POST�������)
	 * 
	 * @return ������
	 */
	public static String queryStringForPost(String url,
			Map<String, String> rawParams) {
		HttpPost request = new HttpPost(url);
		List<NameValuePair> params = null;
		if (rawParams != null) {
			params = new ArrayList<NameValuePair>();
			for (String key : rawParams.keySet()) {
				params.add(new BasicNameValuePair(key, rawParams.get(key)));
			}
		}
		try {
			if (params != null) {
				request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String result = null;
		try {
			HttpResponse response = new DefaultHttpClient().execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
//				System.out.println("requese successful");
				result = EntityUtils.toString(response.getEntity(), "utf-8");
//			    System.out.println("HttpUtil���ص�result----->" + result);
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = HttpUtil.NETWORK_ERROR;
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = HttpUtil.NETWORK_ERROR;
			return result;
		}
		return null;
	}

}

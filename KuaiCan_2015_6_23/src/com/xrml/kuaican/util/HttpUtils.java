package com.xrml.kuaican.util;

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
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpUtils {

	public static final String NETWORK_ERROR = "network_error";
	// ɾ���û���ַ
	public static final String DELETE_USER_ADDRESS = "http://115.159.94.74/Ordering/customer/ReceiveInfo_delete";
	// �޸��û���ַ
	public static final String CHANGE_USER_ADDRESS = "http://115.159.94.74/Ordering/customer/ReceiveInfo_update";
	// �鿴�û���ַ
	public static final String LOOK_USER_ADDRESS = "http://115.159.94.74/Ordering/customer/ReceiveInfo_getReceiveInfoPageByCus";
	// �ύ����
	public static final String SUBMIT_ORDER = "http://115.159.94.74/Ordering/customer/BB8843D418339365B0A4615571670F31";
	// �ύ���ﳵ
	public static final String SEND_GOODS_CART = "http://115.159.94.74/Ordering/customer/Cart_add";
	// �鿴ĳ���̼ҵ���Ʒ�б��URL
	public static final String GET_GOODS_LIST = "http://115.159.94.74/Ordering/customer/Goods_getGoodsPageByCus";
	// �û��޸�����
	public static final String CHANGE_PASSWORD = "http://115.159.94.74/Ordering/customer/D896F91BD8655FA6C997777F84B4247C";
	// �鿴���URL
	public static final String GET_ADPAGE = "http://115.159.94.74/Ordering/customer/Ad_getAdPageByCus";
	// ��������鿴��Ʒ���
	public static final String GET_GOODS_CATE = "http://115.159.94.74/Ordering/customer/GoodsCate_getGoodsCatePageByCus";
	// ��ȡע����֤��
	public static final String GET_CODE = "http://115.159.94.74/Ordering/customer/User_getCode";
	// �û�ע��
	public static final String USER_REGISTER = "http://115.159.94.74/Ordering/customer/0226BF4A2BE77367E8153CA7109B4DB3";
	// �û���¼
	public static final String USER_LOGIN = "http://115.159.94.74/Ordering/customer/D6488C8133052CAA3714E1DCB1E5F32F";
	// ��������
	public static final String RESET_PSD = "http://115.159.94.74/Ordering/customer/User_reSetCode";
	// �鿴�����б�
	public static final String GEt_ORDER_LIST = "http://115.159.94.74/Ordering/customer/03CE22C822F19D4056D2D363E4E690B3";
	// �޸Ķ���״̬
	public static final String CHANGE_ORDER_STATE = "http://115.159.94.74/Ordering/customer/8133683BFB6A3C4818F587C6FC0B9A8A";
	// �����ջ���ַ
	public static final String NEW_ADDRESS = "http://115.159.94.74/Ordering/customer/ReceiveInfo_add";
	// �û�����
	public static final String USER_SUGGESSED = "http://115.159.94.74/Ordering/customer/Suggestion_add";
	// �û���ѯ���г���
	public static final String FIND_ALL_CITY = "http://115.159.94.74/Ordering/customer/City_getCityPageByCus";
	// �û���ѯ����
	public static final String FIND_ALL_AREA = "http://115.159.94.74/Ordering/customer/Area_getAreaPageByCus";
	// ������Ʒ
	public static final String ADD_ORDER = "http://115.159.94.74/Ordering/customer/Goods_add";

	/**
	 * ����POST���������ַ����ķ���
	 * 
	 * @param url
	 *            (����ĵ�ַ)
	 * @param params
	 *            (POST�������)
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
			// e1.printStackTrace();
		}
		String result = null;
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 10 * 1000);
		HttpConnectionParams.setSoTimeout(httpParams, 10 * 1000);
		try {
			HttpResponse response = new DefaultHttpClient(httpParams)
					.execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				System.out.println("requese successful");
				result = EntityUtils.toString(response.getEntity(), "utf-8");
				System.out.println("result----->" + result);
				return result;
			}
		} catch (ClientProtocolException e) {
			// e.printStackTrace();
			result = HttpUtils.NETWORK_ERROR;
			return result;
		} catch (IOException e) {
			// e.printStackTrace();
			result = HttpUtils.NETWORK_ERROR;
			return result;
		}
		return null;
	}
}

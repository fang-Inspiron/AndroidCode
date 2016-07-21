package com.qlf.plants.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


public class HttpUtils {
	//用户注册
	public static String REGISTER = "http://120.25.123.101:8080/sprout/user/register";
	//用户登陆
	public static String LOGIN  = "http://120.25.123.101:8080/sprout/user/login";
	//添加终端
	public static String ADD_TERMINAL = "http://120.25.123.101:8080/sprout/terminal/";
	//终端列表
	public static String TERMINAL_LIST = "http://120.25.123.101:8080/sprout/terminal/{userId}/list";
	//删除终端
	public static String DELETE_TERMINAL = "http://120.25.123.101:8080/sprout/terminal/{terminalId}/delete/";
	//修改终端
	public static String MOTIFY_TERMINAL = "http://120.25.123.101:8080/sprout/terminal/{terminalId}/update/";
	//终端信息
	public static String TERMINAL = "http://120.25.123.101:8080/sprout/terminal/{terminalId}";
	//查看历史信息
	public static String HISTORY_INFO = "http://120.25.123.101:8080/sprout/historyInfo/{terminalId}";
	//上传头像
	public static String UPLOAD_HEAD = "http://120.25.123.101:8080/sprout/upload/";
	
	
	public static String BU = " http://192.168.1.114:8080/terminal/";
	
	

	public static final String NETWORK_ERROR = "network_error";
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
				System.out.println("requese successful");
				result = EntityUtils.toString(response.getEntity(), "utf-8");
				System.out.println("result----->" + result);
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = HttpUtils.NETWORK_ERROR;
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = HttpUtils.NETWORK_ERROR;
			return result;
		}
		return null;
	}
	
	// 请求网页、返回json字符串
		public static String queryStringForGet(String url) {
			HttpGet request = new HttpGet(url);
			String result = null;
			try {
				HttpResponse response = new DefaultHttpClient().execute(request);
				if (response.getStatusLine().getStatusCode() == 200) {
					System.out.println("requese successful");
					result = EntityUtils.toString(response.getEntity(), "utf-8");
					System.out.println("result----->" + result);
					return result;
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				result = HttpUtils.NETWORK_ERROR;
				return result;
			} catch (IOException e) {
				e.printStackTrace();
				result = HttpUtils.NETWORK_ERROR;
				return result;
			}
			return null;
		}
}

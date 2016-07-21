package com.data;

import java.util.HashMap;
import java.util.Map;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserData {

	/**
	 * 保存用户名和密码在本地
	 * @param username
	 * @param password
	 */
	public static void saveUserInfo(Context context, Map<String, String> map) {
		//获得SharedPreferences对象
		SharedPreferences preferences = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("username", map.get("username").toString());
		editor.putString("password", map.get("password").toString());
		editor.commit();
	}

	/**
	 * 获取存在本地的用户名和密码
	 * @return
	 */
	public static  Map<String, Object> getUserInfo(Context context) {
		Map<String, Object> params = new HashMap<String, Object>();
		if(!context.getSharedPreferences("userinfo", Context.MODE_PRIVATE).contains("username")){
			return null;
		}
		SharedPreferences preferences = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		params.put("username", preferences.getString("username", ""));
		params.put("password", preferences.getString("password", ""));
		return params;
	}
	
	/**
	 * 保存获取到的essayId在本地
	 * @param essayId
	 */
	public static void saveEssayIdInfo(Context context, Map<String, String> map) {
		//获得SharedPreferences对象
		SharedPreferences preferences = context.getSharedPreferences("essayidinfo", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("essayId", map.get("essayId").toString());
		editor.commit();
	}
	/**
	 * 保存存在本地的essayId
	 * @param essayId
	 */
	public static Map<String, String> getEssayIdInfo(Context context) {
		Map<String, String> params = new HashMap<String, String>();
		if (!context.getSharedPreferences("essayidinfo", Context.MODE_PRIVATE).contains("essayId")) {
			return null;
		}
		SharedPreferences preferences = context.getSharedPreferences("essayidinfo", Context.MODE_PRIVATE);
		params.put("essayId", preferences.getString("essayId", ""));
		return params;
	}

}

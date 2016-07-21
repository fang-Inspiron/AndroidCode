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
	public static void saveUserInfo(Context context, Map<String, Object> map) {
		//获得SharedPreferences对象
		SharedPreferences preferences = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("userId", map.get("userId").toString());
		editor.putString("check", map.get("check").toString());
		editor.commit();
	}

	/**
	 * 获取存在本地的用户名和密码
	 * @return
	 */
	public static  Map<String, Object> getUserInfo(Context context) {
		Map<String, Object> params = new HashMap<String, Object>();
		if(!context.getSharedPreferences("userinfo", Context.MODE_PRIVATE).contains("userId")){
			return null;
		}
		SharedPreferences preferences = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		params.put("userId", preferences.getString("userId", ""));
		params.put("check", preferences.getString("check", ""));
		return params;
	}

}

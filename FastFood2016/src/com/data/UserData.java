package com.data;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserData {

	/**
	 * �����û����������ڱ���
	 * @param username
	 * @param password
	 */
	public static void saveUserInfo(Context context, Map<String, Object> map) {
		//���SharedPreferences����
		SharedPreferences preferences = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("userId", map.get("userId").toString());
		editor.putString("check", map.get("check").toString());
		editor.commit();
	}

	/**
	 * ��ȡ���ڱ��ص��û���������
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

package com.qlf.plants.userdata;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserData {
	private Context context;

	public UserData(Context context) {
		this.context = context;
	}
	

	/**
	 * 保存用户名和密码在本地
	 * @param username
	 * @param password
	 */
	public void saveUserInfo(Map<String, Object> map) {
		//获得SharedPreferences对象
		SharedPreferences preferences = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("userName", map.get("userName").toString());
		editor.putString("phone", map.get("phone").toString());
		editor.putString("password", map.get("password").toString());
		editor.putString("email", map.get("email").toString());
		editor.putInt("userId", Integer.parseInt(map.get("userId").toString()));
		editor.putString("userImg", map.get("userImg").toString());
		editor.putString("certificate", map.get("certificate").toString());
		editor.commit();
	}

	/**
	 * 获取存在本地的用户名和密码
	 * @return
	 */
	public Map<String, Object> getUserInfo() {
		Map<String, Object> params = new HashMap<String, Object>();
		if(!context.getSharedPreferences("userinfo", Context.MODE_PRIVATE).contains("userName")){
			return null;
		}
		SharedPreferences preferences = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		params.put("userName", preferences.getString("userName", ""));
		params.put("password", preferences.getString("password", ""));
		params.put("phone", preferences.getString("phone", ""));
		params.put("email", preferences.getString("email", ""));
		params.put("userId", preferences.getInt("userId", 0));
		params.put("userImg", preferences.getString("userImg", ""));
		params.put("certificate", preferences.getString("certificate", ""));
		return params;
	}
	
	/**
	 * 保存背景图片
	 * @param drawable
	 */
	public void saveBackground(int drawable) {
		//获得SharedPreferences对象
		SharedPreferences preferences = context.getSharedPreferences("background", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putInt("backgroundid", drawable);
		editor.commit();
	}

	/**
	 * 获取存在本地的背景图片
	 * @return
	 */
	public Map<String, String> getBackground() {
		Map<String, String> params = new HashMap<String, String>();
		if(!context.getSharedPreferences("background", Context.MODE_PRIVATE).contains("backgroundid")){
			System.out.println("as");
			return null;
		}
		SharedPreferences preferences = context.getSharedPreferences("background", Context.MODE_PRIVATE);
		params.put("backgroundid", String.valueOf(preferences.getInt("backgroundid", 0)));
		return params;
	}
	
	/**
	 * 保存用户昵称和签名
	 * @param username
	 * @param sign
	 */
	public void saveUserNameAndSign(String username,String sign) {
		//获得SharedPreferences对象
		SharedPreferences preferences = context.getSharedPreferences("usernameandsign", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("username", username);
		editor.putString("sign", sign);
		editor.commit();
	}

	/**
	 * 获取存在本地的昵称和签名
	 * @return
	 */
	public Map<String, String> getUserNameAndSign() {
		Map<String, String> params = new HashMap<String, String>();
		if(!context.getSharedPreferences("usernameandsign", Context.MODE_PRIVATE).contains("username")){
			return null;
		}
		SharedPreferences preferences = context.getSharedPreferences("usernameandsign", Context.MODE_PRIVATE);
		params.put("username", preferences.getString("username", ""));
		params.put("sign", preferences.getString("sign", ""));
		return params;
	}
	
	
	
	
	public void saveWarning1(boolean w) {
		//获得SharedPreferences对象
		SharedPreferences preferences = context.getSharedPreferences("warning1", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		System.out.println("w:"+w);
		editor.putBoolean("warn1", w);
		editor.commit();
	}
	public Map<String, Boolean> getWarning1() {
		Map<String, Boolean> params = new HashMap<String, Boolean>();
		if(!context.getSharedPreferences("warning1", Context.MODE_PRIVATE).contains("warn1")){
			return null;
		}
		SharedPreferences preferences = context.getSharedPreferences("warning1", Context.MODE_PRIVATE);
		params.put("warn1", preferences.getBoolean("warn1", false));
		return params;
	}
	
	public void saveWarning2(boolean w) {
		//获得SharedPreferences对象
		SharedPreferences preferences = context.getSharedPreferences("warning2", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean("warn2", w);
		editor.commit();
	}
	public Map<String, Boolean> getWarning2() {
		Map<String, Boolean> params = new HashMap<String, Boolean>();
		if(!context.getSharedPreferences("warning2", Context.MODE_PRIVATE).contains("warn2")){
			return null;
		}
		SharedPreferences preferences = context.getSharedPreferences("warning2", Context.MODE_PRIVATE);
		params.put("warn2", preferences.getBoolean("warn2", false));
		return params;
	}
	public void saveWarning3(boolean w) {
		//获得SharedPreferences对象
		SharedPreferences preferences = context.getSharedPreferences("warning3", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean("warn3", w);
		editor.commit();
	}
	public Map<String, Boolean> getWarning3() {
		Map<String, Boolean> params = new HashMap<String, Boolean>();
		if(!context.getSharedPreferences("warning3", Context.MODE_PRIVATE).contains("warn3")){
			return null;
		}
		SharedPreferences preferences = context.getSharedPreferences("warning3", Context.MODE_PRIVATE);
		params.put("warn3", preferences.getBoolean("warn3", false));
		return params;
	}
	public void saveWarning4(boolean w) {
		//获得SharedPreferences对象
		SharedPreferences preferences = context.getSharedPreferences("warning4", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean("warn4", w);
		editor.commit();
	}
	public Map<String, Boolean> getWarning4() {
		Map<String, Boolean> params = new HashMap<String, Boolean>();
		if(!context.getSharedPreferences("warning4", Context.MODE_PRIVATE).contains("warn4")){
			return null;
		}
		SharedPreferences preferences = context.getSharedPreferences("warning4", Context.MODE_PRIVATE);
		params.put("warn4", preferences.getBoolean("warn4", false));
		return params;
	}
	public void saveWarning5(boolean w) {
		//获得SharedPreferences对象
		SharedPreferences preferences = context.getSharedPreferences("warning5", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean("warn5", w);
		editor.commit();
	}
	public Map<String, Boolean> getWarning5() {
		Map<String, Boolean> params = new HashMap<String, Boolean>();
		if(!context.getSharedPreferences("warning5", Context.MODE_PRIVATE).contains("warn5")){
			return null;
		}
		SharedPreferences preferences = context.getSharedPreferences("warning5", Context.MODE_PRIVATE);
		params.put("warn5", preferences.getBoolean("warn5", false));
		return params;
	}
	
	public void saveMessage(boolean w) {
		//获得SharedPreferences对象
		SharedPreferences preferences = context.getSharedPreferences("message", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean("message", w);
		editor.commit();
	}
	public Map<String, Boolean> getMessage() {
		Map<String, Boolean> params = new HashMap<String, Boolean>();
		if(!context.getSharedPreferences("message", Context.MODE_PRIVATE).contains("message")){
			return null;
		}
		SharedPreferences preferences = context.getSharedPreferences("message", Context.MODE_PRIVATE);
		params.put("message", preferences.getBoolean("message", false));
		return params;
	}

}

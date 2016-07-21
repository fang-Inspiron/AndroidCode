package com.qlf.plants.utils;

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
public class JSONUtil {
	
	/**
	 * 获取终端信息
	 * @param jsonStr
	 * @return
	 */
	public static Map<String,Object> getTerminalInfo(String jsonStr){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject object = new JSONObject(jsonStr);
			map.put("code", Integer.parseInt(object.get("code").toString()));
			if(Integer.parseInt(object.get("code").toString())==1){
					Map<String, Object> oMap = new HashMap<String, Object>();
					JSONObject jsonObject = object.getJSONObject("data");
					JSONObject jsonObject2 = jsonObject.getJSONObject("terminal");
					Map<String, Object> oMap1 = new HashMap<String, Object>();
					oMap1.put("terminalName", jsonObject2.get("terminalName"));
					oMap1.put("terminalId", Integer.parseInt(jsonObject2.get("terminalId").toString()));
					oMap1.put("sim", jsonObject2.get("sim"));
					oMap1.put("address", jsonObject2.get("address"));
					oMap1.put("userId", jsonObject2.get("userId"));
					oMap.put("terminal", oMap1);
					oMap.put("soilmoisture", jsonObject.get("soilmoisture"));
					oMap.put("airhumidity", jsonObject.get("airhumidity"));
					oMap.put("waterlevel", jsonObject.get("waterlevel"));
					oMap.put("temperature", jsonObject.get("temperature"));
					map.put("data", oMap);
			}else{
				map.put("data", object.get("data"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println("Map---------------");
		System.out.println(map);
		return map;
	}
	
	/**
	 * 获取终端历史信息
	 * @param jsonStr
	 * @return
	 */
	public static Map<String,Object> getTerminalHisotory(String jsonStr){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject object = new JSONObject(jsonStr);
			map.put("code", Integer.parseInt(object.get("code").toString()));
			if(Integer.parseInt(object.get("code").toString())==1){
				List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
				JSONArray array = object.getJSONArray("data");
				for(int i=0;i<array.length();i++){
					Map<String, Object> oMap = new HashMap<String, Object>();
					JSONObject jsonObject = array.getJSONObject(i);
					oMap.put("waterlevel", jsonObject.get("waterlevel"));
					oMap.put("month", Integer.parseInt(jsonObject.get("month").toString()));
					oMap.put("historyid", Integer.parseInt(jsonObject.get("historyid").toString()));
					oMap.put("year", Integer.parseInt(jsonObject.get("year").toString()));
					oMap.put("terminalid", Integer.parseInt(jsonObject.get("terminalid").toString()));
					oMap.put("hour", Integer.parseInt(jsonObject.get("hour").toString()));
					oMap.put("day", Integer.parseInt(jsonObject.get("day").toString()));
					oMap.put("soilmoisture", jsonObject.get("soilmoisture	"));
					oMap.put("airhumidity", jsonObject.get("airhumidity"));
					oMap.put("temperature", jsonObject.get("temperature"));
					data.add(oMap);
				}
				map.put("data", data);
			}else{
				map.put("data", object.get("data"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 获取终端列表信息
	 * @param jsonStr
	 * @return
	 */
	public static Map<String,Object> getTerminalList(String jsonStr){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject object = new JSONObject(jsonStr);
			map.put("code", Integer.parseInt(object.get("code").toString()));
			if(Integer.parseInt(object.get("code").toString())==1){
				List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
				JSONArray array = object.getJSONArray("data");
				for(int i=0;i<array.length();i++){
					Map<String, Object> oMap = new HashMap<String, Object>();
					JSONObject jsonObject = array.getJSONObject(i);
					oMap.put("terminalName", jsonObject.get("terminalName"));
					oMap.put("terminalId", Integer.parseInt(jsonObject.get("terminalId").toString()));
					oMap.put("sim", jsonObject.get("sim"));
					oMap.put("address", jsonObject.get("address"));
					data.add(oMap);
				}
				map.put("data", data);
			}else{
				map.put("data", object.get("data"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 获取登陆结果
	 * @param jsonStr
	 * @return
	 */
	public static Map<String,Object> getLoginResult(String jsonStr){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject object = new JSONObject(jsonStr);
			map.put("code", object.get("code"));
			if(Integer.parseInt(object.get("code").toString())==1){
				JSONObject obj  = object.getJSONObject("data");
				JSONObject userObject = obj.getJSONObject("user");
				Map<String, Object> oMap = new HashMap<String, Object>();
				oMap.put("userName", userObject.get("userName"));
				oMap.put("phone", userObject.get("phone"));
				oMap.put("password", userObject.get("password"));
				oMap.put("email", userObject.get("email"));
				oMap.put("userImg", userObject.get("userImg"));
				oMap.put("userId", Integer.parseInt(userObject.get("userId").toString()));
				oMap.put("certificate", obj.get("certificate"));
				map.put("data", oMap);
			}else{
				map.put("data", object.get("data"));
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 获取注册结果
	 * @param jsonStr
	 * @return
	 */
	public static Map<String,Object> getRegisterResult(String jsonStr){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject object = new JSONObject(jsonStr);
			map.put("code", object.get("code"));
			map.put("data", object.get("data"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 获取注册结果
	 * @param jsonStr
	 * @return
	 */
	public static Map<String,Object> getAddTerminalResult(String jsonStr){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject object = new JSONObject(jsonStr);
			map.put("code", object.get("code"));
			map.put("data", object.get("data"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 获取植物信息
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, Object> getPlantsInfo(String jsonStr,String image) {
		Map<String, Object> map;
		try {
			map = new HashMap<String, Object>();
			JSONObject object = new JSONObject(jsonStr);
			if(image != null){
				System.out.println(image);
				map.put("image", image);
			}else{
				System.out.println("No Image");
			}
			if (object.has("attributes")) {
				JSONObject attributes = object.getJSONObject("attributes");
				Map<String, Object> attrMap = new HashMap<String, Object>();
				attrMap.put("BL", attributes.get("BL"));
				attrMap.put("FO", attributes.get("FO"));
				attrMap.put("PT", attributes.get("PT"));
				attrMap.put("SF", attributes.get("SF"));
				attrMap.put("SH", attributes.get("SH"));
				attrMap.put("SN", attributes.get("SN"));
				map.put("attributes", attrMap);
			}
			if (object.has("blooming"))
				map.put("blooming", object.get("blooming"));
			if (object.has("characteristics")) {
				JSONObject characteristics = object
						.getJSONObject("characteristics");
				Map<String, Object> charMap = new HashMap<String, Object>();
				charMap.put("fertilizer", characteristics.get("fertilizer"));
				charMap.put("sun", characteristics.get("sun"));
				charMap.put("temperature_max_celsius",
						characteristics.get("temperature_max_celsius"));
				charMap.put("temperature_min_celsius",
						characteristics.get("temperature_min_celsius"));
				charMap.put("water", characteristics.get("water"));
				map.put("characteristics", charMap);
			}
			if (object.has("common_names")) {
				JSONArray common_names = object.getJSONArray("common_names");
				List<Map<String, Object>> nameList = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < common_names.length(); i++) {
					JSONObject nObject = common_names.getJSONObject(i);
					Map<String, Object> nameMap = new HashMap<String, Object>();
					nameMap.put("common_name", nObject.get("common_name"));
					nameMap.put("preferred", nObject.get("preferred"));
					nameList.add(nameMap);
				}
				map.put("common_names", nameList);
			}
			if (object.has("description")) {
				JSONObject description = object.getJSONObject("description");
				Map<String, Object> descMap = new HashMap<String, Object>();
				descMap.put("source", description.get("source"));
				descMap.put("text", description.get("text"));
				map.put("description", descMap);
			}
			if (object.has("fertilizer"))
				map.put("fertilizer", object.get("fertilizer"));
			if (object.has("fullname"))
				map.put("fullname", object.get("fullname"));
			if (object.has("genus_name"))
				map.put("genus_name", object.get("genus_name"));
			if (object.has("growth"))
				map.put("growth", object.get("growth"));
			if (object.has("hardiness_zone_max"))
				map.put("hardiness_zone_max", object.get("hardiness_zone_max"));
			if (object.has("hardiness_zone_max_value"))
				map.put("hardiness_zone_max_value",
						object.get("hardiness_zone_max_value"));
			if (object.has("hardiness_zone_min"))
				map.put("hardiness_zone_min", object.get("hardiness_zone_min"));
			if (object.has("hardiness_zone_min_value"))
				map.put("hardiness_zone_min_value",
						object.get("hardiness_zone_min_value"));
			if (object.has("heat_zone_max"))
				map.put("heat_zone_max", object.get("heat_zone_max"));
			if (object.has("heat_zone_max_value"))
				map.put("heat_zone_max_value",
						object.get("heat_zone_max_value"));
			if (object.has("heat_zone_min"))
				map.put("heat_zone_min", object.get("heat_zone_min"));
			if (object.has("heat_zone_min_value"))
				map.put("heat_zone_min_value",
						object.get("heat_zone_min_value"));
			if (object.has("subspecies_name"))
				map.put("subspecies_name", object.get("subspecies_name"));
			if (object.has("species_name"))
				map.put("species_name", object.get("species_name"));
			if (object.has("soil_irr"))
				map.put("soil_irr", object.get("soil_irr"));
			if (object.has("interesting"))
				map.put("interesting", object.get("interesting"));
			if (object.has("pruning"))
				map.put("pruning", object.get("pruning"));
			if (object.has("latin_name"))
				map.put("latin_name", object.get("latin_name"));
			if (object.has("light_max"))
				map.put("light_max", object.get("light_max"));
			if (object.has("light_min"))
				map.put("light_min", object.get("light_min"));
			if (object.has("pests"))
				map.put("pests", object.get("pests"));
			if (object.has("planting"))
				map.put("planting", object.get("planting"));
			if (object.has("taxonomy_group_id"))
				map.put("taxonomy_group_id", object.get("taxonomy_group_id"));
			if (object.has("id"))
				map.put("id", object.get("id"));
			if (object.has("hidden"))
				map.put("hidden", object.get("hidden"));
			if (object.has("spread_max"))
				map.put("spread_max", object.get("spread_max"));
			if (object.has("spread_min"))
				map.put("spread_min", object.get("spread_min"));
			if (object.has("height_min"))
				map.put("height_min", object.get("height_min"));
			if (object.has("height_max"))
				map.put("height_max", object.get("height_max"));
			if (object.has("t_dying"))
				map.put("t_dying", object.get("t_dying"));
			if (object.has("is_taxonomy_group_head"))
				map.put("is_taxonomy_group_head",
						object.get("is_taxonomy_group_head"));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return map;
	}
	
}

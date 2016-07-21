package com.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bean.Customer;
import com.bean.Flight;
import com.bean.Order;
import com.bean.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtils {

	public static Map<String, Object> getResult(String result) {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject obj;
		try {
			System.out.println("aaa"+result);
			obj = JSONObject.fromObject(result);
			map.put("result", obj.get("result"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public static Map<String, User> getUserOneResult(String result) {
		Map<String, User> map = new HashMap<String, User>();
		JSONObject obj = JSONObject.fromObject(result);
		try {
			JSONObject o =  JSONObject.fromObject(obj.get("result"));
			User user = new User();
			user.setUser(o.getString("user"));
			user.setPassword(o.getString("password"));
			user.setPermission(Integer.parseInt(o.getString("permission")));
			map.put("user",  user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static Map<String, Customer> getCustomerOneResult(String result) {
		Map<String, Customer> map = new HashMap<String, Customer>();
		JSONObject obj = JSONObject.fromObject(result);
		try {
			JSONObject o =  JSONObject.fromObject(obj.get("result"));
			Customer customer = new Customer();
			customer.setName(o.getString("name"));
			customer.setSex(o.getString("sex"));
			customer.setIDnumber(o.getString("iDnumber"));
			customer.setPhone(o.getString("phone"));
			map.put("customer", customer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static Map<String, Flight> getFlightOneResult(String result) {
		
		Map<String, Flight> map = new HashMap<String, Flight>();
		JSONObject obj = JSONObject.fromObject(result);
		if (obj.get("result") == null) {
			return null;
		}
		try {
			JSONObject o =  JSONObject.fromObject(obj.get("result"));
			
			Flight flight = new Flight();
			flight.setFlightNum(o.getString("flightNum"));
			flight.setLeaveCity(o.getString("leaveCity"));
			flight.setArriveCity(o.getString("arriveCity"));
			flight.setLeaveDate(o.getString("leaveDate"));
			flight.setPrice(Integer.parseInt(o.getString("price")));
			map.put("flight", flight);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static Map<String, Order> getOrderOneResult(String result) {
		Map<String, Order> map = new HashMap<String, Order>();
		JSONObject obj = JSONObject.fromObject(result);
		try {
			JSONObject o =  JSONObject.fromObject(obj.get("result"));
			Order order = new Order();
			order.setOrderNum(o.getString("orderNum"));
			order.setIDnumber(o.getString("IDnumber"));
			order.setFlightNum(o.getString("flightNum"));
			order.setTicketNum(Integer.parseInt(o.getString("ticketNum")));
			order.setPurchaseDate(o.getString("purchaseDate"));
			order.setConductor(o.getString("conductor"));
			map.put("order", order);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public static List<Map<String, User>> getUserAllResult(String result) {
		List<Map<String, User>> list = new ArrayList<Map<String, User>>();
		JSONObject jsonObject = JSONObject.fromObject(result);
		JSONArray array = JSONArray.fromObject(jsonObject.get("result"));
		try {
			for (int i=0; i<array.size(); i++) {
				JSONObject obj = array.getJSONObject(i);
				Map<String, User> map = new HashMap<String, User>();
				User u = new User();
				u.setPassword(obj.getString("password"));
				u.setPermission(Integer.parseInt(obj.getString("permission")));
				u.setUser(obj.getString("user"));
				map.put("user", u);
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<Map<String, Customer>> getCustomerAllResult(String result) {
		List<Map<String, Customer>> list = new ArrayList<Map<String, Customer>>();
		JSONObject jsonObject = JSONObject.fromObject(result);
		JSONArray array = JSONArray.fromObject(jsonObject.get("result"));
		try {
			for (int i=0; i<array.size(); i++) {
				JSONObject obj = array.getJSONObject(i);
				Map<String, Customer> map = new HashMap<String, Customer>();
				Customer customer = new Customer();
				customer.setName(obj.getString("name"));
				customer.setSex(obj.getString("sex"));
				customer.setIDnumber(obj.getString("iDnumber"));
				customer.setPhone(obj.getString("phone"));
				map.put("customer", customer);
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<Map<String, Flight>> getFlightAllResult(String result) {
		List<Map<String, Flight>> list = new ArrayList<Map<String, Flight>>();
		JSONObject jsonObject = JSONObject.fromObject(result);
		JSONArray array = JSONArray.fromObject(jsonObject.get("result"));
		try {
			for (int i=0; i<array.size(); i++) {
				JSONObject obj = array.getJSONObject(i);
				Map<String, Flight> map = new HashMap<String, Flight>();
				Flight flight = new Flight();
				flight.setFlightNum(obj.getString("flightNum"));
				flight.setLeaveCity(obj.getString("leaveCity"));
				flight.setArriveCity(obj.getString("arriveCity"));
				flight.setLeaveDate(obj.getString("leaveDate"));
				flight.setPrice(Integer.parseInt(obj.getString("price")));
				map.put("flight", flight);
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(list);
		return list;
	}
	
	public static List<Map<String, Order>> getOrderAllResult(String result) {
		List<Map<String, Order>> list = new ArrayList<Map<String, Order>>();
		JSONObject jsonObject = JSONObject.fromObject(result);
		JSONArray array = JSONArray.fromObject(jsonObject.get("result"));
		try {
			for (int i=0; i<array.size(); i++) {
				JSONObject obj = array.getJSONObject(i);
				Map<String, Order> map = new HashMap<String, Order>();
				Order order = new Order();
				order.setOrderNum(obj.getString("orderNum"));
				order.setIDnumber(obj.getString("IDnumber"));
				order.setFlightNum(obj.getString("flightNum"));
				order.setTicketNum(Integer.parseInt(obj.getString("ticketNum")));
				order.setPurchaseDate(obj.getString("purchaseDate"));
				order.setConductor(obj.getString("conductor"));
				map.put("order", order);
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}

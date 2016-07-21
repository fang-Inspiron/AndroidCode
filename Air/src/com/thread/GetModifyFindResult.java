package com.thread;

import java.util.Map;
import javax.swing.JFrame;
import com.bean.Customer;
import com.bean.Flight;
import com.bean.Order;
import com.bean.User;
import com.function.FlightManager;
import com.function.OrderManager;
import com.utils.HttpUtils;
import com.utils.JsonUtils;

//服务器端需要pid和str（每个Object的主键）
public class GetModifyFindResult extends Thread {
	private String str;
	private JFrame jFrame;
	private int flag = 0;
	public static boolean getInfo = false;
	public static Map<String, User> userMap;
	public static Map<String, Customer> cuatomerMap;
	public static Map<String, Flight> flightMap;
	public static Map<String, Order> orderMap;

	public GetModifyFindResult(String str, int flag, JFrame jFrame) {
		this.str = str;
		this.jFrame = jFrame;
		this.flag = flag;
	}

	@Override
	public void run() {
		String pid;
		String url;
		String res;

		switch (flag) {
		case 1:
			pid = "user";
			url = HttpUtils.GET_INFO_FINDONE + "?pid=" + pid + "&user=" + str;
			res = HttpUtils.queryStringForPost(url);
			userMap = JsonUtils.getUserOneResult(res);
			getInfo = true;
			break;
		case 2:
			pid = "customer";
			url = HttpUtils.GET_INFO_FINDONE + "?pid=" + pid + "&IDnumber=" + str;
			res = HttpUtils.queryStringForPost(url);
			cuatomerMap = JsonUtils.getCustomerOneResult(res);
			getInfo = true;
			break;
		case 3:
			pid = "flight";
			url = HttpUtils.GET_INFO_FINDONE + "?pid=" + pid + "&flightNum=" + str;
			res = HttpUtils.queryStringForPost(url);
			flightMap = JsonUtils.getFlightOneResult(res);
			getInfo = true;
			FlightManager.change(flightMap.get("flight"));
			break;
		case 4:
			pid = "order";
			url = HttpUtils.GET_INFO_FINDONE + "?pid=" + pid + "&orderNum=" + str;
			res = HttpUtils.queryStringForPost(url);
			orderMap = JsonUtils.getOrderOneResult(res);
			getInfo = true;
			OrderManager.change(orderMap.get("order"));
			break;

		default:
			break;
		}

	}
}

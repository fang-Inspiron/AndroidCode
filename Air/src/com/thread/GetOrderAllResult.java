package com.thread;

import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import com.bean.Customer;
import com.bean.Flight;
import com.bean.Order;
import com.bean.User;
import com.function.FlightManager;
import com.function.OrderManager;
import com.function.TicketManager;
import com.utils.HttpUtils;
import com.utils.JsonUtils;

//服务器端需要pid
public class GetOrderAllResult extends Thread{
	private JFrame jFrame;
	private int flag;
	public static boolean getInfo = false; 
	public static List<Map<String, User>> userList; 
	public static List<Map<String, Customer>> customerList; 
	public static List<Map<String, Flight>> flightList; 
	public static List<Map<String, Order>> orderList; 

	public GetOrderAllResult(int flag, JFrame jFrame) {
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
			url = HttpUtils.GET_INFO_FINDALL + "?pid=" + pid;
			res = HttpUtils.queryStringForPost(url);
			userList = JsonUtils.getUserAllResult(res);
			getInfo = true;
			break;
		case 2:
			pid = "customer";
			url = HttpUtils.GET_INFO_FINDALL + "?pid=" + pid;
			res = HttpUtils.queryStringForPost(url);
			customerList = JsonUtils.getCustomerAllResult(res);
			getInfo = true;
			break;
		case 3:
			pid = "flight";
			url = HttpUtils.GET_INFO_FINDALL +"?pid=" + pid;
			res = HttpUtils.queryStringForPost(url);
			FlightManager.list = JsonUtils.getFlightAllResult(res);
			getInfo = true;
			FlightManager.showAll();
			
			break;
		case 4:
			pid = "order";
			url = HttpUtils.GET_INFO_FINDALL + "?pid=" + pid;
			res = HttpUtils.queryStringForPost(url);
			orderList = JsonUtils.getOrderAllResult(res);
			 OrderManager.list = orderList;
			System.out.println("thread:"+OrderManager.list);
			getInfo = true;
			OrderManager.findAllForDelete();
			break;

		default:
			break;
		}

	}
}

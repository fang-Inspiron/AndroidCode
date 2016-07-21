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

//����������Ҫpid
public class GetTicketAllResult extends Thread{
	private JFrame jFrame;
	private int flag;
	public static boolean getInfo = false; 
	public static List<Map<String, User>> userList; 
	public static List<Map<String, Customer>> customerList; 
	public static List<Map<String, Flight>> flightList; 
	public static List<Map<String, Order>> orderList; 

	public GetTicketAllResult(int flag, JFrame jFrame) {
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
			TicketManager.list = JsonUtils.getFlightAllResult(res);
			getInfo = true;
			TicketManager.change();
			break;
		case 4:
			pid = "order";
			url = HttpUtils.GET_INFO_FINDALL + "?pid=" + pid;
			res = HttpUtils.queryStringForPost(url);
			orderList = JsonUtils.getOrderAllResult(res);
			TicketManager.orderList = orderList;
			System.out.println("thread:"+TicketManager.orderList);
			TicketManager.findOrder();
			getInfo = true;
			break;

		default:
			break;
		}

	}
}

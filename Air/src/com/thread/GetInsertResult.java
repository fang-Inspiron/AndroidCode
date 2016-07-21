package com.thread;

import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import com.bean.Customer;
import com.bean.Flight;
import com.bean.Order;
import com.bean.User;
import com.utils.HttpUtils;
import com.utils.JsonUtils;

//服务器端需要pid和Object类型的对象
public class GetInsertResult extends Thread {

	private Customer customer;
	private Flight flight;
	private Order order;
	private User user;
	private JFrame jFrame;
	private int flag = 0;

	public GetInsertResult(User user, JFrame jFrame) {
		this.user = user;
		this.jFrame = jFrame;
		flag = 1;
	}

	public GetInsertResult(Customer customer, JFrame jFrame) {
		this.customer = customer;
		this.jFrame = jFrame;
		flag = 2;
	}

	public GetInsertResult(Flight flight, JFrame jFrame) {
		this.flight = flight;
		this.jFrame = jFrame;
		flag = 3;
	}

	public GetInsertResult(Order order, JFrame jFrame) {
		this.order = order;
		this.jFrame = jFrame;
		flag = 4;
	}

	@Override
	public void run() {
		String pid;
		String url;
		String res;
		Map<String, Object> map;

		switch (flag) {
		case 1:
			pid = "user";
			url = HttpUtils.GET_INFO_INSERT + "?pid=" + pid + user.toURL();
			res = HttpUtils.queryStringForPost(url);
			map = JsonUtils.getResult(res);
			if (map.get("result").equals("1")) {
				JOptionPane.showMessageDialog(this.jFrame, "添加成功！", "提示框",
						JOptionPane.CANCEL_OPTION);
			} else {
				JOptionPane.showMessageDialog(this.jFrame, "添加失败！", "提示框",
						JOptionPane.CANCEL_OPTION);
			}
			break;
		case 2:
			pid = "customer";
			url = HttpUtils.GET_INFO_INSERT + "?pid=" + pid
					+ customer.toURL();
			res = HttpUtils.queryStringForPost(url);
			map = JsonUtils.getResult(res);
			if (map.get("result").equals("1")) {
				JOptionPane.showMessageDialog(this.jFrame, "添加成功！", "提示框",
						JOptionPane.CANCEL_OPTION);
			} else {
				JOptionPane.showMessageDialog(this.jFrame, "添加失败！", "提示框",
						JOptionPane.CANCEL_OPTION);
			}
			break;
		case 3:
			pid = "flight";
			url = HttpUtils.GET_INFO_INSERT + "?pid=" + pid + flight.toURL();
			res = HttpUtils.queryStringForPost(url);
			map = JsonUtils.getResult(res);
			if (map.get("result").equals("1")) {
				JOptionPane.showMessageDialog(this.jFrame, "添加成功！", "提示框",
						JOptionPane.CANCEL_OPTION);
			} else {
				JOptionPane.showMessageDialog(this.jFrame, "添加失败！", "提示框",
						JOptionPane.CANCEL_OPTION);
			}
			break;
		case 4:
			pid = "order";
			url = HttpUtils.GET_INFO_INSERT + "?pid=" + pid + order.toURL();
			res = HttpUtils.queryStringForPost(url);
			map = JsonUtils.getResult(res);
			if (map.get("result").equals("1")) {
				JOptionPane.showMessageDialog(this.jFrame, "添加成功！", "提示框",
						JOptionPane.CANCEL_OPTION);
			} else {
				JOptionPane.showMessageDialog(this.jFrame, "添加失败！", "提示框",
						JOptionPane.CANCEL_OPTION);
			}
			break;

		default:
			break;
		}

	}
}

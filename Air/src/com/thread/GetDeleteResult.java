package com.thread;

import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.function.OrderManager;
import com.utils.HttpUtils;
import com.utils.JsonUtils;

//服务器端需要pid和str（每个Object的主键）
public class GetDeleteResult extends Thread{

	private String str;
	private JFrame jFrame;
	private int flag = 0;

	public GetDeleteResult(String str, int flag, JFrame jFrame) {
		this.str = str;
		this.flag = flag;
		this.jFrame = jFrame;
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
			url = HttpUtils.GET_INFO_DELETE + "?pid=" + pid + "&user=" + str;
			res = HttpUtils.queryStringForPost(url);
			map = JsonUtils.getResult(res);
			if (map.get("result").equals("1")) {
				JOptionPane.showMessageDialog(this.jFrame, "撤销成功！", "提示框",
						JOptionPane.CANCEL_OPTION);
			} else {
				JOptionPane.showMessageDialog(this.jFrame, "撤销失败！",
						"提示框", JOptionPane.CANCEL_OPTION);
			}
			break;
		case 2:
			pid = "customer";
			url = HttpUtils.GET_INFO_DELETE +"?pid=" + pid + "&IDnumber=" + str;
			res = HttpUtils.queryStringForPost(url);
			map = JsonUtils.getResult(res);
			if (map.get("result").equals("1")) {
				JOptionPane.showMessageDialog(this.jFrame, "撤销成功！", "提示框",
						JOptionPane.CANCEL_OPTION);
			} else {
				JOptionPane.showMessageDialog(this.jFrame, "撤销失败！",
						"提示框", JOptionPane.CANCEL_OPTION);
			}
			break;
		case 3:
			pid = "flight";
			url = HttpUtils.GET_INFO_DELETE + "?pid=" + pid + "&flightNum=" + str;
			res = HttpUtils.queryStringForPost(url);
			map = JsonUtils.getResult(res);
			if (map.get("result").equals("1")) {
				JOptionPane.showMessageDialog(this.jFrame, "撤销成功！", "提示框",
						JOptionPane.CANCEL_OPTION);
			} else {
				JOptionPane.showMessageDialog(this.jFrame, "撤销失败！",
						"提示框", JOptionPane.CANCEL_OPTION);
			}
			break;
		case 4:
			pid = "order";
			url = HttpUtils.GET_INFO_DELETE + "?pid=" + pid + "&orderNum=" + str;
			res = HttpUtils.queryStringForPost(url);
			map = JsonUtils.getResult(res);
			if (map.get("result").equals("1")) {
				JOptionPane.showMessageDialog(this.jFrame, "撤销成功！", "提示框",
						JOptionPane.CANCEL_OPTION);
			} else {
				JOptionPane.showMessageDialog(this.jFrame, "撤销失败！",
						"提示框", JOptionPane.CANCEL_OPTION);
			}
			break;

		default:
			break;
		}
	}
}

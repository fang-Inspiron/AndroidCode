package com.thread;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.swing.JFrame;

import com.airplane.Function;
import com.airplane.Login;
import com.function.ConductorTicket;
import com.utils.HttpUtils;
import com.utils.JsonUtils;

public class GetLoginResult extends Thread{

	private String user;
	private String password;
	private JFrame frame;
	
	public GetLoginResult(String user, String password,JFrame jf) {
		super();
		this.user = user;
		this.password = password;
		this.frame = jf;
	}

	@Override
	public void run() {
		
		String u ="";
		try {
			u = URLEncoder.encode(user, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String url = HttpUtils.GET_INFO_LOGIN+"?user="+u+"&password="+password;
		String res = HttpUtils.queryStringForPost(url);
		Map<String, Object> map = JsonUtils.getResult(res);
		
		if (map.get("result").equals("0")) {
			Login.loginZero();
		} else  if (map.get("result").equals("1")) {
			Login.loginOne();
		} else  if (map.get("result").equals("2")) {
			Login.loginTwo();
			this.frame.dispose();
			ConductorTicket window = new ConductorTicket();
			window.frame.setVisible(true);
		} else  if (map.get("result").equals("3")) {
			Login.loginThree();
			this.frame.dispose();
			Function window = new Function();
			window.frame.setVisible(true);
		}
		
	}
}

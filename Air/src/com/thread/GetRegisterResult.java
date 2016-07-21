package com.thread;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import javax.swing.JFrame;
import com.airplane.Login;
import com.airplane.Register;
import com.utils.HttpUtils;
import com.utils.JsonUtils;

public class GetRegisterResult extends Thread {

	private String user;
	private String password;
	private String password2;
	private int permission;
	private Register frame;

	public GetRegisterResult(String user, String password, String password2,
			int permission, Register frame) {
		String u = "";
		try {
			u = URLEncoder.encode(user, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.user = u;
		this.password = password;
		this.password2 = password2;
		this.permission = permission;
		this.frame = frame;
	}

	@Override
	public void run() {
		String url = HttpUtils.GET_INFO_REGISTER + "?user=" + user
				+ "&password=" + password + "&password2=" + password2
				+ "&permission=" + permission;
		System.out.println(url);
		String res = HttpUtils.queryStringForPost(url);
		Map<String, Object> map = JsonUtils.getResult(res);
		if (map.get("result").equals("0")) {
			frame.registerZero();
		} else if (map.get("result").equals("1")) {
			frame.registerOne();
		} else if (map.get("result").equals("2")) {
			frame.registerTwo();
		} else if (map.get("result").equals("3")) {
			frame.registerThree();
			this.frame.dispose();
			new Login();
		}
	}
}

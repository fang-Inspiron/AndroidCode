package com.xrml.kuaican.net;

import java.util.HashMap;
import java.util.Map;

import com.xrml.kuaican.util.HttpUtils;
import com.xrml.kuaican.util.JSONUtil;

import android.os.Handler;
import android.os.Message;

/**
 * @author Administrator
 *	修改密码
 */
public class ChangePasswordThread extends Thread{
	private Handler handler;
	private String phoneNumber;//用户电话
	private String oldPassword;//原密码
	private String newPassword;//新密码
	private String checkStr;//校验
	
	public static final int NETWORK_ERROR = 0;
	public static final int SUCCESS = 1;	
	
	//用户手机号
	private static final String PHONENUMBER = "USERC3B9D6D48C7DFAC5FC1A4EF32DDD2EC2";
	//用户原密码
	private static final String OLDPASSWORD = "USER6D8DA6B0C968B9A6BDC35FA7E62367B0";
	//新密码
	private static final String NEWPASSWORD = "USERCBF221594432CE103F0A782B115FD02D";
	//校验
	private static final String CHECKSTR = "USER418C5509E2171D55B0AEE5C2EA4442B5";
	//修改方式
	private static final String UPDATEWAY = "updateWay";
	
	public ChangePasswordThread(Handler handler, String phoneNumber,
			String oldPassword, String newPassword, String checkStr) {
		super();
		this.handler = handler;
		this.phoneNumber = phoneNumber;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.checkStr = checkStr;
	}
	@Override
	public void run() {
		Map<String, String> rawParams = new HashMap<String, String>();
		rawParams.put(PHONENUMBER, phoneNumber);
		rawParams.put(OLDPASSWORD, oldPassword);
		rawParams.put(NEWPASSWORD, newPassword);
		rawParams.put(CHECKSTR, checkStr);
		rawParams.put(UPDATEWAY, "password");
		String result = HttpUtils.queryStringForPost(HttpUtils.CHANGE_PASSWORD, rawParams);
		if(result == null){
			handler.sendEmptyMessage(NETWORK_ERROR);
		}else{
			Map<String, Object> data = JSONUtil.getChangePasswordResult(result);
			Message msg = Message.obtain();
			msg.what = SUCCESS;
			msg.obj = data;
			handler.sendMessage(msg);
		}
	}
}

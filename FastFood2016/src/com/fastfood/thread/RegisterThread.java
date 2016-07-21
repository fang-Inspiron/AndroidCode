package com.fastfood.thread;

import java.util.HashMap;
import java.util.Map;

import com.fastfood.utils.HttpUtil;
import com.fastfood.utils.JsonUtil;

import android.os.Handler;
import android.os.Message;

public class RegisterThread extends Thread {

	// �û��绰
	public final static String PHONE_NUMBER = "USERC3B9D6D48C7DFAC5FC1A4EF32DDD2EC2";
	// �û�����
	public final static String USER_PASSWORD = "USER6D8DA6B0C968B9A6BDC35FA7E62367B0";
	// ��֤��
	public final static String VERTIFY_CODE = "C13367945D5D4C91047B3B50234AA7AB";
	// У��
	public final static String CHECK_STR = "USER418C5509E2171D55B0AEE5C2EA4442B5";
	// ע��ɹ�
	public final static String REGISTER_SUCCESS = "CBEA0ABDF6E393C33C540E604C8F4E8C";
	// �ֻ������Ѿ���ע��
	public final static String HAS_REGISTER = "A3A2BA02F08301C857AFCFDFC192DF04";
	// �������������ע��
	public final static String NET_ERROR = "C732B67F7AD59A691E15B328A501508D";
	// �Բ�������Ȩע��
	public final static String NO_ACCESS = "F1F5FF7206C408B26AA1A2B63EDBAEF3";

	public static final int NETWORK_ERROR = 200;
	public static final int SUCCESS = 201;

	private Handler handler;
	private String phoneNumber;
	private String userPassword;
	private String vertifyCode;
	private String checkStr;

	public RegisterThread(Handler handler, String phoneNumber,
			String userPassword, String vertifyCode, String checkStr) {
		super();
		this.handler = handler;
		this.phoneNumber = phoneNumber;
		this.userPassword = userPassword;
		this.vertifyCode = vertifyCode;
		this.checkStr = checkStr;
	}

	@Override
	public void run() {
		Map<String, String> rawParams = new HashMap<String, String>();
		rawParams.put(PHONE_NUMBER, phoneNumber);
		rawParams.put(USER_PASSWORD, userPassword);
		rawParams.put(VERTIFY_CODE, vertifyCode);
		rawParams.put(checkStr, checkStr);
		String result = HttpUtil.queryStringForPost(HttpUtil.REGISTER_CODE,
				rawParams);
		if (result == null) {
			handler.sendEmptyMessage(NETWORK_ERROR);
		} else {
			Map<String, Object> data = JsonUtil.getRegisterResult(result);
			Message msg = Message.obtain();
			msg.what = SUCCESS;
			msg.obj = data;
			handler.sendMessage(msg);
		}
	}
}

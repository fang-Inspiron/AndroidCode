package com.xrml.kuaican.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {
	/**
	 * 验证电话号码
	 * @param phone
	 * @return
	 */
	public static boolean isPhoneValidate(String phone) {
		boolean flag;
		Pattern regex = Pattern
				.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
		Matcher matcher = regex.matcher(phone);
		flag = matcher.matches();
		return flag;
	}
	
	/**
	 * 验证日期字符串是否为yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public static boolean isDateYYYYMMDD(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			sdf.parse(date);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
}

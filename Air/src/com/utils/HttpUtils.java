package com.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

public class HttpUtils {
	final static String NETWORK_ERROR = "network_error";
	public final static String GET_INFO_LOGIN = "http://localhost:8080/Air/servlet/LoginServlet";
	public final static String GET_INFO_REGISTER = "http://localhost:8080/Air/servlet/RegisterServlet";
	public final static String GET_INFO_INSERT = "http://localhost:8080/Air/servlet/InsertServlet";
	public final static String GET_INFO_DELETE = "http://localhost:8080/Air/servlet/DeleteServlet";
	public final static String GET_INFO_MODIFY = "http://localhost:8080/Air/servlet/ModifyServlet";
	public final static String GET_INFO_FINDONE = "http://localhost:8080/Air/servlet/FindOneServlet";
	public final static String GET_INFO_FINDALL = "http://localhost:8080/Air/servlet/FindAllServlet";
	
	//ÇëÇóÍøÒ³¡¢½ÓÊÜJson×Ö·û´®
	public static String queryStringForPost(String u) {
		String data="";
		
		try {
			
			URL url = new URL(u);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			InputStream inputStream = conn.getInputStream();
			byte[] getData = readInputStream(inputStream);
			data = new String(getData,"utf-8");
			System.out.println(data);
			return data;
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
		
	}
	public static byte[] readInputStream(InputStream inputStream) throws Exception{
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while((len=inputStream.read(buffer))!=-1){
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}
}

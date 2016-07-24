package com.one;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Lexer {

	public static ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	public static int line = 0;

	public static void main(String[] args) {
		System.out.println(readFile("F://input.c"));
		
		ArrayList<Map<String, Object>> arrayList = lax(readFile("F://input.c"));
		for(int i=0; i<arrayList.size(); i++) {
			Map<String, Object> map = arrayList.get(i);
			Set<Entry<String, Object>> entries = map.entrySet();
			for (Entry<String, Object> entry:entries) {
				System.out.println("{"+entry.getKey()+",'"+entry.getValue()+"'}");
			}
		}
	}
	
	

	public static String readFile(String fileName) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(fileName))));
			String str = br.readLine();
			sb.append(str);
			while (str != null) {
				str = br.readLine();
				if (str != null) {
					line++;
					sb.append("\n"+str);
				}
			}
			br.close();
			return sb.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static String getClassify(StringBuffer sb) {
		String str = sb.toString();
		switch (str) {
		case Classes.INCLUDE:return Classes.PREDEFINE_IDENTIFIER;
		case Classes.MAIN:return Classes.PREDEFINE_IDENTIFIER;
		case Classes.INT:return Classes.KEYWORD;
		case Classes.VOID:return Classes.KEYWORD;
		case Classes.RETURN:return Classes.KEYWORD;
		default:{
				if ((str.charAt(0)>='a'&&str.charAt(0)<='z') || (str.charAt(0)>='A'&&str.charAt(0)<='Z')) {
					return Classes.USER_IDENTIFIER;
				}
				else
					return Classes.VALUE;
		}
		}
	}
	
	public static ArrayList<Map<String, Object>> lax(String str) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);

			if (c >= '0' && c <= '9') {
				sb = sb.append(c);
			} else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
				sb = sb.append(c);
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				switch (c) {
				case Classes.ADD:
					break;
				case Classes.SUBTRACT:
					break;
				case Classes.MULTIPLY:
					break;
				case Classes.FENHAO:
					map.put(getClassify(sb), sb.toString());
					list.add(map);
					HashMap<String, Object> hashMap = new HashMap<String, Object>();
					hashMap.put(String.valueOf(Classes.SPECIALCHARACTER), c);
					list.add(hashMap);
					sb.setLength(0);
					break;
				case Classes.SPACE:
					if (sb != null && sb.length()>0) {
						map.put(getClassify(sb), sb.toString());
						list.add(map);
					}
					sb.setLength(0);
					break;
				case Classes.DOUHAO:
					sb.setLength(0);
					break;
				case Classes.WENHAO:
					sb.setLength(0);
					break;
				case Classes.JINGHAO:
					map.put(String.valueOf(Classes.SPECIALCHARACTER), c);
					list.add(map);
					sb.setLength(0);
					break;
				case Classes.EQUAL:
					map.put(getClassify(sb), sb.toString());
					list.add(map);
					HashMap<String, Object> hashMapEqual = new HashMap<String, Object>();
					hashMapEqual.put(String.valueOf(Classes.SPECIALCHARACTER), c);
					list.add(hashMapEqual);
					sb.setLength(0);
					break;
				case Classes.HUICHE:
					sb.setLength(0);
					break;
				case Classes.POINT:
					if(sb.length()!=0){
						map.put(getClassify(sb), sb.toString());
						list.add(map);
					}
					HashMap<String, Object> hashMapPoint = new HashMap<String, Object>();
					hashMapPoint.put(String.valueOf(Classes.SPECIALCHARACTER), c);
					list.add(hashMapPoint);
					sb.setLength(0);
					break;
				case Classes.ZUOHUA:
					map.put(String.valueOf(Classes.SPECIALCHARACTER), c);
					list.add(map);
					sb.setLength(0);
					break;
				case Classes.YOUHUA:
					map.put(String.valueOf(Classes.SPECIALCHARACTER), c);
					list.add(map);
					sb.setLength(0);
					break;
				case Classes.ZUOYUAN:
					if(sb.length()!=0) {
						map.put(getClassify(sb), sb.toString());
						list.add(map);
					}
					HashMap<String, Object> hashMapZuoYuan = new HashMap<String, Object>();
					hashMapZuoYuan.put(String.valueOf(Classes.SPECIALCHARACTER), c);
					list.add(hashMapZuoYuan);
					sb.setLength(0);
					break;
				case Classes.YOUYUAN:
					map.put(getClassify(sb), sb.toString());
					list.add(map);
					HashMap<String, Object> hashMapYouYuan = new HashMap<String, Object>();
					hashMapYouYuan.put(String.valueOf(Classes.SPECIALCHARACTER), c);
					list.add(hashMapYouYuan);
					sb.setLength(0);
					break;
				case Classes.MORE://大于号
					if (sb.length() > 0) {
						map.put(getClassify(sb), sb.toString());
						list.add(map);
						HashMap<String, Object> hashMapMore = new HashMap<String, Object>();
						hashMapMore.put(String.valueOf(Classes.SPECIALCHARACTER), c);
						list.add(hashMapMore);
						sb.setLength(0);
					}
					
					break;
				case Classes.LESS://小于号
					if (sb.length() >0 ) {
						map.put(getClassify(sb), sb.toString());
						list.add(map);
						HashMap<String, Object> hashMapLess = new HashMap<String, Object>();
						hashMapLess.put(String.valueOf(Classes.SPECIALCHARACTER), c);
						list.add(hashMapLess);
						sb.setLength(0);
					}
					
					break;
				case Classes.DEVIDE:
					//忽略//注释
					if(str.charAt(i+1) == Classes.DEVIDE){
						i++;
						while(str.charAt(i)!=Classes.HUICHE){
							i++;
						}
					}
					//忽略/**/注释
					if(str.charAt(i+1) == Classes.MULTIPLY){
						i++;
						while(str.charAt(i)!=Classes.MULTIPLY){
							i++;
						}
						if(str.charAt(i+1) == Classes.DEVIDE){
							i++;
						}
					}
					break;
				default:
					break;
				}
			}

		}
		return list;
	}

}

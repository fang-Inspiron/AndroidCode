package com.two;

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
		// 读取文件并输出
		System.out.println(readFile("F://input.c"));

		// 进行词法分析，输出结果为二元组形式 {类别,'值'} 的形式
		ArrayList<Map<String, Object>> arrayList = lax(readFile("F://input.c"));
		for (int i = 0; i < arrayList.size(); i++) {
			Map<String, Object> map = arrayList.get(i);
			Set<Entry<String, Object>> entries = map.entrySet();
			for (Entry<String, Object> entry : entries) {
				System.out.println("{" + entry.getKey() + ",'"
						+ entry.getValue() + "'}");
			}
		}

	}

	/**
	 * 读文件
	 * @param fileName:文件名
	 * @return String:将读取到的文件以String类型返回
	 */
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
					sb.append("\n" + str);
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

	/**
	 * 分类：将 字符串 按不同类别进行分类，并返回分类的结果
	 * @param sb:将要分析的目的字符串
	 * @return String:返回分类结果
	 */
	public static String getClassify(StringBuffer sb) {
		String str = sb.toString();
		switch (str) {
		case Classes.INCLUDE:
			return Classes.PREDEFINE_IDENTIFIER;
		case Classes.MAIN:
			return Classes.PREDEFINE_IDENTIFIER;
		case Classes.SCANF:
			return Classes.PREDEFINE_IDENTIFIER;
		case Classes.PRINTF:
			return Classes.PREDEFINE_IDENTIFIER;
		case Classes.DEFINE:
			return Classes.PREDEFINE_IDENTIFIER;
		case Classes.STDIO:
			return Classes.PREDEFINE_IDENTIFIER;
		case Classes.STDLIB:
			return Classes.PREDEFINE_IDENTIFIER;
		case Classes.STRING:
			return Classes.PREDEFINE_IDENTIFIER;

		case Classes.INT:
			return Classes.KEYWORD;
		case Classes.VOID:
			return Classes.KEYWORD;
		case Classes.RETURN:
			return Classes.KEYWORD;
		case Classes.AUTO:
			return Classes.KEYWORD;
		case Classes.BREAK:
			return Classes.KEYWORD;
		case Classes.CASE:
			return Classes.KEYWORD;
		case Classes.CHAR:
			return Classes.KEYWORD;
		case Classes.CONST:
			return Classes.KEYWORD;
		case Classes.CONTINUE:
			return Classes.KEYWORD;
		case Classes.DEFAULT:
			return Classes.KEYWORD;
		case Classes.DO:
			return Classes.KEYWORD;
		case Classes.DOUBLE:
			return Classes.KEYWORD;
		case Classes.ELSE:
			return Classes.KEYWORD;
		case Classes.ENUM:
			return Classes.KEYWORD;
		case Classes.EXTERN:
			return Classes.KEYWORD;
		case Classes.FLOAT:
			return Classes.KEYWORD;
		case Classes.FOR:
			return Classes.KEYWORD;
		case Classes.GOTO:
			return Classes.KEYWORD;
		case Classes.IF:
			return Classes.KEYWORD;
		case Classes.LONG:
			return Classes.KEYWORD;
		case Classes.REGISTER:
			return Classes.KEYWORD;
		case Classes.SHORT:
			return Classes.KEYWORD;
		case Classes.SIGNED:
			return Classes.KEYWORD;
		case Classes.SIZEOF:
			return Classes.KEYWORD;
		case Classes.STATIC:
			return Classes.KEYWORD;
		case Classes.SWITCH:
			return Classes.KEYWORD;
		case Classes.TYPEDEF:
			return Classes.KEYWORD;
		case Classes.UNION:
			return Classes.KEYWORD;
		case Classes.UNSIGNED:
			return Classes.KEYWORD;
		case Classes.VOLATILE:
			return Classes.KEYWORD;
		case Classes.WHILE:
			return Classes.KEYWORD;
		default: {
			if ((str.charAt(0) >= 'a' && str.charAt(0) <= 'z')
					|| (str.charAt(0) >= 'A' && str.charAt(0) <= 'Z')
					|| str.charAt(0) == '_') {
				//合法标识符必须由英文字母或下划线开头
				return Classes.LEGAL_IDENTIFIER;
			} else {
				//说明第一个字符是数字；以数字开头，后面还是字母，说明是非法标识符
				for (int i=1; i<str.length(); i++) {
					if (isLetter(str.charAt(i))) {
						return  Classes.ILLEGAL_IDENTIFIER;
					}
				}
				//是纯数字
				return Classes.VALUE;
			}
		}
		
		}
	}

	//判断一个字符是否是字母
	private static boolean isLetter(char c) {
		if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
			return true;
		}
		return false;
	}
	
	
	/**词法分析：对从文件读取到的字符串一个一个字符的读取并进行分析
	 * @param str:将要分析的目的字符串
	 * @return ArrayList:分析的结果是以二元组的形式放在map中，再将map添加到ArrayList集合中返回
	 */
	public static ArrayList<Map<String, Object>> lax(String str) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c >= '0' && c <= '9') {
				sb = sb.append(c);
			} else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_') {
				sb = sb.append(c);
			} else {
				switch (c) {
				case Classes.SPACE: 	// ' '
					if (sb.length() > 0) {
						addMapToList(getClassify(sb), sb.toString());
					}
					sb.setLength(0);
					break;
				case Classes.HUICHE: // '\n'
					if (sb.length() > 0) {
						addMapToList(getClassify(sb), sb.toString());
					}
					sb.setLength(0);
					break;
				case Classes.PERCENT://%
					if (sb.length() > 0) {
						addMapToList(getClassify(sb), sb.toString());
					}
					if (isLetter(str.charAt(i+1))) { // 判断是不是格式化输出
						addMapToList(String.valueOf(Classes.FORMAT), "%" + str.charAt(i + 1));
						i++;
					} else {
						addMapToList(String.valueOf(Classes.SPECIALCHARACTER), c);
					}
					sb.setLength(0);
					break;
				case Classes.SLASH:		//'\'
					//判断是不是转义字符，如"\n","\t"等
					if (sb.length() > 0) {
						addMapToList(getClassify(sb), sb.toString());
					}
					// 判断是不是"\n"
					if (str.charAt(i + 1) == 'n') { 
						addMapToList(String.valueOf(Classes.ESC), "\\n");
						i++;
					} else if (str.charAt(i + 1) == 't') {
						// 判断 是不是"\t"
						addMapToList(String.valueOf(Classes.ESC), "\\t");
						i++;
					}
					sb.setLength(0);
					break;
				case Classes.BIG: 	// '>'
					if (sb.length() > 0) {
						addMapToList(getClassify(sb), sb.toString());
					}
					// 判断是不是>=
					if (str.charAt(i + 1) == '=') {
						addMapToList(Classes.OPERATOR, ">=");
						sb.setLength(0);
						i++;
						break;
					}
					//若不是，则按特殊字符处理
					addMapToList(String.valueOf(Classes.SPECIALCHARACTER), c);
					sb.setLength(0);
					break;
				case Classes.SMALL: // '<'
					if (sb.length() != 0) {
						addMapToList(getClassify(sb), sb.toString());
					}
					// 判断是不是<=
					if (str.charAt(i + 1) == '=') {
						addMapToList(Classes.OPERATOR, "<=");
						sb.setLength(0);
						i++;
						break;
					}
					//若不是，则按特殊字符处理
					addMapToList(String.valueOf(Classes.SPECIALCHARACTER), c);
					sb.setLength(0);
					break;
				case Classes.ADD:		//'+'
					if (sb.length() > 0) {
						addMapToList(getClassify(sb), sb.toString());
					}
					if (str.charAt(i+1) == '+') {
						//判断是不是"++"运算符
						addMapToList(Classes.OPERATOR, "++");
						sb.setLength(0);
						i++;
						break;
					} else if (str.charAt(i+1) == '=') {
						//判断是不是"+="运算符
						addMapToList(Classes.OPERATOR, "+=");
						sb.setLength(0);
						i++;
						break;
					} 
					//若不是，则按特殊字符处理
					addMapToList(String.valueOf(Classes.SPECIALCHARACTER), c);
					sb.setLength(0);
					break;
				case Classes.SUBTRACT:		//'-'
					if (sb.length() > 0) {
						addMapToList(getClassify(sb), sb.toString());
					}
					if (str.charAt(i+1) == '-') {
						//判断是不是"--"运算符
						addMapToList(Classes.OPERATOR, "--");
						sb.setLength(0);
						i++;
						break;
					} else if (str.charAt(i+1) == '=') {
						//判断是不是"-="运算符
						addMapToList(Classes.OPERATOR, "-=");
						sb.setLength(0);
						i++;
						break;
					} 
					//若不是，则按特殊字符处理
					addMapToList(String.valueOf(Classes.SPECIALCHARACTER), c);
					sb.setLength(0);
					break;
				case Classes.MULTIPLY:		//*
					if (sb.length() > 0) {
						addMapToList(getClassify(sb), sb.toString());
					}
					//判断是不是"*+"运算符
					if (str.charAt(i+1) == '=') {
						addMapToList(Classes.OPERATOR, "*=");
						sb.setLength(0);
						i++;
						break;
					} 
					//若不是，则按特殊字符处理
					addMapToList(String.valueOf(Classes.SPECIALCHARACTER), c);
					sb.setLength(0);
					break;
				case Classes.DEVIDE: // '/'
					// 判断是不是"//"注释
					if (str.charAt(i + 1) == Classes.DEVIDE) {
						i++;
						while (str.charAt(i) != Classes.HUICHE) {
							i++;
						}
						sb.setLength(0);
						break;
					}
					// 判断是不是"/*......*/"注释
					if (str.charAt(i + 1) == Classes.MULTIPLY) {
						i++;
						while (true) {
							if (str.charAt(i) == '*' && str.charAt(i + 1) == '/') {
								sb.setLength(0);
								break;
							} else {
								i++;
							}
						}
						i++;
						break;
					}
					//判断不是注释后，进行运算符的判断
					if (sb.length() != 0) {
						addMapToList(getClassify(sb), sb.toString());
					}
					// 判断是不是"/="运算符
					if (str.charAt(i + 1) == '=') { 
						addMapToList(Classes.OPERATOR, "/=");
						sb.setLength(0);
						i++;
						break;
					}
					addMapToList(String.valueOf(Classes.SPECIALCHARACTER), c);
					sb.setLength(0);
					break;
				case Classes.EXCALMATORY: // '!'
				case Classes.FENHAO:   			// ';'
				case Classes.JINGHAO: 			// '#'
				case Classes.EQUAL:				// '='
				case Classes.POINT:					// '.'
				case Classes.DOUHAO:			// ','
				case Classes.WENHAO:			// '?'
				case Classes.MAOHAO:			// ':'
				case Classes.SHUANGYING:	// ' " '
				case Classes.ZUOYUAN:			// '('
				case Classes.YOUYUAN:			// ')'
				case Classes.ZUOFANG:			// '['
				case Classes.YOUFANG:			// ']'
				case Classes.ZUOHUA:				// '{'
				case Classes.YOUHUA:			// '}'
					if (sb.length() > 0) {
						addMapToList(getClassify(sb), sb.toString());
					}
					addMapToList(Classes.SPECIALCHARACTER, c);
					sb.setLength(0);
					break;
				}
			}

		}
		return list;
	}
	
	private static void addMapToList(String key, Object value) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(key, value);
		list.add(map);
	}

}

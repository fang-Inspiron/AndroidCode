package com.nemo;

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

/**
 * @author yxw19
 *
 */
/**
 * @author yxw19
 *
 */
public class Main {

	public static ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	public static void main(String[] args) {
		print();
	}

	/**
	 * 从源文件读取
	 * 
	 * @param fileName
	 *            文件名
	 * @return 读取到的文件字符串
	 */
	public static String read(String fileName) {
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(fileName)), "utf-8"));
			StringBuffer s = new StringBuffer();
			String str = br.readLine();
			s.append(str);
			while (str != null) {
				str = br.readLine();
				if (str != null)
					s.append("\n" + str);
			}
			br.close();
			return s.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将得到的字符串分类
	 * 
	 * @param 得到的字符串
	 * @return 字符串的类别，如：标识符、关键字等
	 */
	/**
	 * @param s
	 * @return
	 */
	public static String getClassify(StringBuffer s) {
		String str = s.toString();
		switch (str) {
		case Clazz.MAIN:
			return Clazz.GUANJIANZI;
		case Clazz.INCLUDE:
			return Clazz.GUANJIANZI;
		case Clazz.BREAK:
			return Clazz.GUANJIANZI;
		case Clazz.CASE:
			return Clazz.GUANJIANZI;
		case Clazz.CHAR:
			return Clazz.GUANJIANZI;
		case Clazz.CONST:
			return Clazz.GUANJIANZI;
		case Clazz.CONTINUE:
			return Clazz.GUANJIANZI;
		case Clazz.DEFINE:
			return Clazz.GUANJIANZI;
		case Clazz.DEFAULT:
			return Clazz.GUANJIANZI;
		case Clazz.DO:
			return Clazz.GUANJIANZI;
		case Clazz.DOUBLE:
			return Clazz.GUANJIANZI;
		case Clazz.ELSE:
			return Clazz.GUANJIANZI;
		case Clazz.ENUM:
			return Clazz.GUANJIANZI;
		case Clazz.EXTERN:
			return Clazz.GUANJIANZI;
		case Clazz.FLOAT:
			return Clazz.GUANJIANZI;
		case Clazz.FOR:
			return Clazz.GUANJIANZI;
		case Clazz.GOTO:
			return Clazz.GUANJIANZI;
		case Clazz.IF:
			return Clazz.GUANJIANZI;
		case Clazz.INT:
			return Clazz.GUANJIANZI;
		case Clazz.REGISTER:
			return Clazz.GUANJIANZI;
		case Clazz.RETURN:
			return Clazz.GUANJIANZI;
		case Clazz.SHORT:
			return Clazz.GUANJIANZI;
		case Clazz.SIGNED:
			return Clazz.GUANJIANZI;
		case Clazz.SIZEOF:
			return Clazz.GUANJIANZI;
		case Clazz.STATIC:
			return Clazz.GUANJIANZI;
		case Clazz.SWITCH:
			return Clazz.GUANJIANZI;
		case Clazz.TYPEDEF:
			return Clazz.GUANJIANZI;
		case Clazz.UNION:
			return Clazz.GUANJIANZI;
		case Clazz.UNSIGNED:
			return Clazz.GUANJIANZI;
		case Clazz.VOID:
			return Clazz.GUANJIANZI;
		case Clazz.VOLATILE:
			return Clazz.GUANJIANZI;
		case Clazz.WHILE:
			return Clazz.GUANJIANZI;
		default: {

			if ((str.charAt(0) >= 'a' && str.charAt(0) <= 'z') || (str.charAt(0) >= 'A' && str.charAt(0) <= 'Z')
					|| str.charAt(0) == '_') {
				return Clazz.BIAOSHIFU;
			} else {
				for (int i = 1; i < str.length(); i++) {
					if (isLetter(str.charAt(i)) || str.charAt(i) == '_') {
						return Clazz.UNBIAOSHIFU;
					}
				}
				return Clazz.SHUZI;
			}
		}
		}
	}

	public static ArrayList<Map<String, Object>> fenlei(String file) {
		StringBuffer temp = new StringBuffer("");

		for (int i = 0; i < file.length(); i++) {
			char c = file.charAt(i);
			if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_') {
				temp = temp.append(c);
			} else if (c >= '0' && c <= '9') {
				temp = temp.append(c);
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				switch (c) {
				case Clazz.FENHAO: // ;
				case Clazz.JINGHAO: // #
				case Clazz.DENGYUHAO: // =
				case Clazz.DIAN: // .
				case Clazz.DOUHAO: // ,
				case Clazz.WENHAO: // ?
				case Clazz.MAOHAO: // :
				case Clazz.SHUANGYING: // "
				case Clazz.ZUOHUA:// {
				case Clazz.YOUHUA:// }
				case Clazz.ZUOFANG:// {
				case Clazz.YOUFANG:// }
				case Clazz.ZUOYUAN:// (
				case Clazz.YOUYUAN:// )
					if (temp.length() != 0) {
						addToList(getClassify(temp), temp.toString());
					}
					addToList(String.valueOf(Clazz.TESHUFUHAO), c);
					temp.setLength(0);
					break;
				case Clazz.KONGGE: // ' '
					if (temp != null && temp.length() > 0) {
						addToList(getClassify(temp), temp.toString());
					}
					temp.setLength(0);
					break;
				case Clazz.BAIFENHAO: // %
					if (temp.length() != 0) {
						addToList(getClassify(temp), temp.toString());
					}
					if (isLetter(file.charAt(i + 1))) { // 判断是不是格式化输出
						addToList(String.valueOf(Clazz.TESHUFUHAO), "%" + file.charAt(i + 1) + "");
						i++;
					} else {
						addToList(String.valueOf(Clazz.TESHUFUHAO), c);
					}
					temp.setLength(0);
					break;
				case Clazz.HUICHE: // \n
					if (temp.length() != 0) {
						addToList(getClassify(temp), temp.toString());
					}
					temp.setLength(0);
					break;
				case Clazz.JIA:
					if (file.charAt(i + 1) == '+') { // 判断是不是++
						if (temp.length() != 0) {
							addToList(getClassify(temp), temp.toString());
						}
						addToList(Clazz.YUNSUANFU, "++");
						temp.setLength(0);
						i++;
						break;
					}
					if (file.charAt(i + 1) == '=') {// 判断是不是+=
						if (temp.length() != 0) {
							addToList(getClassify(temp), temp.toString());
						}
						addToList(Clazz.YUNSUANFU, "+=");
						temp.setLength(0);
						i++;
						break;
					}
					if (temp.length() != 0)
						addToList(getClassify(temp), temp.toString());
					addToList(String.valueOf(Clazz.TESHUFUHAO), c);
					temp.setLength(0);
					break;
				case Clazz.JIAN:
					if (file.charAt(i + 1) == '-') { // 判断是不是--
						if (temp.length() != 0) {
							addToList(getClassify(temp), temp.toString());
						}
						addToList(Clazz.YUNSUANFU, "--");
						temp.setLength(0);
						i++;
						break;
					}
					if (file.charAt(i + 1) == '=') {// 判断是不是-=
						if (temp.length() != 0) {
							addToList(getClassify(temp), temp.toString());
						}
						addToList(Clazz.YUNSUANFU, "-=");
						temp.setLength(0);
						i++;
						break;
					}
					if (temp.length() != 0)
						addToList(getClassify(temp), temp.toString());
					addToList(String.valueOf(Clazz.TESHUFUHAO), c);
					temp.setLength(0);
					break;
				case Clazz.CHEN:
					if (file.charAt(i + 1) == '=') {// 判断是不是*=
						if (temp.length() != 0) {
							addToList(getClassify(temp), temp.toString());
						}
						addToList(Clazz.YUNSUANFU, "*=");
						temp.setLength(0);
						i++;
						break;
					}
					if (temp.length() != 0)
						addToList(getClassify(temp), temp.toString());
					addToList(String.valueOf(Clazz.TESHUFUHAO), c);
					temp.setLength(0);
					break;
				case Clazz.DAYUHAO:// 大于号
					if (file.charAt(i + 1) == '=') {// 判断是不是>=
						if (temp.length() != 0) {
							addToList(getClassify(temp), temp.toString());
						}
						addToList(Clazz.YUNSUANFU, ">=");
						temp.setLength(0);
						i++;
						break;
					}
					addToList(getClassify(temp), temp.toString());
					addToList(String.valueOf(Clazz.TESHUFUHAO), c);
					temp.setLength(0);
					break;
				case Clazz.XIAOYUHAO:// 小于号
					if (file.charAt(i + 1) == '=') {// 判断是不是<=
						if (temp.length() != 0) {
							addToList(getClassify(temp), temp.toString());
						}
						addToList(Clazz.YUNSUANFU, "<=");
						temp.setLength(0);
						i++;
						break;
					}
					if(temp.length()!=0)
						addToList(getClassify(temp), temp.toString());
					addToList(String.valueOf(Clazz.TESHUFUHAO), c);
					temp.setLength(0);
					break;
				case Clazz.FANXIEGANG:
					// //注释
					if (file.charAt(i + 1) == Clazz.FANXIEGANG) {
						i++;
						while (file.charAt(i) != Clazz.HUICHE) {
							i++;
						}
						temp.setLength(0);
						break;
					}
					// / **/注释
					if (file.charAt(i + 1) == Clazz.CHEN) {
						i++;
						while (true) {
							if (file.charAt(i) == '*' && file.charAt(i + 1) == '/') {
								temp.setLength(0);
								break;
							} else {
								i++;
							}
						}
						i++;
						break;
					}
					if (file.charAt(i + 1) == '=') { // 判断 /= 运算符
						if (temp.length() != 0) {
							addToList(getClassify(temp), temp.toString());
						}
						addToList(Clazz.YUNSUANFU, "/=");
						temp.setLength(0);
						i++;
						break;
					}
					if (temp.length() != 0) {
						addToList(getClassify(temp), temp.toString());
					}
					addToList(String.valueOf(Clazz.TESHUFUHAO), c);
					temp.setLength(0);
					break;
				case '\\':
					if (file.charAt(i + 1) == 'n') { // 判断 \n
						if (temp.length() != 0) {
							addToList(getClassify(temp), temp.toString());
						}
						addToList(String.valueOf(Clazz.HUANHANGFU), "\\n");
						i++;
					} else if (file.charAt(i + 1) == 't') {// 判断 \t
						if (temp.length() != 0) {
							addToList(getClassify(temp), temp.toString());
						}
						addToList(String.valueOf(Clazz.ZHIBIAOFU), "\\t");
						i++;
					}
					temp.setLength(0);
					break;
				}
			}
		}
		return list;
	}

	/**
	 * 将已分类的字符或字符串放入List集合中
	 * 
	 * @param key
	 *            类别
	 * @param value
	 *            值
	 */
	public static void addToList(String key, Object value) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(key, value);
		list.add(map);
	}

	/**
	 * @param 判断字符c是不是英文
	 * @return
	 */
	public static boolean isLetter(char c) {
		if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 将得到的List集合打印出来
	 */
	public static void print() {
		ArrayList<Map<String, Object>> ls = fenlei(read("F:input.c"));
		for (int i = 0; i < ls.size(); i++) {
			Map<String, Object> map = ls.get(i);
			Set<Entry<String, Object>> entries = map.entrySet();
			for (Entry<String, Object> entry : entries) {
				System.out.println("<" + entry.getKey() + ",'" + entry.getValue() + "'>");
			}
		}
	}
}

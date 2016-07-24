package grammar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class FirstFollow {

	char startSymbol;
	ArrayList<String> strList;
	Set<Character> setVn;
	Set<Character> setVt;
	ArrayList<StringBuffer> firstList;
	ArrayList<StringBuffer> followtList;
	StringBuffer order = new StringBuffer();

	public FirstFollow() {
		// TODO Auto-generated constructor stub
		strList = new ArrayList<String>();
		setVn = new TreeSet<>();
		setVt = new TreeSet<>();
	}

	public static void main(String[] args) {
		String file = "grammar.txt";
		FirstFollow firstFollow = new FirstFollow();

		firstFollow.readFile(file);
		firstFollow.getVnVt();
		firstFollow.getFirst();
		firstFollow.getFollow();
	}

	private void getFollow() {
		followtList = new ArrayList<StringBuffer>();
		// 遍历求每个非终结符的follow集
		for (int n=0; n<order.length(); n++) {
			char V=order.charAt(n);
			StringBuffer sb = new StringBuffer();
			
			// 如果V是文法的开始符号，置#于follow(v)中
			if (V == startSymbol) 
				sb.append("#");
			
			// 查找产生式的右部含有V的产生式
			for (int i = 0; i < strList.size(); i++) {
				String str = strList.get(i).toString();
				for (int j = 3; j < str.length(); j++) {
					//若V是产生式最后一个字符，[A->aV]，把follow[A]加入到follow[V]中
					if (str.charAt(j)==V && (j+1==str.length())) {
						for (int k=0; k<order.length(); k++)
							if (order.charAt(k)==str.charAt(0)) {
								sb.append(followtList.get(k).toString());
								break;
							}
					} else	if (str.charAt(j) == V && (j + 1 < str.length())) {
					//V不是产生式最后一个字符
						if (!setVn.contains(str.charAt(j+1)) && !setVt.contains(str.charAt(j+1))) {
							//V后面是“|”，既不是非终结符也不是终结符
							break;
						} else if (setVt.contains(str.charAt(j + 1))) {
							// V的下一个字符x是终结符，加入到follow(v)
							sb.append(str.charAt(j + 1));
							break;
						} else { 
							// V的下一个字符x是非终结符
							char x = str.charAt(j + 1);
							// [A->aVx]，把first(x)中的非ε加到follow(V)中
							int index = 0;
							Iterator<Character> it = setVn.iterator();
							while (it.hasNext()) {
								if (x == it.next()) {
									break;
								}
								index++;
							}
							String strFirst = firstList.get(index).toString();
							ArrayList<String> arraylistFirst = new ArrayList<>();
							for (int k = 0; k < strFirst.length(); k++)
								arraylistFirst.add(strFirst.charAt(k)+"");
							arraylistFirst.remove("ε");
							for (int m = 0; m < arraylistFirst.size(); m++)
								sb.append(arraylistFirst.get(m));
							
							// 判断x能否推出ε
							boolean flag = false;
							for (int r = 0; r < strList.size(); r++) {
								String str2 = strList.get(r).toString();
								if (str2.charAt(0) == x && str2.contains("ε")) {
									flag = true;
									break;
								}
							}
							// 若[A->aVx]，且x->ε，将follow(A)加入到follow(V)中 
							if (flag) {
								for (int k=0; k<order.length(); k++)
									if (order.charAt(k)==x) {
										sb.append(followtList.get(k).toString());
										break;
									}
							}
						}
					}

				}
			}
			followtList.add(sb);
		}
		printFollow();
	}
	
	private void printFollow() {
		ArrayList<TreeSet<Character>> followTreeSet = new ArrayList<>();
		for (int i=0; i<followtList.size(); i++) {
			String item = followtList.get(i).toString();
			TreeSet<Character> set = new TreeSet<>();
			for (int j=0; j<item.length(); j++) {
				set.add(item.charAt(j));
			}
			followTreeSet.add(set);
		}
		
		for (int i=0; i<order.length(); i++) {
			System.out.print("FOLLOW(" + order.charAt(i) + ") = {");
			StringBuffer sb =new StringBuffer(); 
			TreeSet<Character> set = followTreeSet.get(i);
			Iterator<Character> it = set.iterator();
			while (it.hasNext()) 
				sb.append(it.next());
			for (int j = 0; j < sb.length(); j++) {
				if (j != sb.length() - 1)
					System.out.print(sb.charAt(j) + ",");
				else
					System.out.print(sb.charAt(j));
			}
			System.out.println("}");
		}
	}

	private void getFirst() {
		firstList = new ArrayList<StringBuffer>();
		// 遍历求每个非终结符的first集
		Iterator<Character> it = setVn.iterator();
		while (it.hasNext()) {
			char v = it.next();
			StringBuffer sb = new StringBuffer();
			sb = getTerminator(v, sb);// 求非终结符v的终结符，存放在sb中
			firstList.add(sb);
		}
		// 输出first集
		printFirst();
	}

	private void printFirst() {
		Iterator<Character> it = setVn.iterator();
		int i = 0;
		while (it.hasNext()) {
			System.out.print("FIRST(" + it.next() + ") = {");
			String firstStr = firstList.get(i).toString();
			for (int j = 0; j < firstStr.length(); j++) {
				if (j != firstStr.length() - 1)
					System.out.print(firstStr.charAt(j) + ",");
				else
					System.out.print(firstStr.charAt(j));
			}
			System.out.println("}");
			i++;
		}
	}

	// 求非终结符v的终结符，存放在sb中
	private StringBuffer getTerminator(char v, StringBuffer sb) {
		for (int i = 0; i < strList.size(); i++) {
			// 取出其中一条产生式
			String str = strList.get(i).toString();
			if (str.charAt(0) == v) {
				// 产生式的右部第一个符号（箭头后[j=3]）是终结符 ，将该终结符加入到 v 的first集中
				char right1 = str.charAt(3);
				if (setVt.contains(right1)) {
					sb.append(right1);
				}
				// 判断‘|’后的第一个字符能否推出终结符，若能，将该终结符加入到 v 的first集中
				if (str.contains("|")) {
					for (int j = 3; j < str.length(); j++) {
						if (str.charAt(j) == '|') {
							char c = str.charAt(j + 1);
							Iterator<Character> it = setVt.iterator();
							while (it.hasNext()) {
								if (it.next() == c)
									sb.append(c);
							}
						}
					}
				}
				// 右部的第一个非终结符v可以推导
				if (setVn.contains(right1)) {
					getTerminator(right1, sb);
				}
			}
		}
		return sb;
	}

	private void getVnVt() {
		for (int i = 0; i < strList.size(); i++) {
			String str = strList.get(i).toString();
			// 每一行第一个字符是非终结符，加入到setVn中（set集合保证了元素的不重复性）
			setVn.add(str.charAt(0));
		}
		for (int i = 0; i < strList.size(); i++) {
			String str = strList.get(i).toString();
			for (int j = 3; j < str.length(); j++) {
				// 从产生式右部（箭头后[j=3]）逐个遍历字符，若该字符不在非终结符集合，便是终结符，加入到终结符结合
				char c = str.charAt(j);
				if (!setVn.contains(c) && (c != '|')) {
					setVt.add(c);
				}
			}
		}
		printVnVt();
	}

	private void printVnVt() {
		System.out.print("非终结符：");
		Iterator<Character> it = setVn.iterator();
		while (it.hasNext()) {
			System.out.print(it.next() + "  ");
		}
		System.out.print("\n终结符：");
		it = setVt.iterator();
		while (it.hasNext()) {
			System.out.print(it.next() + "  ");
		}
		System.out.println();
	}

	private void readFile(String fileName) {
		BufferedReader br;
		boolean flag = true;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					new File(fileName))));
			String str = null;
			System.out.println("文法如下：");
			while ((str = br.readLine()) != null) {
				if (flag) {
					startSymbol = str.charAt(0);
					flag = false;
				}
				order.append(str.charAt(0));
				strList.add(str);
				System.out.println(str);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

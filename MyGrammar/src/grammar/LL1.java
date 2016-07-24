package grammar;

import java.util.Scanner;
import java.util.Stack;

public class LL1 {
	Table table;
	Stack<Character> signstack;
	Stack<Character> inputstack;
	int row = 7;
	int col = 7;

	public LL1() {
		// TODO Auto-generated constructor stub
		signstack = new Stack<Character>();
		inputstack = new Stack<Character>();
		table = new Table();
	}

	public Boolean isParams(String s) {
		boolean flag = true;
		char[] arrays = s.toCharArray();
		char[] left = table.getLeft();
		char[] top = table.getHead();
		String[][] arc = table.getArc();
		inputstack.push('#');
		for (int i = arrays.length - 1; i >= 0; i--) {
			inputstack.push(arrays[i]);
		}
		int i = 0;
		signstack.push('#');
		signstack.push(left[0]);
		while (flag) {
			System.out.print("符号栈：" + signstack+"\t");
			System.out.println("输入串：" + inputstack);
			char x = signstack.peek();	//Stack栈顶符号x
			char a = inputstack.peek();	//当前输入符号a
			System.out.println("(" + x + "," + a + ")");
			if (x == 'ε') {
				signstack.pop();
			} else if (x == a) {
				if (a == '#' && x == '#') {
					return true;
				} else {
					signstack.pop();
					inputstack.pop();
				}
			} else if (a == '#' && x != '#') {
				int l = table.getLeftIndex(x);
				boolean flag2 = false;
				for (int j = 0; j < col; j++) {
					if (arc[l][j] != null && arc[l][j].equals("ε")) {
						flag2 = true;
						break;
					}
				}
				// 判断符号栈中的元素能不能推出‘ε’
				if (flag2) {
					signstack.pop();
				} else {
					return false;
				}
			} else {
				int l = table.getLeftIndex(x);
				int t = table.getHeadIndex(a);
				if (l == -1 || t == -1) {
					return false;
				}
				signstack.pop();
				if (l < row && t < col) {
					String temp = arc[l][t];
					if (temp == null)
						return false;
					putString(temp);

				}

			}
		}
		return false;
	}

	public void putString(String str) {
		char[] characters = str.toCharArray();
		for (int i = characters.length - 1; i >= 0; i--) {
			signstack.push(characters[i]);
		}
	}

	public static void main(String[] args) {
		LL1 control = new LL1();
		System.out.println("请输入语法串：");
		Scanner scan = new Scanner(System.in);
		String word = scan.nextLine();
		if (control.isParams(word)) {
			System.out.println(word + "符合语法");
		} else
			System.out.println(word + "不符合语法");

	}

}

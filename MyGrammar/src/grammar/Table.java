package grammar;

class Table {
	int row = 7;
	int col = 7;
	String arc[][] = new String[row][col];
	char left[] = { 'E', 'e', 'T', 't', 'F', 'f', 'P' };
	char head[] = { '+', '*', '(', ')', 'i', '^', '#' };

	public Table() {
		arc[0][2] = "Te";
		arc[0][4] = "Te";
		arc[1][0] = "+Te";
		arc[1][3] = "ε";
		arc[1][6] = "ε";
		arc[2][2] = "Ft";
		arc[2][4] = "Ft";
		arc[3][0] = "ε";
		arc[3][1] = "*Ft";
		arc[3][3] = "ε";
		arc[3][6] = "ε";
		arc[4][2] = "Pf";
		arc[4][4] = "Pf";
		arc[5][0] = "ε";
		arc[5][1] = "ε";
		arc[5][3] = "ε";
		arc[5][5] = "^F";
		arc[5][6] = "ε";
		arc[6][2] = "(E)";
		arc[6][4] = "i";
	}

	public String[][] getArc() {
		return this.arc;
	}

	public char[] getHead() {
		return this.head;
	}

	public char[] getLeft() {
		return this.left;
	}

	public int getHeadIndex(char ch) {
		for (int i = 0; i < head.length; i++) {
			if (head[i] == ch) {
				return i;
			}
		}
		return -1;
	}

	public int getLeftIndex(char ch) {

		for (int i = 0; i < left.length; i++) {
			if (left[i] == ch) {
				return i;
			}
		}
		return -1;
	}
}
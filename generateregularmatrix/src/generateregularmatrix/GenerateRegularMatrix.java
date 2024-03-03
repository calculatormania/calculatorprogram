package generateregularmatrix;

import java.security.SecureRandom;
import java.util.Random;

//正則行列の自動生成プログラム
public class GenerateRegularMatrix {

	public static void main(String[] args) {

		int n = 3;   //行列の次数
		int m = 15;  //成分の上限
		Ratio [][] A = createUnitMatrix(n);
		Random rnd = new SecureRandom();
		boolean judge = true;

		for (int i = 0; i <	100; i++) {
			int t = 200;
			
			for (int times = 0; times < t; times++) {
				int s = rnd.nextInt(2);
				int p = rnd.nextInt(n);
				int q = rnd.nextInt(n);
				if (s == 0 && p!= q) {
					int rep = rnd.nextInt(2);
					if (rep == 0) {
						rowReplace(A,p,q);
					} else {
						columnReplace(A,p,q);
					}
				} else if (s == 1 && p!=q) {
					int ope = rnd.nextInt(2);
					int c = rnd.nextInt(3)-1;
					if (ope == 0) {
						rowOperations(A, p, q, new Ratio(c,1));
					} else {
						columnOperations(A, p, q, new Ratio(c,1));
					} 
				}
				if (times == t-1 && matrixJudge(A,m) == false) {
					t++;
				}
				if (t == 350 && matrixJudge(A,m) == false) {
					judge = false;
					break;
				} else {
					judge = true;
				}
			}
			
			if (judge == true ) {
				Ratio.showMatrix(A);
				System.out.println("-----↑生成した正則行列，↓はその逆行列-----------------------------------");
				Ratio.showMatrix(inverseMatrix(A));
				System.out.println("pppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp");
			}
			A = createUnitMatrix(n);
		}
	}
	public static Ratio[][] substitutionMatrix(Ratio [][] A, Ratio [][] B, int sRow, int sCol, int eRow, int eCol) {

		for (int i = sRow; i < eRow; i++) {
			for (int j = sCol; j < eCol; j++) {
				if (j < A[0].length) {
					A[i][j] = new Ratio(B[i][j].getNum(), B[i][j].getDenom());	
				} 
			}
		}

		return A;
	}

	public static Ratio[][] inverseMatrix(Ratio [][] A) {

		Ratio [][] B = new Ratio [A.length][2*A[0].length];

		for (int i = 0; i < B.length; i++) {
			for (int j = 0; j < B[0].length; j++) {
				if (j < A[0].length) {
					B[i][j] = new Ratio(A[i][j].getNum(), A[i][j].getDenom());	
				} else {
					if (i == j - A[0].length) {
						B[i][j] = new Ratio(1, 1);
					} else {
						B[i][j] = new Ratio(0, 1);
					}
				}
			}
		}

		int [] Box = {0,0};
		int mainColumn = 0;

		for (int row = 0; row < B.length; row++) {
			Box[0] = search(B,row, mainColumn, A.length, A[0].length)[0]; //row行目の主成分の候補を探す
			Box[1] = search(B,row, mainColumn, A.length, A[0].length)[1]; //Boxの中身はその成分の座標

			if (Box[0] == -1 || Box[1] == -1 || mainColumn >= A[0].length) {
				break;
			}

			if (row != Box[0]) {
				rowReplace(B,row, Box[0]);// row行目と行を入れ替える
			}
			mainColumn = Box[1];

			for (int r = 0; r < A.length; r++) {
				if(r !=row) {
					rowErase(B,row, r, mainColumn);// 他の行の先頭を消す
				}
			}		
			rowDiv(B,row, mainColumn);
			Ratio.reduceAll(B);
			mainColumn++;
		}

		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[0].length; j++) {
				if (j < A[0].length) {
					A[i][j] = new Ratio(B[i][j+A[0].length].getNum(), B[i][j+A[0].length].getDenom());	
				} 
			}
		}

		return A;
	}

	public static int [] search(Ratio [][] A, int startrow, int startcolumn, int endrow, int endcolumn) { //Aのstartrow行以降の主成分の候補を探す

		boolean zeroJudge = false;

		int a = -1;
		int b = -1;

		for (int j = startcolumn; j < endcolumn; j++) {
			for (int i = startrow; i < endrow; i++) {
				if (A[i][j].getNum() != 0) {
					a = i;
					b = j;
					zeroJudge = true;
					break;
				} 
			}
			if (zeroJudge == true) {
				break;
			}
		}

		int [] box = {a, b};

		return box;
	}

	public static Ratio [][] rowDiv(Ratio [][] A, int row, int column) { //Aのrow行目をAの(row,column)成分で割る

		for (int j = A[0].length-1; j >= column; j--) {
			A[row][j] = Ratio.div(A[row][j], A[row][column]);
		}

		return A;
	}

	public static Ratio [][] rowErase(Ratio [][] A, int p, int q, int column) { //Aのq行目の先頭項を，p行目を使って消す

		for (int j = A[0].length-1; j >= column; j--) {
			A[q][j] = Ratio.minus(A[q][j], Ratio.mul(Ratio.div((A[q][column]), A[p][column]),A[p][j]));
		}//p行を使ってq行の先頭成分を消す（p行column列に主成分あることが前提）

		return A;
	}

	public static boolean matrixJudge(Ratio [][] A, int n) {

		boolean judge = true;

		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[0].length; j++) {
				if (Math.abs(A[i][j].getNum()) > n) {
					judge = false;
					break;
				}
			}
			if (judge == false) {
				break;
			}
		}

		return judge;
	}

	public static Ratio[][] createUnitMatrix(int n) {//n次の単位行列を作る

		Ratio [][] E = new Ratio [n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j) {
					E[i][j] = new Ratio(1, 1);	
				} else {
					E[i][j] = new Ratio(0, 1);	
				}
			}
		}

		return E;
	}

	public static Ratio [][] rowReplace(Ratio [][] A, int p, int q) {// Aのp行目とq行目を入れ替える
		int Num;
		int Denom;

		for (int j = 0; j < A[0].length; j++) {
			Num = A[p][j].getNum();
			A[p][j].setNum(A[q][j].getNum());
			A[q][j].setNum(Num);

			Denom = A[p][j].getDenom();
			A[p][j].setDenom(A[q][j].getDenom());
			A[q][j].setDenom(Denom);
		}

		return A;
	}

	public static Ratio [][] columnReplace(Ratio [][] A, int p, int q) {// Aのp行目とq行目を入れ替える
		int Num;
		int Denom;

		for (int i = 0; i < A[0].length; i++) {
			Num = A[i][p].getNum();
			A[i][p].setNum(A[i][q].getNum());
			A[i][q].setNum(Num);

			Denom = A[p][i].getDenom();
			A[i][p].setDenom(A[i][q].getDenom());
			A[i][q].setDenom(Denom);
		}

		return A;
	}

	public static Ratio [][] rowOperations(Ratio [][] A, int p, int q, Ratio c) {
		for (int j = 0; j < A[0].length; j++) {
			A[q][j] = Ratio.reduce(Ratio.plus(A[q][j],Ratio.mul(c,A[p][j])));
		}
		return A;
	}

	public static Ratio [][] columnOperations(Ratio [][] A, int p, int q, Ratio c) {
		for (int i = 0; i < A.length; i++) {
			A[i][q] = Ratio.reduce(Ratio.plus(A[i][q],Ratio.mul(c,A[i][p])));
		}
		return A;
	}

}
//有理数体上の行列簡約プログラム
import java.util.ArrayList;
import java.util.List;
public class RationalMain {

	public static void main(String[] args) {

		/*int [][][] A = { { {1,1},{2,1},{1,1},{2,1} },
				{ {-1,1},{-3,1},{3,1},{-2,1} },
				{ {0,1},{2,1},{1,1},{2,1} },
				{ {1,1},{1,1},{-4,1},{1,1} }
		};*/

		/*int [][][] A = { { {0,1},{0,1},{-1,1},{1,1},{3,1},{2,1} },
				{ {0,1},{0,1},{2,1},{1,1},{-3,1},{4,1} },
				{ {0,1},{0,1},{1,1},{0,1},{-2,1},{-1,1} },
				{ {0,1},{0,1},{1,1},{2,1},{0,1},{7,1} },
				{ {0,1},{0,1},{0,1},{0,1},{1,1},{0,1} }
		};*/


		/*int [][][] A = { { {2,1},{7,1},{4,1},{19,1},{-7,1} },
				{ {8,1},{-7,1},{-2,1},{3,1},{5,1} },
				{ {9,1},{2,1},{3,1},{10,1},{-3,1} },
				{ {-5,1},{4,1},{1,1},{2,1},{-3,1} }
		};*/

		/*int [][][] A = { { {2,1},{1,1},{1,1},{1,1} },
				{ {0,1},{4,1},{1,1},{1,1} },
				{ {0,1},{0,1},{5,1},{1,1} },
				{ {0,1},{0,1},{0,1},{7,1} }
		};*/

		/*int [][][] A = { { {0,1},{-3,1},{3,1},{-2,1},{1,1},{-7,1} },
				{ {0,1},{-3,1},{3,1},{-2,1},{1,1},{-7,1} },
				{ {0,1},{3,1},{-3,1},{2,1},{0,1},{9,1} },
				{ {0,1},{-2,1},{2,1},{-1,1},{1,1},{-4,1} },
		};*/

		int [][][] A = {  
				{ {1,1},{4,1},{1,1} },
				{ {2,1},{1,1},{1,1} },
				{ {3,1},{4,1},{5,1} }
		};

		Rational [][] B;

		if (A.length != A[0].length) {
			B = transMatrixAlpha(A);
		} else {
			B = transMatrixBeta(A);
		}

		System.out.println("・以下の行列を簡約化し，階数を調べます：\n（もし正方行列ならば行列式を求めます．更に正則であれば逆行列を求めます）");
		showMatrix(B, 0, 0, A.length, A[0].length);
		System.out.println();

		List<Rational> diagonalList = new ArrayList<Rational>();
		int [] Box = {0,0};
		int rank = 0;
		int signCount = 0;
		int mainColumn = 0;

		for (int row = 0; row < A.length; row++) {
			Box[0] = search(B,row, mainColumn, A.length, A[0].length)[0]; //row行目の主成分の候補を探す
			Box[1] = search(B,row, mainColumn, A.length, A[0].length)[1]; //Boxの中身はその成分の座標

			if (Box[0] == -1 || Box[1] == -1 || mainColumn >= A[0].length) {
				if (A.length == A[0].length) {
					diagonalList.add(new Rational(0,1));
				}
				break;
			}

			rank++;

			if (row != Box[0]) {
				rowReplace(B,row, Box[0]);// row行目と行を入れ替える
				signCount++;
			}
			mainColumn = Box[1];

			if (A.length == A[0].length) {
				diagonalList.add(B[row][mainColumn]);
			}

			for (int r = 0; r < A.length; r++) {
				if(r !=row) {
					rowErase(B,row, r, mainColumn);// 他の行の先頭を消す
				}
			}		
			rowDiv(B,row, mainColumn);
			reduceAll(B);
			mainColumn++;
		}//簡約化終わり
		
		System.out.println("・簡約結果：");
		showMatrix(B, 0, 0, A.length, A[0].length);
		System.out.print("\n・階数は" + rank);
		
		if (A.length == A[0].length) {
			int sign = 1;
			
			if (signCount % 2 == 0) {
				sign = 1;
			} else {
				sign = -1;
			}
			
			Rational determinant = new Rational(sign,1);
			for (int i = 0; i < diagonalList.size(); i++) {
				determinant = reduce(mul(determinant, diagonalList.get(i)));
			}
			System.out.print("，行列式は");
			show(determinant);
			if (determinant.getNum() != 0) {
				System.out.println("\n\n・逆行列：");
				showMatrix(B, 0, A[0].length, A.length, 2*A[0].length);
			}
		}




	}



	public static Rational [][] rowDiv(Rational [][] A, int row, int column) { //Aのrow行目をAの(row,column)成分で割る

		for (int j = A[0].length-1; j >= column; j--) {
			A[row][j] = div(A[row][j], A[row][column]);
		}

		return A;
	}

	public static Rational [][] rowErase(Rational [][] A, int p, int q, int column) { //Aのq行目の先頭項を，p行目を使って消す

		for (int j = A[0].length-1; j >= column; j--) {
			A[q][j] = minus(A[q][j], mul(div((A[q][column]), A[p][column]),A[p][j]));
		}//p行を使ってq行の先頭成分を消す（p行column列に主成分あることが前提）

		return A;
	}

	public static Rational [][] rowReplace(Rational [][] A, int p, int q) {// Aのp行目とq行目を入れ替える

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

	public static int [] search(Rational [][] A, int startrow, int startcolumn, int endrow, int endcolumn) { //Aのstartrow行以降の主成分の候補を探す

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

	public static Rational[][] transMatrixAlpha(int [][][] A) {// int型の3次配列で表現した有理数行列をRational型の2次配列に変換

		Rational [][] Ar = new Rational [A.length][A[0].length];

		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[0].length; j++) {
				Ar[i][j] = new Rational(A[i][j][0], A[i][j][1]);	
			}
		}

		return Ar;
	}

	public static Rational[][] transMatrixBeta(int [][][] A) {// tarnsMatrixAlphaメソッドの拡張版

		Rational [][] Ar = new Rational [A.length][2 * A[0].length];

		for (int i = 0; i < Ar.length; i++) {
			for (int j = 0; j < Ar[0].length; j++) {
				if (j < A[0].length) {
					Ar[i][j] = new Rational(A[i][j][0], A[i][j][1]);	
				} else {
					if (i == j - A[0].length) {
						Ar[i][j] = new Rational(1, 1);
					} else {
						Ar[i][j] = new Rational(0, 1);
					}
				}
			}
		}

		return Ar;
	}

	public static Rational plus(Rational r, Rational s) { //有理数同士の足し算

		Rational t = new Rational(r.getNum()*s.getDenom()+s.getNum()*r.getDenom(), r.getDenom()*s.getDenom());
		return t;
	}

	public static Rational minus(Rational r, Rational s) { //有理数同士の引き算

		Rational t = new Rational(r.getNum()*s.getDenom()-s.getNum()*r.getDenom(), r.getDenom()*s.getDenom());
		return t;
	}

	public static Rational mul(Rational r, Rational s) { //有理数同士の掛け算

		Rational t = new Rational(r.getNum()*s.getNum(), r.getDenom()*s.getDenom());
		return t;
	}

	public static Rational inv(Rational r) { //有理数の逆数

		Rational t = new Rational(r.getDenom(), r.getNum());
		return t;
	}

	public static Rational div(Rational r, Rational s) { //有理数同士の割り算

		Rational t = mul(r,inv(s));
		return t;
	}

	public static int gcd(int a, int b){ //aとbの最大公約数（Euclidの互除法を使用）

		int A = a;
		int B = b;

		if (a == 0) {
			return B;
		}

		int r = A % B;
		while (r != 0) {
			A = B;
			B = r;
			r = A % B;
		}
		return B;
	}

	public static Rational reduce(Rational r) { //分数の約分

		if (r.getNum() == 0) {

			r.setDenom(1);
			return r;

		} else {

			int a = r.getNum();
			int b = r.getDenom();
			int d = gcd(a,b);

			a = a/d;
			b = b/d;

			if (b < 0) {
				a = -a;
				b = -b;
			}

			r.setNum(a);
			r.setDenom(b);
			return r;
		}
	}

	public static Rational[][] reduceAll(Rational [][] A) { //行列の全ての成分を約分する

		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[0].length; j++) {
				reduce(A[i][j]);
			}
		}

		return A;
	}

	public static void show(Rational r) { //Rational型変数rを，分数（有理数）として表示

		int a = r.getNum();
		int b = r.getDenom();

		if (b == 1) {
			System.out.print(a);
		} else {
			System.out.print(a + "/" + b);
		}
	}

	public static void showMatrix(Rational [][] A) {  //行列を表示

		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[0].length; j++) {
				if (A[i][j].getDenom() == 1) {
					System.out.print(A[i][j].getNum());
				} else if (A[i][j].getDenom() == -1) {
					System.out.print(-A[i][j].getNum());
				} else {
					show(A[i][j]);
				}

				if (j < A[0].length-1) {
					System.out.print(", ");
				}

			}
			System.out.println();
		}

	}

	public static void showMatrix(Rational [][] A, int startrow, int startcolumn, int endrow, int endcolumn) { //行列を部分的に表示

		for (int i = startrow; i < endrow; i++) {
			for (int j = startcolumn; j < endcolumn; j++) {
				if (A[i][j].getDenom() == 1) {
					System.out.print(A[i][j].getNum());
				} else if (A[i][j].getDenom() == -1) {
					System.out.print(-A[i][j].getNum());
				} else {
					show(A[i][j]);
				}

				if (j < endcolumn-1) {
					System.out.print(", ");
				}

			}
			System.out.println();
		}

	}

}
//有理数体上の行列簡約プログラム
package jp.co.neko.cat;

public class RationalMatrixReduce {

	public static void main(String[] args) {

		/*int [][][] A = { { {2,1},{7,1},{4,1},{19,1},{-7,1} },
				{ {8,1},{-7,1},{-2,1},{3,1},{5,1} },
				{ {9,1},{2,1},{3,1},{10,1},{-3,1} },
				{ {-5,1},{4,1},{1,1},{2,1},{-3,1} }
		};*/

		/*int [][][] A = { { {1,1},{2,1},{-3,1},{4,1},{-5,1} },
				{ {-2,1},{3,1},{5,1},{-7,1},{8,1} },
				{ {4,1},{19,1},{-7,1},{7,1},{2,1} },
				{ {5,1},{7,1},{-8,1},{9,1},{1,1} }
		};*/

		/*int [][][] A = { { {1,1},{2,1},{3,1},{4,1},{5,1} },
				{ {2,1},{3,1},{4,1},{5,1},{6,1} },
				{ {3,1},{4,1},{5,1},{6,1},{7,1} },
				{ {4,1},{5,1},{6,1},{7,1},{8,1} },
				{ {5,1},{6,1},{7,1},{8,1},{9,1} }
		};*/

		/*int [][][] A = { { {1,1},{2,1},{-1,1},{0,1} },
				{ {0,1},{-1,1},{2,1},{-3,1} },
				{ {5,1},{6,1},{-3,1},{2,1} },
				{ {2,1},{-1,1},{-2,1},{5,1} },
				{ {-3,1},{-2,1},{1,1},{-2,1} }
		};*/

		/*int [][][] A = { { {2,1},{-4,1} },
				{ {-1,1},{5,1} },
				{ {-3,1},{6,1} }
		};*/

		int [][][] A = { { {1,1},{2,1},{-3,1},{2,1} },
				{ {-1,1},{-3,1},{3,1},{-2,1} },
				{ {2,1},{4,1},{-6,1},{3,1} },
				{ {1,1},{1,1},{-4,1},{1,1} }
		};

		/*int [][][] A = { { {0,1},{-7,1},{5,1} },
				{ {-4,1},{10,1},{-2,1} },
				{ {1,1},{1,1},{-2,1} },
				{ {-3,1},{4,1},{1,1} }
		};*/

		/*int [][][] A = { { {0,1},{-3,1},{3,1},{-2,1},{1,1},{-7,1} },
				{ {0,1},{-3,1},{3,1},{-2,1},{1,1},{-7,1} },
				{ {0,1},{3,1},{-3,1},{2,1},{0,1},{9,1} },
				{ {0,1},{-2,1},{2,1},{-1,1},{1,1},{-4,1} },
		};*/

		/*int [][][] A = { { {0,1},{0,1},{0,1},{-2,1},{1,1},{-7,1} },
				{ {0,1},{0,1},{3,1},{-2,1},{1,1},{-7,1} },
				{ {0,1},{0,1},{-3,1},{2,1},{0,1},{9,1} },
				{ {0,1},{0,1},{2,1},{-1,1},{1,1},{-4,1} },
		};*/


		/*int [][][] A = { { {1,1},{1,1},{-1,1},{1,1},{2,1} },
				{ {-1,1},{-1,1},{2,1},{0,1},{-1,1} },
				{ {1,1},{1,1},{-1,1},{2,1},{1,1} }
		};*/

		/*int [][][] A = { { {1,1},{2,1},{3,1} },
				{ {0,1},{1,1},{2,1} },
				{ {0,1},{1,1},{1,1} },
		};*/

		Rational [][] E = createUnitMatrix(A);
		Rational [][] B = transMatrix(A);
		Rational [] Diagonal = new Rational [A.length];

		int rank = 0;    //階数( rank(A) )
		boolean judge = true;
		int v = 0;
		int signcount = 0;

		for (int t = 0; t < A.length; t++) {

			if (v >= A[0].length) {
				judge = false;
			}

			for(int column = v; column < A[0].length; column++) {

				for (int row = t; row < A.length; row++) {          //t行目の主成分を作る
					if (B[row][column].getNum() != 0) {
						judge = true;

						if (row == t) {
							v = column;
							break;
						}

						else if (row > t) {
							for(int k = 0; k < A[0].length; k++) {
								B[t][k] = plus(B[t][k], B[row][k]);   //t行目と入れ替え
								B[row][k] = minus(B[t][k], B[row][k]);
								B[t][k] = minus(B[t][k], B[row][k]);

								E[t][k] = plus(E[t][k], E[row][k]);   //t行目と入れ替え
								E[row][k] = minus(E[t][k], E[row][k]);
								E[t][k] = minus(E[t][k], E[row][k]);
							}
							signcount++;
							v = column;
							break;
						}
					} else {
						judge = false;
					}

				}

				/*showMatrix(B);
				System.out.println("(" + t +","+ v + ")");
				System.out.println("*************入れ替え**************" + judge);*/

				if(B[t][v].getNum() != 0) {
					break;
				}

			}//t行目の主成分作り終わり

			if (judge == true && v < A[0].length) {
				Diagonal[t] = new Rational(B[t][t].getNum(), B[t][t].getDenom());
				rank ++;

				for(int i = 0; i < A.length; i++) {                 //t行目以外の行の先頭成分を消す
					if(i != t) {

						for (int j = 0; j < A[0].length; j++) {
							E[i][A[0].length -1-j] 
									= minus(E[i][A[0].length -1-j], mul(div((B[i][v]), B[t][v]),E[t][A[0].length -1-j]));
						}

						for (int j = 0; j < A[0].length; j++) {
							B[i][A[0].length -1-j] 
									= minus(B[i][A[0].length -1-j], mul(div((B[i][v]), B[t][v]),B[t][A[0].length -1-j]));
						}
					}
				}

				for (int j = 0; j < A[0].length; j++) {    //主成分を1にする
					E[t][A[0].length -1-j] =  div((E[t][A[0].length -1-j]), B[t][v]);
				}

				for (int j = 0; j < A[0].length; j++) {
					B[t][A[0].length -1-j] =  div((B[t][A[0].length -1-j]), B[t][v]);  
				}

			} else {
				Diagonal[t] = new Rational(0,1);
			}


			reduceAll(B);
			reduceAll(E);
			v++;

		}//tについてのfor文終わり

		System.out.println("簡約結果******************************************************");
		showMatrix(reduceAll(B));
		System.out.println("******************************************************");

		Kai();
		System.out.println("行列の階数は"+ rank +"です．");

		if(A.length == A[0].length) {

			int sign;

			if (signcount % 2 == 0) {
				sign = 1;
			} else {
				sign = -1;
			}

			Rational detA = new Rational(sign,1);

			for (int i = 0; i < A.length; i++) {
				detA = mul(detA,Diagonal[i]);
			}

			System.out.print("行列式は");
			show(reduce(detA));
			System.out.println("です．");

			if (detA.getNum() != 0) {
				Kai();
				System.out.println("逆行列************************************************");
				showMatrix(reduceAll(E));
				System.out.println("************************************************");
			}
		}


	}


	public static Rational[][] transMatrix(int [][][] A) {

		Rational [][] Ar = new Rational [A.length][A[0].length];

		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[0].length; j++) {
				Ar[i][j] = new Rational(A[i][j][0], A[i][j][1]);	
			}
		}

		return Ar;
	}

	public static Rational[][] createUnitMatrix(int [][][] A) {//単位行列を作る

		Rational [][] E = new Rational [A.length][A[0].length];

		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[0].length; j++) {
				if (i == j) {
					E[i][j] = new Rational(1, 1);	
				} else {
					E[i][j] = new Rational(0, 1);	
				}
			}
		}

		return E;
	}

	public static Rational[][] createZeroMatrix(int [][][] A) {//零行列を作る（未使用）

		Rational [][] O = new Rational [A.length][A[0].length];

		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[0].length; j++) {
				O[i][j] = new Rational(0, 1);	
			}
		}

		return O;
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

	public static void Kai() {  //改行関数
		System.out.println();
	}

	public static void showMatrix(Rational [][] A) {  //行列を表示

		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[0].length; j++) {
				if (A[i][j].getDenom() == 1) {
					System.out.print(A[i][j].getNum());
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
}
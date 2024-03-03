package generateregularmatrix;

public class Ratio {
	private int num;
	private int denom;
	
		public Ratio () {}
		public Ratio(int num,int denom){
			this.num = num;
			this.denom = denom;
		}
		public void setNum(int num) {
			this.num = num;
		}
		public int getNum() {
			return num;
		}
		public void setDenom(int denom) {
			this.denom = denom;
		}
		public int getDenom() {
			return denom;
		}
		public static Ratio[][] transMatrixAlpha(int [][][] A) {// int型の3次配列で表現した有理数行列をRational型の2次配列に変換
			Ratio [][] Ar = new Ratio [A.length][A[0].length];

			for (int i = 0; i < A.length; i++) {
				for (int j = 0; j < A[0].length; j++) {
					Ar[i][j] = new Ratio(A[i][j][0], A[i][j][1]);	
				}
			}

			return Ar;
		}	
		public static Ratio plus(Ratio r, Ratio s) { //有理数同士の足し算
			Ratio t = new Ratio(r.getNum()*s.getDenom()+s.getNum()*r.getDenom(), r.getDenom()*s.getDenom());
			return t;
		}
		public static Ratio minus(Ratio r, Ratio s) { //有理数同士の引き算
			Ratio t = new Ratio(r.getNum()*s.getDenom()-s.getNum()*r.getDenom(), r.getDenom()*s.getDenom());
			return t;
		}
		public static Ratio mul(Ratio r, Ratio s) { //有理数同士の掛け算
			Ratio t = new Ratio(r.getNum()*s.getNum(), r.getDenom()*s.getDenom());
			return t;
		}
		public static Ratio inv(Ratio r) { //有理数の逆数
			Ratio t = new Ratio(r.getDenom(), r.getNum());
			return t;
		}
		public static Ratio div(Ratio r, Ratio s) { //有理数同士の割り算
			Ratio t = mul(r,inv(s));
			return t;
		}
		public static int gcd(int a, int b){ //aとbの最大公約数（Euclidの互除法を使用）
			if (a == 0) {
				return b;
			}

			int r = a % b;
			while (r != 0) {
				a = b;
				b = r;
				r = a % b;
			}
			return b;
		}
		public static Ratio reduce(Ratio r) { //分数の約分
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
		public static Ratio[][] reduceAll(Ratio [][] A) { //行列の全ての成分を約分する

			for (int i = 0; i < A.length; i++) {
				for (int j = 0; j < A[0].length; j++) {
					reduce(A[i][j]);
				}
			}

			return A;
		}
		public static void show(Ratio r) { //Rational型変数rを，分数（有理数）として表示

			int a = r.getNum();
			int b = r.getDenom();

			if (b == 1) {
				System.out.print(a);
			} else {
				System.out.print(a + "/" + b);
			}
		}
		public static void showMatrix(Ratio [][] A) {  //行列を表示

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
	
}
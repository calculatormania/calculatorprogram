package compositionoffunctions;

public class Ratio {
	private long num;
	private long denom;

	public Ratio () {}
	public Ratio(long num,long denom){
		this.num = num;//分子
		this.denom = denom;//分母
	}
	public void setNum(long num) {
		this.num = num;
	}
	public long getNum() {
		return num;
	}
	public void setDenom(long denom) {
		this.denom = denom;
	}
	public long getDenom() {
		return denom;
	}
	/*public static Ratio[][] transMatrixAlpha(int [][][] A) {// int型の3次配列で表現した有理数行列をRational型の2次配列に変換
			Ratio [][] Ar = new Ratio [A.length][A[0].length];

			for (int i = 0; i < A.length; i++) {
				for (int j = 0; j < A[0].length; j++) {
					Ar[i][j] = new Ratio(A[i][j][0], A[i][j][1]);	
				}
			}

			return Ar;
		}*/	
	public static Ratio plus(Ratio r, Ratio s) { //有理数同士の足し算
		if (r.getNum()==0) {
			return s;
		}
		if (s.getNum()==0) {
			return r;
		}
		long l = r.getDenom()/gcd(r.getDenom(),s.getDenom());
		l *= s.getDenom();
		if (l < 0) {
			l=-l;
		}
		long m = l/r.getDenom();
		long mm = l/s.getDenom();
		Ratio t = new Ratio(r.getNum()*m+s.getNum()*mm, l);
		return reduce(t);
	}
	public static Ratio minus(Ratio r, Ratio s) { //有理数同士の引き算
		Ratio u = new Ratio(-s.getNum(),s.getDenom());
		Ratio t = plus(r,u);
		return reduce(t);
	}
	public static Ratio mul(Ratio r, Ratio s) { //有理数同士の掛け算
		if (r.getNum()==0 || s.getNum()==0) {
			return new Ratio(0,1);
		}
		long g1 = gcd(r.getDenom(),s.getNum());
		long g2 = gcd(r.getNum(),s.getDenom());
		if (g1 < 0) {
			g1=-g1;
		}
		if (g2 < 0) {
			g2=-g2;
		}
		long a = (r.getNum()/g2);
		long b = (s.getNum()/g1);
		long c =  (r.getDenom()/g1);
		long d = (s.getDenom()/g2);
		Ratio t = new Ratio(a*b, c*d);
		return reduce(t);
	}
	public static Ratio inv(Ratio r) { //有理数の逆数
		Ratio t = new Ratio(r.getDenom(), r.getNum());
		return t;
	}
	public static Ratio div(Ratio r, Ratio s) { //有理数同士の割り算
		Ratio t = mul(r,inv(s));
		return reduce(t);
	}
	public static long gcd(long a, long b){ //aとbの最大公約数（Euclidの互除法を使用）
		if (a == 0) {
			return b;
		}
		if (a < b) {
			long w = a;
			a = b;
			b = w;
		}
		long r = a % b;
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
			long a = r.getNum();
			long b = r.getDenom();
			long d = gcd(a,b);
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
	public static boolean equal(Ratio r, Ratio s) {
		long a = r.getNum();
		long b = r.getDenom();
		long c = s.getNum();
		long d = s.getDenom();
		if (a*d == c*b) {
			return true;
		} else {
			return false;
		}
	}
	public static boolean compare(Ratio r, Ratio s) {//r>=sならtrue
		long a = r.getNum();
		long b = r.getDenom();
		long c = s.getNum();
		long d = s.getDenom();
		if (a*b*d*d >= c*b*b*d) {
			return true;
		} else {
			return false;
		}
	}
	public static void show(Ratio r) { //Rational型変数rを，分数（有理数）として表示
		long a = r.getNum();
		long b = r.getDenom();
		if (b == 1) {
			System.out.print(a);
		} else {
			System.out.print(a + "/" + b);
		}
	}
	/*public static Ratio[][] reduceAll(Ratio [][] A) { //行列の全ての成分を約分する
			for (int i = 0; i < A.length; i++) {
				for (int j = 0; j < A[0].length; j++) {
					reduce(A[i][j]);
				}
			}
			return A;
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
		}*/
}
//べき乗法による固有値計算プログラム！

public class DominateEigenvalueCalculator {
	public static void main(String[] args) {

		// A.lengthは行数
		// A[0].lengthは列数	

		double [][] A_real = {{0,0,8.97},  //行列の実部
				{1,0,-14.96},        
				{0,1,6.99}};
		double [][] A_image = {{0,0,0},  //行列の虚部
				{0,0,0},        
				{0,0,0}};

		/*double [][] A_real = {{1,0,2,0},  //行列の実部
				{0,-1,0,0},        
				{0,1,2,0},
				{0,0,0,11}};
		double [][] A_image = {{0,0,0,0},  //行列の虚部
				{0,0,0,0},        
				{0,0,0,0},
				{0,0,0,0}};*/
		
		/*double [][] A_real = {{0,0,0,0},  //行列の実部
				{1,0,0,-5},        
				{0,1,0,1},
				{0,0,1,5}};
		double [][] A_image = {{0,0,0,0},  //行列の虚部
				{0,0,0,0},        
				{0,0,0,0},
				{0,0,0,0}};*/
		
		/*double [][] A_real = {{2,1,0,0,0,0,0},  //行列の実部
				{0,2,1,0,0,0,0},        
				{0,0,2,1,0,0,0},
				{0,0,0,1,1,0,0},
				{0,0,0,0,2,1,0},
				{0,0,0,0,0,2,1},
				{0,0,0,0,0,0,1}};
		double [][] A_image = {{0,0,0,0,0,0,0},  //行列の虚部
				{0,0,0,0,0,0,0},        
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0}};*/

		/*double [][] A_real = {{2,1,0,0,0,0,0,0},  //行列の実部
				{0,2,1,0,0,0,0,0},        
				{0,0,2,1,0,0,0,0},
				{0,0,0,1,1,0,0,0},
				{0,0,0,0,2,0,0,0},
				{0,0,1,0,0,2,1,0},
				{0,0,0,0,0,0,1,0},
				{0,0,0,0,0,0,0,5}};
		double [][] A_image = {{0,0,0,0,0,0,0,0},  //行列の虚部
				{0,0,0,0,0,0,0,0},        
				{0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0}};*/
		
		double [][] x = {{1},{1},{1}}; //ベクトルの初期値, xは実部ベクトル, yは虚部ベクトル
		double [][] y = {{0},{0},{0}};

		double [][] x_new; //行列を掛けて得られるベクトル, x_new,y_newはそれぞれ実部ベクトル, 虚部ベクトル
		double [][] y_new;

		int N = 10; //ループ回数を指定

		System.out.println("以下の複素正方行列の、絶対値が最大の固有値の近似値計算を行います。\n");
		Showmat(A_real,A_image);
		System.out.println("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
		
		System.out.println("ベクトルの初期値は以下の通り");
		Showmat(x,y);
		System.out.println("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");

		for(int i=1; i<=N; i++) {
			x_new = plus(multiple(A_real,x),scalar(-1,multiple(A_image,y)));
			y_new = plus(multiple(A_real,y),multiple(A_image,x));
			//if(i >= N-10) {
			double a = innerproduct(x_new, x_new) - innerproduct(y_new, y_new);
			double b = 2*innerproduct(x_new, y_new);
			double c = innerproduct(x_new, x) - innerproduct(y_new, y);
			double d = innerproduct(x_new, y) + innerproduct(x, y_new);
			/*System.out.println("↓初期ベクトルにAを" + N + "回掛けて得られたベクトル\n");
			Showmat(x_new,y_new);*/
			System.out.println(i + "回目の計算結果");
			System.out.println("固有値の実部: " + (a*c+b*d)/(c*c+d*d));
			System.out.println("固有値の虚部: " + (b*c - a*d)/(c*c+d*d));
			System.out.println("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
			//}
			x = x_new;
			y = y_new;
		}
	}


	//行列を参照する関数
	public static void Showmat(double [][]A, double [][]B){
		for (int i = 0; i <= A.length-1; i++) {
			for (int j = 0; j <= A[0].length-1; j++) {
				if (A[i][j] == 0 && B[i][j] != 0) {
					System.out.print(B[i][j] + "i\t");
				} else if(B[i][j] == 0 && A[i][j] != 0) {
					System.out.print(A[i][j] + "\t\t");
				} else if (A[i][j] == 0 && B[i][j] == 0) {
					System.out.print(A[i][j] + "\t\t");
				} else if (B[i][j] >= 0) {
					System.out.print(A[i][j] + "+" + B[i][j] + "i\t");
				}else {
						System.out.print(A[i][j] + "" + B[i][j] + "i\t");
				}
			}
			System.out.println("\n");
		}
	}

	//行列の積演算関数
	public static double [][] multiple(double [][]A, double [][]B){
		double [][]C = new double [A.length][B[0].length];
		for (int i = 0; i <= A.length-1; i++) {
			for (int j = 0; j <= B[0].length-1; j++) {
				C[i][j]=0;
				for(int k = 0; k <= A.length-1; k++) {
					C[i][j] = C[i][j] + A[i][k]*B[k][j];
				}
			}
		}
		return C;
	}

	//行列の和演算関数
	public static double [][] plus(double [][]A, double [][]B){
		double [][]C = new double [A.length][A[0].length];
		for (int i = 0; i <= A.length-1; i++) {
			for (int j = 0; j <= A[0].length-1; j++) {
				C[i][j] = A[i][j] + B[i][j];
			}
		}
		return C;
	}

	//行列のスカラー倍関数
	public static double [][] scalar(double c,double [][]A){
		double [][]C = new double [A.length][A[0].length];
		for (int i = 0; i <= A.length-1; i++) {
			for (int j = 0; j <= A[0].length-1; j++) {
				C[i][j] = c*A[i][j];
			}
		}
		return C;
	}

	//ベクトルの内積関数
	public static double innerproduct(double [][]x, double [][]y){
		double val = 0;
		for(int k = 0; k<=x.length-1; k++) {
			val = val + x[k][0]*y[k][0];
		}
		return val;
	}

}
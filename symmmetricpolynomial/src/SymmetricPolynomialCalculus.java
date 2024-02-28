//対称式を基本対称式で表すプログラム
import java.util.Arrays;
public class SymmetricPolynomialCalculus {

	public static void main(String[] args) {
		int [] mono = {7,2,1,1};
		int [][][]P = Orbit(mono);
		int n = mono.length;
		int [] exp = new int[n];

		int S[][] = new int [n][];
		for(int i = 0; i < n; i++) {
			S[i] = new int [n];
		}

		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				if(i < j) {
					S[i][j] = 0;
				} else {
					S[i][j] = 1;
				}
			}
		}

		int s [][][][] = new int [n][][][];//基本対称式を生成し、sに格納
		for(int i = 0; i < n; i++) {
			s[i] = new int [factorial(n)/(factorial(i+1)*factorial(n-(i+1)))][][];
		}

		for(int i = 0; i < n; i++) {
			for(int j = 0; j < factorial(n)/(factorial(i+1)*factorial(n-(i+1))); j++) {
				s[i][j] = new int [2][];
			}
		}

		for(int i = 0; i < n; i++) {
			for(int j = 0; j < factorial(n)/(factorial(i+1)*factorial(n-(i+1))); j++) {
				for(int k = 0; k < 2; k++) {
					if(k == 0) {
						s[i][j][k] = new int [1];	
					} else {
						s[i][j][k] = new int [n];	
					}
				}
			}
		}

		for(int i = 0; i < n; i++) {
			s[i] = Orbit(S[i]);
		}


		while(P.length != 0) {
			for(int i = 0; i < n; i++) {
				if(i == n-1) {
					exp[i] = Lterm(P)[1][i];
				} else {
					exp[i] = Lterm(P)[1][i] - Lterm(P)[1][i+1];
				}
			}

			int v = n-1;
			while(exp[v] == 0) {
				v--;
			}

			if(Lterm(P)[0][0] > 0) {
				System.out.print("+" + Lterm(P)[0][0] + " * ");
			} else {
				System.out.print(Lterm(P)[0][0] + " * ");
			}
			for(int i = 0; i <= v; i++) {

				if(i < v && exp[i] != 0) {
					System.out.print("s"+ (i+1) + "^" + exp[i] + " * ");
				} else if (i == v) {
					System.out.print("s"+ (i+1) + "^" + exp[i]);
				} 
			}
			P = Minus(P,Termpow(Lterm(P),s));
			System.out.println();
		}

	}
	
	public static int factorial(int n){//階乗関数
		if(n == 0){
			return 1;
		}
		return n * factorial(n-1);
	}

	public static int count = 0;//buildPerm用のグローバル変数

	private static int[][] buildPerm(int [][] U, int[] seed, int[] perm, boolean[] used, int index) {//一次元配列の順列を列挙して、二次配列に格納させる関数

		if (index == seed.length) {
			for(int i = 0; i < seed.length; i++) {
				U[count][i] = perm[i];
			}
			count++;
		}


		for (int i = 0; i < seed.length; i++) {
			if (used[i] == true) continue;
			perm[index] = seed[i];
			used[i] = true;
			buildPerm(U, seed, perm, used, index + 1);
			used[i] = false;
		}

		if(count == factorial(seed.length)) {
			count = 0;
		}

		return U;
	}

	public static int [][][] Orbit(int [] A){//n次対称群による、単項式の軌道多項式を返す関数
		int n = A.length;
		int N = factorial(n);
		int [][] V = new int [N][];
		for(int i = 0; i < N; i++) {
			V[i] = new int [n];
		} 
		int[] perm = new int[A.length];
		boolean[] used = new boolean[A.length];
		int [][] PermA = buildPerm(V, A, perm, used, 0);

		int [][][][] U = new int[PermA.length][][][];
		for(int i = 0; i < PermA.length; i++) {
			U[i] = new int [1][][];
			U[i][0] = new int [2][];
		}
		for(int i = 0; i < PermA.length; i++) {
			for(int j = 0; j < 2; j++) {
				if(j == 0) {
					U[i][0][j] = new int [1];	
				} else {
					U[i][0][j] = new int [PermA[0].length];	
				}
			}
		}

		for(int i = 0; i < PermA.length; i++) {
			U[i][0][0][0] = 1;
			U[i][0][1] = PermA[i];
		}

		int [][][] NewP = U[0];
		if(PermA.length > 1) {
			for(int i = 1; i < PermA.length; i++) {
				NewP = Plus(NewP,U[i]);
			}
		}

		for(int i = 0; i < NewP.length; i++) {
			NewP[i][0][0] = 1;
		}

		return NewP;
	}

	public static int [][][] Zerodelete(int [][][] P){//係数0の単項式を削除する関数

		int M = P.length; 
		int [][][] Zero = new int[0][][];

		if (M == 0) {
			return Zero;
		}

		int n = 0;
		int varnum = P[0][1].length;

		int [][][]R = new int [M][][];
		for(int i = 0; i < M; i++) {
			R[i] = new int [2][];
		}
		for(int i = 0; i < M; i++) {
			for(int j = 0; j < 2; j++) {
				if(j == 0) {
					R[i][j] = new int [1];	
				} else {
					R[i][j] = new int [varnum];	
				}
			}
		}

		for(int i = 0; i < P.length; i++) {
			if(P[i][0][0] != 0) {
				R[n][0][0] = P[i][0][0];
				R[n][1] = P[i][1];
				n++;
			}
		}

		if(n == M) {
			return P;
		}

		int [][][]NewP = new int [n][][];
		for(int i = 0; i < n; i++) {
			NewP[i] = new int [2][];
		}
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < 2; j++) {
				if(j == 0) {
					NewP[i][j] = new int [1];	
				} else {
					NewP[i][j] = new int [varnum];	
				}
			}
		}

		for(int i = 0; i < NewP.length; i++) {
			NewP[i][0][0] = R[i][0][0];
			NewP[i][1] = R[i][1];
			n++;
		}
		return NewP;
	}



	public static int [][][] Plus(int [][][] P, int [][][] Q){//多項式の足し算

		int M = Math.max(P.length,Q.length); 

		int varnum = P[0][1].length;

		int [][][]R = new int [M][][];
		for(int i = 0; i < M; i++) {
			R[i] = new int [2][];
		}
		for(int i = 0; i < M; i++) {
			for(int j = 0; j < 2; j++) {
				if(j == 0) {
					R[i][j] = new int [1];	
				} else {
					R[i][j] = new int [varnum];	
				}
			}
		}

		int [][][]S = new int [2*M][][];
		for(int i = 0; i < 2*M; i++) {
			S[i] = new int [2][];
		}
		for(int i = 0; i < 2*M; i++) {
			for(int j = 0; j < 2; j++) {
				if(j == 0) {
					S[i][j] = new int [1];	
				} else {
					S[i][j] = new int [varnum];	
				}	
			}
		}                

		int n = 0;
		int k = 0;
		int l = 0;
		for (int i = 0; i < P.length; i++) {
			n = 0;
			for (int j = 0; j < Q.length; j++) {
				if (!Arrays.equals(P[i][1],Q[j][1])) {
					n++;
				} else {
					R[k][0][0] = P[i][0][0] + Q[j][0][0];   //PとQ共通の単項式と、各単項式の係数の和をRに入れる
					R[k][1] = P[i][1];
					k++;
				}

				if (n == Q.length) {        //Qに出現しないPの単項式とその係数をSに入れる
					S[l][0][0] = P[i][0][0];
					S[l][1] = P[i][1];
					l++;
				}
			}
		}

		int r = 0;
		int s = 0;
		for (int i = 0; i < Q.length; i++) {             //Pに出現しないQの単項式とその係数をSに入れる
			r = 0;
			for (int j = 0; j < P.length; j++) {
				if (!Arrays.equals(Q[i][1],P[j][1])) {
					r++;
				} 

				if (r == P.length) {
					S[l+s][0][0] = Q[i][0][0];
					S[l+s][1] = Q[i][1];
					s++;
				}
			}
		}

		int N = k+l+s;

		int [][][]U = new int [N][][];
		for(int i = 0; i < N; i++) {
			U[i] = new int [2][];
		}
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < 2; j++) {
				if(j == 0) {
					U[i][j] = new int [1];	
				} else {
					U[i][j] = new int [varnum];	
				}	
			}
		}

		for(int i = 0; i < N; i++) {
			for(int j = 0; j < 2; j++) {
				if(i < k) {
					U[i][j] = R[i][j];
				} else {
					U[i][j] = S[i-k][j];
				}
			}
		}

		return Zerodelete(U);

	}

	public static int [][][] Minus(int [][][] P, int [][][] Q){//多項式の引き算
		int M = Q.length; 

		int varnum = P[0][1].length;

		int [][][]R = new int [M][][];
		for(int i = 0; i < M; i++) {
			R[i] = new int [2][];
		}
		for(int i = 0; i < M; i++) {
			for(int j = 0; j < 2; j++) {
				if(j == 0) {
					R[i][j] = new int [1];	
				} else {
					R[i][j] = new int [varnum];	
				}
			}
		}

		for(int i = 0; i < M; i++) {
			R[i][0][0] = -Q[i][0][0];
			R[i][1] = Q[i][1];
		}

		return Plus(P,R);

	}


	public static int [][][] multiple(int [][] mono, int [][][] P){//単項式と多項式の掛け算

		int M = P.length; 
		int varnum = P[0][1].length;

		int [][][]Q = new int [M][][];
		for(int i = 0; i < M; i++) {
			Q[i] = new int [2][];
		}
		for(int i = 0; i < M; i++) {
			for(int j = 0; j < 2; j++) {
				if(j == 0) {
					Q[i][j] = new int [1];	
				} else {
					Q[i][j] = new int [varnum];	
				}
			}
		}

		for(int i = 0; i < M; i++) {
			for(int j = 0; j < 2; j++) {
				if (j == 0) {
					Q[i][j][0] = mono[j][0]*P[i][j][0];
				} else {
					for(int p = 0; p < varnum; p++) {
						Q[i][j][p] = mono[j][p]+P[i][j][p];

					}
				}
			}
		}

		return Zerodelete(Q);
	}

	public static int [][][] Multiple(int [][][] P, int [][][] Q){//多項式の掛け算

		int M = P.length; 
		int [][][]R;
		R = multiple(P[0],Q);

		if(M >= 2) {
			for(int i = 1; i < M; i++) {
				R = Zerodelete(Plus(R,multiple(P[i],Q)));
			}
		}
		return R;
	}

	public static int [][][] Pow(int [][][] P, int e){//多項式のべき乗
		int [][][]R;
		if(e == 0) {
			R = new int [1][][];   //Rの定義
			R[0] = new int [2][];
			R[0][0] = new int [1];	
			R[0][1] = new int [P[0][1].length];
			R[0][0][0] = 1;
			for(int i = 0; i < P[0][1].length; i++) {
				R[0][1][i] = 0;
			}
		} else {
			R = P;
		}

		if(e > 1) {
			for(int i = 1; i <= e-1; i++) {
				R = Multiple(P,R);
			}
		}
		return R;
	}

	public static int [][] Maxlex(int [][] mono1, int [][] mono2){//辞書式順序関数
		int [][] M;
		int i = 0;
		if(Arrays.equals(mono1[1], mono2[1])){
			M = mono1;
		}
		else {
			while(mono1[1][i] == mono2[1][i]) {
				i++;
			}
			if(mono1[1][i] < mono2[1][i]) {
				M = mono2;
			}
			else {
				M = mono1;
			}
		}
		return M;
	}

	public static int [][] Lterm(int P[][][]){//辞書式順序に関する先頭項を返す関数
		int [][] LT;
		LT = P[0];
		if(P.length > 1) {
			for(int i =1; i < P.length; i++) {
				LT = Maxlex(P[i], LT);
			}
		}
		return LT;
	}

	public static int [][][] Termpow(int mono[][], int s[][][][]){//先頭項を消す多項式を作るための関数
		int v = s.length;
		int [][][] P = Pow(s[v-1],mono[1][v-1]);

		for(int i = 1; i < v; i++) {
			P = Multiple(P,Pow(s[v-i-1], mono[1][v-i-1] - mono[1][v-i]));
		}

		for(int i = 0; i < P.length; i++) {
			P[i][0][0] = mono[0][0]*P[i][0][0];
		}
		return P;
	}

}






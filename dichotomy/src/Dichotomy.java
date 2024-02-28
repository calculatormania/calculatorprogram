public class Dichotomy {
	//関数
	public static double f(double x) {
		return x*x - 3;
	}
	//mainメソッド
	public static void main(String[] args) {
		double a = 1.0;
		double b = 2.0;
		double eps = Math.pow(10, -7);
		int N = 1000;

		//もし初期値たちが中間値の定理の仮定を満たさず、なおかつ根にならないならば処理を行わない
		if(f(a)*f(b) > 0) {
			System.out.println("Not find a root");
		}
		//もし初期値のどちらかが根になるならそれ自体を出力
		else if(f(a) == 0 && f(b) != 0) {
			System.out.println("Root is " + a);
		}
		else if(f(b) == 0 && f(a) != 0) {
			System.out.println("Root is " + b);
		}
		else if(f(a) == 0 && f(b) == 0) {
			System.out.println("Root is " + a +  " and " + b);
		}
		//定理の仮定を満たす時、二分法を実行し、根の近似値と代入した値を出力！
		else {
			while (b-a > eps) {
				if(f(a)*f((a+b)*0.5) < 0) {
					b = (a+b)*0.5;
				}
				else {
					a = (a+b)*0.5;
				}
				if(f((a+b)*0.5) == 0) {
					break;
				}
			}
			System.out.println("Root is " + (a+b)*0.5);
			System.out.println("Subsitution is " + (Math.floor(f( (a+b)*0.5 )*N))/N);
		}
	}
}
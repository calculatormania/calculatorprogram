public class ApproximationOfConstant{
	public static void main(String[] args) {

		double N = Math.pow(2,50);
		System.out.println("数列を用いた時のネピア数eの近似値は" + Math.pow(1+1/N,N));

		double Napier = 0;
		for(double k = 0; k<=100; k++) {
			Napier = Napier + 1/(Fac(k));
		}
		System.out.println("級数展開を用いたネピア数eの近似値は" + Napier);

		double log2 = 1;
		for(double l = 1; l<=1000000; l++) {
			if(l%2 != 0){
				log2 = log2 - 1/(l+1);
			} else {
				log2 = log2 + 1/(l+1);
			}
		}
		System.out.println("log_e(2)の近似値は" + log2);

		double Pi = 1;
		for(double l = 1; l<=100000; l += 2) {
			if(l%4 == 1){
				Pi = Pi - 1/(l+2);
			} else {
				Pi = Pi + 1/(l+2);
			}
		}
		System.out.println("円周率πの近似値は" + 4*Pi);
	}


	public static double Fac(double n){
		double factorial = 1L;
		if(n == 0) {
			factorial = 1;
		} else {
			for (int i = 1; i < n; i++) {
				factorial = factorial * (i + 1);
			}
		}
		return factorial;
	}
}
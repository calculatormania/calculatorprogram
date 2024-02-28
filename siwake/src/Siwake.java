import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public class Siwake {
	public static void main(String[] args) {
		
		int L = 1000000;
	    int [] A = new int [L];

		for(int i = 0; i < L; i++) {
			if (i < L/2) {
				A[i] = 1;
			} else {
				A[i] = 2;
			}
		}

		ArrayList<Integer> index = new ArrayList<>();

		double n = 0;
		double m = 0;
		double N = 10000;

		Random r = new SecureRandom();

		for(int j = 1; j <= N; j++) {
			
			int x = 0;
			while(index.contains(x)) {
				x = r.nextInt(L);
			}
			
			index.add(x);
			
			if(A[x] == 1) {
				n++;
			} else if (A[x] == 2) {
				m++;
			}

		}
		System.out.println(n);
		System.out.println(m);
		System.out.println((n-m)/N);

	}
}
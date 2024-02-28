import java.security.SecureRandom;
import java.util.Random;

public class LawOfLargeNumbers {
	public static void main(String[] args) {

		double epsilon = 0.01;
		double n = 0;
		double m = 0;
		double N = 100000;
		double M = 100;
		
		Random rnd = new SecureRandom();

	  for(int j = 1; j <= M; j++) {
		  
		  n= 0;
		
		for(int i = 1; i <= N; i++) {
			
		  int x = rnd.nextInt(6);
			if(x == 0) {
				n++;
			}
			
		}
		  
		  if (Math.abs((1.0/6.0) - (n/N)) > epsilon) {
			m++;
		  } else {
			  m = m + 0; 
		  }
	  
	  }	
		System.out.println(m/M);
		
		
	}
}
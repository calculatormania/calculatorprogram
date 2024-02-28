import java.security.SecureRandom;
import java.util.Random;
public class Shuffle {		

	public static void main(String[] args) {
		
		int [] DICE = {1,2,3,4,5,6};
        int [] SHU;
		SHU = new int [6];
        
		Random R = new SecureRandom();
		
		
		for(int i = 0; i < DICE.length; i++) {
			int j = R.nextInt(6);
			while(DICE[j] == -1) {
				j = R.nextInt(6);
			}
			SHU[i] = DICE[j];
			DICE[j] = -1;
		}
		for (int k = 0; k < DICE.length; k++) {
			DICE[k] = SHU[k];
		}
				for(int l = 0; l < DICE.length; l++) {
					if (l < DICE.length-1) {
						System.out.print(DICE[l] + ",");
					} else {
						System.out.print(DICE[l]);
					}
				}
	}
}
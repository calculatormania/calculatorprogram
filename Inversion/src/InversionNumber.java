import java.util.ArrayList;
import java.util.List;
public class InversionNumber {
	public static void main(String[] args) {

		int [] sigma = {2,0,3,1};	
		int inv = 0;

		System.out.println("以下の置換の転倒数と偶奇を調べ、隣接互換の積に分解します。\n");

		System.out.println("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
		for (int i = 0; i < sigma.length; i++) {
			System.out.print(i + 1 + "\t");
		}
		System.out.println("\n");
		for (int i = 0; i < sigma.length; i++) {
			System.out.print(sigma[i] + 1 + "\t");
		}
		System.out.println();
		System.out.println("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆\n");

		for (int i = 0; i < sigma.length; i++) {
			for (int j = i+1; j < sigma.length; j++) {
				if (sigma[i] > sigma[j]) {
					inv ++;
				}
			}
		}
		System.out.print("・転倒数は" + inv + ", ");

		if (inv % 2 == 0) {
			System.out.println("偶置換です。");
		} else {
			System.out.println("奇置換です。");
		}

		int index = 0;
		List<Integer> I = new ArrayList<Integer>();
		
		for (int finalpoint = sigma.length-1; finalpoint >= 0; finalpoint--) {
			
			index = search(sigma,finalpoint);
			while (index < finalpoint) {
				sigma[index] = sigma[index] + sigma[index+1];
				sigma[index+1] = sigma[index] - sigma[index+1];
				sigma[index] = sigma[index] - sigma[index+1];
				I.add(index+1);
				index++;
			}
			
		}
		
		
		System.out.print("\n・隣接互換分解: ");
		for (int i = I.size()-1; i >= 0; i--) {
			System.out.print("(" + I.get(i) + ", "+  (I.get(i)+1)  + ")");
		}
		
		System.out.print("\n\n・互換の数: " + I.size());
	
	
		System.out.print("\n\n・バブルソート完了チェック：");
		for (int i = 0; i < sigma.length; i++) {
			if (i < sigma.length-1) {
			System.out.print(sigma[i] + ", ");
			} else {
				System.out.print(sigma[i]);
			}
		}
	
	}

	public static int search(int [] A, int finalpoint) {   //偶数なら2で割った値を，奇数なら3倍して1足した値を返す

		int p = 0;

		for (int i = 0; i < A.length; i++) {
			if (A[i] == finalpoint) {
				p = i;
			}
		}

		return p;
	}	
	
}
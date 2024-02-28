import java.util.Arrays;
public class LexLeadingTerm {
	//2つの単項式のうち、辞書式順序に関して大きい方をとる関数！
	//public static int N = 3;
	public static int [] lex(int mono1[], int mono2[]){
		int [] M;
		int i = 0;
		if(Arrays.equals(mono1, mono2)){
			M = mono1;			
		}
		else {
			while(mono1[i] == mono2[i]) {
				i++;
			}
			if(mono1[i] < mono2[i]) {
				M = mono2;
			}
			else {
				M = mono1;
			}
		}
		return M;
	}
	//有限個の単項式達の中から辞書式順序に関して最大のもの（多項式のLeadingTerm）をとるメソッド
	public static void main(String[] args) {
		int [] [] monomials = {{2,7,6,1},{2,7,9,5},{2,7,5,2},{2,7,9,1}};
		int [] LT = monomials[0];    //単項式たちの集合の中の、一番初めのものを代入
		for(int i = 0; i < monomials.length; i++) {
			LT = lex(monomials[i], LT);     //MaxLex関数により、順に大きいものを代入
		}
		System.out.println(Arrays.toString(LT));
	}
}
import java.util.Arrays;
public class LexLeadingTerm {
	//2つの単項式のうち、辞書式順序に関して大きい方をとる関数！
	//public static int N = 3;
	public static int [] lex(int mono1[], int mono2[]){
		//int [] M;
		int i = 0;
		if(Arrays.equals(mono1, mono2)){
			return mono1;			
		}
		else {
			while(mono1[i] == mono2[i]) {
				i++;
			}
			if(mono1[i] < mono2[i]) {
				return mono2;
			}
			else {
				return mono1;
			}
		}
	}
	
	public static int [] sub(int mono1[], int mono2[]){
		
		for (int i = 0; i < mono1.length; i++) {
			mono2[i] = mono1[i];
		}
		
		return mono2;
	}
	
	//有限個の単項式達の中から辞書式順序に関して最大のもの（多項式のLeadingTerm）をとるメソッド
	public static void main(String[] args) {
		int [] [] monomials = {{2,7,6,1},{2,7,0,5},{2,7,5,2},{2,7,6,2}};
		int [] LT = new int [monomials[0].length];    //単項式たちの集合の中の、一番初めのものを代入
		sub(monomials[0], LT);
		
		for(int i = 0; i < monomials.length; i++) {
			sub( lex(monomials[i], LT), LT );     //MaxLex関数により、順に大きいものを代入
		}
		System.out.println(Arrays.toString(LT));
	}
}
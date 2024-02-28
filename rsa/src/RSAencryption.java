import java.util.Arrays;
import java.util.Random;
public class RSAencryption {
	public static void main(String[] args) {

		//平文
		String str = "MATH";
		
        char[] Plain = str.toCharArray();
        
		System.out.println("以下の平文をRSA暗号により暗号化します。\n");
		System.out.print("平文: " + str);
		
		
		long [] Numbers = new long [Plain.length]; //平文を数に変換(文字と数を対応させる)
		long [] Cipher = new long [Plain.length]; //Numbersの要素たちを暗号化した数を入れる用の配列
		long [] Decrypt = new long [Plain.length]; //Cipherの要素たちを復号化した数を入れる用の配列

		for(int k = 0; k < Plain.length; k++) {
					Numbers[k] = (long)Plain[k]; 
		}
		
		System.out.println("");
		System.out.print("平文を文字コード変換: " + Arrays.toString(Numbers));
		System.out.println("\n☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆\n");
		
		
		//生成する素数の上限の指定
		long N = 10000L;

		//十分大きい素数p,qをランダム生成(N/2以上N以下で生成)
		Random ran = new Random();
		long p = 2L;
		while (judgeprime(p) == false || p < N/2) {
			p = ran.nextLong(N); 
		}

		long q = p;
		while (q == p || judgeprime(q) == false || q < N/2) {
			q = ran.nextLong(N); 
		}
		
		System.out.println("<暗号化・復号化する際に使うもの>");
		System.out.println("2つの素数: p=" + p + ", q=" + q);

		long n = p*q; //公開鍵その1
		long lcm = ((p-1)/gcd(p-1,q-1)) * (q-1);

		long e = 0L;   //公開鍵その2(ランダムにとる)
		while (gcd(e,lcm) != 1 || powremm(e,2,lcm) == 1) {
			e = ran.nextLong(lcm);
		}
		
		System.out.println("公開鍵: n=pq=" + n + ", e=" + e);

		long d = 2L; //秘密鍵を見つける(復号化する際に用いる)
		for(long j = 2L; j < lcm; j++){
			if (prodrem(e,j,lcm) == 1) {
				d = j;
			} 
		}
		
		System.out.println("秘密鍵: d=" + d);
		System.out.println("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆\n");
		

		//公開鍵e,nにより暗号化
		for (int k = 0; k < Plain.length; k++) {
			Cipher[k] = powrem(Numbers[k],e,n);
		}
		
		System.out.print("公開鍵e,nによる暗号文: "+ Arrays.toString(Cipher) + "\n");
		
		System.out.print("暗号文を16進数変換したもの: [");
		for (int k = 0; k < Plain.length; k++) {
			if (k < Plain.length - 1) {
			System.out.print(Long.toHexString(Cipher[k]) + ", ");
			} else {
				System.out.println(Long.toHexString(Cipher[k]) + "]");
			}
		}
		
		System.out.println("");
		
		//秘密鍵dにより復号化する
		for (int k = 0; k < Plain.length; k++) {
			Decrypt[k] = powremm(Cipher[k],d,n);	
		}
		System.out.print("秘密鍵dにより復号化した文: " + Arrays.toString(Decrypt));
		
	}

	//ユークリッドの互除法により最大公約数を返す関数
	public static long gcd(long a, long b){
		long r = a % b;
		while (r != 0) {
			a = b;
			b = r;
			r = a%b;
		}
		return b;
	}


	//素数判定関数
	public static boolean judgeprime(long a) {
		boolean judge = false;
		for(long i = 2; (i < a && a % i != 0) || i == a; i++) {
			if (i == a) {
				judge = true;
			} 
		}
		return judge;
	}
	
	public static boolean Superjudgeprime(long a) {
		long sq_a = (long)Math.floor(Math.sqrt(a));
		boolean judge = false;
		
		if (a == 2) {
			judge = true;
		}
		if (a % 2 == 0) {
			return judge;
		}
		for(long i = 1; i <= sq_a && a % i != 0; i += 2) {
			if (sq_a % 2 == 0 && i == sq_a - 1) {
				judge = true;
			} else if (sq_a % 2 != 0 && i == sq_a) {
				judge = true;
			}
		}
		return judge;
	}

	//aのpow乗をnで割った余りを返す関数
	public static long powrem(long a, long pow, long n) {
		long r = 1;
		for (long i = 1; i <= pow; i++) {
			r = (r*a) % n;
		}
		return r;
	}

	public static long powremm(long a, long pow, long n) {
		long r = 1L;
		for (long i = 1L; i <= pow; i++) {
			//if (a*a < a && i > 1) {
				r = ( (9999999*((r*(a/10000000))%n))%n + (r*(a-9999999*(a/10000000)))%n ) % n;
			//} else {
			//r = (r*a) % n;
			//}
		}
		return r;
	}
	
	public static long prodrem(long a, long b, long n) {
			return ( (9999999*((b*(a/10000000))%n))%n + (b*(a-9999999*(a/10000000)))%n ) % n;
	}

}



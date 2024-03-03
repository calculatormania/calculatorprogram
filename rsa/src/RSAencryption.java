import java.util.Arrays;
import java.util.Random;
public class RSAencryption {
	public static void main(String[] args) {

		//平文
		String str = "MATH";

		char [] Plain = str.toCharArray();

		System.out.println("以下の平文をRSA暗号により暗号化します。\n");
		System.out.print("平文: " + str);


		long [] Numbers = new long [Plain.length]; //平文を数に変換(文字と数を対応させる)
		long [] Cipher = new long [Plain.length]; //Numbersの要素たちを暗号化した数を入れる用の配列
		long [] Decrypt = new long [Plain.length]; //Cipherの要素たちを復号化した数を入れる用の配列

		for(int k = 0; k < Plain.length; k++) {
			Numbers[k] = (long)Plain[k]; 
		}

		System.out.println("");
		System.out.println("平文を文字コード変換: " + Arrays.toString(Numbers));
		System.out.println("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆\n");


		//生成する素数の上限の指定
		long N = 50000L;

		//十分大きい素数p,qをランダム生成(N/2以上N以下で生成)
		Random ran = new Random();
		long p = 2L;
		while (superPrimeJudge(p) == false || p < N/2) {
			p = ran.nextLong(N); 
		}

		long q = p;
		while (q == p || superPrimeJudge(q) == false || q < N/2) {
			q = ran.nextLong(N); 
		}

		System.out.println("<暗号化・復号化する際に使うもの>");
		System.out.println("2つの素数: p=" + p + ", q=" + q);

		long n = p*q; //公開鍵その1
		long lcm = ((p-1)/gcd(p-1,q-1)) * (q-1);

		long e = 1L;   //公開鍵その2(ランダムにとる)
		while (gcd(e,lcm) != 1 || powMod(e,2,lcm) == 1) {
			e = ran.nextLong(lcm);
		}

		System.out.println("公開鍵: n=pq=" + n + ", e=" + e + "\n");


		//公開鍵e,nにより暗号化
		for (int k = 0; k < Plain.length; k++) {
			Cipher[k] = powMod(Numbers[k],e,n);
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


		long d = 2L; //秘密鍵を見つける(復号化する際に用いる)
		for(long j = 2L; j < lcm; j++){
			if (prodMod(e,j,lcm) == 1) {
				d = j;
			} 
		}
		System.out.println("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆\n");
		System.out.println("秘密鍵: d=" + d);

		//秘密鍵dにより復号化する
		for (int k = 0; k < Plain.length; k++) {
			Decrypt[k] = powMod(Cipher[k],d,n);	
		}
		System.out.print("秘密鍵dにより復号化した文: " + Arrays.toString(Decrypt));

	}

	public static long gcd(long a, long b){//ユークリッドの互除法により最大公約数を返す関数
		long r = a % b;
		while (r != 0) {
			a = b;
			b = r;
			r = a%b;
		}
		return b;
	}

	public static boolean primeJudge(long a) {//素数判定関数
		boolean judge = false;
		for(long i = 2; (i < a && a % i != 0) || i == a; i++) {
			if (i == a) {
				judge = true;
			} 
		}
		return judge;
	}

	public static boolean superPrimeJudge(long a) {
		long sq_a = (long)Math.round(Math.sqrt(a));
		boolean judge = false;

		if (a == 2 || a == 3 || a == 5) {
			judge = true;
		}
		if (a % 2 == 0) {
			return judge;
		}

		for(long i = 3; i <= sq_a && a % i != 0; i += 2) {
			if (sq_a % 2 == 0 && i == sq_a - 1) {
				judge = true;
			} else if (sq_a % 2 != 0 && i == sq_a) {
				judge = true;
			}
		}
		return judge;
	}

	public static long powMod(long a, long pow, long n) {//aのpow乗をnで割った余りを返す関数
		long r = a;
		for (long i = 2; i <= pow; i++) {
			if (digit(a) + digit(r) >= 19) {
				if (digit(a) >= digit(r)) {
					r = ( (r * timesMod(halfDigitPow(a), (a/halfDigitPow(a))%n, n) )%n + (r*((a%halfDigitPow(a))%n))%n ) % n;
				} else {
					r = ( (a * timesMod(halfDigitPow(r), (r/halfDigitPow(r))%n, n) )%n + (a*((r%halfDigitPow(r))%n))%n ) % n;
				}
			} else {
				r = (r*a) % n;
			}
		}
		return r;
	}

	public static long prodMod(long a, long b, long n) {
		if (digit(a) + digit(b) >= 19) {
			if (digit(a) >= digit(b)) {
				b = ( (b * timesMod(halfDigitPow(a), (a/halfDigitPow(a))%n, n) )%n + (b*((a%halfDigitPow(a))%n))%n ) % n;
			} else {
				b = ( (a * timesMod(halfDigitPow(b), (b/halfDigitPow(b))%n, n) )%n + (a*((b%halfDigitPow(b))%n))%n ) % n;
			}
		} else {
			b = (b*a) % n;
		}
		return b;
	}

	public static long halfDigitPow(long a) {
		return pow(10,digit(a)/2);
	}

	public static long pow(long a, long pow) {

		if (pow <= 0) {
			return 1;
		}

		long b = a;

		for (long p = 2; p <= pow; p++) {
			a = a * b;
		}
		return a;

	}

	public static long digit(long a) {
		return String.valueOf(a).length();
	}

	public static long timesMod(long m, long a, long n) {

		long b = a;
		for (long i = 2; i <= m; i++) {
			b = (b + a) % n; 
		}
		return b;
	}

}








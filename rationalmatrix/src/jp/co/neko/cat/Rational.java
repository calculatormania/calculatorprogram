package jp.co.neko.cat;

public class Rational {

	private int num;
	private int denom;
	
		public Rational () {}

		public Rational(int num,int denom){
			
			//zeroAlert(denom);
			/*if (num == 0) {             //既約分数化
				denom = 1;
			} else {
				
				int d = gcd(num,denom);

				num = num/d;
				denom = denom/d;

				if (denom < 0) {
					num = -num;
					denom = -denom;
				}
			}*/
			
			this.num = num;
			this.denom = denom;
		}

		public void setNum(int num) {
			this.num = num;
		}

		public int getNum() {
			return num;
		}

		public void setDenom(int denom) {
			this.denom = denom;
		}

		public int getDenom() {
			return denom;
		}

		
		public void zeroAlert(int denom) {
			if (denom == 0) {
				System.out.println("分母が0です");
			}
		}

		public int gcd(int a, int b){ //aとbの最大公約数（Euclidの互除法を使用）

			int A = a;
			int B = b;

			if (a == 0) {
				return B;
			}

			int r = A % B;
			while (r != 0) {
				A = B;
				B = r;
				r = A % B;
			}
			return B;
		}


	}
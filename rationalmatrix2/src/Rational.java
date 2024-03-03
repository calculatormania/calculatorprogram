public class Rational {

	private int num;
	private int denom;

	public Rational () {} //デフォルトコンストラクタ

	public Rational(int num,int denom){
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

}
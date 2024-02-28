import java.util.Random;
import java.util.Scanner;
public class CommandBattle {
	public static final int H_Max_HP = 150;
	public static final int H_Max_MP = 50;
	public static final int H_constpow = 30;

	public static void main(String[] args) {
		int H_HP = 150;
		int H_MP = 50;
		int H_pow = 30;
		int E_HP = 200;
		int E_MP = 70;
		int Turn = 0;
		Random rand = new Random();
		System.out.println("てきがあらわれた！\n");
		System.out.println("<ゆうしゃのHP:" + H_HP + " ゆうしゃのMP:" +  H_MP+ ">");
		System.out.println("<<てきのHP:" + E_HP + ">>\n");

		java.util.Scanner scanner = new java.util.Scanner(System.in);
		Scanner scan = new Scanner(System.in);

		for(int turn = 1; turn <= 1000; turn++) { //ターン数の上限は25。これを超えたら戦闘を強制終了

			System.out.println(">ゆうしゃのターン"); //ゆうしゃのターン

			System.out.println("1.こうげき " + "2.まほう " + "3.めいそう " + "4.ちからをためる"); //行動を選択。逃げるコマンドはなし！ｗ

			int sel = scanner.nextInt();
			if (sel == 1) { //通常攻撃
				int critical = rand.nextInt(11);
				System.out.println("ゆうしゃのこうげき"); //ゆうしゃのこうげきは必中
				if (0 <= critical && critical <= 1) {
					System.out.println("クリティカルヒット！");
					System.out.println("てきに" + (H_pow + 30) + "のダメージを与えた！\n");
					E_HP = f(E_HP,H_pow+30);
				} else {
					System.out.println("てきに" + H_pow + "のダメージを与えた\n");
					E_HP = f(E_HP,H_pow);
				}
				H_pow = H_constpow;   //ちからをためるコマンドを選んだ後にこうげきコマンドを選んだら攻撃力を元に戻す
			} else if (sel == 2) { //魔法
				System.out.println("1.攻撃魔法: しょうひMP20 " + "2.回復魔法: しょうひMP10");

				int sell = scanner.nextInt();
				//scanner.close();
				if (sell == 1) { //攻撃魔法
					if (H_MP < 20) {
						System.out.println("MPがたりません！\n");
						continue; //MPが足りない場合は行動選択に戻る
					} else {
						H_MP = f(H_MP,20);
						System.out.println("ゆうしゃのまほうこうげき！");
						System.out.println("てきに" + 50 + "のダメージを与えた！\n");
						E_HP = f(E_HP,50);
					}

				} else { //回復魔法
					if (H_MP < 10) {
						System.out.println("MPがたりません！\n");
						continue; //MPが足りない場合は行動選択に戻る
					} else {
						H_MP = f(H_MP,10);
						System.out.println("ゆうしゃはかいふくまほうの詠唱を試みた！");
						System.out.println("ゆうしゃのHPが50回復した\n");
						H_HP = g(H_HP,50);

					}
				}
			} else if (sel == 3) {
				System.out.println("ゆうしゃはめいそうをした！");
				H_HP = g(H_HP,20);
				H_MP = h(H_MP,7);
				System.out.println("ゆうしゃのHPとMPが少し回復した！\n");
			} else {
				H_pow = H_pow + 40;
				System.out.println("ゆうしゃのこうげきりょくがあがった！\n"); //重ね掛け可
			}

			//ゆうしゃのターンおわり

			System.out.println("<ゆうしゃのHP:" + H_HP + " ゆうしゃのMP:" +  H_MP+ ">");
			System.out.println("<<てきのHP:" + E_HP + ">>\n");

			if (E_HP == 0) { //てきの死亡判定
				System.out.println("てきは倒れた！");
				System.out.println("ゆうしゃのしょうりだ！");
				System.out.println("かかったターン数:" + Turn);
				return;
			} else if (E_HP <= 50) {
				System.out.println("てきはよわっている！攻めどきだ！\n");
			} else {
				System.out.println("てきはまだピンピンしている...\n");
			}
			scan.nextLine();

			System.out.println(">>てきのターン"); //てきのターン
			scan.nextLine();
			int Eact = rand.nextInt(3);
			int hit = rand.nextInt(5);
			if (0 <= Eact && Eact <= 1) {
				System.out.println("てきのこうげき！");
				if (0 <= hit && hit <= 3) { //てきの攻撃の命中率は4/5
					System.out.println("ゆうしゃは30のダメージをうけた\n");
					H_HP = f(H_HP,30);
				} else {                    //1/5の確率で外れる
					System.out.println("てきのこうげきはあたらなかった！");
				}
			} else {
				System.out.println("てきのまほうこうげき！");
				if (E_MP < 20) {
					System.out.println("しかし、てきのMPがたりなかった！\n");
				} else {
					System.out.println("ゆうしゃは50のダメージをうけた\n");
					H_HP = f(H_HP,50);
					E_MP = f(E_MP,20);
				}
			} //てきのターンおわり

			System.out.println("<ゆうしゃのHP:" + H_HP + " ゆうしゃのMP:" +  H_MP+ ">");
			System.out.println("<<てきのHP:" + E_HP + ">>\n");

			if (H_HP == 0) { //ゆうしゃの死亡判定
				System.out.println("ゆうしゃはしんでしまった...");
				System.out.println("かかったターン数:" + Turn);
				return;
			}
			Turn++;
		}//for文のおわり
		System.out.println("けっちゃくがつかない...");
	}

	public static int f(int a, int b){
		if (a > b) {
			a = a - b;
		} else {
			a = 0;
		}
		return a;
	}

	public static int g(int a, int b){
		if (a + b >= H_Max_HP) {
			a = H_Max_HP;
		} else {
			a = a + b;
		}
		return a;
	}

	public static int h(int a, int b){
		if (a + b >= H_Max_MP) {
			a = H_Max_MP;
		} else {
			a = a + b;
		}
		return a;
	}

}
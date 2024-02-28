class Question {
	
	public static void main(String[] args){
		int n = 0;
		System.out.println("クイズを行います。番号を選択して回答してください（3問以上正解で合格）。");
		System.out.println();

		//一問目
		System.out.println("問1:2の8乗は？");
		System.out.println("1.128 " + "2.256 " + "3.64 " + "4.1024");
		int ans1 = new java.util.Scanner(System.in).nextInt();
		if(ans1 == 2) {
			System.out.println("正解！");
			n += 1; 
		}
		else {
			System.out.println("はずれ！残念...");
			n += 0;
		}
		System.out.println();
		//二問目
		System.out.println("問2:べき零行列の固有値は全て0になるか？");
		System.out.println("1.はい " + "2.いいえ");
		int ans2 = new java.util.Scanner(System.in).nextInt();
		if(ans2 == 1) {
			System.out.println("正解！");
			n += 1;
		}
		else {
			System.out.println("はずれ！残念...");
			n += 0;
		}
		System.out.println();
		//三問目
		System.out.println("問3:私の身長は何cmでしょう？");
		System.out.println("1.165cm " + "2.169.5cm " + "3.170cm " + "4.180cm");
		int ans3 = new java.util.Scanner(System.in).nextInt();
		if(ans3 == 2) {
			System.out.println("正解！");
			n += 1;
		}
		else {
			System.out.println("はずれ！残念...");
			n += 0;
		}
		System.out.println();
		//四問目
		System.out.println("問4:2の1000乗を3で割った余りはいくつ？");
		System.out.println("1.余りは1 " + "2.余りは2 " + "3.余りは0");
		int ans4 = new java.util.Scanner(System.in).nextInt();
		if(ans4 == 1) {
			System.out.println("正解！");
			n += 1;
		}
		else {
			System.out.println("はずれ！残念...");
			n += 0;
		}
		System.out.println();
		//五問目
		System.out.println("問5:SFCのローンチタイトルを次の選択肢から選んでください");
		System.out.println("1.FF4 " + "2.スーパーマリオワールド " + "3.F-ZERO " + "4.MOTHER2 " + "5.パイロットウィングス");
		int ans5 = new java.util.Scanner(System.in).nextInt();
		if(ans5 == 2 || ans5 == 3) {
			System.out.println("正解！");
			n += 1;
		}
		else {
			System.out.println("はずれ！残念...");
			n += 0;
		}
		System.out.println();
		System.out.println("お疲れさまでした。全部で" + n + "問正解です。");
	if(n > 3) {
		System.out.println("合格！");
	}
	else {
		System.out.println("残念ながら不合格...");
	}
	}
	
}
package integral;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Integral {
	public static void main(String[] args) {
		double a = 0;
		double b = 1;
		long count = 0;
		long upperLimit = 10000L;//ループ回数の上限
		List<Double> delta = new ArrayList<Double>();
		List<Double> widthList = new ArrayList<Double>();

		delta.add(a);
		delta.add(b);

		double mean = -1;
		double epsilon = 0.003;//0.001以下は時間がかかる
		widthList.add(epsilon+1);

		while (Collections.max(widthList) > epsilon) {
			while (b < mean || mean < a || delta.contains(mean) ) {
				mean = Math.random();
			}
			delta.add(mean);
			sort(delta);

			widthList.clear();//中身をリセット

			for (int k = 0; k < delta.size()-1; k++) {
				widthList.add(delta.get(k+1) - delta.get(k));
			}
			if (count == upperLimit) {
				break;
			}
			count++;
		}

		/*for (int i = 0; i < delta.size(); i++) {
			System.out.print(delta.get(i) + ", ");
		}*/

		List<Double> xi = new ArrayList<Double>();

		double s = -1;
		for (int i = 0; i < delta.size()-1; i++) {
			while (delta.get(i+1) < s || s < delta.get(i) || delta.contains(s)) {
				s = Math.random();
			}
			xi.add(s);
		}
		
		//System.out.println("\n");

		/*for (int i = 0; i < xi.size(); i++) {
			System.out.print(xi.get(i) + ", ");
		}*/

		double S = 0;
		for (int k = 0; k < xi.size(); k++) {
			S = S + f(xi.get(k))*(delta.get(k+1)-delta.get(k));
		}
		System.out.println("\n\n" + S + "\n\n" + count + "\n\n" + Collections.max(widthList) + "\n\n" + delta.size());
	}
	public static double min(List<Double> I) {

		double width = 0;

		for (int i = 0; i < I.size()-2; i++) {
			if (I.get(i+1)-I.get(i) < I.get(i+2) - I.get(i+1)) {
				width = I.get(i+1)-I.get(i);
			} else {
				width = I.get(i+2)-I.get(i+1);
			}
		}

		return width;
	}
	public static List<Double> sort(List<Double> I) {

		double dummy = 0;
		for (int i = 0; i <= I.size()-2; i++) {
			for (int j = 0; j < I.size() - i-1; j++) {
				if (I.get(j+1) < I.get(j) ) {
					dummy = I.get(j);
					I.set(j, I.get(j+1));
					I.set(j+1, dummy);
				}
			}
		}
		return I;
	}
	public static boolean contain(List<Double> I, double x) {

		if (I.size() == 0) {
			return false;
		}

		boolean judge = false; 
		for (int i=0; i < I.size(); i++) {
			if (x == I.get(i)) {
				return true;
			}
		}
		return judge;
	}
	public static double f(double x) {
		return (x*x*x);
	}
}

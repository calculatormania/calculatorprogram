package compositionoffunctions;

import java.util.ArrayList;
import java.util.List;

//区分的線形関数(狭義単調増加)の合成を計算するプログラム
public class CompositionOfFunctions {

	public static void main(String[] args) {
		List<Ratio[]> f = new ArrayList<>();
		Ratio[] f1 = new Ratio [4];
		f1[0] = new Ratio(1,2);
		f1[1] = new Ratio(0,1);
		f1[2] = new Ratio(0,1);
		f1[3] = new Ratio(1,2);
		Ratio[] f2 = new Ratio [4];
		f2[0] = new Ratio(1,1);
		f2[1] = new Ratio(-1,4);
		f2[2] = new Ratio(1,2);
		f2[3] = new Ratio(3,4);
		Ratio[] f3 = new Ratio [4];
		f3[0] = new Ratio(2,1);
		f3[1] = new Ratio(-1,1);
		f3[2] = new Ratio(3,4);
		f3[3] = new Ratio(1,1);	
		f.add(f1);
		f.add(f2);
		f.add(f3);

		List<Ratio[]> g = new ArrayList<>();
		Ratio[] g1 = new Ratio [4];
		g1[0] = new Ratio(1,1);
		g1[1] = new Ratio(0,1);
		g1[2] = new Ratio(0,1);
		g1[3] = new Ratio(1,2);
		Ratio[] g2 = new Ratio [4];
		g2[0] = new Ratio(1,2);
		g2[1] = new Ratio(1,4);
		g2[2] = new Ratio(1,2);
		g2[3] = new Ratio(3,4);
		Ratio[] g3 = new Ratio [4];
		g3[0] = new Ratio(1,1);
		g3[1] = new Ratio(-1,8);
		g3[2] = new Ratio(3,4);
		g3[3] = new Ratio(7,8);
		Ratio[] g4 = new Ratio [4];
		g4[0] = new Ratio(2,1);
		g4[1] = new Ratio(-1,1);
		g4[2] = new Ratio(7,8);
		g4[3] = new Ratio(1,1);
		g.add(g1);
		g.add(g2);
		g.add(g3);
		g.add(g4);

		List<Ratio[]> id = new ArrayList<>();
		Ratio[] I = new Ratio [4];
		I[0] = new Ratio(1,1);
		I[1] = new Ratio(0,1);
		I[2] = new Ratio(0,1);
		I[3] = new Ratio(1,1);
		id.add(I);

		/*int [] F1 = {2,2,1,2,1,1,1,2,2};
		int [] G1 = new int [F1.length];
		int [] F2 = {1,1,2,1,2,1,1,2,1,1,2,1,2,2,2,2,1};
		int [] G2 = new int [F2.length];

		for (int i=0; i<F1.length; i++) {
			if (F1[i]==1) {
				G1[i]=2;
			} else {
				G1[i]=1;
			}
		}

		for (int i=0; i<F2.length; i++) {
			if (F2[i]==1) {
				G2[i]=2;
			} else {
				G2[i]=1;
			}
		}
		int [] w = {1};

		System.out.println("wordlength: " + (F1.length+F2.length));
		System.out.println("***********************************");

		List<Ratio[]> h = comps(comps(F1),comps(w),comps(F2),inv(comps(G2)),inv(comps(w)),inv(comps(G1)));

		showFunc(h);

		System.out.println("***********************************");

		List<Ratio[]> k = comps(comps(F1),comps(F2),inv(comps(G2)),inv(comps(G1)));

		showFunc(k);

		System.out.println("***********************************");

		System.out.println(compareb2(h,id)+", "+compareb2(k,id));*/

		/*
		int [] W = {1,2};
		int [] V = {2,2,2,1};
		 */
		int [] W = {1,2,1};
		int [] V = {2,1,2};
		//{1,2,2,1,1,2,1,2,2,1,2,2,2,2,1,1,2,1,2,1,1};
		//{2,1,1,2,2,1,2,1,1,2,1,1,1,1,1,2,1,2,1,2,2};
		List<Ratio[]> h = comp(comps(W),inv(comps(V)));
		showFunc(h);
		System.out.println("***********************************");
		System.out.println(compareb3(h));
		System.out.println(W.length);
	}

	public static void showString(int[] w) {
		for (int i=0; i<w.length; i++) {
			System.out.print("x_" + w[i]);
		}
	}
	public static boolean compareb(List<Ratio[]> f) {
		for (int i=0; i<f.size(); i++) {
			if (!Ratio.equal(f.get(i)[0],new Ratio(1,1))) {
				if (Ratio.compare(f.get(i)[0],new Ratio(1,1))) {
					return true;
				} else {
					return false;
				}
			}
		}
		return true;
	}
	public static boolean compareb2(List<Ratio[]> f) {
		for (int i=f.size()-1; i>=0; i--) {
			if (!Ratio.equal(f.get(i)[0],new Ratio(1,1))) {
				if (Ratio.compare(f.get(i)[0],new Ratio(1,1))) {
					return true;
				} else {
					return false;
				}
			}
		}
		return true;
	}
	public static boolean compareb3(List<Ratio[]> f) {
		if (!Ratio.equal(f.get(0)[0],new Ratio(1,1))) {
			if (Ratio.compare(new Ratio(1,1), f.get(0)[0])) {
				return true;
			} else {
				return false;
			}
		} else {
			return compareb(f);
		}
	}
	public static boolean compareb(List<Ratio[]> f, List<Ratio[]> g) {//w >= vならtrue
		return compareb(comp(f,inv(g)));
	}
	public static boolean compareb2(List<Ratio[]> f, List<Ratio[]> g) {//w >= vならtrue
		return compareb2(comp(f,inv(g)));
	}
	public static boolean compareb3(List<Ratio[]> f, List<Ratio[]> g) {//w >= vならtrue
		return compareb3(comp(f,inv(g)));
	}
	public static boolean compareb(int[] w, int [] v) {//w >= vならtrue
		return compareb(comps(w),comps(v));
	}
	public static boolean compareb2(int[] w, int [] v) {//w >= vならtrue
		return compareb2(comps(w),comps(v));
	}
	public static boolean compareb3(int[] w, int [] v) {//w >= vならtrue
		return compareb3(comps(w),comps(v));
	}
	public static List<Ratio[]> comps(int [] w) {

		List<Ratio[]> f = new ArrayList<>();
		Ratio[] f1 = new Ratio [4];
		f1[0] = new Ratio(1,2);
		f1[1] = new Ratio(0,1);
		f1[2] = new Ratio(0,1);
		f1[3] = new Ratio(1,2);
		Ratio[] f2 = new Ratio [4];
		f2[0] = new Ratio(1,1);
		f2[1] = new Ratio(-1,4);
		f2[2] = new Ratio(1,2);
		f2[3] = new Ratio(3,4);
		Ratio[] f3 = new Ratio [4];
		f3[0] = new Ratio(2,1);
		f3[1] = new Ratio(-1,1);
		f3[2] = new Ratio(3,4);
		f3[3] = new Ratio(1,1);	
		f.add(f1);
		f.add(f2);
		f.add(f3);

		List<Ratio[]> g = new ArrayList<>();
		Ratio[] g1 = new Ratio [4];
		g1[0] = new Ratio(1,1);
		g1[1] = new Ratio(0,1);
		g1[2] = new Ratio(0,1);
		g1[3] = new Ratio(1,2);
		Ratio[] g2 = new Ratio [4];
		g2[0] = new Ratio(1,2);
		g2[1] = new Ratio(1,4);
		g2[2] = new Ratio(1,2);
		g2[3] = new Ratio(3,4);
		Ratio[] g3 = new Ratio [4];
		g3[0] = new Ratio(1,1);
		g3[1] = new Ratio(-1,8);
		g3[2] = new Ratio(3,4);
		g3[3] = new Ratio(7,8);
		Ratio[] g4 = new Ratio [4];
		g4[0] = new Ratio(2,1);
		g4[1] = new Ratio(-1,1);
		g4[2] = new Ratio(7,8);
		g4[3] = new Ratio(1,1);
		g.add(g1);
		g.add(g2);
		g.add(g3);
		g.add(g4);

		List<Ratio[]> h = new ArrayList<>();
		for (int i=0; i<w.length; i++) {
			if (i==0) {
				if (w[0] == 1) {
					h=f;
				} else if (w[0] == 2){
					h=g;
				} else if (w[0] == -1) {
					h = inv(f);
				} else {
					h = inv(g);
				}
			} else {
				if (w[i] == 1) { 
					h = comp(h,f);
				} else if (w[i] == 2) {
					h = comp(h,g);
				} else if (w[i] == -1) {
					h = comp(h,inv(f));
				} else {
					h = comp(h,inv(g));
				}
			}
		}
		return h;
	}
	public static List<Ratio[]> comp(List<Ratio[]> f, List<Ratio[]> g) {
		List<Ratio[]> h = new ArrayList<>();
		int m = g.size();
		int n = f.size();
		int start = 0;
		int end = 0;
		int c = 0;
		int count = 0;
		for (int i=0; i<m; i++) {
			Ratio a = Ratio.plus(Ratio.mul(g.get(i)[0],g.get(i)[2]),g.get(i)[1]);
			Ratio b = Ratio.plus(Ratio.mul(g.get(i)[0],g.get(i)[3]),g.get(i)[1]);
			for (int j=c; j<n; j++) {
				if (Ratio.compare(a, f.get(j)[2])) {
					start = j;
					c = j;
					break;
				}
			}
			for (int j=c; j<n; j++) {
				if (Ratio.compare(f.get(j)[3], b)) {
					if (Ratio.equal(f.get(j)[3], b)) {
						c = j+1;
					} else {
						c = j;
					}
					end = j;
					break;
				}
			}
			for (int k=start; k<=end; k++) {
				Ratio[] F = new Ratio [4];
				F[0] = Ratio.mul(f.get(k)[0],g.get(i)[0]);
				F[1] = Ratio.plus(Ratio.mul(f.get(k)[0],g.get(i)[1]), f.get(k)[1]);
				if (k==start && k!=end) {
					F[2] = new Ratio(g.get(i)[2].getNum(),g.get(i)[2].getDenom());
					F[3] = Ratio.div(Ratio.minus(f.get(k)[3],g.get(i)[1]), g.get(i)[0]);
				} else if (k!=start && k==end) {
					F[2] = Ratio.div(Ratio.minus(f.get(k)[2],g.get(i)[1]), g.get(i)[0]);
					F[3] = new Ratio(g.get(i)[3].getNum(),g.get(i)[3].getDenom());
				} else if (k==start && k==end) { 
					F[2] = new Ratio(g.get(i)[2].getNum(),g.get(i)[2].getDenom());
					F[3] = new Ratio(g.get(i)[3].getNum(),g.get(i)[3].getDenom());
				} else {
					F[2] = Ratio.div(Ratio.minus(f.get(k)[2],g.get(i)[1]), g.get(i)[0]);
					F[3] = Ratio.div(Ratio.minus(f.get(k)[3],g.get(i)[1]), g.get(i)[0]);
				}
				if (count>0 && Ratio.equal(h.get(count-1)[0],F[0]) && Ratio.equal(h.get(count-1)[1],F[1])) {
					h.get(count-1)[3].setNum(F[3].getNum());
					h.get(count-1)[3].setDenom(F[3].getDenom());
					//h.get(count-1)[3]= new Ratio(F[3].getNum(),F[3].getDenom());
					continue;
				}
				h.add(F);
				count++;
			}
		}
		return h;
	}
	public static List<Ratio[]> com(List<Ratio[]> f, List<Ratio[]> g){
		return comps(f,g,inv(f),inv(g));
	}
	public static List<Ratio[]> inv(List<Ratio[]> f) {
		List<Ratio[]> h = new ArrayList<>();
		for (int i=0; i<f.size(); i++) {
			Ratio[] F = new Ratio [4];
			F[0] = new Ratio(f.get(i)[0].getDenom(),f.get(i)[0].getNum());
			F[1] = Ratio.mul(new Ratio(-1,1),Ratio.div(f.get(i)[1],f.get(i)[0]));
			F[2] = Ratio.plus(Ratio.mul(f.get(i)[0],f.get(i)[2]),f.get(i)[1]);
			F[3] = Ratio.plus(Ratio.mul(f.get(i)[0],f.get(i)[3]),f.get(i)[1]);
			h.add(F);	
		}
		return h;
	}
	public static List<Ratio[]> comps(List<Ratio[]>... f) {

		List<Ratio[]> h = new ArrayList<>();
		for (int i=0; i<f.length; i++) {
			if (i==0) {
				h = f[0];
			} else {
				h = comp(h,f[i]);
			}
		}
		return h;
	}
	public static void showFunc(List<Ratio[]> f) {
		for (int i=0; i<f.size(); i++) {
			System.out.println(f.get(i)[0].getNum()+"/"+f.get(i)[0].getDenom()+ ", " + f.get(i)[1].getNum()+"/"+f.get(i)[1].getDenom() +", ["+f.get(i)[2].getNum()+"/"+f.get(i)[2].getDenom()+", "+f.get(i)[3].getNum()+"/"+f.get(i)[3].getDenom()+"]");
		}
	}
}
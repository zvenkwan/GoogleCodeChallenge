package com.google.challenges;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * calculate with fraction
 * goo.gl/BrWzJa
 * @author zg55
 *
 */
public class Doomsday {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		int[][] m = {{1} };
//		int[][] m = { { 0, 1, 0, 0, 0, 1 }, { 4, 0, 0, 3, 2, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 },
//				{ 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 } };
		int[][] m = { 
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{4, 0, 2, 1, 0, 3, 2, 1, 3, 4},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{1, 4, 3, 3, 3, 4, 1, 0, 2, 3},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{4, 3, 4, 2, 2, 1, 0, 0, 0, 0},
				{0, 1, 1, 0, 1, 0, 0, 4, 4, 3},
				{2, 2, 1, 4, 0, 2, 0, 3, 0, 4},
				{4, 3, 4, 0, 3, 0, 3, 4, 1, 1}  };
//		int[][] m = { 
//				{0, 0, 0, 0, 0, 0, 0, 0},
//				{1, 0, 1, 2, 0, 4, 4, 0},
//				{0, 0, 0, 0, 0, 0, 0, 0},
//				{2, 4, 3, 1, 0, 2, 1, 4},
//				{1, 3, 4, 2, 2, 1, 2, 0},
//				{2, 4, 2, 4, 3, 1, 0, 1},
//				{3, 3, 2, 2, 3, 3, 3, 1},
//				{1, 0, 3, 2, 2, 2, 0, 4}  };
//		int[][] m = { 
//				{ 0, 1, 0, 0, 0, 1, 0, 1, 0, 1 }, 
//				{ 0, 0, 3, 0, 2, 1, 0, 0, 0, 1 }, 
//				{ 0, 1, 0, 3, 0, 1, 2, 2, 0, 0 }, 
//				{ 1, 1, 0, 3, 0, 1, 2, 2, 0, 1 }, 
//				{ 0, 1, 0, 0, 0, 1, 0, 0, 0, 0 }, 
//				{ 0, 0, 0, 1, 0, 1, 0, 0, 0, 0 }, 
//				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
//				{ 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, 
//				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
//				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }  };
		System.out.println(Arrays.toString(answer(m)));
	}

	public static int[] answer(int[][] m) {

		// Your code goes here.
		int length = m.length;
		if(length == 1) {
			return new int[]{1,1};
		}
		ArrayList<Integer> terminals = new ArrayList<>();
		ArrayList<Integer> transits = new ArrayList<>();
		for (int i = 0; i < length; i++) {
			int rowsum = 0;
			for (int j = 0; j < length; j++) {
				rowsum += m[i][j];
			}
			if (rowsum == 0) {
				terminals.add(i);
			} else {
				transits.add(i);
			}
		}
		int numOfTerminal = terminals.size();

		int index = 0;
		int[][] m1 = new int[length][length];
		for (int t : terminals) {
			m1[index++] = m[t];
		}
		for (int t : transits) {
			m1[index++] = m[t];
		}
		int[][] m2 = new int[length][length];
		index = 0;
		int[] denom = new int[length];
		for (int c : terminals) {
			for (int r = 0; r < length; r++) {
				denom[r] += m1[r][c];
				m2[r][index] = m1[r][c];
			}
			index++;
		}
		for (int c : transits) {
			for (int r = 0; r < length; r++) {
				denom[r] += m1[r][c];
				m2[r][index] = m1[r][c];
			}
			index++;
		}
		//
//		for (int[] r : m2)
//			System.out.println(Arrays.toString(r));

		// System.out.println(Arrays.toString(denom));

		Fraction[][] R = new Fraction[length - numOfTerminal][length - numOfTerminal];
		for (int r = numOfTerminal; r < length; r++) {
			for (int c = numOfTerminal; c < length; c++) {
				// System.out.print(m2[r][c]+"/"+denom[r]+" ");
				R[r - numOfTerminal][c - numOfTerminal] = new Fraction(m2[r][c], denom[r]);
			}
			// System.out.println();
		}

		// for(double[] r : R)
		// System.out.println(Arrays.toString(r));

		Fraction[][] IminusR = new Fraction[R.length][R.length];
		for (int r = 0; r < R.length; r++) {
			for (int c = 0; c < R.length; c++) {
				if (r == c)
					IminusR[r][c] = Fraction.ONE.minus(R[r][c]);
				else {
					IminusR[r][c] = Fraction.ZERO.minus(R[r][c]);
				}
			}
		}

//		System.out.println("==========IminusR==========");
//		 for(Fraction[] r : IminusR)
//		 System.out.println(Arrays.toString(r));
		
		Fraction[][] N = getInvMatrix(IminusR);
//		System.out.println("==========N==========");
//		for (Fraction[] r : N)
//			System.out.println(Arrays.toString(r));

		Fraction[][] Q = new Fraction[length - numOfTerminal][numOfTerminal];
		for (int r = 0; r < Q.length; r++) {
			for (int c = 0; c < Q[0].length; c++) {
				Q[r][c] = new Fraction( m2[numOfTerminal + r][c], denom[numOfTerminal + r]);
			}
		}
//		System.out.println("==========Q==========");
//		for (Fraction[] r : Q)
//			System.out.println(Arrays.toString(r));

		Fraction[][] NQ = getProductMatrix(N, Q);
//		System.out.println("==========NQ==========");
//		for (Fraction[] r : NQ)
//			System.out.println(Arrays.toString(r));

		Fraction[] res = NQ[0];
//		for (int i = 0; i < res.length; i++) {
//			res[i] = res[i] * 14;
//		}
//		System.out.println("==========res==========");
//		System.out.println(Arrays.toString(res));
		
		
		ArrayList<Long> d = new ArrayList<Long>();
		for(Fraction f : res) {
			if(!f.equals(Fraction.ZERO)) {
				d.add(f.denominator);
			}
		}
//		System.out.println("==========d==========");
//		System.out.println(d);
		long gcd = d.get(0);
		long lcm = d.get(0);
		for(int i = 1; i < d.size(); i++) {
			gcd = Fraction.gcd(lcm, d.get(i));
//			System.out.println("g is "+lcm);
			lcm = lcm * d.get(i) / gcd;
//			System.out.println("l is "+lcm);
		}
//		for(int i = 1; i < d.size(); i++) {
//		}
		
		int[] result = new int[res.length + 1];
		for(int i = 0; i < res.length; i++) {
			result[i] = (int)(lcm / res[i].denominator * res[i].numerator);
		}
		result[res.length] = (int)lcm;
		return result;
	}

	private static Fraction[][] getProductMatrix(Fraction[][] matrix0, Fraction[][] matrix1) {

		int lenrow = matrix0.length;
		int lencol = matrix1[0].length;
		Fraction[][] product_matrix = new Fraction[lenrow][lencol];
		for (int i = 0; i < lenrow; i++) {
			for (int j = 0; j < lencol; j++){
				product_matrix[i][j] = Fraction.ZERO;
			}
		}
		for (int i = 0; i < lenrow; i++) {
			for (int j = 0; j < lencol; j++){
				for (int k = 0; k < matrix0[0].length; k++){
					product_matrix[i][j] = product_matrix[i][j].add(matrix0[i][k].times(matrix1[k][j]));
				}
			}
		}
		return product_matrix;
	}

	private static Fraction[][] getInvMatrix(Fraction[][] matrix) {
		Fraction[][] expand_matrix = new Fraction[matrix.length][matrix.length * 2];
		Fraction[][] new_matrix = new Fraction[matrix.length][matrix.length];
		initExpandMatrix(matrix, expand_matrix);
		boolean canAdjust = adjustMatrix(expand_matrix);
		if (false == canAdjust)
			return null;
		calculateExpandMatrix(expand_matrix);
		getNewMatrix(expand_matrix, new_matrix);
		return new_matrix;
	}

	private static void initExpandMatrix(Fraction[][] init_matrix, Fraction[][] expand_matrix) {

		for (int i = 0; i < expand_matrix.length; i++)
			for (int j = 0; j < expand_matrix[i].length; j++) {
				if (j < expand_matrix.length) {
					expand_matrix[i][j] = init_matrix[i][j];
				} else { 
					if (j == expand_matrix.length + i)
						expand_matrix[i][j] = Fraction.ONE;
					else
						expand_matrix[i][j] = Fraction.ZERO;
				}
			}

	}

	private static boolean adjustMatrix(Fraction[][] expand_matrix) {

		for (int i = 0; i < expand_matrix.length; i++) {
			if (expand_matrix[i][i].equals(Fraction.ZERO)) {
				int j;
				for (j = 0; j < expand_matrix.length; j++) {
					if (!expand_matrix[j][i].equals(Fraction.ZERO)) {
						Fraction[] temp = expand_matrix[i];
						expand_matrix[i] = expand_matrix[j];
						expand_matrix[j] = temp;
						break;
					}

				}
				if (j >= expand_matrix.length) {
					return false;
				}
			}
		}
		return true;
	}
	private static void calculateExpandMatrix(Fraction[][] expand_matrix) {

		for (int i = 0; i < expand_matrix.length; i++) {
			Fraction first_element = expand_matrix[i][i];
			for (int j = 0; j < expand_matrix[i].length; j++)
				expand_matrix[i][j] = expand_matrix[i][j].times(first_element.reciprocal());
			for (int m = 0; m < expand_matrix.length; m++) {
				if (m == i)
					continue;
				Fraction multi = expand_matrix[m][i];
				for (int n = 0; n < expand_matrix[i].length; n++) {
					expand_matrix[m][n] = expand_matrix[m][n].minus( expand_matrix[i][n].times(multi));
				}
			}
		}
	}

	private static void getNewMatrix(Fraction[][] expand_matrix, Fraction[][] new_matrix) {
		for (int i = 0; i < expand_matrix.length; i++)
			for (int j = 0; j < expand_matrix[i].length; j++) {
				if (j >= expand_matrix.length)
					new_matrix[i][j - expand_matrix.length] = expand_matrix[i][j];
			}
	}
}

class Fraction {
	long numerator;
	long denominator = 1;
	public static final Fraction ZERO = new Fraction(0, 1);
	public static final Fraction ONE = new Fraction(1, 1);
	public Fraction(long n, long d) {
		if(n == 0) d = 1;
		if(d < 0) {
			d = -d;
			n = -n;
		}
		numerator = n;
		denominator = d;
	}
	static long gcd(long x, long y) {
		long mod; 
		if (x < y) 
		{
			mod = x;
			x = y;
			y = mod;
		}
		long r = x % y; 
		while (r != 0)
		{
			x = y;
			y = r;
			r = x % y;
		}
		return y;

	}
	Fraction reduce() {
		if (this.numerator == 0)
			return Fraction.ZERO;
		long gcdNum = gcd(this.numerator, this.denominator);
		this.denominator = this.denominator / gcdNum;
		this.numerator = this.numerator / gcdNum;
		return new Fraction(this.numerator, this.denominator);
	}

	Fraction add(Fraction b) {
//		System.out.println("adding " + this + "   to " + b);
		long num1 = (this.numerator * b.denominator) + (b.numerator * this.denominator);
		long num2 = this.denominator * b.denominator;
		return new Fraction(num1, num2).reduce();
	}
	
	Fraction minus(Fraction b) {
//		System.out.println("substracting " + this + "   to " + b);
		long num1 = (this.numerator * b.denominator) - (b.numerator * this.denominator); 
		long num2 = this.denominator * b.denominator; 
		return new Fraction(num1, num2).reduce();
	}
	
	Fraction reciprocal() {
		if(this.numerator == 0) return Fraction.ONE;
		return new Fraction(this.denominator, this.numerator);
	}

	Fraction times(Fraction b) {
		if (this.numerator == 0 || b.numerator == 0)
			return ZERO;
		long num1 = this.numerator * b.numerator;
		long num2 = this.denominator * b.denominator;
		return new Fraction(num1, num2).reduce();

	}

	public String toString() {
		return this.numerator + "/" + this.denominator;
	}

}
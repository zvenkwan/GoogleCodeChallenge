package com.google.challenges;

public class QueueToDo {

	public static void main(String[] args) {
//		 System.out.println(answer(1,2));
		// xor(2, 4);
		// xor(4, 8);
		// xor(8, 16);
		// xor(16, 32);
		// xor(32, 64);
		// xorFromTo(0, 32132, 500_000_000);
		// xor(32132, 500_000_000);
//		xorFromTo(0, 17, 4);
//		xor(17, 47);4
		 System.out.println(answer(0, 4));
	}

	public static int answer(int start, int length) {

		// Your code goes here.
		long s = start;
		long l = length;
		long res = 0;
		long line = length;
		while (line > 0) {
			if(s > 20000_00000) break;
			long e = s + line - 1;
			if(e > 20000_00000) e = 20000_00000;
			res ^= xorFromTo(s, e);
			s += l;
			line--;
		}
		return (int)res;
	}

	static long xorFromZeroTo(long a) {
	    long res[] = {a,1,a+1,0};
	    return res[(int) (a%4)];
	}

	static long xorFromTo(long a, long b) {
		if(a == 0) return xorFromZeroTo(b);
	    return xorFromZeroTo(b)^xorFromZeroTo(a-1);
	}
}

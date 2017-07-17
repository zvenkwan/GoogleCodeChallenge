package com.google.challenges;
import java.math.BigInteger;
import java.util.Arrays;

public class PowerHungry {

	public static void main(String[] args) {
		int[] xs = {2,3};
		System.out.println(answer(xs));
	}
	
	public static String answer(int[] xs) {

        // Your code goes here.
		if(xs.length == 1 && xs[0] < 0) 
			return String.valueOf(xs[0]);
		Arrays.sort(xs);
		int numOfNega = 0;
		for(int x : xs) {
			if(x < 0) numOfNega ++;
		}
		int len = xs.length;
		BigInteger prod = BigInteger.ONE;
		int i = len - 1;
		int numOfPos = 0;
		for(; i >=0; i--) {
			if(xs[i] > 0) {
				prod = prod.multiply(new BigInteger(String.valueOf(xs[i])));
				numOfPos ++;
			}
			else
				break;
		}
		
		if(numOfNega < 2 && numOfPos == 0 && numOfNega + numOfPos < xs.length)
			return "0";
		
		if(numOfNega % 2 == 1) 
			numOfNega--;
		for(i = 0; i < numOfNega; i ++) {
			prod = prod.multiply(new BigInteger(String.valueOf(xs[i])));
		}
		
		return prod.max(BigInteger.ZERO).toString();
    } 
}

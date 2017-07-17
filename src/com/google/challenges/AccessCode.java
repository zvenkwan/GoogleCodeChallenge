package com.google.challenges;
import java.util.Arrays;
import java.util.HashSet;

public class AccessCode {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] l = {4,2,2,2,8};
		System.out.println(answer(l));
	}

	public static int answer(int[] l) { 

        // Your code goes here.
		
		if(l == null || l.length < 3) return 0;
		
		int count = 0;
		int[] numOfDouble = new int[l.length];
		for(int j = 1; j < l.length - 1; j ++) {
			for(int i = 0; i < j; i ++) {
				if(l[j] % l[i] == 0) {
					numOfDouble[j]++;
				}
			}
		}
		for(int k = 2; k < l.length; k++) {
			for(int j = 1; j < k; j++) {
				if(l[k] % l[j] == 0) {
					count += numOfDouble[j];
				}
			}
		}
		return count;

    } 
}

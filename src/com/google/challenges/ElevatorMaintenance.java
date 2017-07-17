package com.google.challenges;
import java.util.Arrays;
import java.util.Comparator;

public class ElevatorMaintenance {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String[] l = {"10.0.1", "1.1.1","1.1", "2.2.3","2.2.2","10.2","0.15."};
		System.out.println(Arrays.toString(answer(l)));
		
		String s = "a.b";
		String[] arr = s.split(".");
		System.out.println(arr.length);
	}

	public static String[] answer(String[] l) {

        // Your code goes here.
		Arrays.sort(l, new Comparator<String>(){

			@Override
			public int compare(String arg0, String arg1) {
				// TODO Auto-generated method stub
				String[] s0 = arg0.split("\\.");
				String[] s1 = arg1.split("\\.");
				int i = 0;
				while(i < s0.length && i < s1.length) {
					int vi = Integer.parseInt(s0[i]);
					int vj = Integer.parseInt(s1[i]);
					i++;
					if(vi < vj) return -1;
					else if(vi == vj) continue;
					else return 1;
				}
				if(s0.length < s1.length) return -1;
				else return 1;
			}
			
		});
		return l;
    } 
	
	class MyComparator implements Comparator {

		@Override
		public int compare(Object arg0, Object arg1) {
			// TODO Auto-generated method stub
			String[] s0 = ((String) arg0).split(".");
			String[] s1 = ((String) arg1).split(".");
			int i = 0;
			while(i < s0.length && i < s0.length) {
				int vi = Integer.parseInt(s0[i]);
				int vj = Integer.parseInt(s1[i]);
				i++;
				if(vi < vj) return -1;
				else if(vi == vj) continue;
				else return 1;
			}
			if(s0.length < s1.length) return -1;
			else return 1;
		}
		
	}
	
}

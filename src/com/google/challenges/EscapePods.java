package com.google.challenges;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class EscapePods {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		int[] entrances = {1};
//		int[] exits = { 0,3};
//		int[][] path = {
//				{0, 0, 0, 0},
//				{0, 0, 2, 4},
//				{1, 0, 0, 3},
//				{0, 0, 0, 0},
//				
//		};
//		int[] entrances = {0};
//		int[] exits = { 5};
//		int[][] path = {
//				{0, 10, 0, 0, 0, 0},
//				{0, 0, 8, 2, 0, 0},
//				{0, 0, 0, 0, 6, 4},
//				{0, 0, 0, 0, 6, 6},
//				{0, 0, 0, 0, 0, 12},
//				{0, 0, 0, 0, 0, 0},
//				
//		};
		int[] entrances = {0, 1};
		int[] exits = {4, 5};
		int[][] path = {
				{0, 0, 4, 6, 0, 0},
				{0, 0, 5, 2, 0, 0},
				{0, 0, 0, 0, 4, 4},
				{0, 0, 0, 0, 6, 6},
				{0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0},
				
		};
		System.out.println(
		answer(entrances, exits, path)
				);
	}
    public static int answer(int[] entrances, int[] exits, int[][] path) { 

        // Your code goes here.
    	int length = path.length + 2;
    	int[][] newPath = new int[length][length];
    	for(int e: entrances) {
    		newPath[0][e+1] = Integer.MAX_VALUE;
    	}
    	for(int e: exits) {
    		newPath[e+1][length-1] = Integer.MAX_VALUE;
    	}
    	for(int i = 0; i < path.length; i++) {
    		for(int j = 0; j < path.length; j++) {
    			newPath[i+1][j+1] = path[i][j];
    		}
    	}
    	
//    	for(int[] a: newPath) {
//    		System.out.println(Arrays.toString(a));
//    	}
    	return singlesource(0, length - 1, newPath);
    }
    
    public static int singlesource (int start, int end, int[][] path) {
    	int[][] residual = Arrays.copyOf(path, path.length);
    	HashMap<Integer, Integer> parentMap = new HashMap<Integer, Integer>();
    	ArrayList<ArrayList<Integer>> allPath = new ArrayList<>();
    	int count = 0;
    	
    	while(bfs(start, end, residual, parentMap)) {
    		ArrayList<Integer> p = new ArrayList<Integer>();
    		p.add(end);
    		int num = Integer.MAX_VALUE;
    		int current = end;
    		while(current != start) {
    			int parent = parentMap.get(current);
    			p.add(parent);
    			num = Math.min(num, residual[parent][current]);
    			current = parent;
    		}
    		p.add(start);

            Collections.reverse(p);
            allPath.add(p);
            count += num;

            current = end;
            while(current != start) {
    			int parent = parentMap.get(current);
            	residual[parent][current] -= num;
            	residual[current][parent] += num;
            	current = parent;
            }
    	}
    	return count;
    }
    
    public static boolean bfs(int start, int end, int[][] residual, HashMap<Integer, Integer> parentMap) {

    	LinkedList<Integer> queue = new LinkedList<Integer>();
    	HashSet<Integer> visited = new HashSet<Integer>();
    	queue.offer(start);
    	visited.add(start);
    	while(!queue.isEmpty()) {
    		int current = queue.poll();
    		for(int next = 0; next < residual[current].length; next++) {
    			if(! visited.contains(next) && residual[current][next] > 0) {
    				parentMap.put(next, current);
    				queue.offer(next);
    				visited.add(next);
    				if(next == end) {
    					return true;
    				}
    			}
    		}
    	}
    	return false;
    }
}


package com.google.challenges;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
/**
 *   

 


Running with Bunnies
====================

You and your rescued bunny prisoners need to get out of this collapsing death trap of a space station - and fast! Unfortunately, 
some of the bunnies have been weakened by their long imprisonment and can't run very fast. Their friends are trying to help 
them, but this escape would go a lot faster if you also pitched in. The defensive bulkhead doors have begun to close, and if you 
don't make it through in time, you'll be trapped! You need to grab as many bunnies as you can and get through the 
bulkheads before they close. 

The time it takes to move from your starting point to all of the bunnies and to the bulkhead will be given to you in a square 
matrix of integers. Each row will tell you the time it takes to get to the start, first bunny, second bunny, ..., last bunny, and 
the bulkhead in that order. The order of the rows follows the same pattern (start, each bunny, bulkhead). The bunnies can jump 
into your arms, so picking them up is instantaneous, and arriving at the bulkhead at the same time as it seals still allows for a 
successful, if dramatic, escape. (Don't worry, any bunnies you don't pick up will be able to escape with you since they no 
longer have to carry the ones you did pick up.) You can revisit different spots if you wish, and moving to the bulkhead 
doesn't mean you have to immediately leave - you can move to and from the bulkhead to pick up additional bunnies if time 
permits.

In addition to spending time traveling between bunnies, some paths interact with the space station's security checkpoints and 
add time back to the clock. Adding time to the clock will delay the closing of the bulkhead doors, and if the time goes back up to 
0 or a positive number after the doors have already closed, it triggers the bulkhead to reopen. Therefore, it might be possible to 
walk in a circle and keep gaining time: that is, each time a path is traversed, the same amount of time is used or added.

Write a function of the form answer(times, time_limit) to calculate the most bunnies you can pick up and which bunnies they are, 
while still escaping through the bulkhead before the doors close for good. If there are multiple sets of bunnies of the same size, 
return the set of bunnies with the lowest prisoner IDs (as indexes) in sorted order. The bunnies are represented as a sorted list 
by prisoner ID, with the first bunny being 0. There are at most 5 bunnies, and time_limit is a non-negative integer that is at 
most 999.
For instance, in the case of
[
  [0, 2, 2, 2, -1],  # 0 = Start
  [9, 0, 2, 2, -1],  # 1 = Bunny 0
  [9, 3, 0, 2, -1],  # 2 = Bunny 1
  [9, 3, 2, 0, -1],  # 3 = Bunny 2
  [9, 3, 2, 2,  0],  # 4 = Bulkhead
]
and a time limit of 1, the five inner array rows designate the starting point, bunny 0, bunny 1, bunny 2, and the bulkhead door 
exit respectively. You could take the path:

Start End Delta Time Status
    -   0     -    1 Bulkhead initially open
    0   4    -1    2
    4   2     2    0
    2   4    -1    1
    4   3     2   -1 Bulkhead closes
    3   4    -1    0 Bulkhead reopens; you and the bunnies exit

With this solution, you would pick up bunnies 1 and 2. This is the best combination for this space station hallway, so the answer 
is [1, 2].

Test cases
==========

Inputs:
    (int) times = [[0, 1, 1, 1, 1], [1, 0, 1, 1, 1], [1, 1, 0, 1, 1], [1, 1, 1, 0, 1], [1, 1, 1, 1, 0]]
    (int) time_limit = 3
Output:
    (int list) [0, 1]
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class RunningWithBunnies {

//	static ArrayList<Integer> bestRoute = new ArrayList<>();
	
	public static void print(int[][] times) {
		System.out.println("generated matrix is ");
		for(int i = 0 ; i < times.length; i++) {
			System.out.print("{");
			for(int j =0; j< times.length; j++) {
				System.out.print(times[i][j] );
				if(j < times.length - 1)
					System.out.print(", ");
			}
			System.out.print("}");
			if(i < times.length - 1)
				System.out.print(", ");
			System.out.println();
		}
	}

	public static int[] answer(int[][] times, int time_limit) {

		HashMap<Integer, LinkedList<Integer>> bestStates = new HashMap<>(7);
		for (int i = 0; i < times.length; i++) {
			bestStates.put(i, new LinkedList<Integer>());
		}
		HashSet<Integer> best = new HashSet<Integer>(7);
		int rescued = 0;
		HashMap<Integer, Integer> visitedMap = new HashMap<Integer, Integer>(7);
		int visited = 0;
		dfs(times, best, bestStates, 0, time_limit, rescued, visitedMap, visited);
		int[] res = new int[best.size()];
		int j = 0;
		for (int i : best) {
			res[j++] = i - 1;
		}
		Arrays.sort(res);
		return res;
	}

	private static int dfs(int[][] times, 
			HashSet<Integer> best, 
			HashMap<Integer, LinkedList<Integer>> bestStates, 
			int curr, 
			int timeLeft, 
			int rescued, 
			HashMap<Integer, Integer> visitedMap,
			int visited
			) {
		
		
		if (curr == times.length - 1 && timeLeft >= 0) {
			rescued |= visited;
			updateBest(best, visited, times, curr);
			if (best.size() == times.length - 2) {
				return best.size();
			}
		}
		int oldVisited = -1;
		if(visitedMap.get(curr) != null) {
			oldVisited = visitedMap.get(curr);
		}
		if(oldVisited == visited && (bestStates.get(curr).isEmpty() || bestStates.get(curr).peek() >= timeLeft)) {
			return best.size();
		}
		visitedMap.put(curr, visited);
		visited |= (1<<curr);
		ArrayList<Integer> canGoList = new ArrayList<>();
		if (isCycleExists(times, visited, rescued, curr, timeLeft, bestStates, canGoList)) {
			for(int i = 1 ; i < times.length - 1; i++) {
				best.add(i);
			}
			return best.size();
		}
		bestStates.get(curr).push(timeLeft);
		for (int i = 0; i < canGoList.size(); i++) {
			int next = canGoList.get(i);
			int oldNextVisited = -1;
			if(visitedMap.get(next) != null) {
				oldNextVisited = visitedMap.get(next);
			}
			int foundAll = dfs(times, best, bestStates, next, timeLeft - times[curr][next], rescued, visitedMap, visited);
			if(foundAll == times.length - 2) {
				break;
			}
			
			if (oldNextVisited != visitedMap.get(next)) {
				bestStates.get(next).pop();
				visitedMap.put(next, oldNextVisited);
			}
		}
		return best.size();
	}

	private static void updateBest(HashSet<Integer> best, 
			int visited,
			int[][] times, int curr) {
		if(curr != times.length - 1) return;
		HashSet<Integer> set = new HashSet<Integer>(7);
		int i = 1;
		int mask = 0;
		int max = 1 << (times.length - 1);
		while( (mask = (1 << i )) < max) {
			if((visited & mask) == mask) {
				set.add(i);
			}
			i++;
		}
		if (best.size() < set.size()) {
			best.removeAll(best);
			best.addAll(set);
		}
	}
	
	private static int countBunnies(int[][] times, int set) {
		int count = 0;
		set = set & (1 << (times.length - 1) - 2);
		while((set & 1) == 1) {
			count++;
			set = set >> 1;
		}
		return count;
	}

	private static boolean isCycleExists(
			final int[][] times, 
			final int visited,
			int rescued, 
			final int curr, 
			final int timeLeft,
			HashMap<Integer, LinkedList<Integer>> bestStates, 
			ArrayList<Integer> canGoList
			) {
		int end = times.length - 1;
		int bunNum = end - 1;
		int countRescued = countBunnies(times, rescued);
//		all bunnies have been taken
		if (countRescued == bunNum && curr == end) {
			return false;
		}
		int countToBeRescued = countBunnies(times, visited);
		for (int i = 0; i < times[curr].length; i++) {
			if (i == curr)
				continue;
			int newTimeLeft = timeLeft - times[curr][i];
			if(newTimeLeft < 0 && timeLeft < 0) continue;
			LinkedList<Integer> statesList = bestStates.get(i);
			boolean isInitial = false;
			if(statesList.isEmpty()) {
				isInitial = true;
			} else if(statesList.peek() < newTimeLeft) {
				return true;
			} 
			else if(statesList.peek() == newTimeLeft) {
				isInitial = true;
			}
			if ((timeLeft < newTimeLeft && (i == end  || i != end && isInitial) )
					|| (newTimeLeft >= 0 && isInitial) || timeLeft >= 0) {
				canGoList.add(i);
			}
		}
		final boolean allToBeRescued = countToBeRescued == bunNum;
		Collections.sort(canGoList, new Comparator<Integer>(){

			public int compare(Integer o1, Integer o2) {
				if(allToBeRescued) {
					if(o1 == times.length - 1 && timeLeft - times[curr][o1] >= 0) return -1;
					else if(o2 == times.length - 1 && timeLeft - times[curr][o2] >= 0) return 1;
				}
				if((visited & (1 << o1)) == 1 && (visited & (1 << o2)) == 1) {
					return times[curr][o1] - times[curr][o2];
				} else if((visited & (1 << o1)) == 1) {
					return 1;
				} else if((visited & (1 << o2)) == 1){
					return -1;
				} else {
					return times[curr][o1] - times[curr][o2];
				}
			}
			
		});
		return false;
	}
	
	
	
	
	static {
		PrintStream o;
		try {
			File output = new File("src\\output\\bunny" +".out");
			o = new PrintStream(output);
			System.setOut(o);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		int[][] times = {
				{0, 14,19, 10, -1, 12, 4}, 
				{7, 0, 19, 4, 19, 17, 7}, 
				{15, 8, 0, 14, 8, 4, 3}, 
				{10, 14, 6, 0, 0, 5, 9}, 
				{18, 8, 4, 0, 0, 12, 16}, 
				{0, 13, 1, -1, 12, 0, 4}, 
				{8, 5, 2, 11, 12, 16, 0}
		};  //# 4 = Bulkhead 123
		int time = 7;	
//		int[][] times = {
//				{0, 97, 689, -132, 988, 899, 898}, 
//				{26, 0, 404, 641, 793, 628, 914}, 
//				{481, 47, 0, 175, 420, 570, 298}, 
//				{464, 341, 769, 0, 715, 867, 298}, 
//				{282, 494, 377, 49, 0, 728, 981}, 
//				{780, 749, 958, 618, 778, 0, 424}, 
//				{177, 807, 835, 827, 281, 136, 0}
//		};  //# 4 = Bulkhead
//		int time = 900;	
//		int[][] times = {
//				{0,99,99,99,99,99,-1},
//				{99,0,99,99,99,99,99},
//				{99,99,0,99,99,99,99},
//				{99,99,99,0,99,99,99},
//				{99,99,99,99,0,99,99},
//				{99,99,99,99,0,0,99},
//				{0,99,99,99,99,99,0},
//		};  //# 4 = Bulkhead
//		int time = 1;	
//		int[][] times = {
//		{0, 2, 2, 2, -1},
//		{9, 0, 2, 2, -1},  //# 1 = Bunny 0
//		{9, 3, 0, 2, -1},  //# 2 = Bunny 1
//		{9, 3, 2, 0, -1},  //# 3 = Bunny 2
//		{9, 3, 2, 2,  0}
//};  //# 4 = Bulkhead
//int time = 1;
//int[][] times = {
//		{0, 1, 1,1,1},
//		{1,0,1,1,1},  //# 1 = Bunny 0
//		{1,1,0,1,1},  //# 2 = Bunny 1
//		{1,1,1,0,1},  //# 3 = Bunny 2
//		{1,1,1,1,0}
//};  //# 4 = Bulkhead
//int time = 3;
		print(times);
		int r[] = answer(times, time);
		System.out.println(" finally bunnies saved are " + Arrays.toString(r));
//		System.out.println("route is " + RunningWithBunnies.bestRoute);
	}
}
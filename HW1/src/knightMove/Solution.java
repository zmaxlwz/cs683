package knightMove;

import java.util.*;

/**
 * this is the main class to solve the Knight-Move problem
 * @author wenzhao
 *
 */
public class Solution {
	
	/**
	 * this method is used to search the path from the start position to the goal position
	 * @param start the start position
	 * @param goal the goal position to search
	 * @return the number of nodes expanded during the search
	 */
	public int search(Position start, Position goal){
		//use a priority queue to store generated positions
		//use a HashSet to store already expanded nodes
		
		//f-value(f=g+h) comparator 
		FValueComparator comp = new FValueComparator();
		//create a priority queue for positions according to their f-value
		PriorityQueue<Position> pq = new PriorityQueue<Position>(100, comp);
		//a set with expanded nodes
		HashSet<Position> set = new HashSet<Position>();
		//number of nodes expanded
		int nodes_expanded = 0;
		
		//insert the start position
		start.g_value = 0;
		start.heuristic_val = heuristicFunction(start, goal);
		start.f_value = start.g_value + start.heuristic_val;
		pq.add(start);
		
		while(!pq.isEmpty()){
			//get the head node from the priority queue
			Position p = pq.poll();
			if(!set.contains(p)){
				set.add(p);
				nodes_expanded++;
				
				if(p.equals(goal)){
					goal.g_value = p.g_value;					
					return nodes_expanded;
				}
					 				
				//expand p
				List<Position> neighborList = p.generateNeighbors();
				for(int i=0;i<neighborList.size();i++){
					Position neighbor = neighborList.get(i);
					neighbor.g_value = p.g_value+1;
					neighbor.heuristic_val = heuristicFunction(neighbor, goal);
					neighbor.f_value = neighbor.g_value + neighbor.heuristic_val;
					pq.add(neighbor);
				}
			}
						
		}
		
		return nodes_expanded;
	}
	
	/**
	 * 
	 * @param current the current position
	 * @param goal the goal position
	 * @return the heuristic function value for the current position
	 */
	public int heuristicFunction(Position current, Position goal){
		//result is the heuristic function value
		int result;
		
		int x_diff = (int)Math.ceil(Math.abs((double)current.x - (double)goal.x)/2);
		int y_diff = (int)Math.ceil(Math.abs((double)current.y - (double)goal.y)/2);
		int x_y_diff = Math.abs(Math.abs(current.x-goal.x) - Math.abs(current.y-goal.y));

		result = Math.max(x_diff, y_diff);
		result = Math.max(result, x_y_diff);
		
		return result;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Position start = new Position(0,0);
		Position goal = new Position(-23,-28);
		
		Solution sol = new Solution();
		
		long startTime = System.currentTimeMillis();
		int num_nodes_expanded = sol.search(start, goal);
		long duration = System.currentTimeMillis() - startTime;
		
		System.out.println(num_nodes_expanded);
		System.out.println(goal.g_value);
		System.out.println((double)duration/1000 + " seconds.");

	}

}

package knightMove;

import java.util.*;

/**
 * this is the class which defines each position in the chess board
 * @author wenzhao
 *
 */
public class Position {
	
	int x;
	int y;
	
	//g value = the number of moves to this position
	int g_value;
	//h value
	int heuristic_val;
	//f = g+h
	int f_value;
	
	
	public Position(int x, int y){
		this.x = x;
		this.y = y;
		this.g_value = 0;
		this.heuristic_val = 0;
		this.f_value = 0;
		
	}
	
	public Position(int x, int y, int g, int h, int f){
		this.x = x;
		this.y = y;
		this.g_value = g;
		this.heuristic_val = h;
		this.f_value = f;
	}
	
	/**
	 * compares this position with another position
	 * @param p the other position to compare 
	 * @return true if the two positions are the same, otherwise, return false
	 */
	public boolean equals(Position p){
		if(this.x == p.x && this.y == p.y)
			return true;
		else
			return false;
	}
	
	/**
	 * this method generate a list of neighboring positions for this position
	 * @return a list of neighboring positions for this position
	 */
	public List<Position> generateNeighbors(){
		
		List<Position> neighborList = new ArrayList<Position>();
		//add the 8 neighbors can be moved from this position
		neighborList.add(new Position(x+1, y+2));
		neighborList.add(new Position(x+2, y+1));
		neighborList.add(new Position(x+2, y-1));
		neighborList.add(new Position(x+1, y-2));
		neighborList.add(new Position(x-1, y+2));
		neighborList.add(new Position(x-2, y+1));
		neighborList.add(new Position(x-2, y-1));
		neighborList.add(new Position(x-1, y-2));
		
		return neighborList;
	} 

}

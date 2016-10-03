package knightMove;

import java.util.Comparator;

/**
 * this is a comparator class to compare the f-values of two positions
 * @author wenzhao
 *
 */
public class FValueComparator implements Comparator<Position> {
	
	/**
	 * the overrided compare method
	 */
	public int compare(Position p1, Position p2){
		if(p1.f_value < p2.f_value)
			return -1;
		else if(p1.f_value == p2.f_value)
			return 0;
		else{
			return 1;
		}
		
	}

}

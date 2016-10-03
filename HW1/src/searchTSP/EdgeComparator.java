package searchTSP;

import java.util.*;

/**
 * the comparator to compare the distances of two edges
 * @author wenzhao
 *
 */
public class EdgeComparator implements Comparator<Edge>{
	
	
	public int compare(Edge e1, Edge e2){
		if(e1.distance < e2.distance){
			return -1;
		}
		else if(e1.distance == e2.distance){
			return 0;
		}
		else{
			return 1;
		}
	}

	
}

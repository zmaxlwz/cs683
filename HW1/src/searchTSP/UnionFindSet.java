package searchTSP;

/**
 * this is the Union-Find data structure
 * this is to facilitate MST construction
 * @author wenzhao
 *
 */
public class UnionFindSet {
	
	/**
	 * make set method
	 * @param c the city to make set
	 * @return a UFNode for the city
	 */
	public UFNode make_set(City c){
		return new UFNode(c);
	}

	/**
	 * find set method
	 * @param n the input UFNode 
	 * @return the set representative UFNode for the input UFNode
	 */
	public UFNode find_set(UFNode n){
		if(n.parent != n){
			n.parent = find_set(n.parent);
		}
		return n.parent;
	}
	
	/**
	 * a method to be called by union
	 * @param n1 UFNode n1
	 * @param n2 UFNode n2
	 */
	public void link(UFNode n1, UFNode n2){
		//link two sets
		//set one rooted tree point to the other rooted tree
		//n1 and n2 are the root of two rooted trees
		
		if(n1.rank > n2.rank){
			n2.parent = n1;
		}
		else{
			n1.parent = n2;
			if(n1.rank == n2.rank){
				n2.rank = n2.rank+1;
			}
		}
		return;
	}
	
	/**
	 * the union method
	 * @param n1 the input UFNode n1
	 * @param n2 the input UFNode n2
	 */
	public void union(UFNode n1, UFNode n2){
		//combine the two rooted tree together
		link(find_set(n1), find_set(n2));
		return;
	}
	
}

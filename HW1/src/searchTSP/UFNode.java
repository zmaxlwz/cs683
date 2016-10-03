package searchTSP;

/**
 * this class defines the node of the Union-Find structure, which are rooted trees
 * @author wenzhao
 *
 */
public class UFNode {
	
	UFNode parent;
	City c;
	int rank;
	
	public UFNode(City c){
		this.c = c;
		this.parent = this;
		this.rank = 0;
	}
	
	

}

package searchTSP;

/**
 * this class defines the edge between two cities
 * @author wenzhao
 *
 */
public class Edge {
	
	City source;
	City desination;
	double distance;
	
	public Edge(City s, City d, double distance){
		this.source = s;
		this.desination = d;
		this.distance = distance;
	}

}

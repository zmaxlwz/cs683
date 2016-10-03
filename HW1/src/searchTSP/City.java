package searchTSP;

/**
 * the class for city objects
 * @author wenzhao
 *
 */
public class City {
	
	double x;
	double y;
	
	public City(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public String toString(){
		return "[" + this.x + ", " + this.y + "]";
	}
	
	public boolean equals(City c){
		if(this.x == c.x && this.y == c.y){
			return true;
		}
		else{
			return false;
		}
	}

}

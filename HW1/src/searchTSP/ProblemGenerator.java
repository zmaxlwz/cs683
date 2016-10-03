package searchTSP;

import java.util.*;

/**
 * this class is used to generate problems, create a given number of cities with x and y axis within a range
 * @author wenzhao
 *
 */
public class ProblemGenerator {
	
	double min_value;
	double max_value;
	
	int num_cities;
	
	List<City> cityList;
	double[][] distanceMatrix;
	
	/**
	 * this is the method to generate problems
	 * @param city_num the number of cities to generate
	 * @param min_value the min value for x and y axis
	 * @param max_value the max value for x and y axis
	 */
	public ProblemGenerator(int city_num, double min_value, double max_value){
		this.num_cities = city_num;
		this.min_value = min_value;
		this.max_value = max_value;
		this.cityList = new ArrayList<City>();
		this.distanceMatrix = new double[city_num][city_num];
		
		Random rand = new Random();
		double x, y;
		//generate all cities
		for(int i=0;i<city_num;i++){
			x = rand.nextDouble()*(this.max_value - this.min_value) + this.min_value;
			y = rand.nextDouble()*(this.max_value - this.min_value) + this.min_value;
			this.cityList.add(new City(x,y));			
		}
		//generate the distance matrix
		for(int i=0;i<this.num_cities;i++){
			for(int j=0;j<this.num_cities;j++){
				if(i==j){
					this.distanceMatrix[i][j] = Double.MAX_VALUE;
				}
				else{
					double source_x = this.cityList.get(i).x;
					double source_y = this.cityList.get(i).y;
					double destination_x = this.cityList.get(j).x;
					double destination_y = this.cityList.get(j).y;
					//Euclidean distance between the two cities
					this.distanceMatrix[i][j] = Math.sqrt(Math.pow(destination_x-source_x, 2)+Math.pow(destination_y-source_y, 2));
					
				}
				
			}
		}
	}
	
	/**
	 * get city list
	 * @return a list of cities
	 */
	public List<City> getCityList(){
		return this.cityList;
	}
	
	/**
	 * get distance matrix
	 * @return get the distance matrix between pairs of cities
	 */
	public double[][] getDistanceMatrix(){
		return this.distanceMatrix;
	}
	
	public static void main(String[] args){
		
		int city_num = 5;
		double min = 0;
		double max = 1;
		ProblemGenerator gen = new ProblemGenerator(city_num, min, max);
		List<City> list = gen.getCityList();
		double[][] distanceMatrix = gen.getDistanceMatrix();
		System.out.println(list);
		for(int i=0;i<distanceMatrix.length;i++){
			for(int j=0;j<distanceMatrix[0].length;j++){
				System.out.print(distanceMatrix[i][j] + ", ");
			}
			System.out.println();
		}
		
	}

}

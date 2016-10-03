package searchTSP;

import java.util.*;

/**
 * this is the main class to solve TSP problem
 * @author wenzhao
 *
 */
public class TSP {

	List<City> list;
	double[][] distanceMatrix;
	PriorityQueue<Edge> pq;
	boolean[] visited;
	int total_city_num;
	int num_city_visited;
	
	List<City> path;
	
	int startCityIndex;
	
	/**
	 * do some initialization work 
	 * @param city_num the number of cities in the TSP
	 * @param min_value the range min value in the coordinate(for both x and y)
	 * @param max_value the range max value in the coordinate(for both x and y)
	 */
	public void initialize(int city_num, double min_value, double max_value){
		
		ProblemGenerator gen = new ProblemGenerator(city_num, min_value, max_value);
		this.list = gen.getCityList();
		this.distanceMatrix = gen.getDistanceMatrix();
		
		this.total_city_num = city_num;
		this.num_city_visited = 0;
		
		//the TSP traveling path, from start city to every other city and back to the start city
		this.path = new ArrayList<City>();		
		
		this.visited = new boolean[city_num];
		for(int i=0;i<city_num;i++){
			this.visited[i] = false;
		}
				
				
	}
	
	/**
	 * put each edge into the priority queue
	 */
	public void fillEdgePQ(){
		
		this.pq = new PriorityQueue<Edge>(100, new EdgeComparator());
		for(int i=0;i<this.total_city_num-1;i++){
			for(int j=i+1;j<this.total_city_num;j++){
				Edge e = new Edge(this.list.get(i), this.list.get(j), this.distanceMatrix[i][j]);
				this.pq.add(e);
			}
		}
		
	}
	
	/**
	 * compute total distance of the MST for the unvisited cities
	 * @return the total distance of the MST for the unvisited cities
	 */
	public double weight_MST(){
		//the union-find set for all cities
		UnionFindSet set = new UnionFindSet();
		List<UFNode> uf_list = new ArrayList<UFNode>();
		
		for(int i=0;i<this.list.size();i++){
			//make set for each city
			uf_list.add(set.make_set(this.list.get(i)));
		}
		
		//*****what if there is only 1 unvisited city?********
		//if there is only 1 unvisited city, this method will return 0
		int num_unvisited_city = this.total_city_num-this.num_city_visited;
		int num_edge_add = 0;
		double MST_weight = 0;
		while(!this.pq.isEmpty() && num_edge_add<num_unvisited_city-1){
			Edge e = this.pq.poll();
			City source = e.source;
			City destination = e.desination;
			int source_index = this.list.indexOf(source);
			int destination_index = this.list.indexOf(destination);
			UFNode sourceNode = uf_list.get(source_index);
			UFNode destinationNode = uf_list.get(destination_index);
			if(!this.visited[source_index] && !this.visited[destination_index] && set.find_set(sourceNode) != set.find_set(destinationNode)){
				//if both the city are unvisited and this two cities are not in the same set
				num_edge_add++;
				MST_weight += e.distance;
				set.union(sourceNode, destinationNode);
			}
		}
		
		if(num_edge_add == num_unvisited_city-1){
			return MST_weight;
		}
		else{
			System.out.println("Priority Queue is empty in weight_MST method.");
			return -1;
		}
		
	}
	
	/**
	 * compute the f-value in A* search
	 * @param cityIndex the index of the city to compute f-value
	 * @return the f-value of the city
	 */
	public double computeFValue(int cityIndex){
		//compute the f-value for the city of cityIndex
		
		double g_value = 0;
		double h_value = 0;
		double f_value = 0;
		
		//the index of the last city visited
		int lastCityIndex = this.list.indexOf(this.path.get(this.path.size()-1));
		//g_value: distance from last city to this city, 
		//ignoring the path length from start city to last city, because they are the same for other cities as well
		g_value = this.distanceMatrix[lastCityIndex][cityIndex];
		
		//compute h-value: the sum of the three parts below
		//(1)distance from this city to the nearest unvisited city 
		//(2)MST weight of all the unvisited cities
		//(3)distance from the nearest unvisited city to the start city
		double part1 = Double.MAX_VALUE;
		double part3 = Double.MAX_VALUE;
		double distance;
		boolean existUnvisited = false;
		for(int i=0;i<this.list.size();i++){
			if(!this.visited[i]){
				//distance from this city to the unvisited city
				distance = this.distanceMatrix[cityIndex][i];
				if(distance < part1){
					part1 = distance;
				}
				//distance from the unvisited city to the start city
				distance = this.distanceMatrix[i][this.startCityIndex];
				if(distance < part3){
					part3 = distance;
				}
			}
		}
		
		if(existUnvisited){
			//there is still city unvisited
			
			//put all edges into a priority queue
			fillEdgePQ();
			//compute MST weight
			double part2 = weight_MST();
			
			h_value = part1+part2+part3;
			
			f_value = g_value + h_value;
			
			return f_value;
		}
		else{
			//there is no unvisited city, return the real distance
			return g_value + this.distanceMatrix[cityIndex][this.startCityIndex];
		}
		
	}
	
	/**
	 * find the next city to expand
	 * @return the index of the next city
	 */
	public int findNextCity(){
		//find the index of the next city to visit
		
		double min_Fvalue = Double.MAX_VALUE;
		int nextCityIndex = -1;
		for(int i=0;i<this.list.size();i++){
			//compute the f value for each unvisited city
			if(!this.visited[i]){
				this.visited[i] = true;
				double f_value = computeFValue(i);
				if(f_value < min_Fvalue){
					min_Fvalue = f_value;
					nextCityIndex = i;
				}				
				this.visited[i] = false;
			}			
		}
		if(nextCityIndex < 0){
			System.out.println("haven't found the next city in findNextCity()!");
		}
		return nextCityIndex;
	}
	
	/**
	 * the method to solve TSP problem, compute the shortest tour and put into this.path
	 * @param startCityIndex the start city index
	 */
	public int search_TSP(int startCityIndex){
		//the main method to solve the TSP problem using A* with MST heuristics
		
		if(startCityIndex<0 || startCityIndex >= this.total_city_num){
			System.out.println("the start city index is not valid!");
			return 0;
		}
		
		this.startCityIndex = startCityIndex;
		this.num_city_visited = 1;
		this.visited[startCityIndex] = true;
		this.path.add(this.list.get(startCityIndex));
		
		
		while(this.num_city_visited<this.total_city_num){
			//there is still more than one unvisited city
			
			//find the next city to visit
			int nextCityIndex = findNextCity();
			this.num_city_visited++;
			this.visited[nextCityIndex] = true;
			this.path.add(this.list.get(nextCityIndex));
		}
		this.path.add(this.list.get(startCityIndex));
		
		return this.num_city_visited;
	}
	
	/**
	 * compute the length of the shortest tour for TSP according to the path found
	 * @return the length of the shortest tour for TSP
	 */
	public double minLength_TSP(){
		//call this method after search_TSP() method
		//because this method will use the path found by search_TSP() method
		
		double tour_length = 0;
		for(int i=0;i<this.path.size()-1;i++){
			City source = this.path.get(i);
			City destination = this.path.get(i+1);
			int sourceIndex = this.list.indexOf(source);
			int destinationIndex = this.list.indexOf(destination);
			tour_length += this.distanceMatrix[sourceIndex][destinationIndex];
			
		}
		
		return tour_length;
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TSP tsp = new TSP();
		
		double min_value = 0;
		double max_value = 1;
		int num_city = 1000;
		
		tsp.initialize(num_city, min_value, max_value);		
		int startCityIndex = 0;
		int num_nodes_expanded;
		num_nodes_expanded = tsp.search_TSP(startCityIndex);
		//System.out.println(sol.path);
		System.out.println(num_nodes_expanded);
		System.out.println(tsp.minLength_TSP());
		
		/*
		for(int i=0;i<num_city;i++){
			tsp.initialize(num_city, min_value, max_value);
			tsp.search_TSP(i);
			System.out.println(tsp.minLength_TSP());
		}
		*/

	}

}

package searchTSP;

import java.io.*;

/**
 * this is the class to run experiments
 * @author wenzhao
 *
 */
public class ExperimentPlatform {

	/**
	 * this is the method to run experiment
	 * @param filename the name of the output file
	 * @param min_value the min value for x and y axis of each city
	 * @param max_value the max value for x and y axis of each city
	 * @throws IOException if there is exception when opening or writing the output files
	 */
	public void runExperiment(String filename, double min_value, double max_value) throws IOException{
		
		TSP tsp = new TSP();
		PrintWriter writer = new PrintWriter(filename, "UTF-8");
		int num_nodes_expanded = 0;
		double tourLengthTSP = 0;
		
		for(int cityNum = 50; cityNum<=1000;cityNum += 50){
			System.out.println(cityNum + " cities: run...");
			
			long startTime = System.currentTimeMillis();
			
			tsp.initialize(cityNum, min_value, max_value);
			num_nodes_expanded = tsp.search_TSP(0);
			tourLengthTSP = tsp.minLength_TSP();
			
			long duration = System.currentTimeMillis() - startTime;
			
			writer.println(cityNum + "," + num_nodes_expanded + "," + (double)duration/1000);
			
			System.out.println("Tour length: " + tourLengthTSP);
		}
		
		writer.close();
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String filename = "output/tsp_output.txt";
		
		ExperimentPlatform ep = new ExperimentPlatform();
		double min_value = 0;
		double max_value = 1;
		
		try{
			ep.runExperiment(filename, min_value, max_value);
		}
		catch(IOException e){
			System.out.println("IOError: " + e.getMessage());			
		}
		

	}

}

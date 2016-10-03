package knightMove;

import java.util.*;
import java.io.*;

/**
 * this is the class to run experiment
 * @author wenzhao
 *
 */
public class ExperimentPlatform {
	
	//range for x and y axis
	int x_min = -25;
	int x_max = 25;
	int y_min = -25;
	int y_max = 25;
	
	/**
	 * this is the main method to run Knight-Move experiments
	 * @param filename the output file name
	 * @param n the number of times to run the experiment
	 */
	public void runExperiment(String filename, int n) throws IOException{
		
		
		
		int goal_x;
		int goal_y;
		
		Solution sol = new Solution();
		Random rand = new Random();
		PrintWriter writer = new PrintWriter(filename, "UTF-8");
		
		//the start position is (0,0)
		Position start = new Position(0, 0);
		Position goal;
		//the number of nodes expanded
		int nodes_expanded;
		//solution length
		int solution_length;
		
		for(int i=0;i<n;i++){
			
			System.out.println("Experiment: " + i);
			
			//generate a random integer within [x_min, x_max] for goal.x
			goal_x = rand.nextInt(x_max - x_min + 1) + x_min;
			//generate a random integer within [y_min, y_max] for goal.y
			goal_y = rand.nextInt(y_max - y_min + 1) + y_min;
			//generate goal position
			goal = new Position(goal_x, goal_y);
			
			long startTime = System.currentTimeMillis();
			
			//run search
			nodes_expanded = sol.search(start, goal);
			
			long duration = System.currentTimeMillis() - startTime;
			
			//solution length is the g-value of the goal
			solution_length = goal.g_value;
			
			//output to file
			writer.println(solution_length + "," + nodes_expanded + "," + (double)duration/1000);
			
		}
		
		writer.close();
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String filename = "output/knight_output.txt";
		int experiment_times = 500;
		
		ExperimentPlatform ep = new ExperimentPlatform();
		try{
			ep.runExperiment(filename, experiment_times);
		}
		catch(IOException e){
			System.out.println("IOError: " + e.getMessage());
		}

	}

}

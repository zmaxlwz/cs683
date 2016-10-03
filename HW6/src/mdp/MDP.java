package mdp;

import java.util.Scanner;

public class MDP {

	private int stateNum;
	private double[] utilities;
	private int[] bestActions;
	
	
	public MDP(int stateNum){
		this.stateNum = stateNum;
		this.utilities = new double[stateNum];
		this.bestActions = new int[stateNum];
	}
	
	public int getStateNum(){
		return this.stateNum;
	}
	
	public double[] getUtilities(){
		return this.utilities;
	}
	
	public int[] getBestActions(){
		return this.bestActions;
	}
	
	public void computeMDP(int stateNum, double[] rewards, double[][][] transitionMatrix, double gamma){
		//main method to do value iteration
		
		//utilities values
		double[] utilities1 = new double[stateNum];
		double[] utilities2 = new double[stateNum];
		//max difference of all states utilities update
		double delta = 0;
		//the difference threshold to stop
		double sigma = 0.001;
		//best action for each state
		int[] bestAction = new int[stateNum];
		
		for(int i=0;i<stateNum;i++){
			utilities2[i] = 0;
		}
		
		double maxValue = 0;
		double sumValue = 0;
		
		//System.out.println("start value iteration...");
		//int count = 0;
		
		do{
			//assign values of utilities2 to utilities1
			for(int i=0;i<stateNum;i++){
				utilities1[i] = utilities2[i];
			}
			delta = 0;
			
			for(int i=0;i<stateNum;i++){
				//for each state s
				maxValue = Double.NEGATIVE_INFINITY;
				
				for(int k=0;k<4;k++){
					//4 actions
					
					sumValue = 0;
					for(int j=0;j<stateNum;j++){
						sumValue += transitionMatrix[k][i][j]*utilities1[j];
					}
					if(sumValue > maxValue){
						maxValue = sumValue;
						bestAction[i] = k;
					}
					
				}
				
				utilities2[i] = rewards[i] + gamma * maxValue;
				
				if(Math.abs(utilities2[i] - utilities1[i]) > delta){
					delta = Math.abs(utilities2[i] - utilities1[i]);
				}
				
			}		
			
			/*
			System.out.println(delta);
			count++;
			if(count>20){
				break;
			}
			*/
		}
		while(delta >= sigma);
		
		for(int i=0;i<stateNum;i++){
			this.utilities[i] = utilities2[i];
			this.bestActions[i] = bestAction[i];
		}
		
		return;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner scan = new Scanner(System.in);
		int numStates = scan.nextInt();
		
		double[] rewards = new double[numStates];
		for(int i=0;i<numStates;i++){
			rewards[i] = scan.nextDouble();
		}
		
		String s = null;
		String[] strs = null; 
		
		double[][] leftMatrix = new double[numStates][numStates];
		for(int i=0;i<numStates;i++){
			s = scan.next();
			strs = s.split(",");
			for(int j=0;j<numStates;j++){
				leftMatrix[i][j] = Double.parseDouble(strs[j]);
				//System.out.print(leftMatrix[i][j] + " ");
			}
			//System.out.println();
		}
		
		//System.out.println();
		
		double[][] upMatrix = new double[numStates][numStates];
		for(int i=0;i<numStates;i++){
			s = scan.next();
			strs = s.split(",");
			for(int j=0;j<numStates;j++){
				upMatrix[i][j] = Double.parseDouble(strs[j]);
				//System.out.print(upMatrix[i][j] + " ");
			}
			//System.out.println();
		}
		
		//System.out.println();
		
		double[][] rightMatrix = new double[numStates][numStates];
		for(int i=0;i<numStates;i++){
			s = scan.next();
			strs = s.split(",");
			for(int j=0;j<numStates;j++){
				rightMatrix[i][j] = Double.parseDouble(strs[j]);
				//System.out.print(rightMatrix[i][j] + " ");
			}
			//System.out.println();
		}
		
		//System.out.println();
		
		double[][] downMatrix = new double[numStates][numStates];
		for(int i=0;i<numStates;i++){
			s = scan.next();
			strs = s.split(",");
			for(int j=0;j<numStates;j++){
				downMatrix[i][j] = Double.parseDouble(strs[j]);
				//System.out.print(downMatrix[i][j] + " ");
			}
			//System.out.println();
		}
		
		//System.out.println();
		
		double[][][] transitionMatrix = new double[4][numStates][numStates];
		transitionMatrix[0] = leftMatrix;
		transitionMatrix[1] = upMatrix;
		transitionMatrix[2] = rightMatrix;
		transitionMatrix[3] = downMatrix;
		
		double gamma = 1.0;
		//double gamma = 0.95;
		
		MDP mdp = new MDP(numStates);
		mdp.computeMDP(numStates, rewards, transitionMatrix, gamma);
		double[] utilities = mdp.getUtilities();
		int[] bestActions = mdp.getBestActions();
		
		for(int i=0;i<numStates;i++){
			System.out.printf("State %d: %f, ", i, utilities[i]);
			if(bestActions[i] == 0){
				System.out.println("Left");
			}
			else if(bestActions[i] == 1){
				System.out.println("Up");
			}
			else if(bestActions[i] == 2){
				System.out.println("Right");
			}
			else if(bestActions[i] == 3){
				System.out.println("Down");
			}
			else{
				System.out.println("No action");
			}
		}
		
		scan.close();

	}

}

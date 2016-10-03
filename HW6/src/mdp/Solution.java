package mdp;

import java.util.*;

public class Solution {

	
	public Solution(){
		
	}
	
	public void computeMDP(int stateNum, double[] rewards, double[][] leftMatrix, double[][] upMatrix, double[][] rightMatrix, double[][] downMatrix){
		
		
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner scan = new Scanner(System.in);
		int numStates = scan.nextInt();
		
		/*
		String s = scan.next();
		String[] strs = s.split(",");
		double d1 = Double.parseDouble(strs[0]);
		double d2 = Double.parseDouble(strs[1]);
		double d3 = Double.parseDouble(strs[2]);
		
		System.out.println(numStates + " " + d1 + " " + d2 + " " + d3);
		
		s = scan.next();
		strs = s.split(",");
		d1 = Double.parseDouble(strs[0]);
		d2 = Double.parseDouble(strs[1]);
		d3 = Double.parseDouble(strs[2]);
		
		System.out.println(numStates + " " + d1 + " " + d2 + " " + d3);
		*/
		
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
				System.out.print(leftMatrix[i][j] + " ");
			}
			System.out.println();
		}
		
		System.out.println();
		
		double[][] upMatrix = new double[numStates][numStates];
		for(int i=0;i<numStates;i++){
			s = scan.next();
			strs = s.split(",");
			for(int j=0;j<numStates;j++){
				upMatrix[i][j] = Double.parseDouble(strs[j]);
				System.out.print(upMatrix[i][j] + " ");
			}
			System.out.println();
		}
		
		System.out.println();
		
		double[][] rightMatrix = new double[numStates][numStates];
		for(int i=0;i<numStates;i++){
			s = scan.next();
			strs = s.split(",");
			for(int j=0;j<numStates;j++){
				rightMatrix[i][j] = Double.parseDouble(strs[j]);
				System.out.print(rightMatrix[i][j] + " ");
			}
			System.out.println();
		}
		
		System.out.println();
		
		double[][] downMatrix = new double[numStates][numStates];
		for(int i=0;i<numStates;i++){
			s = scan.next();
			strs = s.split(",");
			for(int j=0;j<numStates;j++){
				downMatrix[i][j] = Double.parseDouble(strs[j]);
				System.out.print(downMatrix[i][j] + " ");
			}
			System.out.println();
		}
		
		System.out.println();
		
		
		
		
		scan.close();
	}

}

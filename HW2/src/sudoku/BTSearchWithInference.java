package sudoku;

import java.util.*;

public class BTSearchWithInference {

	int totalGuesses;

	public BTSearchWithInference(){
		this.totalGuesses = 0;		
	}

	public void searchWithInference(String inputFileName, String outputFileName){

		//read from input file and build the sudoku CSP
		SudokuIO.makeSudokuPuzzle(inputFileName);

		int vars[] = new int[SudokuIO.NUM_VARS];

		//define working copy of the variable arrays
		for(int i = 0;i<SudokuIO.NUM_VARS;i++){
			vars[i] = SudokuIO.vars[i];
		}

		//run the main backtracking search with MRV heuristic
		boolean hasSolution = search(vars);

		//output the solution if one exists
		if(hasSolution){

			for(int i=0;i<SudokuIO.NUM_VARS;i++){
				SudokuIO.vars[i] = vars[i];
			}
			SudokuIO.printSudoku(outputFileName);

		}
		else{
			//no solution
			System.out.println("There is no solution for this Sudoku CSP.");
		}

		return;

	}

	public boolean search(int[] vars){

		boolean hasUnsignedVariable = false;
		for(int i=0;i<vars.length;i++){
			if(vars[i] == 0){
				hasUnsignedVariable = true;
				break;
			}
		}

		if(!hasUnsignedVariable){
			//no unsigned variable
			return true;
		}

		//find MRV variable
		int numRV = 0;
		int minNumRV = 81;
		int minVariableIndex = 0;
		for(int varIndex=0;varIndex<vars.length;varIndex++){
			if(vars[varIndex] == 0){
				//count numRV
				numRV = 0;
				for(int i=0;i<9;i++){
					vars[varIndex] = i+1;
					if(!hasConflict(vars, varIndex)){
						numRV++;
					}
					vars[varIndex] = 0;
				}
				if(numRV<minNumRV){
					minNumRV = numRV;
					minVariableIndex = varIndex;
				}
			}
		}

		if(minNumRV == 0){
			//there is an unsigned variable with 0 remaining values
			return false;
		}

		//apply inference to variable at vars[minVariableIndex]

		List<Integer> validValueList = new ArrayList<Integer>();
		int numGuesses = 0;
		for(int i=0;i<9;i++){

			vars[minVariableIndex] = i+1;
			//check if conflict with already signed variables
			if(!hasConflict(vars, minVariableIndex)){
				//apply inference 
				
				
				if(singlePlaceInference(vars, minVariableIndex)){
					//this value can only be placed here
					numGuesses = 1;
					validValueList.clear();
					validValueList.add(i+1);
					vars[minVariableIndex] = 0;
					break;
				}
				
				
				if(archConsistencyInference(vars, minVariableIndex)){
					numGuesses++;
					validValueList.add(i+1);
				}
			}			
			vars[minVariableIndex] = 0;
		}

		if(numGuesses == 0){
			//no valid value for this variable
			return false;
		}

		this.totalGuesses += numGuesses-1;

		for(int value: validValueList){
			vars[minVariableIndex] = value;
			if(search(vars)){
				return true;
			}
			vars[minVariableIndex] = 0;
		}

		return false;
	}

	public boolean archConsistencyInference(int[] vars, int varIndex){
		//if the value is valid with peer unassigned variables, return true
		//if the value is not valid with peer unassigned variables, return false

		int rowNum = varIndex/9;
		int colNum = varIndex%9;

		//check row
		for(int i=0;i<9;i++){
			int index = rowNum*9 + i;
			if(index != varIndex && vars[index] == 0){
				//unassigned element in this row
				boolean hasValidValue = false;
				for(int value=1;value<=9;value++){
					vars[index] = value;
					if(!hasConflict(vars, index)){
						hasValidValue = true;
					}
					vars[index] = 0;
				}
				if(!hasValidValue){
					return false;
				}
			}
		}

		//check column
		for(int i=0;i<9;i++){
			int index = i*9 + colNum;
			if(index != varIndex && vars[index] == 0){
				//unassigned element in this column
				boolean hasValidValue = false;
				for(int value=1;value<=9;value++){
					vars[index] = value;
					if(!hasConflict(vars, index)){
						hasValidValue = true;
					}
					vars[index] = 0;
				}
				if(!hasValidValue){
					return false;
				}
			}
		}

		//check square
		int squareRow = rowNum/3;
		int squareCol = colNum/3;
		int rowIndex = squareRow*3;
		int colIndex = squareCol*3;
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				int index = rowIndex*9+colIndex+j;
				if(index != varIndex && vars[index] == 0){
					//unassigned element in this square
					boolean hasValidValue = false;
					for(int value=1;value<=9;value++){
						vars[index] = value;
						if(!hasConflict(vars, index)){
							hasValidValue = true;
						}
						vars[index] = 0;
					}
					if(!hasValidValue){
						return false;
					}
				}
			}
			rowIndex++;
		}

		return true;
	}

	public boolean singlePlaceInference(int[] vars, int varIndex){
		//check if varIndex is the only place to put the value in the row, column, or square

		int value = vars[varIndex];
		vars[varIndex] = 0;

		int rowNum = varIndex/9;
		int colNum = varIndex%9;

		//check row

		boolean SinglePlaceForRow = true;
		for(int i=0;i<9;i++){
			int index = rowNum*9 + i;
			if(index != varIndex && vars[index] == 0){
				//check if value is valid for this cell
				vars[index] = value;
				if(!hasConflict(vars, index)){
					SinglePlaceForRow = false;
				}
				vars[index] = 0;

			}
		}
		if(SinglePlaceForRow){
			vars[varIndex] = value;
			return true;
		}

		//check column
		boolean SinglePlaceForColumn = true;
		for(int i=0;i<9;i++){
			int index = i*9 + colNum;
			if(index != varIndex && vars[index] == 0){
				//check if the value is valid for this cell
				vars[index] = value;
				if(!hasConflict(vars, index)){
					SinglePlaceForColumn = false;
				}
				vars[index] = 0;
			}
		}
		if(SinglePlaceForColumn){
			vars[varIndex] = value;
			return true;
		}

		//check square
		int squareRow = rowNum/3;
		int squareCol = colNum/3;
		int rowIndex = squareRow*3;
		int colIndex = squareCol*3;
		boolean SinglePlaceForSquare = true;
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				int index = rowIndex*9+colIndex+j;
				if(index != varIndex && vars[index] == 0){
					//check if the value is valid for this cell
					vars[index] = value;
					if(!hasConflict(vars, index)){
						SinglePlaceForSquare = false;
					}
					vars[index] = 0;
				}
			}
			rowIndex++;
		}
		if(SinglePlaceForSquare){
			vars[varIndex] = value;
			return true;
		}

		//not single place for the row, column, or square
		vars[varIndex] = value;
		
		return false;
	}

	public boolean hasConflict(int[] vars, int varIndex){
		//check if there is conflict in the row, column or square for this cell
		//check against already signed variable

		int rowNum = varIndex/9;
		int colNum = varIndex%9;

		//check row
		for(int i=0;i<9;i++){
			int index = rowNum*9 + i;
			if(index != varIndex && vars[index] == vars[varIndex]){
				//find conflict
				return true;
			}
		}

		//check column
		for(int i=0;i<9;i++){
			int index = i*9 + colNum;
			if(index != varIndex && vars[index] == vars[varIndex]){
				return true;
			}
		}

		//check square
		int squareRow = rowNum/3;
		int squareCol = colNum/3;
		int rowIndex = squareRow*3;
		int colIndex = squareCol*3;
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				int index = rowIndex*9+colIndex+j;
				if(index != varIndex && vars[index] == vars[varIndex]){
					return true;
				}
			}
			rowIndex++;
		}

		//no conflict
		return false;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BTSearchWithInference search = new BTSearchWithInference();
		String inputFilePath = "input/input100.txt";
		String outputFilePath = "output/output.txt";

		search.searchWithInference(inputFilePath, outputFilePath);
		System.out.println("Total number of guesses: " + search.totalGuesses);

	}

}

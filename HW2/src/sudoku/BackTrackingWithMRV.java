package sudoku;

public class BackTrackingWithMRV {

	int totalGuesses;

	public BackTrackingWithMRV(){
		this.totalGuesses = 0;
	}

	public void searchWithMRV(String inputFileName, String outputFileName){

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
		this.totalGuesses += minNumRV-1;
		
		for(int i=0;i<9;i++){
			vars[minVariableIndex] = i+1;
			if(!hasConflict(vars, minVariableIndex)){
				if(search(vars)){
					return true;
				}
			}
			vars[minVariableIndex] = 0;
		}

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
		
		BackTrackingWithMRV search = new BackTrackingWithMRV();
		String inputFilePath = "input/input100.txt";
		String outputFilePath = "output/output.txt";
		
		search.searchWithMRV(inputFilePath, outputFilePath);
		System.out.println("Total number of guesses: " + search.totalGuesses);

	}

}

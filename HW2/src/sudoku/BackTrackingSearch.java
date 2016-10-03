package sudoku;

public class BackTrackingSearch {
	
	int totalGuesses;
	
	public BackTrackingSearch(){
		this.totalGuesses = 0;
	}

	public void backTrackSearch(String inputFileName, String outputFileName){
		
		//read from input file and build the sudoku CSP
		SudokuIO.makeSudokuPuzzle(inputFileName);
		
		int vars[] = new int[SudokuIO.NUM_VARS];
		boolean[][] varDomain = new boolean[SudokuIO.NUM_VARS][9];
		
		//define working copy of the variable arrays
		for(int i = 0;i<SudokuIO.NUM_VARS;i++){
			vars[i] = SudokuIO.vars[i];
			if(vars[i] > 0){
				//this value is given
				for(int j=0;j<9;j++){
					varDomain[i][j] = SudokuIO.varDomain[i][j];
				}
			}
		}
		
		//run the main backtracking search
		boolean hasSolution = dfs(vars, varDomain, 0);
		
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
	
	public boolean dfs(int[] vars, boolean[][] varDomain, int varIndex){
		if(varIndex >= SudokuIO.NUM_VARS){
			//complete solution
			return true;
		}
		
		if(SudokuIO.vars[varIndex] > 0){
			//this cell is given in the input, no need to assign value
			return dfs(vars, varDomain, varIndex+1);
		}
		
		//this cell is not given, need to assign value
		
		//count guesses
		int numGuesses = 0;
		for(int i=0;i<9;i++){
			vars[varIndex] = i+1;
			if(!hasConflict(vars, varIndex)){
				numGuesses++;
			}
			vars[varIndex] = 0;
		}
		if(numGuesses>0){
			this.totalGuesses += numGuesses-1;
		}
		else{
			return false;
		}
		
		//System.out.println("varIndex: " + varIndex + ", totalGuesses: "+ this.totalGuesses);
		
		//recursive backtracking
		for(int i=0;i<9;i++){
			vars[varIndex] = i+1;
			varDomain[varIndex][i] = true;
			//check if there is conflict
			if(!hasConflict(vars, varIndex) && dfs(vars, varDomain, varIndex+1)){
				return true;
			}
			vars[varIndex] = 0;
			varDomain[varIndex][i] = false;
		}
		
		//no valid value for this cell
		return false;
	}
	
	public boolean hasConflict(int[] vars, int varIndex){
		//check if there is conflict in the row, column or square for this cell
		
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
		
		BackTrackingSearch search = new BackTrackingSearch();
		String inputFilePath = "input/input100.txt";
		String outputFilePath = "output/output.txt";
		
		search.backTrackSearch(inputFilePath, outputFilePath);
		System.out.println("Total number of guesses: " + search.totalGuesses);

	}

}

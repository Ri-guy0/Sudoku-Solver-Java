class Sudoku {
	private int[][] puzzle = new int[9][9];
	private int[][] complete = new int[9][9];
	private int length;
	
	Sudoku (int[][] puzzle) {
		for (int i=0; i<puzzle.length; i++){
			for (int j=0; j<puzzle[i].length; j++) {
				this.puzzle[i][j] = puzzle[i][j];
				this.complete[i][j] = puzzle[i][j];
			}
		}	
		length = puzzle.length;//to reduce big-O
	}
	
	void viewPuzzle () {
		this.viewformat(puzzle);
	}
	
	void viewAnswer () {
		this.viewformat(complete);
	}
	
	private void viewformat (int[][] puzzle) {
		for (int j=0; j<(2+length); j++) {
			System.out.print("- ");
		}
		System.out.println();
		
		for (int i=0; i<length; i++){
			System.out.print("|");
			for (int j=0; j<length; j++) {
				System.out.print(puzzle[i][j] + " ");
				if ((j+1)%3==0) System.out.print("|");
			}
			
			if ((i+1)%3==0) {
				System.out.println();
				for (int j=0; j<(2+length); j++) {
					System.out.print(" -");
				}
			}
			
			System.out.println();
		}
	}
	
	boolean solver() {
		// checks next empty position
		int[] position = this.findEmpty();;
		// finishes if there is no more empty positions
		if (position[0]==-1) {
			return true;
		}
		// uses backtrack method
		return this.backtrack(position);
	}
	
	private int[] findEmpty() {
		int[] position = {0,0};
		for (int i=0; i<length; i++){
			for (int j=0; j<complete[i].length; j++) {
				if (complete[i][j]==0) {
					position[0]=i;
					position[1]=j;
					return position;
				}
			}
		}
		
		//sets a flag for no more zeros
		position[0]=-1;
		return position;
	}
	
	private boolean backtrack(int[] position) { 
		//for statement goes through the possible numbers 1-9
		for (int n=1; n<10; n++) {
			//checks valid cases
			if (this.isValid(n, position)) {
				//input value
				complete[position[0]][position[1]]=n;
				
				//checks next node
				boolean nextSolver = this.solver();
				if (nextSolver) return true;
				
				//if next node is impossible, reset value to zero and continue
				complete[position[0]][position[1]]=0;
			}
		}
		
		return false;
	}
	
	private boolean isValid(int value, int[] position) {
		// next for statements scan for rows and columns
		for (int i=0; i<length; i++) {
			if (complete[position[0]][i]==value) return false;
		}
		
		for (int i=0; i<length; i++) {
			if (complete[i][position[1]]==value) return false;
		}
		
		// creates shifts for the 3x3 boxes
		int r =position[0]/3;
		int c =position[1]/3;
		//scans the 3x3 box
		for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++) {
				if (complete[i+3*r][j+3*c]==value) return false;
			}
		}
		
		return true;
	}
}

public class SudokuSolver {

	public static void main(String[] args) {
		int[][] puzzle = {{0, 0, 0, 2, 6, 0, 7, 0, 1},
		        {6, 8, 0, 0, 7, 0, 0, 9, 0},
				{1, 9, 0, 0, 0, 4, 5, 0, 0},
				
				{8, 2, 0, 1, 0, 0, 0, 4, 0},
				{0, 0, 4, 6, 0, 2, 9, 0, 0},
				{0, 5, 0, 0, 0, 3, 0, 2, 8},
				
				{0, 0, 9, 3, 0, 0, 0, 7, 4},
				{0, 4, 0, 0, 5, 0, 0, 3, 6},
				{7, 0, 3, 0, 1, 8, 0, 0, 0}};

		Sudoku sdk1 = new Sudoku(puzzle);

		sdk1.solver();
		sdk1.viewPuzzle();
		System.out.println("#######");
		sdk1.viewAnswer();
	}
}

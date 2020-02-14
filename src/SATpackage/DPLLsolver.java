package SATpackage;

import java.util.Arrays;

public class DPLLsolver {
	boolean vars[] = new boolean[1];
	DPLLsolver(){}
	boolean solve(int formula[][]) {
		//assignment array
		if(vars.length < 3) {
			vars = new boolean[formula[0][0] + 1];
			Arrays.fill(vars, Boolean.FALSE);
		}
		int specialVar;
		//check for empty clauses
		for(int r = 1; r < formula.length; r++) {
			if(formula[r].length == 0) {
				return false;
			}
		}
		//check if all clauses are satisfied
		if (formula.length == 1) {
			return true;
		}
		while(checkForUnit(formula) != 0 || checkForLiteral(formula) != 0) {
			//get unit var
			specialVar = checkForUnit(formula);
			//if no unit var get literal
			if(specialVar == 0) {
				specialVar = checkForLiteral(formula);
			}
			//check for (x) & (-x) if found unsatisfiable else remove x
			if(checkForUnitConflict(formula, specialVar)) {
				return(false);
			} else {
				formula = simplify(formula, specialVar);
			}
			if (formula.length == 1) {
				return(true);
			}
		}
		//check if all clauses are satisfied


		//pick some literal p from the shortest clause, min size should be 2
		specialVar = 0;
		for(int r = 1; r < formula.length; r++) {
			if (formula[r].length == 2) {
				specialVar = formula[r][0];
			}
		}
		//no size 2 clauses
		if (specialVar == 0) {
			specialVar = formula[1][0];
		}
		if (solve(simplify(formula.clone(), specialVar)) == true) {
			return(true);
		} else {
			return solve(simplify(formula.clone(),-specialVar));
		}
	}
	
	int[][] simplify(int formula[][], int x) {
		vars[Math.abs(x)] = true;
		for(int r = formula.length - 1; r > 0; r--) {
			//delete every clause containing x;
			for (int c = formula[r].length - 1; c >= 0; c--) {
				//if clause has x remove and shorten array
				if (formula[r].length > c && formula[r][c] == x && formula.length > 1) {
					int temp[][] = new int[formula.length - 1][];
					temp[0] = formula[0];
					int i;
					//copy everything until matching clause
					for (i = 1; i < r; i++) {
						temp[i] = formula[i];
					}
					//copy everything after matching clauses
					for(i = r + 1; i < formula.length; i++) {
						temp[i - 1] = formula[i];
					}
					formula = temp.clone();
					c = 0;

				}else if (formula[r].length > c && formula[r][c] == -x && formula.length > 1) {//delete every occurrence of ~x;
					int temp[] = new int[formula[r].length - 1];
					int i = 0;
					//copy everything until matching clause
					for (i = 0; i < c; i++) {
						temp[i] = formula[r][i];
					}
					//copy everything after matching clauses
					for(i = c; i < formula[r].length - 1; i++) {
						temp[i] = formula[r][i+1];
					}
					formula[r] = temp.clone();

				}
			}
		}
		return formula;
	}
	
	//look for unit clauses
	int checkForUnit(int formula[][]) {
		//scan through clauses
		for(int r = 1; r < formula.length; r++) {
			//check for unit clause
			if(formula[r].length == 1) {
				return formula[r][0];
			}
		}
		//none found
		return 0;
	}
	
	//return true if x and -x unit clauses are found
	boolean checkForUnitConflict(int formula[][], int x) {
		//scan through clauses 
		for(int r = 1; r < formula.length; r++) {
			//check or unit clause
			if(formula[r].length == 1) {
				if(formula[r][0] == -x) {
					return true;
				}
			}
		}
		return false;
	}
	
	//check for literals and return if found
	int checkForLiteral(int formula[][]) {
		boolean foundLiteral = true;
		//scan through each variable
		for(int x = 1; x < formula[0][0]; x++) {
			if (vars[x] == false) { //skip if already eliminated
				foundLiteral = true;
				//check through each clause
				for(int r = 1; r < formula.length; r++) {
					//check each item in clause
					for(int c = 0; c < formula[r].length; c++) {
						if(formula[r][c] == -x) {
							foundLiteral = false;
						}
					}
				}
				if(foundLiteral) {
					return x;
				}
				//if x is not literal check -x
				foundLiteral = true;
				//check through each clause looking for -x literal
				for(int r = 1; r < formula.length; r++) {
					//check each item in clause
					for(int c = 0; c < formula[r].length; c++) {
						if(formula[r][c] == x) {
							foundLiteral = false;
						}
					}
				}
				//if literal found return it
				if(foundLiteral) {
					return -x;
				}
			}
		}
		//none found
		return 0;
	}
}

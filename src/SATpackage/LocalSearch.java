package SATpackage;

public class LocalSearch {
	LocalSearch(){}
	
	int solve(int formula[][]) {
		int g = 0;
		int flip = 0;
		//randomly generate a
		int vars[] = new int[formula[0][0] + 1];
		for(int i = 0; i < vars.length; i++) {
			if(Math.random() < 0.5) {
				vars[i] = 0;
			} else {
				vars[i] = 1;
			}
		}

		vars[0] = findFitness(vars, formula);
		
		//repeat g times
		while(g < 100000 && vars[0] < formula[0][1]) {
			vars[0] = findFitness(vars, formula);
			//if phi(a)=T output a, m, halt
			if(vars[0] == formula[0][1]) {
				return vars[0];
			} else if(findFlip(vars.clone(), formula) != 0) {	//else if we can flip a variable to get a such that phi(a')>phi(a), flip such a variable
				flip = findFlip(vars.clone(), formula);
				if(vars[flip] == 1) {
					vars[flip] = 0;
				} else {
					vars[flip] = 1;
				}
			} else {	//else output a
				return vars[0];
			}
		}
		return vars[0];
	}
	
	int findFitness(int pop[], int formula[][]) {
		int fitness = 0;
		int count;
		for(int i = 1; i < formula.length; i++) {
			count = 0;
			//check if var 1 is true
			if(Math.abs(formula[i][0]) == formula[i][0]) {//first var is not negated
				if(pop[Math.abs(formula[i][0])] == 1) {
					count++;
				}
			}else if(-(Math.abs(formula[i][0])) == formula[i][0]) {//first var is negated
				if(pop[Math.abs(formula[i][0])] == 0) {
					count++;
				}
			}
			//check if var 2 is true
			if(Math.abs(formula[i][1]) == formula[i][1]) {//second var is not negated
				if(pop[Math.abs(formula[i][1])] == 1) {
					count++;
				}
			}else if(-(Math.abs(formula[i][1])) == formula[i][1]) {//second var is negated
				if(pop[Math.abs(formula[i][1])] == 0) {
					count++;
				}
			}
			//check if var 3 is true
			if(Math.abs(formula[i][2]) == formula[i][2]) {//third var is not negated
				if(pop[Math.abs(formula[i][2])] == 1) {
					count++;
				}
			}else if(-(Math.abs(formula[i][2])) == formula[i][2]) {//third var is negated
				if(pop[Math.abs(formula[i][2])] == 0) {
					count++;
				}
			}
			//determine if at least one var was true (count = 1, 2, or 3)
			if(count % 4 != 0) {
				fitness++;
			}
			
		}
		return fitness;
	}

	int findFlip(int varsCopy[], int formula[][]) {
		int a = 0;
		int temp[];
		//loop through variables
		for( int x = 0; a == 0 && x < formula[0][0]; x++) {
			temp = varsCopy.clone();
			//flip current variable
			if(temp[x] == 1) {
				temp[x] = 0;
			} else {
				temp[x] = 1;
			}
			//check if more
			if (findFitness(temp, formula) > varsCopy[0]) {
				a = x;
			}
		}
		
		return a;
	}
}

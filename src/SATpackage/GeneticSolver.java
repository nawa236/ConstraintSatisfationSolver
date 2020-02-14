package SATpackage;
import java.util.Random;

public class GeneticSolver {
	GeneticSolver(){}
	int numPop = 100;
	int numGenerations = 100000;
	int numMates = 25;
	int numMutates = 4;
	
	int solve(int formula[][]) {
		int generations = 0;
		//Initialize a random population of r assignments of assignment a and compute their fitness
		int population[][] = new int[numPop][formula[0][0] + 1];
		for(int i = 0; i < population.length; i++) {
			for(int a = 1; a < formula[0][0] + 1; a++) {
				//randomizer from https://stackoverflow.com/questions/28401093/problems-generating-a-math-random-number-either-0-or-1
				if(Math.random() < 0.5) {
					population[i][a] = 0;
				} else {
					population[i][a] = 1;
				}
			}
			//determine initial fitness
			population[i][0] = findFitness(population[i], formula);
		}
		//sort by fitness
		population = sortPop(population);

		//repeat for g generations or until fitness = m
		while(generations < numGenerations && population[0][0] < formula[0][1]) {
			//Mate k pairs
			population = matePops(population);
			//Mutate s assignments(s small!)
			population = mutatePops(population);
			//recalculate fitness
			for(int i = 0; i < population.length; i++) {
				population[i][0] = findFitness(population[i], formula);
			}
			population = sortPop(population);
			//Remove k individuals
			population = cullPops(population);
			generations++;
		}
		//System.out.println(generations);
		return population[0][0];
	}
	
	//determine fitness
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
	
	//sort by fitness
	int[][] sortPop(int pops[][]) {
		int temp[];
		//go through pops comparing fitness and order in descending fitness
		for(int a = 0; a < pops.length - 1; a++) {
			for(int b = a + 1; b < pops.length; b++) {
				if(pops[a][0] < pops[b][0]) {
					temp = pops[a];
					pops[a] = pops[b];
					pops[b] = temp;
				}
			}
		}
		return pops;
	}

	//Mate k pairs
	int[][] matePops(int pops[][]){
		int newPops[][] = new int[pops.length + numMates][pops[1].length];
		//copy over original pops
		for(int i = 0; i < pops.length; i++) {
			newPops[i] = pops[i];
		}
		for(int a = 0; a < numMates; a += 2) {
			for(int b = 0; b < pops[a].length; b++) {
				if(b < pops.length/2) {
					newPops[pops.length + a][b] = pops[a][b];
				} else {
					newPops[pops.length + a][b] = pops[a + 1][b];
				}
			}
		}
		return newPops;
	}

	//Mutate s assignments(s small!)
	int[][] mutatePops(int pops[][]){
		int mutates = 0;
		Random random = new Random();
		int randomPop;
		int randomVar;
		while(mutates < numMutates) {
			randomPop = random.nextInt(pops.length);
			randomVar = random.nextInt(pops[randomPop].length - 1) + 1;//don't want to use 0
			if(pops[randomPop][randomVar] == 1) {
				pops[randomPop][randomVar] = 0;
			} else {
				pops[randomPop][randomVar] = 1;
			}
			mutates++;
		}
		return pops;
	}

	//remove k pops
	int[][] cullPops(int pops[][]){
		int newPops[][] = new int[pops.length - numMates][];
		//copy pops to keep
		for(int i = 0; i < newPops.length; i++) {
			newPops[i] = pops[i];
		}
		return newPops;
	}
}

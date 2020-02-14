package SATpackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CNFParser {
	CNFParser(){}
	
	int[][] getFormula(File cnf) throws FileNotFoundException {
		int formula[][];
		Scanner scanner = new Scanner(cnf);
		Scanner scanner2 = new Scanner(cnf);
		String line = null;
		String line2 = scanner2.nextLine();
		//next line from https://stackoverflow.com/questions/28076076/scanner-will-not-scan-negative-numbers
		scanner.useDelimiter(",|\\s+");
		//ski past comments
		while (line2.charAt(0) != 'p') {
			line = scanner.nextLine();
			line2 = scanner2.nextLine();
		}
		//line should start with p now, get past p and cnf
		scanner.next();
		scanner.next();
		//grab number of variables and clauses
		int numVar = scanner.nextInt();
		int numClauses = scanner.nextInt();
		scanner2.close();
		formula = new int[numClauses+1][3];
		//first line will store number of variables and clauses
		formula[0][0] = numVar;
		formula[0][1] = numClauses;
		//iterate through files making each clause its own row
		for(int r = 1; r < numClauses + 1; r++) {
			for(int c = 0; c < 3; c++) {
				formula[r][c] = scanner.nextInt();
			}
			//skip 0 on the end of each line
			scanner.nextInt();
		}
		scanner.close();
		return formula;
	}
}

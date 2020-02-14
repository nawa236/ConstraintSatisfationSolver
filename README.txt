This program can be run by using the ConstraintSatisfationSolver.jar file in the root folder. To use this program, once you run the program
use the open button which will bring up a file explorer window that defaults to the local folder of cnf files. After selecting a file use
the solve button to run the solving algorithms,when the program finishes data will be displayed in the table in the lower part of the
window and saves data the data.csv file in the root directory.

The algorithms i selected for this project were dpll, genetic search and local search. The dpll algorithm is a recursive algorithm were
it first eliminates unit clauses and pure literals and then sets a random variable to true and eliminates clauses recursively until none
remain (satisfied) or an empty clause is created (unsatisfiable).
The genetic algorithm creates 100 randomized pops and mates 25 pairs to create more pops then mutates 4 pops and after recalculating the 
fitness it culls the least fit pops until there are 100 pops left. It will repeat this process until it finds a satisfying answer or 
10000 generations pass (This was originally 1000 but i increased it when i got to the hard problems.
The local search creates a random assignment of variables and then looks at the fitness of the assignment and looks for an assignment one
variable flip away that is better than the current assignment and repeats if it find one or returns the fitness it reached if it can't
find a better one or it finds the solution.

What i learned in this assignment is that local search is faster than dpll which is faster than genetic search but local search (at least
my implementation) is less reliable than genetic search. And i had more fun writing the genetic search than the others.
package SATpackage;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SATmain implements ActionListener {
	JFrame frame;
	JFileChooser fileExplorer = new JFileChooser(new File(".\\PA3_Benchmarks"));
	JButton openButton = new JButton("Open");
	JButton solveButton = new JButton("Solve");
	JLabel typeLabel = new JLabel("Type");
	JLabel resultLabel = new JLabel("Result");
	JLabel timeLabel = new JLabel("Time");
	JLabel dpllLabel = new JLabel("DPLL: ");
	JLabel dpllResult = new JLabel("N/A");
	JLabel dpllTime = new JLabel("N/A");
	JLabel genLabel = new JLabel("Genetic: ");
	JLabel genResult = new JLabel("N/A");
	JLabel genTime = new JLabel("N/A");
	JLabel lSLabel = new JLabel("Local Search: ");
	JLabel lSResult = new JLabel("N/A");
	JLabel lSTime = new JLabel("N/A");
	File cnf;
	JLabel selectedFile = new JLabel("N/A");
	int formula[][];
	long startTime, stopTime;
	double averageTime = 0;
	int best = 0, run = 0;
	
	SATmain(){

		frame = new JFrame("SAT Solver");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300,300);
		frame.setLayout(new GridLayout(5, 3));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CNF File", "cnf", "cnf");
		fileExplorer.setFileFilter(filter);
		openButton.addActionListener(this);
		solveButton.addActionListener(this);
		frame.add(openButton);
		frame.add(solveButton);
		frame.add(selectedFile);
		frame.add(typeLabel);
		frame.add(resultLabel);
		frame.add(timeLabel);
		frame.add(dpllLabel);
		frame.add(dpllResult);
		frame.add(dpllTime);
		frame.add(genLabel);
		frame.add(genResult);
		frame.add(genTime);
		frame.add(lSLabel);
		frame.add(lSResult);
		frame.add(lSTime);
		frame.setVisible(true);
	}	

	@Override
	public void actionPerformed(ActionEvent e) {
		//open cnf file
		if (e.getSource() == openButton) {
			fileExplorer.showSaveDialog(null);
			cnf = fileExplorer.getSelectedFile();
			//in case of cancel file select
			if(cnf != null) {
				selectedFile.setText(cnf.getName());
			}
	   }
		if (e.getSource() == solveButton) {
			CNFParser parser = new CNFParser();
			DPLLsolver dpllsolver = new DPLLsolver();
			GeneticSolver gsolver = new GeneticSolver();
			LocalSearch lssolver = new LocalSearch();
			
			//dpll
			try {
				formula = parser.getFormula(cnf);
				startTime = System.nanoTime();
				if(dpllsolver.solve(formula.clone())) {
					dpllResult.setText("Satisfiable");
				} else {
					dpllResult.setText("Not Satisfiable");
				}
				stopTime = System.nanoTime();
				dpllTime.setText(Double.toString((double)(stopTime-startTime) / 1000000000));
			} catch (FileNotFoundException e1) {
				//Auto-generated catch block
				e1.printStackTrace();
			}
			
			//genetic
			best = 0;
			averageTime = 0;
			for(int times = 0; times < 10; times++) {
				startTime = System.nanoTime();
				run = gsolver.solve(formula.clone());
				stopTime = System.nanoTime();
				if(run > best) {
					best = run;
				}
				averageTime += (double)(stopTime-startTime) / 1000000000;
			}
			averageTime = averageTime / 10;
			genResult.setText(best + "/" + formula[0][1]);
			genTime.setText(Double.toString(averageTime));
			
			//local search
			best = 0;
			averageTime = 0;
			for(int times = 0; times < 10; times++) {
				startTime = System.nanoTime();
				run = lssolver.solve(formula.clone());
				stopTime = System.nanoTime();
				if(run > best) {
					best = run;
				}
				averageTime += (double)(stopTime-startTime) / 1000000000;
			}
			lSResult.setText(best + "/" + formula[0][1]);
			averageTime = averageTime / 10;
			lSTime.setText(Double.toString(averageTime));
			
			//write to csv
			try {
				FileWriter csv = new FileWriter("data.csv",true);
				csv.append(selectedFile.getText());
				csv.append(",");
				csv.append(dpllResult.getText());
				csv.append(",");
				csv.append(dpllTime.getText());
				csv.append(",");
				csv.append(genResult.getText());
				csv.append(",");
				csv.append(genTime.getText());
				csv.append(",");
				csv.append(lSResult.getText());
				csv.append(",");
				csv.append(lSTime.getText());
				csv.append("\n");
				csv.close();
			} catch (IOException e1) {
				//Auto-generated catch block
				e1.printStackTrace();
			} 
		}
	}

	
	public static void main(String[] args) {
		new SATmain();
	}
}
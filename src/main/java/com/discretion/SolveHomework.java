package com.discretion;

import com.discretion.problem.Problem;
import com.discretion.problem.ProblemFactory;
import com.discretion.proof.Proof;
import com.discretion.proof.printer.ProofPrettyPrinter;
import com.discretion.proof.printer.ProofPrettyPrinterFactory;
import com.discretion.solver.Solver;
import com.discretion.solver.SolverFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class SolveHomework {
    public static void main(String[] args) {
		// Define default settings
		String problemName = null;
		String printerName = "indented";
		String solverName = "besteffort";
		int searchDepth = 6;
		String outputDirectory = "output/solutions";

		// Collect command line arguments
		for (int i = 0; i<args.length; ++i) {
			String arg = args[i];
			if (arg.equalsIgnoreCase("-p") || arg.equalsIgnoreCase("-problem"))
				problemName = args[++i];

			else if (arg.equalsIgnoreCase("-s") || arg.equalsIgnoreCase("-style"))
				printerName = args[++i];

			else if (arg.equalsIgnoreCase("-d") || arg.equalsIgnoreCase("-depth"))
				searchDepth = Integer.parseInt(args[++i]);

			else if (arg.equalsIgnoreCase("-o") || arg.equalsIgnoreCase("-out"))
				outputDirectory = args[++i];

			else
				System.out.println("Warning: unrecognized argument: " + arg);

		}

		if (problemName == null)
			throw new IllegalArgumentException("Problem name must be supplied as a command line argument");

		// Instantiate solver/etc from the arguments
		Solver solver = SolverFactory.getSolver(solverName);
		ProofPrettyPrinter printer = ProofPrettyPrinterFactory.getPrinter(printerName);
		Problem problem = ProblemFactory.getProblem(problemName);

		System.out.println(String.format("Solving %s...", problem.getTitle()));

		// Solve the Problem
		long proofStartTime = System.currentTimeMillis();
		Proof proof = solver.solve(problem, searchDepth);
		long proofSolveTime = System.currentTimeMillis() - proofStartTime;
		System.out.println(String.format("Completed in %sms", proofSolveTime));

		// Output the solution to a file
		File outputDirFile = new File("output/solutions");
		outputDirFile.mkdirs();
		String fileName = "discretion.out";
		File solutionFile = new File(outputDirectory, fileName);
		try {
			PrintStream outputStream = new PrintStream(solutionFile);
			printer.prettyPrint(proof, outputStream);
			System.out.println("Saved output to " + solutionFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

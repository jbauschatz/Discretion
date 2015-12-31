package com.discretion;

import com.discretion.problem.Homework;
import com.discretion.problem.ProblemSet;
import com.discretion.proof.Proof;
import com.discretion.proof.ProofPrinter;
import com.discretion.solver.BestEffortSolver;
import com.discretion.problem.Problem;
import com.discretion.solver.Solver;
import com.discretion.solver.StructureOnlySolver;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class SolveHomework {
    public static void main(String[] args) {
        List<Solver> solvers = Arrays.asList(
				new BestEffortSolver(),
				new StructureOnlySolver()
		);
        ProofPrinter printer = new ProofPrinter(new PrettyPrinter());

		try {
			File outputDirectory = new File("output/solutions");
			System.out.println("Output directory: " + outputDirectory);
			outputDirectory.mkdirs();

			for (ProblemSet problemSet : Homework.ALL_PROBLEM_SETS) {
				System.out.println("Solving problem set: " + problemSet.getTitle());

				for (Solver solver : solvers) {
					String solverName = solver.getClass().getSimpleName();
					System.out.println("   solver: " + solverName);

					File solutionFile = new File(outputDirectory, problemSet.getTitle() + "-" + solverName + ".txt");
					PrintStream outputStream = new PrintStream(solutionFile);

					for (Problem problem : problemSet.getProblems()) {
						Proof proof = solver.solve(problem);
						printer.prettyPrint(proof, outputStream);
						outputStream.println("\n");
					}
				}
			}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

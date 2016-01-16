package com.discretion;

import com.discretion.problem.Homework;
import com.discretion.problem.ProblemSet;
import com.discretion.proof.Proof;
import com.discretion.proof.printer.IndentedProofPrinter;
import com.discretion.proof.printer.ParagraphProofPrinter;
import com.discretion.proof.printer.ProofPrettyPrinter;
import com.discretion.solver.BestEffortSolver;
import com.discretion.problem.Problem;
import com.discretion.solver.Solver;
import com.discretion.solver.StructureOnlySolver;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SolveHomework {
    public static void main(String[] args) {
        List<Solver> solvers = Arrays.asList(
				new BestEffortSolver(),
				new StructureOnlySolver()
		);
		List<ProofPrettyPrinter> printers = Arrays.asList(
				new IndentedProofPrinter("indented"),
				new ParagraphProofPrinter("english")
		);

		try {
			File outputDirectory = new File("output/solutions");
			System.out.println("Output directory: " + outputDirectory);
			outputDirectory.mkdirs();

			for (ProblemSet problemSet : Homework.ALL_PROBLEM_SETS) {
				System.out.println(String.format("Solving problem set: %s (%s problems)",
						problemSet.getTitle(),
						problemSet.getProblems().size()));

				for (Solver solver : solvers) {
					String solverName = solver.getClass().getSimpleName();
					System.out.println("  solver: " + solverName);

					// Solve each problem and track the total time
					long solverStartTime = System.currentTimeMillis();
					List<Proof> solutions = new LinkedList<>();
					for (Problem problem : problemSet.getProblems()) {
						long proofStartTime = System.currentTimeMillis();
						Proof proof = solver.solve(problem);
						long proofSolveTime = System.currentTimeMillis() - proofStartTime;

						System.out.println(String.format("    %s - %sms",
								problem.getTitle(),
								proofSolveTime));

						solutions.add(proof);
					}
					long solveTime = System.currentTimeMillis() - solverStartTime;

					// Output the time taken to solve the problems
					System.out.println(String.format("    finished in %dms", solveTime));

					// Pretty print the solutions with each printer
					for (ProofPrettyPrinter printer : printers) {
						String fileName = String.format("%s-%s-%s.txt", problemSet.getTitle(), solverName, printer.getName());
						File solutionFile = new File(outputDirectory, fileName);
						PrintStream outputStream = new PrintStream(solutionFile);

						for (Proof proof : solutions) {
							printer.prettyPrint(proof, outputStream);
							outputStream.println();
						}
					}
				}
			}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

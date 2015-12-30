package com.discretion;

import com.discretion.parser.ProofParser;
import com.discretion.proof.Proof;
import com.discretion.proof.ProofPrinter;
import com.discretion.solver.BestEffortSolver;
import com.discretion.solver.Problem;
import com.discretion.solver.Solver;
import com.discretion.solver.StructureOnlySolver;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class SolveHomework {
    public static void main(String[] args) {
        Reader homeworkReader = new InputStreamReader(SolveHomework.class.getResourceAsStream("/problems/homework.json"));
        List<Problem> problems = ProofParser.parseProblems(homeworkReader);

        List<Solver> solvers = Arrays.asList(
				new BestEffortSolver(),
				new StructureOnlySolver()
		);
        ProofPrinter printer = new ProofPrinter(new PrettyPrinter());

        try {
            File outputDirectory = new File("output/solutions");
            outputDirectory.mkdirs();

			for (Solver solver : solvers) {
				File solutionFile = new File(outputDirectory, solver.getClass().getSimpleName());
				PrintStream outputStream = new PrintStream(solutionFile);

				for (Problem problem : problems) {
					Proof proof = solver.solve(problem);
					printer.prettyPrint(proof, outputStream);
					outputStream.println("\n");
				}
			}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

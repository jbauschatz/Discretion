package com.discretion;

import com.discretion.parser.ProofParser;
import com.discretion.proof.Proof;
import com.discretion.proof.ProofPrinter;
import com.discretion.solver.BestEffortSolver;
import com.discretion.solver.Problem;
import com.discretion.solver.Solver;

import java.io.*;
import java.util.List;

public class SolveHomework {
    public static void main(String[] args) {
        Reader homeworkReader = new InputStreamReader(SolveHomework.class.getResourceAsStream("/problems/homework.json"));
        List<Problem> problems = ProofParser.parseProblems(homeworkReader);

        Solver solver = new BestEffortSolver();
        ProofPrinter printer = new ProofPrinter(new PrettyPrinter());

        try {
            File outputDirectory = new File("output");
            outputDirectory.mkdirs();

            File solutionFile = new File(outputDirectory, "homework-solutions");
            PrintStream outputStream = new PrintStream(solutionFile);

            for (Problem p : problems) {
                Proof proof = solver.solve(p.getConclusion(), p.getGiven());
                printer.prettyPrint(proof, outputStream);
                outputStream.println("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

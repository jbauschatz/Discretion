package com.discretion;

import com.discretion.parser.ProofParser;
import com.discretion.proof.Proof;
import com.discretion.solver.PartialSolver;
import com.discretion.solver.Problem;
import com.discretion.solver.Solver;

import java.io.File;
import java.util.List;

public class SolveHomework {
    public static void main(String... args) {
        File homework = new File("data/homework.json");
        List<Problem> problems = ProofParser.parseProblems(homework);
        Solver solver = new PartialSolver();
        ProofPrinter printer = new ProofPrinter(new PrettyPrinter());

        for (Problem p : problems) {
            Proof proof = solver.solve(p.getConclusion(), p.getGiven());
            printer.prettyPrint(proof);
            System.out.println("\n");
        }
    }
}

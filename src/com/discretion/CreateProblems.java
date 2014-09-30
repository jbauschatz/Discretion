package com.discretion;

import com.discretion.expression.*;
import com.discretion.parser.ProofParser;
import com.discretion.solver.Problem;
import com.discretion.statement.Statement;
import com.discretion.statement.SubsetOf;

import java.io.File;
import java.util.LinkedList;

public class CreateProblems {
    public static void main(String[] args) {
        Variable setX = new Variable("X");
        Variable setY = new Variable("Y");
        Variable setZ = new Variable("U");

        LinkedList<Statement> supps = new LinkedList<>();
        supps.add(new SubsetOf(setX, setZ));
        supps.add(new SubsetOf(setY, setZ));

        Statement conclusion = new SubsetOf(new SetComplement(new SetUnion(setX, setY)), new SetIntersection(new SetComplement(setX), new SetComplement(setY)));
        Problem problem = new Problem(conclusion, supps);

        LinkedList<Problem> problems = new LinkedList<>();
        problems.add(problem);

        File problemsFile = new File("data/tempProblems.json");
        ProofParser.save(problems, problemsFile);
    }
}

package com.discretion;

import com.discretion.proof.Proof;
import com.discretion.proof.ProofItem;
import com.discretion.proof.ProofStatement;
import com.discretion.statement.ElementOf;
import com.discretion.statement.Statement;
import com.discretion.statement.SubsetOf;

import java.util.LinkedList;

public class QuickTest {
    public static void main(String... args) {
        ProofPrinter printer = new ProofPrinter(new PrettyPrinter());

        Variable setX = new Variable("X");
        Variable setY = new Variable("Y");
        Variable setZ = new Variable("Z");

        LinkedList<Statement> supps = new LinkedList<>();
        supps.add(new SubsetOf(setX, setY));
        supps.add(new SubsetOf(setY, setZ));

        // The sub-proof: if x is in X, then x is in Y, so x is in Z
        Variable x = new Variable("x");
        LinkedList<Statement> subSuppositions = new LinkedList<>();
        subSuppositions.add(new ElementOf(x, setY));
        LinkedList<ProofItem> subStatements = new LinkedList<>();
        subStatements.add(new ProofStatement(
                new ElementOf(x, setY)
        ));
        Statement subConclusion = new ElementOf(x, setZ);
        Proof subProof = new Proof(subSuppositions, subStatements, subConclusion);

        LinkedList<ProofItem> statements = new LinkedList<>();
        statements.add(subProof);
        Statement conclusion = new SubsetOf(setX, setY);

        Proof proof = new Proof(supps, statements, conclusion);
        printer.prettyPrint(proof);
    }
}

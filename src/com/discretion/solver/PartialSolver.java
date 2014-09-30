package com.discretion.solver;

import com.discretion.proof.Proof;
import com.discretion.proof.ProofItem;
import com.discretion.proof.UnknownSteps;
import com.discretion.statement.Statement;

import java.util.LinkedList;
import java.util.List;

public class PartialSolver implements Solver {
    public Proof solve(Statement conclusion, List<Statement> given) {
        for (ProofStructureProducer structure : structures) {
            if (structure.applies(conclusion)) {
                List<ProofItem> statements = structure.produceStructure(conclusion);
                return new Proof(given, statements, conclusion);
            }
        }


        List<ProofItem> statements = new LinkedList<>();
        statements.add(new UnknownSteps());

        return new Proof(given, statements, conclusion);
    }

    public PartialSolver() {
        structures = new LinkedList<>();
        structures.add(new SetEqualityStructure());
    }

    private List<ProofStructureProducer> structures;
}

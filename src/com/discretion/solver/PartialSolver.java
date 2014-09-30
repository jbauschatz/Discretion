package com.discretion.solver;

import com.discretion.proof.Proof;
import com.discretion.proof.ProofItem;
import com.discretion.proof.UnknownSteps;
import com.discretion.statement.Statement;

import java.util.LinkedList;
import java.util.List;

public class PartialSolver implements Solver {
    public Proof solve(Statement conclusion, List<Statement> given) {
        List<ProofItem> statements = getStructure(conclusion, given);

        return new Proof(given, statements, conclusion);
    }

    public PartialSolver() {
        structures = new LinkedList<>();
        structures.add(new SetEqualityStructure());
        structures.add(new SubsetStructure());
    }

    private List<ProofItem> getStructure(Statement conclusion, List<Statement> given) {
        for (ProofStructureProducer structure : structures) {
            if (structure.applies(conclusion)) {
                List<ProofItem> statements = structure.produceStructure(conclusion);

                // Scan steps for sub-proofs, which may be partial and need fleshing out
                for (ProofItem item : statements) {
                    if (item instanceof Proof) {
                        Proof subproof = (Proof)item;
                        // TODO is it safe to assume the sub-proof is incomplete?
                        subproof.setProofItems(getStructure(subproof.getConclusion(), subproof.getSuppositions()));
                    }
                }

                return statements;
            }
        }

        List<ProofItem> statements = new LinkedList<>();
        statements.add(new UnknownSteps());
        return statements;
    }

    private List<ProofStructureProducer> structures;
}

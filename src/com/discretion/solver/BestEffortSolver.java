package com.discretion.solver;

import com.discretion.proof.Proof;
import com.discretion.proof.ProofItem;
import com.discretion.proof.ProofStatement;
import com.discretion.proof.UnknownSteps;
import com.discretion.solver.inference.*;
import com.discretion.solver.structure.ProofStructureProducer;
import com.discretion.solver.structure.SetEqualityStructure;
import com.discretion.solver.structure.SubsetStructure;
import com.discretion.statement.Statement;

import java.util.LinkedList;
import java.util.List;

public class BestEffortSolver implements Solver {
    public Proof solve(Statement conclusion, List<Statement> given) {
        TruthEnvironment environment = new TruthEnvironment();
        environment.addTruths(given);

        List<ProofItem> statements = getStructure(conclusion, environment);

        environment.removeTruths(given);

        return new Proof(given, statements, conclusion);
    }

    public BestEffortSolver() {
        structures = new LinkedList<>();
        structures.add(new SetEqualityStructure());
        structures.add(new SubsetStructure());

        inference = new BestEffortInferenceChain();
    }

    private List<ProofItem> getStructure(Statement conclusion, TruthEnvironment environment) {
        for (ProofStructureProducer structure : structures) {
            if (structure.applies(conclusion)) {
                List<ProofItem> statements = structure.produceStructure(conclusion);

                // Scan steps for sub-proofs, which may be partial and need fleshing out
                for (ProofItem item : statements) {
                    if (item instanceof Proof) {
                        Proof subproof = (Proof)item;
                        // TODO is it safe to assume the sub-proof is incomplete?
                        environment.addTruths(subproof.getSuppositions());
                        subproof.setProofItems(getStructure(subproof.getConclusion(), environment));

                        // After dealing with the sub-proof, clean out all its suppositions and inferences
                        environment.removeTruths(subproof.getSuppositions());
                        for (ProofItem proofItem : subproof.getProofItems()) {
                            if (item instanceof ProofStatement)
                                environment.removeTruth(((ProofStatement)proofItem).getStatement());
                        }
                    }
                }

                return statements;
            }
        }

        // No further structure could be imposed on this problem, so we must build a chain of inferences
        List<ProofItem> statements = inference.buildInferenceChain(conclusion, environment);

        // Now clean those inferences out of the environment
        for (ProofItem proofItem : statements) {
            if (proofItem instanceof ProofStatement)
                environment.removeTruth(((ProofStatement)proofItem).getStatement());
        }

        return statements;
    }

    private List<ProofStructureProducer> structures;
    private InferenceChainProducer inference;
}

package com.discretion.solver;

import com.discretion.proof.Proof;
import com.discretion.proof.ProofItem;
import com.discretion.proof.ProofStatement;
import com.discretion.proof.UnknownSteps;
import com.discretion.solver.inference.ElementOfSuperset;
import com.discretion.solver.inference.InferenceProducer;
import com.discretion.solver.inference.UnionDisjunction;
import com.discretion.solver.structure.ProofStructureProducer;
import com.discretion.solver.structure.SetEqualityStructure;
import com.discretion.solver.structure.SubsetStructure;
import com.discretion.statement.Statement;

import java.util.LinkedList;
import java.util.List;

public class PartialSolver implements Solver {
    public Proof solve(Statement conclusion, List<Statement> given) {
        TruthEnvironment environment = new TruthEnvironment();
        environment.addTruths(given);

        List<ProofItem> statements = getStructure(conclusion, environment);

        return new Proof(given, statements, conclusion);
    }

    public PartialSolver() {
        structures = new LinkedList<>();
        structures.add(new SetEqualityStructure());
        structures.add(new SubsetStructure());

        inferences = new LinkedList<>();
        inferences.add(new ElementOfSuperset());
        inferences.add(new UnionDisjunction());
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

                        // Leaving the sub-proof, so those assumptions are no longer "true"
                        environment.removeTruths(subproof.getSuppositions());
                    }
                }

                return statements;
            }
        }

        // No further structure could be imposed on this problem, so we must build a chain of inferences
        List<ProofItem> statements = buildInferenceChain(conclusion, environment);

        // Might not have reached the conclusion
        if (statements.isEmpty())
            statements.add(new UnknownSteps());

        return statements;
    }

    /**
     * A sequence of statements inferred from the truth environment, and from which the conclusion can be inferred.
     *
     * Does not include the conclusion - that is recorded in the conclusion part of a proof
     */
    private List<ProofItem> buildInferenceChain(Statement conclusion, TruthEnvironment environment) {
        List<ProofItem> statements = new LinkedList<>();

        boolean stillInfering = false;
        do {
            stillInfering = false;
            for (InferenceProducer inference : inferences) {
                for (ProofStatement newTruth : inference.getInferences(environment)) {
                    // If we can successfully infer the conclusion, our job here is done
                    if (newTruth.getStatement().equals(conclusion))
                        return statements;

                    if (environment.addTruth(newTruth.getStatement())) {
                        stillInfering = true;
                        statements.add(newTruth);
                    }
                }
            }
        } while (stillInfering);

        // If we reach here, we couldn't infer the conclusion
        statements.add(new UnknownSteps());

        return statements;
    }

    private List<ProofStructureProducer> structures;
    private List<InferenceProducer> inferences;
}

package com.discretion.solver.inference;

import com.discretion.proof.ProofItem;
import com.discretion.proof.ProofStatement;
import com.discretion.proof.UnknownSteps;
import com.discretion.solver.TruthEnvironment;
import com.discretion.statement.Statement;

import java.util.LinkedList;
import java.util.List;

public class BestEffortInferenceChain implements InferenceChainProducer {
    public List<ProofItem> buildInferenceChain(Statement conclusion, TruthEnvironment environment) {
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

    public BestEffortInferenceChain() {
        inferences = new LinkedList<>();
        inferences.add(new ElementOfSuperset());
        inferences.add(new UnionDisjunction());
        inferences.add(new AssociateDisjunction());
    }

    private List<InferenceProducer> inferences;
}

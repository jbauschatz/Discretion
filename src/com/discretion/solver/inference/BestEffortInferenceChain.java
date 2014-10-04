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
        InferenceNode root = new InferenceNode();
        expand(conclusion, root, environment);

        if (root.reachesConclusion) {
            InferenceNode current = root.successor;
            while (current != null) {
                statements.add(current.inference);
                current = current.successor;
            }
        } else {
            statements.add(new UnknownSteps());
        }

        return statements;
    }

    public BestEffortInferenceChain() {
        inferenceProducers = new LinkedList<>();
        inferenceProducers.add(new ElementOfSuperset());
        inferenceProducers.add(new UnionDisjunction());
        inferenceProducers.add(new AssociateDisjunction());
        //inferenceProducers.add(new DeMorgansLaw());
    }

    private List<ProofStatement> getImmediateInferences(TruthEnvironment environment) {
        List<ProofStatement> inferences = new LinkedList<>();
        for (InferenceProducer inference : inferenceProducers) {
            for (ProofStatement newTruth : inference.getInferences(environment))
                inferences.add(newTruth);
        }
        return inferences;
    }

    private void expand(Statement conclusion, InferenceNode node, TruthEnvironment environment) {
        int bestDistance = Integer.MAX_VALUE-1;
        InferenceNode bestChild = null;

        for (ProofStatement statement : getImmediateInferences(environment)) {
            if (!environment.containsTruth(statement.getStatement())) {
                InferenceNode child = new InferenceNode();
                child.inference = statement;

                if (statement.getStatement().equals(conclusion)) {
                    node.distanceToConclusion = 1;
                    node.reachesConclusion = true;
                    node.successor = child;
                    return;
                }

                environment.addTruth(statement.getStatement());
                expand(conclusion, child, environment);
                environment.removeTruth(child.inference.getStatement());

                if (child.reachesConclusion && child.distanceToConclusion < bestDistance) {
                    bestChild = child;
                    node.reachesConclusion = true;
                    bestDistance = child.distanceToConclusion;
                }
            }
        }

        node.successor = bestChild;
        node.distanceToConclusion = bestDistance + 1;
    }

    private List<InferenceProducer> inferenceProducers;
}

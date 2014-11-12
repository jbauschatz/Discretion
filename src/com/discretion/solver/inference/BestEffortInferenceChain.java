package com.discretion.solver.inference;

import com.discretion.proof.ProofItem;
import com.discretion.proof.ProofStatement;
import com.discretion.proof.UnknownSteps;
import com.discretion.solver.environment.TruthEnvironment;
import com.discretion.statement.Statement;

import java.util.LinkedList;
import java.util.List;

public class BestEffortInferenceChain implements InferenceChainProducer {
    public List<ProofItem> buildInferenceChain(Statement conclusion, TruthEnvironment environment) {
        InferenceNode root = new InferenceNode();
        root.environment = environment;
        List<InferenceNode> frontier = new LinkedList<>();
        frontier.add(root);

        for (int depth = 0; depth<maxSearchDepth; ++depth) {
            LinkedList<InferenceNode> newFrontier = new LinkedList<>();
            for (InferenceNode node : frontier) {
                // Search all inference steps that could be taken
                for (ProofStatement statement : getImmediateInferences(node.environment)) {
                    if (statement.getStatement().equals(conclusion)) {
                        // We've reached the conclusion, which means we can reconstruct the
                        // shortest inference chain
                        LinkedList<ProofItem> chain = new LinkedList<>();
                        InferenceNode predecessor = node;
                        while (predecessor != root) {
                            chain.addFirst(predecessor.inference);
                            predecessor = predecessor.predecessor;
                        }
                        return chain;
                    } else {
                        InferenceNode newSearchNode = new InferenceNode();
                        newSearchNode.predecessor = node;
                        newSearchNode.inference = statement;
                        newSearchNode.environment = node.environment.getChildEnvironment(statement.getStatement());
                        newFrontier.add(newSearchNode);
                    }
                }
            }
            frontier = newFrontier;
        }

        // All possibilities have been searched to the search depth,
        // so the proof is unknown

        List<ProofItem> unknown = new LinkedList<>();
        unknown.add(new UnknownSteps());

        return unknown;
    }

    public BestEffortInferenceChain() {
        maxSearchDepth = 6;

        inferenceProducers = new LinkedList<>();

        // logical inferences
        inferenceProducers.add(new DeMorgansLaw());
        inferenceProducers.add(new Specialization());

        // set theory specific
        inferenceProducers.add(new ElementOfSuperset());
        inferenceProducers.add(new UnionDisjunction());
        inferenceProducers.add(new IntersectionConjunction());
        inferenceProducers.add(new AssociateDisjunction());
        inferenceProducers.add(new SetComplementInference());
        inferenceProducers.add(new SetDifferenceInference());
    }

    private List<ProofStatement> getImmediateInferences(TruthEnvironment environment) {
        List<ProofStatement> inferences = new LinkedList<>();
        for (InferenceProducer inference : inferenceProducers) {
            for (ProofStatement newTruth : inference.getInferences(environment)) {
                if (!environment.containsTruth(newTruth.getStatement()))
                    inferences.add(newTruth);
            }
        }
        return inferences;
    }

    private int maxSearchDepth;
    private List<InferenceProducer> inferenceProducers;
}

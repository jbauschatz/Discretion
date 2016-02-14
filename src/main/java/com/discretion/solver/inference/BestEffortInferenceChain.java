package com.discretion.solver.inference;

import com.discretion.proof.ProofItem;
import com.discretion.proof.ProofStatement;
import com.discretion.proof.UnknownSteps;
import com.discretion.solver.environment.TruthEnvironment;
import com.discretion.statement.Statement;

import java.util.LinkedList;
import java.util.List;

public class BestEffortInferenceChain implements InferenceChainProducer {
	public List<ProofItem> buildInferenceChain(Statement conclusion, TruthEnvironment environment, int maxDepth) {
		InferenceNode root = new InferenceNode();
		root.environment = environment;
		List<InferenceNode> frontier = new LinkedList<>();
		frontier.add(root);

		for (int depth = 0; depth<maxDepth; ++depth) {
			LinkedList<InferenceNode> newFrontier = new LinkedList<>();
			for (InferenceNode node : frontier) {
				// Search all inference steps that could be taken
				for (ProofStatement statement : getImmediateInferences(node.environment)) {
					if (statement.getStatement().equals(conclusion)) {
						// We've reached the conclusion, which means we can reconstruct the inference chain
						LinkedList<ProofItem> chain = new LinkedList<>();
						chain.add(statement);

						// Work backwards from the conclusion
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
		inferenceRules = new LinkedList<>();

		// logical inferences
		inferenceRules.add(new DoubleNegative());
		inferenceRules.add(new DeMorgansLaw());
		inferenceRules.add(new Specialization());
		inferenceRules.add(new AssociateDisjunction());

		// set theory specific
		inferenceRules.add(new ElementOfSuperset());
		inferenceRules.add(new UnionDisjunction());
		inferenceRules.add(new IntersectionConjunction());
		inferenceRules.add(new SetComplementInference());
		inferenceRules.add(new SetDifferenceInference());
	}

	private List<ProofStatement> getImmediateInferences(TruthEnvironment environment) {
		List<ProofStatement> inferences = new LinkedList<>();
		for (InferenceRule inference : inferenceRules) {
			for (ProofStatement newTruth : inference.getInferences(environment)) {
				if (!environment.containsTruth(newTruth.getStatement()))
					inferences.add(newTruth);
			}
		}
		return inferences;
	}

	private List<InferenceRule> inferenceRules;
}

package com.discretion.solver;

import com.discretion.proof.Proof;
import com.discretion.proof.ProofItem;
import com.discretion.proof.UnknownSteps;
import com.discretion.solver.environment.TruthEnvironment;
import com.discretion.solver.inference.BestEffortInferenceChain;
import com.discretion.solver.inference.InferenceChainProducer;
import com.discretion.solver.inference.UnionDisjunction;
import com.discretion.statement.Statement;

import java.util.List;

public class BestEffortSolver extends StructuredSolver {

    public BestEffortSolver() {
        inference = new BestEffortInferenceChain();
    }

	@Override
	protected Proof fleshOutProof(Proof proof, TruthEnvironment environment, int maxInferenceDepth) {
		Statement conclusion = proof.getConclusion().getStatement();
		List<ProofItem> statements = inference.buildInferenceChain(conclusion, environment, maxInferenceDepth);

		if (!(statements.get(0) instanceof UnknownSteps))
			return new Proof(proof.getSuppositions(), statements);
		return proof;
	}

    private InferenceChainProducer inference;
}

package com.discretion.solver;

import com.discretion.proof.Proof;
import com.discretion.proof.ProofItem;
import com.discretion.solver.environment.TruthEnvironment;
import com.discretion.solver.inference.BestEffortInferenceChain;
import com.discretion.solver.inference.InferenceChainProducer;
import com.discretion.statement.Statement;

import java.util.List;

public class BestEffortSolver extends StructuredSolver {

    public BestEffortSolver() {
        inference = new BestEffortInferenceChain();
    }

	@Override
	protected Proof fleshOutProof(Proof proof, TruthEnvironment environment) {
		Statement conclusion = proof.getConclusion().getStatement();
		List<ProofItem> statements = inference.buildInferenceChain(conclusion, environment);

		return new Proof(proof.getSuppositions(), statements);
	}

    private InferenceChainProducer inference;
}

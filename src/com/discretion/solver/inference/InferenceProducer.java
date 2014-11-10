package com.discretion.solver.inference;

import com.discretion.proof.ProofStatement;
import com.discretion.solver.environment.TruthEnvironment;

import java.util.List;

public interface InferenceProducer {
    public List<ProofStatement> getInferences(TruthEnvironment environment);
}

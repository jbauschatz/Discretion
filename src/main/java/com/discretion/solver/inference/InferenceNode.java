package com.discretion.solver.inference;

import com.discretion.proof.ProofStatement;
import com.discretion.solver.environment.TruthEnvironment;

class InferenceNode {
    /**
     *
     */
    ProofStatement inference;

    /**
     *
     */
    TruthEnvironment environment;

    /**
     *
     */
    InferenceNode predecessor;
}

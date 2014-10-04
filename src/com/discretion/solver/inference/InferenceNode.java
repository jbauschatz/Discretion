package com.discretion.solver.inference;

import com.discretion.proof.ProofStatement;

class InferenceNode {
    ProofStatement inference;
    InferenceNode successor;

    int distanceToConclusion;
    boolean reachesConclusion;
}

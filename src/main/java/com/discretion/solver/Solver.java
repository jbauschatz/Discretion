package com.discretion.solver;

import com.discretion.problem.Problem;
import com.discretion.proof.Proof;

public interface Solver {
    Proof solve(Problem problem);
}

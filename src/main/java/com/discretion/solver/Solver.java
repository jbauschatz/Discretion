package com.discretion.solver;

import com.discretion.proof.Proof;
import com.discretion.statement.Statement;

import java.util.List;

public interface Solver {
    Proof solve(Statement conclusion, List<Statement> given);
}

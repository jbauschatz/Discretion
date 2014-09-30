package com.discretion.solver.inference;

import com.discretion.solver.TruthEnvironment;
import com.discretion.statement.Statement;

import java.util.List;

public interface InferenceProducer {
    public List<Statement> getInferences(TruthEnvironment environment);
}

package com.discretion.solver.structure;

import com.discretion.proof.Proof;
import com.discretion.solver.environment.TruthEnvironment;
import com.discretion.statement.Statement;

import java.util.List;

public interface ProofStructureProducer {
    public boolean applies(Statement statement);
    public Proof produceStructure(List<Statement> suppositions, Statement conclusion, TruthEnvironment environment);
}

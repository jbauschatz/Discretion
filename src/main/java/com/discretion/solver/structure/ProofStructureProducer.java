package com.discretion.solver.structure;

import com.discretion.proof.Proof;
import com.discretion.solver.environment.TruthEnvironment;
import com.discretion.statement.Statement;

import java.util.List;

public interface ProofStructureProducer {
    boolean applies(Statement statement);
    Proof produceStructure(List<Statement> suppositions, Statement conclusion, TruthEnvironment environment);
}

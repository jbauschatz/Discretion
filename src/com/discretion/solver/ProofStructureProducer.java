package com.discretion.solver;

import com.discretion.proof.ProofItem;
import com.discretion.statement.Statement;

import java.util.List;

public interface ProofStructureProducer {
    public boolean applies(Statement statement);
    public List<ProofItem> produceStructure(Statement statement);
}

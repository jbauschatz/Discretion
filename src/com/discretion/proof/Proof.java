package com.discretion.proof;

import com.discretion.statement.Statement;

import java.util.List;

public class Proof implements ProofItem {
    public final List<Statement> suppositions;
    public final List<ProofItem> proof;
    public final Statement conclusion;

    public void accept(ProofItemVisitor visitor) {
        visitor.visit(this);
    }

    public Proof(List<Statement> suppositions, List<ProofItem> proof, Statement conclusion) {
        this.suppositions = suppositions;
        this.proof = proof;
        this.conclusion = conclusion;
    }
}

package com.discretion.proof;

import com.discretion.statement.Statement;

public class ProofStatement implements ProofItem {

    public final Statement statement;

    public void accept(ProofItemVisitor visitor) {
        visitor.visit(this);
    }

    public ProofStatement(Statement statement) {
        this.statement = statement;
    }
}

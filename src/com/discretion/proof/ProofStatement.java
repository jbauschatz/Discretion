package com.discretion.proof;

import com.discretion.statement.Statement;

public class ProofStatement implements ProofItem {

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public void accept(ProofItemVisitor visitor) {
        visitor.visit(this);
    }

    public ProofStatement(Statement statement) {
        this.statement = statement;
    }

    public ProofStatement() {
    }

    private Statement statement;
}

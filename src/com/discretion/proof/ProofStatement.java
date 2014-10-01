package com.discretion.proof;

import com.discretion.statement.Statement;

public class ProofStatement implements ProofItem {

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

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

    public ProofStatement(Statement statement, String reason) {
        this.statement = statement;
        this.reason = reason;
    }

    public ProofStatement() {
    }

    private Statement statement;
    private String reason;
}

package com.discretion.proof;

public interface ProofItemVisitor {
    public void visit(Proof proof);
    public void visit(ProofStatement statement);
    public void visit(UnknownSteps unknown);
}

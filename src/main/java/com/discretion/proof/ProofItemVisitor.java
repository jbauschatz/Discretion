package com.discretion.proof;

public interface ProofItemVisitor {
    void visit(Proof proof);
    void visit(ProofStatement statement);
    void visit(UnknownSteps unknown);
}

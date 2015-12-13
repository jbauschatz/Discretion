package com.discretion.proof;

public class UnknownSteps implements ProofItem {

    public void accept(ProofItemVisitor visitor) {
        visitor.visit(this);
    }

    public UnknownSteps() {
    }
}

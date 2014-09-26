package com.discretion.proof;

public interface ProofItem {

    public void accept(ProofItemVisitor visitor);
}

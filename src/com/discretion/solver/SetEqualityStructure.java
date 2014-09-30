package com.discretion.solver;

import com.discretion.proof.Proof;
import com.discretion.proof.ProofItem;
import com.discretion.proof.UnknownSteps;
import com.discretion.statement.Equality;
import com.discretion.statement.Statement;
import com.discretion.statement.SubsetOf;

import java.util.LinkedList;
import java.util.List;

public class SetEqualityStructure implements ProofStructureProducer {
    public boolean applies(Statement statement) {
        return statement instanceof Equality;
    }

    public List<ProofItem> produceStructure(Statement statement) {
        Equality equality = (Equality)statement;

        List<ProofItem> structure = new LinkedList<>();

        Proof subsetA = new Proof();
        subsetA.getSuppositions().add(new SubsetOf(equality.getLeft(), equality.getRight()));
        subsetA.getProofItems().add(new UnknownSteps());
        subsetA.setConclusion(new SubsetOf(equality.getRight(), equality.getLeft()));

        Proof subsetB = new Proof();
        subsetB.getSuppositions().add(new SubsetOf(equality.getRight(), equality.getLeft()));
        subsetB.getProofItems().add(new UnknownSteps());
        subsetB.setConclusion(new SubsetOf(equality.getLeft(), equality.getRight()));

        structure.add(subsetA);
        structure.add(subsetB);

        return structure;
    }
}

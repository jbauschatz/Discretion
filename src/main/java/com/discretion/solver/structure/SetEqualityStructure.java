package com.discretion.solver.structure;

import com.discretion.Variable;
import com.discretion.proof.Proof;
import com.discretion.proof.ProofItem;
import com.discretion.proof.ProofStatement;
import com.discretion.proof.UnknownSteps;
import com.discretion.solver.environment.TruthEnvironment;
import com.discretion.statement.ElementOf;
import com.discretion.statement.Equality;
import com.discretion.statement.Statement;
import com.discretion.statement.SubsetOf;

import java.util.LinkedList;
import java.util.List;

public class SetEqualityStructure implements ProofStructureProducer {
    public boolean applies(Statement statement) {
        return statement instanceof Equality;
    }

    public Proof produceStructure(List<Statement> suppositions, Statement conclusion, TruthEnvironment environment) {
        Equality equality = (Equality)conclusion;

        List<ProofItem> structure = new LinkedList<>();

        Proof subsetA = new Proof();
		Variable elementA = environment.newElementName(equality.getLeft());
        subsetA.getSuppositions().add(new ElementOf(elementA, equality.getLeft()));
        subsetA.getProofItems().add(new UnknownSteps());
        subsetA.setConclusion(new ElementOf(elementA, equality.getRight()));
        structure.add(subsetA);
        structure.add(new ProofStatement(new SubsetOf(equality.getLeft(), equality.getRight())));

        Proof subsetB = new Proof();
		Variable elementB = environment.newElementName(equality.getRight());
        subsetB.getSuppositions().add(new ElementOf(elementB, equality.getRight()));
        subsetB.getProofItems().add(new UnknownSteps());
        subsetB.setConclusion(new ElementOf(elementB, equality.getLeft()));
        structure.add(subsetB);
        structure.add(new ProofStatement(new SubsetOf(equality.getRight(), equality.getLeft())));

		ProofStatement newConclusion = new ProofStatement(conclusion, "by the definition of set equality");

        return new Proof(suppositions, structure, newConclusion);
    }
}

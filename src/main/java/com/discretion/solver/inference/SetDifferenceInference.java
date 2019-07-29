package com.discretion.solver.inference;

import com.discretion.AbstractMathObjectVisitor;
import com.discretion.MathObject;
import com.discretion.expression.SetDifference;
import com.discretion.proof.ProofStatement;
import com.discretion.solver.Replacer;
import com.discretion.solver.environment.TruthEnvironment;
import com.discretion.statement.*;

import java.util.LinkedList;
import java.util.List;

public class SetDifferenceInference extends AbstractMathObjectVisitor implements InferenceRule {
    @Override
    public List<ProofStatement> getInferences(TruthEnvironment environment) {
        inferences = new LinkedList<>();

        // Each truth-statement in the environment can potentially be substituted using this rule
        for (Statement object : environment.getTruths()) {
            originalStatement = object;
            traverse(object);
        }

        return inferences;
    }

    public SetDifferenceInference() {
        replacer = new Replacer();
    }

    @Override
    protected void handle(Conjunction conjunction) {
		// Case 1: x ∈ A ∧ x ∉ B  ->  x ∈ A - B
		if (conjunction.getLeft() instanceof ElementOf
				&& conjunction.getRight() instanceof NotElementOf) {
			ElementOf leftSide = (ElementOf)conjunction.getLeft();
			NotElementOf rightSide = (NotElementOf)conjunction.getRight();

			if (leftSide.getElement().equals(rightSide.getElement())) {
				ElementOf elementOfDifference = new ElementOf(leftSide.getElement(),
						new SetDifference(leftSide.getSet(), rightSide.getSet()));
				Statement replaced = (Statement)replacer.substitute(originalStatement, conjunction, elementOfDifference);
				inferences.add(new ProofStatement(replaced, "by the definition of set difference"));
			}
		}

		// TODO Case 2: x ∉ A ∧ x ∈ B  ->  x ∈ B - A
    }

    @Override
    protected void handle(ElementOf elementOf) {
        MathObject set = elementOf.getSet();
        if (set instanceof SetDifference) {
			SetDifference diff = (SetDifference) set;

			if (elementOf.isNegative()) {
				// x ∉ A - B -> ¬(x ∈ A ∧ x ∉ B)
				ElementOf left = new ElementOf(elementOf.getElement(), diff.getLeft());
				NotElementOf right = new NotElementOf(elementOf.getElement(), diff.getRight());
				Negation notConjunction = new Negation(new Conjunction(left, right));
				Statement replaced = (Statement) replacer.substitute(originalStatement, elementOf, notConjunction);
				inferences.add(new ProofStatement(replaced, "by the definition of set difference"));
			} else {
				// x ∈ A - B -> x ∈ A ∧ x ∉ B
				ElementOf left = new ElementOf(elementOf.getElement(), diff.getLeft());
				NotElementOf right = new NotElementOf(elementOf.getElement(), diff.getRight());
				Conjunction conj = new Conjunction(left, right);
				Statement replaced = (Statement) replacer.substitute(originalStatement, elementOf, conj);
				inferences.add(new ProofStatement(replaced, "by the definition of set difference"));
			}
        }
    }

    private Statement originalStatement;
    private LinkedList<ProofStatement> inferences;
    private Replacer replacer;
}

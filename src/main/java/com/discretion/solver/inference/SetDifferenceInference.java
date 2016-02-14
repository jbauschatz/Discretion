package com.discretion.solver.inference;

import com.discretion.AbstractMathObjectVisitor;
import com.discretion.MathObject;
import com.discretion.expression.SetDifference;
import com.discretion.proof.ProofStatement;
import com.discretion.solver.Replacer;
import com.discretion.solver.environment.TruthEnvironment;
import com.discretion.statement.Conjunction;
import com.discretion.statement.ElementOf;
import com.discretion.statement.Negation;
import com.discretion.statement.Statement;

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
		// Case 1: a in A and a not in B -> a in A-B
		if (conjunction.getLeft() instanceof ElementOf && conjunction.getRight() instanceof Negation) {
			ElementOf leftSide = (ElementOf)conjunction.getLeft();
			Negation rightSide = (Negation)conjunction.getRight();

			if (rightSide.getTerm() instanceof ElementOf) {
				ElementOf rightElementOf = (ElementOf)rightSide.getTerm();
				if (rightElementOf.getElement().equals(leftSide.getElement())) {
					ElementOf elementOfDifference = new ElementOf(leftSide.getElement(),
							new SetDifference(leftSide.getSet(), rightElementOf.getSet()));
					Statement replaced = (Statement)replacer.substitute(originalStatement, conjunction, elementOfDifference);
					inferences.add(new ProofStatement(replaced, "by the definition of set difference"));
				}
			}
		}

		// TODO Case 2: a not in A and a in B -> a in B-A
    }

    @Override
    protected void handle(ElementOf elementOf) {
        MathObject set = elementOf.getSet();
        if (set instanceof SetDifference) {
            SetDifference diff = (SetDifference)set;
            ElementOf left = new ElementOf(elementOf.getElement(), diff.getLeft());
            Negation right = new Negation(new ElementOf(elementOf.getElement(), diff.getRight()));
            Conjunction conj = new Conjunction(left, right);
            Statement replaced = (Statement)replacer.substitute(originalStatement, elementOf, conj);
            inferences.add(new ProofStatement(replaced, "by the definition of set difference"));
        }
    }

    private Statement originalStatement;
    private LinkedList<ProofStatement> inferences;
    private Replacer replacer;
}

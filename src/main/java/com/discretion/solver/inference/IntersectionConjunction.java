package com.discretion.solver.inference;

import com.discretion.AbstractMathObjectVisitor;
import com.discretion.MathObject;
import com.discretion.expression.SetIntersection;
import com.discretion.proof.ProofStatement;
import com.discretion.solver.Replacer;
import com.discretion.solver.environment.TruthEnvironment;
import com.discretion.statement.Conjunction;
import com.discretion.statement.ElementOf;
import com.discretion.statement.Statement;

import java.util.LinkedList;
import java.util.List;

public class IntersectionConjunction extends AbstractMathObjectVisitor implements InferenceRule {
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

    public IntersectionConjunction() {
        replacer = new Replacer();
    }

    @Override
    protected void handle(Conjunction conjunction) {
        if (!(conjunction.getLeft() instanceof ElementOf))
            return;
        if (!(conjunction.getRight() instanceof ElementOf))
            return;
        ElementOf left = (ElementOf)conjunction.getLeft();
        ElementOf right = (ElementOf)conjunction.getRight();

        if (!left.getElement().equals(right.getElement()))
            return;

        ElementOf substitution = new ElementOf(left.getElement(),
                new SetIntersection(left.getSet(), right.getSet()));
        inferences.add(new ProofStatement(
                (Statement) replacer.substitute(originalStatement, conjunction, substitution),
                "by the definition of intersection"
        ));
    }

    @Override
    protected void handle(ElementOf elementOf) {
        if (elementOf.getSet() instanceof SetIntersection) {
            MathObject object = elementOf.getElement();
            SetIntersection oldIntersection = (SetIntersection)elementOf.getSet();
            Conjunction newConjunction = new Conjunction(
                    new ElementOf(object, oldIntersection.getLeft()),
                    new ElementOf(object, oldIntersection.getRight())
            );
            inferences.add(new ProofStatement(
                    (Statement)
                    replacer.substitute(originalStatement, elementOf, newConjunction),
                    "by the definition of intersection"
            ));
        }
    }

    private Statement originalStatement;
    private LinkedList<ProofStatement> inferences;
    private Replacer replacer;
}

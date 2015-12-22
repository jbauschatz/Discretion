package com.discretion.solver.inference;

import com.discretion.AbstractMathObjectVisitor;
import com.discretion.MathObject;
import com.discretion.expression.SetUnion;
import com.discretion.proof.ProofStatement;
import com.discretion.solver.Replacer;
import com.discretion.solver.environment.TruthEnvironment;
import com.discretion.statement.Disjunction;
import com.discretion.statement.ElementOf;
import com.discretion.statement.Statement;

import java.util.LinkedList;
import java.util.List;

public class UnionDisjunction extends AbstractMathObjectVisitor implements InferenceRule {
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

    public UnionDisjunction() {
        replacer = new Replacer();
    }

    @Override
    protected void handle(Disjunction disjunction) {
        if (!(disjunction.getLeft() instanceof ElementOf))
            return;
        if (!(disjunction.getRight() instanceof ElementOf))
            return;
        ElementOf left = (ElementOf)disjunction.getLeft();
        ElementOf right = (ElementOf)disjunction.getRight();

        if (!left.getElement().equals(right.getElement()))
            return;

        ElementOf substitution = new ElementOf(left.getElement(),
                new SetUnion(left.getSet(), right.getSet()));
        inferences.add(new ProofStatement(
                (Statement)
                        replacer.substitute(originalStatement, disjunction, substitution),
                "by the definition of union"
        ));
    }

    @Override
    protected void handle(ElementOf elementOf) {
        if (elementOf.getSet() instanceof SetUnion) {
            MathObject object = elementOf.getElement();
            SetUnion oldUnion = (SetUnion)elementOf.getSet();
            Disjunction newDisjunction = new Disjunction(
                    new ElementOf(object, oldUnion.getLeft()),
                    new ElementOf(object, oldUnion.getRight())
            );
            inferences.add(new ProofStatement(
                    (Statement)
                    replacer.substitute(originalStatement, elementOf, newDisjunction),
                    "by the definition of union"
            ));
        }
    }

    private Statement originalStatement;
    private LinkedList<ProofStatement> inferences;
    private Replacer replacer;
}

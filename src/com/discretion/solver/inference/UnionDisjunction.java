package com.discretion.solver.inference;

import com.discretion.AbstractMathObjectVisitor;
import com.discretion.MathObject;
import com.discretion.expression.SetUnion;
import com.discretion.proof.ProofStatement;
import com.discretion.solver.Replacer;
import com.discretion.solver.TruthEnvironment;
import com.discretion.statement.Disjunction;
import com.discretion.statement.ElementOf;
import com.discretion.statement.Statement;

import java.util.LinkedList;
import java.util.List;

public class UnionDisjunction extends AbstractMathObjectVisitor implements InferenceProducer{
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
        // TODO - apply the definition synthetically - create a union from a disjunction
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

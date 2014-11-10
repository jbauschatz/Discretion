package com.discretion.solver.inference;

import com.discretion.AbstractMathObjectVisitor;
import com.discretion.MathObject;
import com.discretion.expression.SetComplement;
import com.discretion.proof.ProofStatement;
import com.discretion.solver.Replacer;
import com.discretion.solver.environment.TruthEnvironment;
import com.discretion.statement.Disjunction;
import com.discretion.statement.ElementOf;
import com.discretion.statement.Negation;
import com.discretion.statement.Statement;

import java.util.LinkedList;
import java.util.List;

public class SetComplementInference extends AbstractMathObjectVisitor implements InferenceProducer {
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

    public SetComplementInference() {
        replacer = new Replacer();
    }

    @Override
    protected void handle(Negation negation) {
        MathObject negated = negation.getTerm();
        if (negated instanceof ElementOf) {
            ElementOf element = (ElementOf)negated;
            ElementOf elementOfComplement = new ElementOf(element.getElement(), new SetComplement(element.getSet()));
            Statement replaced = (Statement)replacer.substitute(originalStatement, negation, elementOfComplement);

            inferences.add(new ProofStatement(replaced, "by the definition of set complement"));
        }
    }

    @Override
    protected void handle(ElementOf elementOf) {
        MathObject set = elementOf.getSet();
        MathObject complemented = (set instanceof SetComplement) ? ((SetComplement)set).getSet() : new SetComplement(set);

        Negation negation = new Negation(
            new ElementOf(elementOf.getElement(), complemented)
        );
        Statement replaced = (Statement)replacer.substitute(originalStatement, elementOf, negation);

        inferences.add(new ProofStatement(replaced, "by the definition of set complement"));
    }

    private Statement originalStatement;
    private LinkedList<ProofStatement> inferences;
    private Replacer replacer;
}

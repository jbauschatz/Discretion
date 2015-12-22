package com.discretion.solver.inference;

import com.discretion.AbstractMathObjectVisitor;
import com.discretion.MathObject;
import com.discretion.proof.ProofStatement;
import com.discretion.solver.Replacer;
import com.discretion.solver.environment.TruthEnvironment;
import com.discretion.statement.Conjunction;
import com.discretion.statement.Disjunction;
import com.discretion.statement.Negation;
import com.discretion.statement.Statement;

import java.util.LinkedList;
import java.util.List;

public class DeMorgansLaw extends AbstractMathObjectVisitor implements InferenceRule {
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

    public DeMorgansLaw() {
        replacer = new Replacer();
    }

    @Override
    protected void handle(Conjunction conjunction) {
        if (parent instanceof Negation)
            return;

        Statement notDisjunction = new Negation(
                new Disjunction(
                        negate(conjunction.getLeft()),
                        negate(conjunction.getRight())
                )
        );
        Statement replaced = (Statement)replacer.substitute(originalStatement, conjunction, notDisjunction);
        inferences.add(new ProofStatement(replaced, "by DeMorgan's Law"));
    }

    @Override
    protected void handle(Disjunction disjunction) {
        if (parent instanceof Negation)
            return;

        Statement notConjunction = new Negation(
                new Conjunction(
                        negate(disjunction.getLeft()),
                        negate(disjunction.getRight())
                )
        );
        Statement replaced = (Statement)replacer.substitute(originalStatement, disjunction, notConjunction);
        inferences.add(new ProofStatement(replaced, "by DeMorgan's Law"));
    }

    @Override
    protected void handle(Negation negation) {
        if (negation.getTerm() instanceof Disjunction) {
            Disjunction disjunction = (Disjunction)negation.getTerm();
            Conjunction conjunction = new Conjunction(negate(disjunction.getLeft()), negate(disjunction.getRight()));
            Statement replaced = (Statement)replacer.substitute(originalStatement, negation, conjunction);
            inferences.add(new ProofStatement(replaced, "by DeMorgan's Law"));
        }

        if (negation.getTerm() instanceof Conjunction) {
            Conjunction conjunction = (Conjunction)negation.getTerm();
            Disjunction disjunction = new Disjunction(negate(conjunction.getLeft()), negate(conjunction.getRight()));
            Statement replaced = (Statement)replacer.substitute(originalStatement, negation, disjunction);
            inferences.add(new ProofStatement(replaced, "by DeMorgan's Law"));
        }
    }

    private MathObject negate(MathObject object) {
        if (object instanceof Negation)
            return ((Negation)object).getTerm();

        return new Negation(object);
    }

    private Statement originalStatement;
    private LinkedList<ProofStatement> inferences;
    private Replacer replacer;
}

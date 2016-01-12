package com.discretion.solver.inference;

import com.discretion.AbstractMathObjectVisitor;
import com.discretion.proof.ProofStatement;
import com.discretion.solver.Replacer;
import com.discretion.solver.environment.TruthEnvironment;
import com.discretion.statement.Conjunction;
import com.discretion.statement.Negation;
import com.discretion.statement.Statement;
import com.sun.glass.ui.EventLoop;

import java.util.LinkedList;
import java.util.List;

public class DoubleNegative extends AbstractMathObjectVisitor implements InferenceRule {
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

    public DoubleNegative() {
        replacer = new Replacer();
    }

    @Override
    protected void handle(Negation negation) {
		// TODO make sure that a triple negative doesn't produce a duplicate inference
		if (negation.getTerm() instanceof Negation) {
			Statement term = (Statement) ((Negation)negation.getTerm()).getTerm();
			Statement simplified = (Statement)replacer.substitute(originalStatement, negation, term);
			inferences.add(new ProofStatement(simplified, "by double negative"));
		}
    }

    private Statement originalStatement;
    private LinkedList<ProofStatement> inferences;
    private Replacer replacer;
}

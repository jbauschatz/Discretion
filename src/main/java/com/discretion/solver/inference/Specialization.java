package com.discretion.solver.inference;

import com.discretion.AbstractMathObjectVisitor;
import com.discretion.proof.ProofStatement;
import com.discretion.solver.Replacer;
import com.discretion.solver.environment.TruthEnvironment;
import com.discretion.statement.Conjunction;
import com.discretion.statement.Statement;

import java.util.LinkedList;
import java.util.List;

public class Specialization extends AbstractMathObjectVisitor implements InferenceRule {
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

    public Specialization() {
        replacer = new Replacer();
    }

    @Override
    protected void handle(Conjunction conjunction) {
        inferences.add(new ProofStatement((Statement)conjunction.getLeft(), "by specialization"));
        inferences.add(new ProofStatement((Statement)conjunction.getRight(), "by specialization"));
    }

    private Statement originalStatement;
    private LinkedList<ProofStatement> inferences;
    private Replacer replacer;
}

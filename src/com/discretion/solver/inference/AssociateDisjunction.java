package com.discretion.solver.inference;

import com.discretion.AbstractMathObjectVisitor;
import com.discretion.MathObject;
import com.discretion.PrettyPrinter;
import com.discretion.expression.SetUnion;
import com.discretion.proof.ProofStatement;
import com.discretion.solver.Replacer;
import com.discretion.solver.TruthEnvironment;
import com.discretion.statement.Disjunction;
import com.discretion.statement.ElementOf;
import com.discretion.statement.Statement;

import java.util.LinkedList;
import java.util.List;

public class AssociateDisjunction extends AbstractMathObjectVisitor implements InferenceProducer{
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

    public AssociateDisjunction() {
        replacer = new Replacer();
    }

    @Override
    protected void handle(Disjunction disjunction) {
        // Left-nested parentheses
        if (disjunction.getLeft() instanceof Disjunction) {
            Disjunction left = (Disjunction)disjunction.getLeft();
            Disjunction shiftedRight = new Disjunction(left.getLeft(), new Disjunction(left.getRight(), disjunction.getRight()));
            Statement replaced = (Statement)replacer.substitute(originalStatement, disjunction, shiftedRight);

            inferences.add(new ProofStatement(replaced, "by associativity"));
        }
        // Right-nested parentheses
        if (disjunction.getRight() instanceof Disjunction) {
            Disjunction right = (Disjunction)disjunction.getRight();
            Disjunction shiftedLeft = new Disjunction(new Disjunction(disjunction.getLeft(), right.getLeft()), right.getRight());
            Statement replaced = (Statement)replacer.substitute(originalStatement, disjunction, shiftedLeft);

            inferences.add(new ProofStatement(replaced, "by associativity"));
        }
    }

    private Statement originalStatement;
    private LinkedList<ProofStatement> inferences;
    private Replacer replacer;
}

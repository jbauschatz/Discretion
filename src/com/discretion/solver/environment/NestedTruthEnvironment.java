package com.discretion.solver.environment;

import com.discretion.statement.Statement;

import java.util.LinkedList;
import java.util.List;

public class NestedTruthEnvironment implements TruthEnvironment {

    public boolean containsTruth(Statement truth) {
        return truths.contains(truth)
            || (parent != null && parent.containsTruth(truth));
    }

    /**
     * Returns whether the new statement was added
     * (false if it already existed)
     */
    public boolean addTruth(Statement truth) {
        if (truths.contains(truth))
            return false;

        if (parent != null && parent.containsTruth(truth))
            return false;

        truths.add(truth);
        return true;
    }

    public <S extends Statement> List<S> getTruths(Class<S> statementClass) {
        LinkedList<S> filtered = new LinkedList<>();

        for (Statement s : getTruths()) {
            if (s.getClass().equals(statementClass))
                filtered.add((S)s);
        }

        return filtered;
    }

    public List<Statement> getTruths() {
        // TODO: Custom linked list that iterates up the parent
        if (parent != null) {
            LinkedList<Statement> all = new LinkedList<>();
            all.addAll(parent.getTruths());

            all.addAll(truths);

            return all;
        }

        return truths;
    }

    public NestedTruthEnvironment getChildEnvironment(Statement newTruth) {
        LinkedList<Statement> childStatements = new LinkedList<>();
        childStatements.add(newTruth);
        NestedTruthEnvironment child = new NestedTruthEnvironment(childStatements);
        child.parent = this;
        return child;
    }

    public NestedTruthEnvironment getChildEnvironment(List<Statement> newTruths) {
        NestedTruthEnvironment child = new NestedTruthEnvironment(newTruths);
        child.parent = this;
        return child;
    }

    public NestedTruthEnvironment(List<Statement> initialTruths) {
        truths = new LinkedList<>();

        for (Statement s : initialTruths)
            addTruth(s);
    }

    public NestedTruthEnvironment(Statement... initialTruths) {
        truths = new LinkedList<>();

        for (Statement s : initialTruths)
            addTruth(s);
    }

    private TruthEnvironment parent;
    private List<Statement> truths;
}

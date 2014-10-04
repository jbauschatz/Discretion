package com.discretion.solver;

import com.discretion.MathObject;
import com.discretion.statement.ElementOf;
import com.discretion.statement.Statement;
import com.discretion.statement.SubsetOf;

import java.util.LinkedList;
import java.util.List;

/**
 * The set of truths that are "accessible" to the proof at a given point.
 *
 * This includes all suppositions from the outset of the proof, as well all
 * suppositions of all sup-proofs that nest the given point.
 *
 * This should organize truths for efficiency of access, so that inferences
 * can be made without searching every possible truth combination. For example,
 * all "ElementOf" statements can be kept together in a list.
 */
public class TruthEnvironment {

    public void addTruths(List<Statement> truths) {
        for (Statement truth : truths)
            addTruth(truth);
    }

    public void removeTruths(List<Statement> truths) {
        for (Statement truth : truths)
            removeTruth(truth);
    }

    public boolean containsTruth(Statement truth) {
        return truths.contains(truth);
    }

    /**
     * Returns whether the new statement was added
     * (false if it already existed)
     */
    public boolean addTruth(Statement truth) {
        if (truths.contains(truth))
            return false;

        truths.add(truth);

        Class<?> truthClass = truth.getClass();
        if (truthClass == ElementOf.class)
            elementOfs.add((ElementOf)truth);
        if (truthClass == SubsetOf.class)
            subsetOfs.add((SubsetOf)truth);

        return true;
    }

    public <O extends MathObject> List<? extends MathObject> getTruths(Class<O> mathClass) {
        if (mathClass == ElementOf.class)
            return elementOfs;
        if (mathClass == SubsetOf.class)
            return subsetOfs;

        return null;
    }

    public List<Statement> getTruths() {
        return truths;
    }

    public boolean removeTruth(Statement truth) {
        return truths.remove(truth);
    }

    public boolean hasContradiction() {
        // TODO search all statements for their negation
        return false;
    }

    public TruthEnvironment() {
        truths = new LinkedList<>();
        elementOfs = new LinkedList<>();
        subsetOfs = new LinkedList<>();
    }

    private List<Statement> truths;
    private List<ElementOf> elementOfs;
    private List<SubsetOf> subsetOfs;
}

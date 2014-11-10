package com.discretion.solver.environment;

import com.discretion.statement.Statement;

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
public interface TruthEnvironment {

    public boolean containsTruth(Statement truth);

    public List<Statement> getTruths();
    public <S extends Statement> List<S> getTruths(Class<S> statementClass);

    public NestedTruthEnvironment getChildEnvironment(Statement newTruth);
    public NestedTruthEnvironment getChildEnvironment(List<Statement> newTruths);
}

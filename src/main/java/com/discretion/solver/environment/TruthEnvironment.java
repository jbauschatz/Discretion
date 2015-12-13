package com.discretion.solver.environment;

import com.discretion.MathObject;
import com.discretion.Variable;
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

	public boolean containsName(String variableName);

    public List<Statement> getTruths();
    public <S extends Statement> List<S> getTruths(Class<S> statementClass);

    public NestedTruthEnvironment getChildEnvironment(Statement newTruth);
    public NestedTruthEnvironment getChildEnvironment(List<Statement> newTruths);

	/**
	 * Gets an available variable name based on the names used in the environment,
	 * and similar to a given string.
	 *
	 * For example, if a variable name like "x" is desired but "x" is unavailable in
	 * context, will produce an alternative like "x'". The returned variable is
	 * guaranteed not to collide.
	 */
	public Variable newVariableName(String likeThis);
	public Variable newElementName(MathObject set);
}

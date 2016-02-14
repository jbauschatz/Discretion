package com.discretion.solver.environment;

import com.discretion.MathObject;
import com.discretion.statement.Variable;
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

	boolean containsTruth(Statement truth);

	boolean containsName(String variableName);

    List<Statement> getTruths();

	<S extends Statement> List<S> getTruths(Class<S> statementClass);

	TruthEnvironment getChildEnvironment(Statement newTruth);

	TruthEnvironment getChildEnvironment(List<Statement> newTruths);

	/**
	 * Gets an available variable name based on the names used in the environment,
	 * and similar to a given string.
	 *
	 * For example, if a variable name like "x" is desired but "x" is unavailable in
	 * context, will produce an alternative like "x'". The returned variable is
	 * guaranteed not to collide.
	 */
	Variable newVariableName(String likeThis);

	Variable newElementName(MathObject set);
}

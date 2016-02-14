package com.discretion.solver.inference;

import com.discretion.proof.ProofStatement;
import com.discretion.solver.environment.NestedTruthEnvironment;
import com.discretion.solver.environment.TruthEnvironment;
import com.discretion.statement.Conjunction;
import com.discretion.statement.Statement;
import com.discretion.statement.Variable;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.discretion.EqualExpression.equalToExpression;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;

public class SpecializationTest {

	@Before
	public void setUpTest() {
		specialization = new Specialization();
	}

	/**
	 * Test that Specialization infers the left side of a conjunction
	 *
	 * Given:
	 *     P ∧ Q
	 *
	 * Infer:
	 *     P
	 */
	@Test
	public void testLeftSide() {
		TruthEnvironment environment = new NestedTruthEnvironment(
				new Conjunction("P", "Q"));
		List<ProofStatement> inferences = specialization.getInferences(environment);
		List<Statement> justStatements = inferences.stream()
				.map(ProofStatement::getStatement)
				.collect(Collectors.toList());

		assertThat("Two inferences should be made", inferences.size(), is(2));
		assertThat("(P and Q) implies P",
				justStatements,
				hasItem(is(equalToExpression(new Variable("P")))));
	}

	/**
	 * Test that Specialization infers the right side of a conjunction
	 *
	 * Given:
	 *     P ∧ Q
	 *
	 * Infer:
	 *     Q
	 */
	@Test
	public void testRightSide() {
		TruthEnvironment environment = new NestedTruthEnvironment(
				new Conjunction("P", "Q"));
		List<ProofStatement> inferences = specialization.getInferences(environment);
		List<Statement> justStatements = inferences.stream()
				.map(ProofStatement::getStatement)
				.collect(Collectors.toList());

		assertThat("Two inferences should be made", inferences.size(), is(2));
		assertThat("(P and Q) implies Q",
				justStatements,
				hasItem(is(equalToExpression(new Variable("Q")))));
	}

	private Specialization specialization;
}

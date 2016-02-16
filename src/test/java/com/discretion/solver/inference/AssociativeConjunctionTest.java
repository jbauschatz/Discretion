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

import static com.discretion.EqualExpression.equalToExpression;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 *
 */
public class AssociativeConjunctionTest {

	/**
	 * Prepare the object to be tested
	 */
	@Before
	public void setUpTest() {
		associative = new AssociativeConjunction();
	}

	/**
	 * Tests that left-nested parentheses yield right-nested parentheses
	 *
	 * Given:
	 *       (P ∧ Q) ∧ R
	 *
	 * Infer:
	 *       P ∧ (Q ∧ R)
	 */
	@Test
	public void testLeftNested() {
		TruthEnvironment environment = new NestedTruthEnvironment(
				new Conjunction(
						new Conjunction("P", "Q"),
						new Variable("R")));
		List<ProofStatement> inferences = associative.getInferences(environment);

		assertThat("Only one inference should be made", inferences.size(), is(1));

		Statement expected = new Conjunction(
				new Variable("P"),
				new Conjunction("Q", "R"));
		assertThat("((P and Q) and R) implies (P and (Q and R))",
				inferences.get(0).getStatement(),
				equalToExpression(expected));
	}

	/**
	 * Tests that right-nested parentheses yield left-nested parentheses
	 *
	 * Given:
	 *       P ∧ (Q ∧ R)
	 *
	 * Infer:
	 *       (P ∧ Q) ∧ R
	 */
	@Test
	public void testRightNested() {
		TruthEnvironment environment = new NestedTruthEnvironment(
				new Conjunction(
						new Variable("P"),
						new Conjunction("Q", "R")));
		List<ProofStatement> inferences = associative.getInferences(environment);

		assertThat("Only one inference should be made", inferences.size(), is(1));

		Statement expected =
				new Conjunction(
						new Conjunction("P", "Q"),
						new Variable("R"));
		assertThat("(P and (Q and R)) implies ((P and Q) and R)",
				inferences.get(0).getStatement(),
				equalToExpression(expected));
	}

	private AssociativeConjunction associative;
}

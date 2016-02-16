package com.discretion.solver.inference;

import com.discretion.proof.ProofStatement;
import com.discretion.solver.environment.NestedTruthEnvironment;
import com.discretion.solver.environment.TruthEnvironment;
import com.discretion.statement.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.discretion.EqualExpression.equalToExpression;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 *
 */
public class AssociativeDisjunctionTest {

	/**
	 * Prepare the object to be tested
	 */
	@Before
	public void setUpTest() {
		associative = new AssociativeDisjunction();
	}

	/**
	 * Tests that left-nested parentheses yield right-nested parentheses
	 *
	 * Given:
	 *       (P ∨ Q) ∨ R
	 *
	 * Infer:
	 *       P ∨ (Q ∨ R)
	 */
	@Test
	public void testLeftNested() {
		TruthEnvironment environment = new NestedTruthEnvironment(
				new Disjunction(
						new Disjunction("P", "Q"),
						new Variable("R")));
		List<ProofStatement> inferences = associative.getInferences(environment);

		assertThat("Only one inference should be made", inferences.size(), is(1));

		Statement expected = new Disjunction(
				new Variable("P"),
				new Disjunction("Q", "R"));
		assertThat("((P or Q) or R) implies (P or (Q or R))",
				inferences.get(0).getStatement(),
				equalToExpression(expected));
	}

	/**
	 * Tests that right-nested parentheses yield left-nested parentheses
	 *
	 * Given:
	 *       P ∨ (Q ∨ R)
	 *
	 * Infer:
	 *       (P ∨ Q) ∨ R
	 */
	@Test
	public void testRightNested() {
		TruthEnvironment environment = new NestedTruthEnvironment(
				new Disjunction(
						new Variable("P"),
						new Disjunction("Q", "R")));
		List<ProofStatement> inferences = associative.getInferences(environment);

		assertThat("Only one inference should be made", inferences.size(), is(1));

		Statement expected =
				new Disjunction(
						new Disjunction("P", "Q"),
						new Variable("R"));
		assertThat("(P or (Q or R)) implies ((P or Q) or R)",
				inferences.get(0).getStatement(),
				equalToExpression(expected));
	}

	private AssociativeDisjunction associative;
}

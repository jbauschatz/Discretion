package com.discretion.solver.inference;

import com.discretion.expression.SetUnion;
import com.discretion.proof.ProofStatement;
import com.discretion.solver.environment.NestedTruthEnvironment;
import com.discretion.solver.environment.TruthEnvironment;
import com.discretion.statement.Disjunction;
import com.discretion.statement.ElementOf;
import com.discretion.statement.Variable;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.discretion.EqualExpression.equalToExpression;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UnionDisjunctionTest {

	/**
	 * Initialize the object under test
	 */
	@Before
	public void setUpTest() {
		union = new UnionDisjunction();
	}

	/**
	 * Test the synthetic application: creating a Union from a Disjunction
	 *
	 * Given:
	 *      x ∈ A ∨ x ∈ B
	 *
	 * Infer:
	 *      x ∈ A ∪ B
	 */
	@Test
	public void testSynthetic() {
		TruthEnvironment environment = new NestedTruthEnvironment(
				new Disjunction(
					new ElementOf("x", "A"),
					new ElementOf("x", "B")));
		List<ProofStatement> inferences = union.getInferences(environment);

		assertThat("Only one inference should be made", inferences.size(), is(1));
		assertThat("(x in A) or (x in B) implies (x in A union B)",
				inferences.get(0).getStatement(),
					is(equalToExpression(
						new ElementOf(new Variable("x"), new SetUnion("A", "B")))));
	}

	/**
	 * Test the analytic application: deconstructing a Union into a Disjunction
	 *
	 * Given:
	 *      x ∈ A ∪ B
	 *
	 * Infer:
	 *      x ∈ A ∨ x ∈ B
	 */
	@Test
	public void testAnalytic() {
		TruthEnvironment environment = new NestedTruthEnvironment(
				new ElementOf(new Variable("x"), new SetUnion("A", "B")));
		List<ProofStatement> inferences = union.getInferences(environment);

		assertThat("Only one inference should be made", inferences.size(), is(1));
		assertThat("(x in A) or (x in B) implies (x in A union B)",
				inferences.get(0).getStatement(),
				is(equalToExpression(
						new Disjunction(
							new ElementOf("x", "A"),
							new ElementOf("x", "B")))));
	}

	private UnionDisjunction union;
}

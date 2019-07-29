package com.discretion.solver.inference;

import com.discretion.statement.NotElementOf;
import com.discretion.statement.Variable;
import com.discretion.expression.SetComplement;
import com.discretion.proof.ProofStatement;
import com.discretion.solver.environment.NestedTruthEnvironment;
import com.discretion.solver.environment.TruthEnvironment;
import com.discretion.statement.ElementOf;
import com.discretion.statement.Negation;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.discretion.EqualExpression.equalToExpression;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SetComplementTest {

	@Before
	public void setUpTest() {
		complement = new SetComplementInference();
	}

	/**
	 * Given:
	 *      x ∈ X
	 *
	 * Infer:
	 *      x ∉ ~X
	 */
	@Test
	public void testInSet() {
		TruthEnvironment environment = new NestedTruthEnvironment(
				new ElementOf("x", "X"));
		List<ProofStatement> inferences = complement.getInferences(environment);

		assertThat("Only one inference should be made", inferences.size(), is(1));
		assertThat("(x in X) implies (x not in ~X)",
				inferences.get(0).getStatement(),
				is(equalToExpression(
						new NotElementOf(new Variable("x"), new SetComplement("X"))
				)));
	}

	/**
	 * Given:
	 *      x ∉ X
	 *
	 * Infer:
	 *      x ∈ ~X
	 */
	@Test
	public void testNotInSet() {
		TruthEnvironment environment = new NestedTruthEnvironment(
				new NotElementOf("x", "X"));
		List<ProofStatement> inferences = complement.getInferences(environment);

		assertThat("Only one inference should be made", inferences.size(), is(1));
		assertThat("(x not in X) implies (x in ~X)",
				inferences.get(0).getStatement(),
				is(equalToExpression(
						new ElementOf(new Variable("x"), new SetComplement("X")))
				));
	}

	/**
	 * Given:
	 *      x ∈ ~X
	 *
	 * Infer:
	 *      x ∉ X
	 */
	@Test
	public void testInComplement() {
		TruthEnvironment environment = new NestedTruthEnvironment(
				new ElementOf(new Variable("x"), new SetComplement("X")));
		List<ProofStatement> inferences = complement.getInferences(environment);

		assertThat("Only one inference should be made", inferences.size(), is(1));
		assertThat("(x in ~X) implies (x not in X)",
				inferences.get(0).getStatement(),
				is(equalToExpression(new NotElementOf("x", "X"))));
	}

	/**
	 * Given:
	 *      x ∉ ~X
	 *
	 * Infer:
	 *      x ∈ X
	 */
	@Test
	public void testNotInComplement() {
		TruthEnvironment environment = new NestedTruthEnvironment(
				new NotElementOf(new Variable("x"), new SetComplement("X")));
		List<ProofStatement> inferences = complement.getInferences(environment);

		assertThat("Only one inference should be made", inferences.size(), is(1));
		assertThat("(x not in ~X) implies (x in X)",
				inferences.get(0).getStatement(),
				is(equalToExpression(new ElementOf("x", "X"))));
	}

	private SetComplementInference complement;
}

package com.discretion.solver.inference;

import com.discretion.expression.SetComplement;
import com.discretion.expression.SetDifference;
import com.discretion.proof.ProofStatement;
import com.discretion.solver.environment.NestedTruthEnvironment;
import com.discretion.solver.environment.TruthEnvironment;
import com.discretion.statement.Conjunction;
import com.discretion.statement.ElementOf;
import com.discretion.statement.NotElementOf;
import com.discretion.statement.Variable;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.discretion.EqualExpression.equalToExpression;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SetDifferenceTest {

	@Before
	public void setUpTest() {
		difference = new SetDifferenceInference();
	}

	/**
	 * Given:
	 *      x ∈ A - B
	 *
	 * Infer:
	 *      x ∈ A ∧ x ∉ B
	 */
	@Test
	public void testInDifference() {
		TruthEnvironment environment = new NestedTruthEnvironment(
				new ElementOf(new Variable("x"), new SetDifference("A", "B")));
		List<ProofStatement> inferences = difference.getInferences(environment);

		assertThat("Only one inference should be made", inferences.size(), is(1));
		assertThat("(x not in ~X) implies (x in X)",
				inferences.get(0).getStatement(),
				is(equalToExpression(
						new Conjunction(
								new ElementOf("x", "A"),
								new NotElementOf("x", "B")
						)
				)));
	}

	private SetDifferenceInference difference;
}

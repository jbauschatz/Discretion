package com.discretion.solver.inference;

import com.discretion.statement.Variable;
import com.discretion.expression.SetUnion;
import com.discretion.proof.ProofStatement;
import com.discretion.solver.environment.NestedTruthEnvironment;
import com.discretion.solver.environment.TruthEnvironment;
import com.discretion.statement.ElementOf;
import com.discretion.statement.SubsetOf;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.discretion.EqualExpression.equalToExpression;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ElementOfSupersetTest {

	/**
	 * Prepare the object under test
	 */
	@Before
	public void setUpTest() {
		elementOfSuperset = new ElementOfSuperset();
	}

	/**
	 * Test simple subset case with named sets
	 *
	 * Given:
	 *      {x ∈ X, X ⊆ Y}
	 *
	 * Infer:
	 *      x ∈ Y
	 */
	@Test
	public void testInference() {
		TruthEnvironment environment = new NestedTruthEnvironment(
				new ElementOf("x", "X"),
				new SubsetOf("X", "Y")
		);
		List<ProofStatement> inferences = elementOfSuperset.getInferences(environment);

		assertThat("Only one inference should be made", inferences.size(), is(1));
		assertThat("Should infer that x is in Y",
				inferences.get(0).getStatement(),
				is(equalToExpression(new ElementOf("x", "Y"))));
	}

	/**
	 * Test that more complicated sets can be used	 *
	 *
	 * Given:
	 *      {x ∈ (X ∪ Y), (X ∪ Y) ⊆ (A ∪ B)}
	 *
	 * Infer:
	 *      x ∈ (A ∪ B)
	 */
	@Test
	public void testComplexSets() {
		TruthEnvironment environment = new NestedTruthEnvironment(
				new SubsetOf(
						new SetUnion("X", "Y"),
						new SetUnion("A", "B")),
				new ElementOf(
						new Variable("x"),
						new SetUnion("X", "Y"))
		);
		List<ProofStatement> inferences = elementOfSuperset.getInferences(environment);

		assertThat("Only one inference should be made", inferences.size(), is(1));
		assertThat("Should infer that x is in A union B",
				inferences.get(0).getStatement(),
				is(equalToExpression(
						new ElementOf(new Variable("x"), new SetUnion("A", "B")))
				));
	}

	private ElementOfSuperset elementOfSuperset;
}

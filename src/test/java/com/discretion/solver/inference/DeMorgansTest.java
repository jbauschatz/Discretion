package com.discretion.solver.inference;

import com.discretion.PrettyPrinter;
import com.discretion.Variable;
import com.discretion.proof.ProofStatement;
import com.discretion.solver.environment.NestedTruthEnvironment;
import com.discretion.solver.environment.TruthEnvironment;
import com.discretion.statement.Conjunction;
import com.discretion.statement.Disjunction;
import com.discretion.statement.Negation;
import com.discretion.statement.Statement;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.discretion.EqualExpression.equalToExpression;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 *
 */
public class DeMorgansTest {

	@Before
	public void setUpTest() {
		deMorgan = new DeMorgansLaw();
	}

	/**
	 * Tests that:
	 * (p and q) implies ~(~p or ~q)
	 */
	@Test
	public void testPAndQ() {
		TruthEnvironment environment = new NestedTruthEnvironment(
				new Conjunction(new Variable("p"), new Variable("q")));
		List<ProofStatement> inferences = deMorgan.getInferences(environment);

		assertThat("Only one inference should be made", inferences.size(), is(1));

		Statement expected = new Negation(new Disjunction(new Negation(new Variable("p")), new Negation(new Variable("q"))));
		assertThat("(p and q) implies ~(~p or ~q)",
				inferences.get(0).getStatement(),
				equalToExpression(expected));
	}

	/**
	 * Tests that:
	 * (~p and ~q) implies ~(p or q)
	 *
	 * note that double-negatives are simplified
	 */
	@Test
	public void testInnerNegations() {
		TruthEnvironment environment = new NestedTruthEnvironment(
				new Conjunction(new Negation(new Variable("p")), new Negation(new Variable("q"))));
		List<ProofStatement> inferences = deMorgan.getInferences(environment);

		assertThat("Only one inference should be made", inferences.size(), is(1));

		Statement expected = new Negation(new Disjunction(new Variable("p"), new Variable("q")));
		assertThat("(~p and ~q) implies ~(p or q)",
				inferences.get(0).getStatement(),
				equalToExpression(expected));
	}

	/**
	 * Tests that:
	 * ~(p and q) implies (~p or ~q)
	 *
	 * note that the double-negative is simplified
	 */
	@Test
	public void testOuterNegation() {
		TruthEnvironment environment = new NestedTruthEnvironment(
				new Negation(new Conjunction(new Variable("p"), new Variable("q"))));
		List<ProofStatement> inferences = deMorgan.getInferences(environment);

		assertThat("Only one inference should be made", inferences.size(), is(1));

		Statement expected = new Disjunction(new Negation(new Variable("p")), new Negation(new Variable("q")));
		assertThat("(~p and ~q) implies ~(p or q)",
				inferences.get(0).getStatement(),
				equalToExpression(expected));
	}

	private DeMorgansLaw deMorgan;
}

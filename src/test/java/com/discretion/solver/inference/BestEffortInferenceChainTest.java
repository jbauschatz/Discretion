package com.discretion.solver.inference;

import com.discretion.statement.Variable;
import com.discretion.expression.SetUnion;
import com.discretion.proof.ProofItem;
import com.discretion.proof.ProofStatement;
import com.discretion.solver.environment.NestedTruthEnvironment;
import com.discretion.solver.environment.TruthEnvironment;
import com.discretion.statement.Disjunction;
import com.discretion.statement.ElementOf;
import com.discretion.statement.Statement;
import com.discretion.statement.SubsetOf;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.discretion.EqualExpression.equalToExpression;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BestEffortInferenceChainTest {

	@Before
	public void setUpTest() {
		chainProducer = new BestEffortInferenceChain();
	}

	@Test
	public void testSimpleCase() {
		TruthEnvironment environment = new NestedTruthEnvironment(
				new SubsetOf(new Variable("X"), new Variable("Y")),
				new ElementOf(new Variable("a"), new Variable("X"))
		);
		Statement conclusion = new ElementOf(new Variable("a"), new Variable("Y"));
		List<ProofItem> chain = chainProducer.buildInferenceChain(conclusion, environment, 6);

		assertThat("Inference chain should include only 1 step", chain.size(), is(1));
		assertThat("Environment should not be polluted with extra statements", environment.getTruths().size(), is(2));
		assertThat("Inference chain should reach correct conclusion",
				((ProofStatement)chain.get(0)).getStatement(),
				equalToExpression(conclusion));
	}

	@Test
	public void testIrrelevantTruths() {
		// The environment has two irrelevant facts to "distract" from reaching the conclusion
		TruthEnvironment environment = new NestedTruthEnvironment(
				new SubsetOf(new Variable("X"), new Variable("Y")),
				new ElementOf(new Variable("a"), new Variable("X")),
				new ElementOf(new Variable("x"), new Variable("RedHerringSet")),
				new ElementOf(new Variable("redHerringElement"), new Variable("X"))
		);
		Statement conclusion = new ElementOf(new Variable("a"), new Variable("Y"));
		List<ProofItem> chain = chainProducer.buildInferenceChain(conclusion, environment, 6);

		assertThat("Inference chain should include only 1 step", chain.size(), is(1));
		assertThat("Environment should not be polluted with extra statements", environment.getTruths().size(), is(4));
		assertThat("Inference chain should reach correct conclusion",
				((ProofStatement)chain.get(0)).getStatement(),
				equalToExpression(conclusion));
	}

	/**
	 * Given:
	 *     x is in ((A union B) union C)
	 *
	 * Prove:
	 *     x is in (A union (B union C))
	 *
	 * The steps of this proof should be exactly as follows:
	 * 1. (x is in (A union B)) or (x is in C)        by definition of union
	 * 2. ((x is in A) or (x is in B)) or (x is in C) by definition of union
	 * 3. (x is in A) or ((x is in B) or (x is in C)) by associativity
	 * 4. (x is in A) or (x is in (B union C))        by definition of union
	 * 5. x is in (A union (B union C))               by definition of union
	 */
	@Test
	public void testAssociativeUnion() {
		// x in ((A union B) union C)
		Statement leftParenthesized = new ElementOf(new Variable("x"), new SetUnion(
				new SetUnion(new Variable("A"), new Variable("B")),
				new Variable("C")
		));
		// x in (A union (B union C))
		Statement rightParenthesized = new ElementOf(new Variable("x"), new SetUnion(
				new Variable("A"),
				new SetUnion(new Variable("B"), new Variable("C"))
		));
		TruthEnvironment environment = new NestedTruthEnvironment(leftParenthesized);
		List<ProofItem> chain = chainProducer.buildInferenceChain(rightParenthesized, environment, 6);

		assertThat("Inference chain should include 5 steps", chain.size(), is(5));
		ProofStatement step1 = (ProofStatement)chain.get(0);
		assertThat("Step 1 reason should be 'definition of union'", step1.getReason(), is("by the definition of union"));
		assertThat("Step 1 should be: (x is in (A union B)) or (x is in C)",
				step1.getStatement(),
				equalToExpression(new Disjunction(
						new ElementOf(new Variable("x"), new SetUnion(new Variable("A"), new Variable("B"))),
						new ElementOf(new Variable("x"), new Variable("C")))
				));

		ProofStatement step2 = (ProofStatement)chain.get(1);
		assertThat("Step 2 reason should be 'definition of union'", step2.getReason(), is("by the definition of union"));
		assertThat("Step 2 should be: ((x is in A) or (x is in B)) or (x is in C)",
				step2.getStatement(),
				equalToExpression(new Disjunction(
						new Disjunction(
								new ElementOf(new Variable("x"), new Variable("A")),
								new ElementOf(new Variable("x"), new Variable("B"))
						),
						new ElementOf(new Variable("x"), new Variable("C")))
				));

		ProofStatement step3 = (ProofStatement)chain.get(2);
		assertThat("Step 3 reason should be 'associativity'", step3.getReason(), is("by associativity"));
		assertThat("Step 3 should be: (x is in A) or ((x is in B) or (x is in C))",
				step3.getStatement(),
				equalToExpression(new Disjunction(
						new ElementOf(new Variable("x"), new Variable("A")),
						new Disjunction(
								new ElementOf(new Variable("x"), new Variable("B")),
								new ElementOf(new Variable("x"), new Variable("C"))
						))
				));

		ProofStatement step4 = (ProofStatement)chain.get(3);
		assertThat("Step 4 reason should be 'definition of union'", step2.getReason(), is("by the definition of union"));
		assertThat("Step 4 should be: (x is in A) or (x is in (B union C))",
				step4.getStatement(),
				equalToExpression(new Disjunction(
						new ElementOf(new Variable("x"), new Variable("A")),
						new ElementOf(new Variable("x"), new SetUnion(new Variable("B"), new Variable("C"))))
				));

		ProofStatement step5 = (ProofStatement)chain.get(4);
		assertThat("Step 5 reason should be 'definition of union'", step2.getReason(), is("by the definition of union"));
		assertThat("Step 5 should be: x is in (A union (B union C))",
				step5.getStatement(),
				equalToExpression(rightParenthesized));
	}

	BestEffortInferenceChain chainProducer;
}

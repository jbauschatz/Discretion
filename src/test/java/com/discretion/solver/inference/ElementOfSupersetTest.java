package com.discretion.solver.inference;

import com.discretion.Variable;
import com.discretion.expression.SetUnion;
import com.discretion.proof.ProofStatement;
import com.discretion.solver.environment.NestedTruthEnvironment;
import com.discretion.solver.environment.TruthEnvironment;
import com.discretion.statement.ElementOf;
import com.discretion.statement.SubsetOf;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ElementOfSupersetTest {

	@Before
	public void setUpTest() {
		elementOfSuperset = new ElementOfSuperset();
	}

	@Test
	public void testInference() {
		TruthEnvironment environment = new NestedTruthEnvironment(
				new ElementOf(new Variable("x"), new Variable("X")),
				new SubsetOf(new Variable("X"), new Variable("Y"))
		);
		List<ProofStatement> inferences = elementOfSuperset.getInferences(environment);

		assertThat("Only one inference should be made", inferences.size(), is(1));
		assertThat("Should infer that x is in Y",
				inferences.get(0).getStatement(),
				is(new ElementOf(new Variable("x"), new Variable("Y"))));
	}

	@Test
	public void testComplexSets() {
		TruthEnvironment environment = new NestedTruthEnvironment(
				new SubsetOf(
						new SetUnion(new Variable("X"), new Variable("Y")),
						new SetUnion(new Variable("A"), new Variable("B"))),
				new ElementOf(new Variable("x"), new SetUnion(new Variable("X"), new Variable("Y")))
		);
		List<ProofStatement> inferences = elementOfSuperset.getInferences(environment);

		assertThat("Only one inference should be made", inferences.size(), is(1));
		assertThat("Should infer that x is in A union B",
				inferences.get(0).getStatement(),
				is(new ElementOf(new Variable("x"), new SetUnion(new Variable("A"), new Variable("B")))));
	}

	private ElementOfSuperset elementOfSuperset;
}

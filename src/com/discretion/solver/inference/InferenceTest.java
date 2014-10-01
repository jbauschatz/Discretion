package com.discretion.solver.inference;

import com.discretion.PrettyPrinter;
import com.discretion.Variable;
import com.discretion.expression.SetUnion;
import com.discretion.proof.ProofStatement;
import com.discretion.solver.TruthEnvironment;
import com.discretion.statement.ElementOf;
import com.discretion.statement.Statement;
import com.discretion.statement.SubsetOf;
import junit.framework.Assert;
import org.junit.Test;

import java.util.List;

public class InferenceTest {
    @Test
    public void testElementOfSuperset() {
        TruthEnvironment environment = new TruthEnvironment();
        environment.addTruth(new SubsetOf(new Variable("X"), new Variable("Y")));
        environment.addTruth(new ElementOf(new Variable("x"), new Variable("X")));

        InferenceProducer infer = new ElementOfSuperset();

        // Knowing that X ⊆ Y and x ∈ X, we should infer that x ∈ Y
        List<ProofStatement> inferences = infer.getInferences(environment);
        Assert.assertEquals("Only one inference", inferences.size(), 1);

        Statement targetInference = new ElementOf(new Variable("x"), new Variable("Y"));
        Assert.assertTrue("Infer that x is in Y", targetInference.equals(inferences.get(0).getStatement()));

        environment.addTruth(targetInference);
        inferences = infer.getInferences(environment);
        Assert.assertEquals("No duplicate inference", 1, inferences.size());

        environment = new TruthEnvironment();
        environment.addTruth(new SubsetOf(new SetUnion(new Variable("X"), new Variable("Y"))
                , new SetUnion(new Variable("A"), new Variable("B"))));
        environment.addTruth(new ElementOf(new Variable("x"), new SetUnion(new Variable("X"), new Variable("Y"))));
        targetInference = new ElementOf(new Variable("x"), new SetUnion(new Variable("A"), new Variable("B")));
        inferences = infer.getInferences(environment);
        Assert.assertTrue("Complex sets", inferences.get(0).getStatement().equals(targetInference));
    }
}

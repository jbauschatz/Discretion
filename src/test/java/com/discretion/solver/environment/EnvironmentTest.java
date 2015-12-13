package com.discretion.solver.environment;

import com.discretion.Variable;
import com.discretion.statement.ElementOf;
import com.discretion.statement.Statement;
import com.discretion.statement.SubsetOf;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class EnvironmentTest {
    @Test
    public void testEnvironment() {
        Statement statementA = new ElementOf(new Variable("x"), new Variable("X"));
        Statement statementB = new SubsetOf(new Variable("X"), new Variable("Y"));

        List<Statement> statements = new LinkedList<>();
        statements.add(statementA);
        statements.add(statementB);
        TruthEnvironment environment = new NestedTruthEnvironment(statements);

        Assert.assertTrue("Contains added truth", environment.containsTruth(statementA));

        Statement equalsA = new ElementOf(new Variable("x"), new Variable("X"));
        Assert.assertTrue("Contains equal truth", environment.containsTruth(equalsA));

        List<Statement> allTruths = environment.getTruths();
        Assert.assertEquals("Retrieve truths", 2, allTruths.size());

        List<ElementOf> elements = environment.getTruths(ElementOf.class);
        Assert.assertEquals("Typed retrieval", 1, elements.size());

        List<SubsetOf> subsets = environment.getTruths(SubsetOf.class);
        Assert.assertEquals("Typed retrieval", 1, subsets.size());
    }
}

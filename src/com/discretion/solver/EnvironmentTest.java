package com.discretion.solver;

import com.discretion.Variable;
import com.discretion.statement.ElementOf;
import com.discretion.statement.Statement;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class EnvironmentTest {
    @Test
    public void testEnvironment() {
        TruthEnvironment environment = new TruthEnvironment();

        Statement statement = new ElementOf(new Variable("x"), new Variable("X"));
        Statement equalStatement = new ElementOf(new Variable("x"), new Variable("X"));

        environment.addTruth(statement);
        Assert.assertTrue("Contains added truth", environment.containsTruth(statement));
        Assert.assertTrue("Contains equal truth", environment.containsTruth(equalStatement));

        environment.addTruth(new ElementOf(new Variable("x"), new Variable("Y")));
        List<ElementOf> elements = (List<ElementOf>)environment.getTruths(ElementOf.class);
        Assert.assertEquals("Typed accessor methods", 2, elements.size());
    }
}

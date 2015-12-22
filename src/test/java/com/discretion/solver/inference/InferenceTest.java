package com.discretion.solver.inference;

import com.discretion.Variable;
import com.discretion.expression.SetUnion;
import com.discretion.proof.ProofItem;
import com.discretion.proof.ProofStatement;
import com.discretion.solver.environment.NestedTruthEnvironment;
import com.discretion.solver.environment.TruthEnvironment;
import com.discretion.statement.Disjunction;
import com.discretion.statement.ElementOf;
import com.discretion.statement.Statement;
import com.discretion.statement.SubsetOf;
import junit.framework.Assert;
import org.junit.Test;

import java.util.List;

public class InferenceTest {

    @Test
    public void testInferenceChains() {
        InferenceChainProducer chain = new BestEffortInferenceChain();
        TruthEnvironment environment = new NestedTruthEnvironment(
                new SubsetOf(new Variable("X"), new Variable("Y")),
                new ElementOf(new Variable("a"), new Variable("X"))
        );

        Statement conclusion = new ElementOf(new Variable("a"), new Variable("Y"));

        List<ProofItem> statements = chain.buildInferenceChain(conclusion, environment);

        Assert.assertEquals("Length of inference chain", 1, statements.size());
        Assert.assertEquals("Reaches conclusion", conclusion, ((ProofStatement)statements.get(0)).getStatement());

        Assert.assertEquals("Environment not polluted", 2, environment.getTruths().size());

        // These suppositions do nothing to help reach the conclusion
        environment = environment.getChildEnvironment(new ElementOf(new Variable("x"), new Variable("RedHerringSet")));
        environment = environment.getChildEnvironment(new ElementOf(new Variable("redHerringElement"), new Variable("X")));
        statements = chain.buildInferenceChain(conclusion, environment);

        Assert.assertEquals("Ignores irrelevent facts", 1, statements.size());
        Assert.assertEquals("Reaches conclusion", conclusion, ((ProofStatement)statements.get(0)).getStatement());
        Assert.assertEquals("Environment not polluted", 4, environment.getTruths().size());

        // x ∈ (A ∪ B) ∪ C
        Statement leftParenthesized = new ElementOf(new Variable("x"), new SetUnion(
                new SetUnion(new Variable("A"), new Variable("B")),
                new Variable("C")
        ));
        // x ∈ A ∪ (B ∪ C)
        Statement rightParenthesized = new ElementOf(new Variable("x"), new SetUnion(
                new Variable("A"),
                new SetUnion(new Variable("B"), new Variable("C"))
        ));

        // Assuming Left, we should conclude Right
        environment = new NestedTruthEnvironment(leftParenthesized);

        conclusion = new Disjunction(
                new ElementOf(new Variable("x"), new SetUnion(new Variable("A"), new Variable("B"))),
                new ElementOf("x", "C")
        );
        statements = chain.buildInferenceChain(conclusion, environment);
        Assert.assertEquals("Found an easy conclusion", 1, statements.size());

        conclusion = new Disjunction(
                new Disjunction(new ElementOf("x", "A"), new ElementOf("x", "B")),
                new ElementOf("x", "C")
        );
        statements = chain.buildInferenceChain(conclusion, environment);
        Assert.assertEquals("Found a harder conclusion", 2, statements.size());

        conclusion = new Disjunction(
                new ElementOf("x", "A"),
                new Disjunction(new ElementOf("x", "B"), new ElementOf("x", "C"))
        );
        statements = chain.buildInferenceChain(conclusion, environment);
        Assert.assertEquals("Found an even harder conclusion", 3, statements.size());

        conclusion = new Disjunction(
                new ElementOf("x", "A"),
                new ElementOf(new Variable("x"), new SetUnion(new Variable("B"), new Variable("C")))
        );
        statements = chain.buildInferenceChain(conclusion, environment);
        Assert.assertEquals("Found an almost useful conclusion", 4, statements.size());

        statements = chain.buildInferenceChain(rightParenthesized, environment);
        Assert.assertEquals("Completed a proof", 5, statements.size());
    }
}

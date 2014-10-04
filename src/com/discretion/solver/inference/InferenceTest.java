package com.discretion.solver.inference;

import com.discretion.PrettyPrinter;
import com.discretion.Variable;
import com.discretion.expression.SetUnion;
import com.discretion.proof.ProofItem;
import com.discretion.proof.ProofStatement;
import com.discretion.solver.TruthEnvironment;
import com.discretion.statement.*;
import junit.framework.Assert;
import org.junit.Test;

import java.util.List;

public class InferenceTest {

    @Test
    public void testDeMorgans() {
        DeMorgansLaw morgan = new DeMorgansLaw();

        TruthEnvironment environment = new TruthEnvironment();
        environment.addTruth(new Conjunction(new Variable("p"), new Variable("q")));
        List<ProofStatement> inferences = morgan.getInferences(environment);
        Statement inference = new Negation(new Disjunction(new Negation(new Variable("p")), new Negation(new Variable("q"))));
        assertStatementsEqual("p and q -> ~(~p or ~q)",
                inference,
                inferences.get(0).getStatement());

        environment = new TruthEnvironment();
        environment.addTruth(new Conjunction(new Negation(new Variable("p")), new Negation(new Variable("q"))));
        inferences = morgan.getInferences(environment);
        inference = new Negation(new Disjunction(new Variable("p"), new Variable("q")));
        assertStatementsEqual("~p and ~q -> ~(p or q)...shortcut inner double negative",
                inference,
                inferences.get(0).getStatement());

        environment = new TruthEnvironment();
        environment.addTruth(new Disjunction(new Negation(new Variable("p")), new Negation(new Variable("q"))));
        inferences = morgan.getInferences(environment);
        inference = new Negation(new Conjunction(new Variable("p"), new Variable("q")));
        assertStatementsEqual("~p or ~q -> ~(p and q)...shortcut inner double negative",
                inference,
                inferences.get(0).getStatement());

        environment = new TruthEnvironment();
        environment.addTruth(new Negation(new Disjunction(new Negation(new Variable("p")), new Negation(new Variable("q")))));
        inferences = morgan.getInferences(environment);
        inference = new Conjunction(new Variable("p"), new Variable("q"));
        assertStatementsEqual("~(~p or ~q) -> p and q...shortcut outer double negative",
                inference,
                inferences.get(0).getStatement());

        environment = new TruthEnvironment();
        environment.addTruth(new Negation(new Conjunction(new Negation(new Variable("p")), new Negation(new Variable("q")))));
        inferences = morgan.getInferences(environment);
        inference = new Disjunction(new Variable("p"), new Variable("q"));
        assertStatementsEqual("~(~p and ~q) -> p or q...shortcut outer double negative",
                inference,
                inferences.get(0).getStatement());
    }

    @Test
    public void testInferenceChains() {
        InferenceChainProducer chain = new BestEffortInferenceChain();
        TruthEnvironment environment = new TruthEnvironment();
        environment.addTruth(new SubsetOf(new Variable("X"), new Variable("Y")));
        environment.addTruth(new ElementOf(new Variable("a"), new Variable("X")));

        Statement conclusion = new ElementOf(new Variable("a"), new Variable("Y"));

        List<ProofItem> statements = chain.buildInferenceChain(conclusion, environment);

        Assert.assertEquals("Length of inference chain", 1, statements.size());
        Assert.assertEquals("Reaches conclusion", conclusion, ((ProofStatement)statements.get(0)).getStatement());

        Assert.assertEquals("Environment not polluted", 2, environment.getTruths().size());

        // These suppositions do nothing to help reach the conclusion
        environment.addTruth(new ElementOf(new Variable("x"), new Variable("RedHerringSet")));
        environment.addTruth(new ElementOf(new Variable("redHerringElement"), new Variable("X")));
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
        environment = new TruthEnvironment();
        environment.addTruth(leftParenthesized);

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

    private void assertStatementsEqual(String message, Statement statementA, Statement statementB) {
        PrettyPrinter printer = new PrettyPrinter();
        Assert.assertEquals(message, printer.prettyString(statementA), printer.prettyString(statementB));
    }
}

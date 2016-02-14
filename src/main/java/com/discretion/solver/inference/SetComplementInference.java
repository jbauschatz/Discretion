package com.discretion.solver.inference;

import com.discretion.AbstractMathObjectVisitor;
import com.discretion.MathObject;
import com.discretion.expression.SetComplement;
import com.discretion.proof.ProofStatement;
import com.discretion.solver.Replacer;
import com.discretion.solver.environment.TruthEnvironment;
import com.discretion.statement.ElementOf;
import com.discretion.statement.Negation;
import com.discretion.statement.NotElementOf;
import com.discretion.statement.Statement;

import java.util.LinkedList;
import java.util.List;

public class SetComplementInference extends AbstractMathObjectVisitor implements InferenceRule {
    @Override
    public List<ProofStatement> getInferences(TruthEnvironment environment) {
        inferences = new LinkedList<>();

        for (Statement object : environment.getTruths()) {
            originalStatement = object;
            traverse(object);
        }

        return inferences;
    }

    public SetComplementInference() {
        replacer = new Replacer();
    }

    @Override
    protected void handle(ElementOf elementOf) {
        if (elementOf.isNegative()) {
			MathObject set = elementOf.getSet();
			MathObject complemented = (set instanceof SetComplement) ?
					((SetComplement)set).getSet()
					: new SetComplement(set);

			ElementOf elementOfComplement = new ElementOf(elementOf.getElement(), complemented);
			Statement replaced = (Statement)replacer.substitute(originalStatement, elementOf, elementOfComplement);

			inferences.add(new ProofStatement(replaced, "by the definition of set complement"));
		} else {
			MathObject set = elementOf.getSet();
			MathObject complemented = (set instanceof SetComplement) ?
					((SetComplement) set).getSet()
					: new SetComplement(set);

			NotElementOf notElementOf = new NotElementOf(elementOf.getElement(), complemented);
			Statement replaced = (Statement) replacer.substitute(originalStatement, elementOf, notElementOf);

			inferences.add(new ProofStatement(replaced, "by the definition of set complement"));
		}
    }

    private Statement originalStatement;
    private LinkedList<ProofStatement> inferences;
    private Replacer replacer;
}

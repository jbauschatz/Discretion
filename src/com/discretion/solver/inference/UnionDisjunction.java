package com.discretion.solver.inference;

import com.discretion.expression.SetUnion;
import com.discretion.proof.ProofStatement;
import com.discretion.solver.TruthEnvironment;
import com.discretion.statement.Disjunction;
import com.discretion.statement.ElementOf;

import java.util.LinkedList;
import java.util.List;

public class UnionDisjunction implements InferenceProducer {
    @Override
    public List<ProofStatement> getInferences(TruthEnvironment environment) {
        List<ProofStatement> inferences = new LinkedList<>();

        // Apply the definition analytically - break the union into a disjunction
        List<ElementOf> elementOfs = (List<ElementOf>)environment.getTruths(ElementOf.class);
        for (ElementOf elementOf : elementOfs) {
            if (elementOf.getSet() instanceof SetUnion) {
                SetUnion union = (SetUnion)elementOf.getSet();
                inferences.add(new ProofStatement(
                    new Disjunction(
                            new ElementOf(elementOf.getElement(), union.getLeft()),
                            new ElementOf(elementOf.getElement(), union.getRight())),
                    "by the definition of union"
                ));
            }
        }

        // TODO - apply the definition synthetically - create a union from a disjunction

        return inferences;
    }
}

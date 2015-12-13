package com.discretion.solver.inference;

import com.discretion.MathObject;
import com.discretion.proof.ProofStatement;
import com.discretion.solver.environment.TruthEnvironment;
import com.discretion.statement.ElementOf;
import com.discretion.statement.SubsetOf;

import java.util.LinkedList;
import java.util.List;

/**
 * Infers that an element of any set is also an element of that set's Superset
 *
 * x elementOf X and X subsetOf Y
 * implies x elementOf Y
 */
public class ElementOfSuperset implements InferenceProducer {
    public List<ProofStatement> getInferences(TruthEnvironment environment) {
        List<ProofStatement> inferences = new LinkedList<>();

        // Find any truths of the form "x elementOf X"
        List<ElementOf> elementOfs = environment.getTruths(ElementOf.class);
        for (ElementOf elementOf : elementOfs) {
            MathObject set = elementOf.getSet();
            // Find any subset truths whose subset matches this set
            List<SubsetOf> subsetOfs = environment.getTruths(SubsetOf.class);
            for (SubsetOf subsetOf : subsetOfs) {
                if (subsetOf.getSubset().equals(set)) {
                    // We found a matching subset statement and can make an inference
                    inferences.add(new ProofStatement(
                            new ElementOf(elementOf.getElement(), subsetOf.getSet()),
                            "by the definition of subset")
                    );
                }
            }
        }

        return inferences;
    }
}

package com.discretion.solver.structure;

import com.discretion.MathObject;
import com.discretion.Variable;
import com.discretion.proof.Proof;
import com.discretion.proof.ProofItem;
import com.discretion.proof.UnknownSteps;
import com.discretion.solver.structure.ProofStructureProducer;
import com.discretion.statement.ElementOf;
import com.discretion.statement.Statement;
import com.discretion.statement.SubsetOf;

import java.util.LinkedList;
import java.util.List;

public class SubsetStructure implements ProofStructureProducer {
    public boolean applies(Statement statement) {
        return statement instanceof SubsetOf;
    }

    public List<ProofItem> produceStructure(Statement statement) {
        SubsetOf subsetOf = (SubsetOf)statement;
        MathObject subset = subsetOf.getSubset();
        MathObject set = subsetOf.getSet();

        Variable element;
        if (subset instanceof Variable) {
            // Match the variable name to the set name for readability
            element = new Variable(((Variable)subset).getName().toLowerCase());
        } else {
            // TODO - find a free variable name in our current proof-context
            element = new Variable("x");
        }
        Proof elementProof = new Proof();
        elementProof.getSuppositions().add(new ElementOf(element, subset));
        elementProof.getProofItems().add(new UnknownSteps());
        elementProof.setConclusion(new ElementOf(element, set));

        List<ProofItem> structure = new LinkedList<>();
        structure.add(elementProof);
        return structure;
    }
}

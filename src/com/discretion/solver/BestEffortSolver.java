package com.discretion.solver;

import com.discretion.proof.Proof;
import com.discretion.proof.ProofItem;
import com.discretion.proof.ProofStatement;
import com.discretion.solver.environment.NestedTruthEnvironment;
import com.discretion.solver.environment.TruthEnvironment;
import com.discretion.solver.inference.*;
import com.discretion.solver.structure.ProofStructureProducer;
import com.discretion.solver.structure.SetEqualityStructure;
import com.discretion.solver.structure.SubsetStructure;
import com.discretion.statement.Statement;

import java.util.LinkedList;
import java.util.List;

public class BestEffortSolver implements Solver {
    public Proof solve(Statement conclusion, List<Statement> given) {
        TruthEnvironment environment = new NestedTruthEnvironment(given);

        List<ProofItem> statements = getStructure(conclusion, environment);

        return new Proof(given, statements, conclusion);
    }

    public BestEffortSolver() {
        structures = new LinkedList<>();
        structures.add(new SetEqualityStructure());
        structures.add(new SubsetStructure());

        inference = new BestEffortInferenceChain();
    }

    private List<ProofItem> getStructure(Statement conclusion, TruthEnvironment environment) {
        for (ProofStructureProducer structure : structures) {
            if (structure.applies(conclusion)) {
                List<ProofItem> statements = structure.produceStructure(conclusion);

                // Scan steps for sub-proofs, which may be partial and need fleshing out
                for (ProofItem item : statements) {
                    if (item instanceof Proof) {
                        // Try to recursively solve this sub-proof
                        Proof subproof = (Proof)item;

                        TruthEnvironment subroofEnvironment = environment.getChildEnvironment(subproof.getSuppositions());
                        List<ProofItem> substructure = getStructure(subproof.getConclusion().getStatement(), subroofEnvironment);

                        // if the substructure reaches the conclusion, cut that out of the proof body (it is already in the conclusion)
                        if (!substructure.isEmpty() && substructure.get(substructure.size()-1) instanceof ProofStatement) {
                            ProofStatement lastSubStatement = (ProofStatement)substructure.get(substructure.size()-1);
                            if (lastSubStatement.getStatement().equals(subproof.getConclusion().getStatement())) {
                                subproof.getConclusion().setReason(lastSubStatement.getReason());
                                substructure.remove(substructure.size()-1);
                            }
                        }

                        subproof.setProofItems(substructure);
                    }
                }

                return statements;
            }
        }

        // No further structure could be imposed on this problem, so we must build a chain of inferences
        List<ProofItem> statements = inference.buildInferenceChain(conclusion, environment);

        return statements;
    }

    private List<ProofStructureProducer> structures;
    private InferenceChainProducer inference;
}

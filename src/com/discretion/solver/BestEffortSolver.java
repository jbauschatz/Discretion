package com.discretion.solver;

import com.discretion.proof.Proof;
import com.discretion.proof.ProofItem;
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

        return getStructure(given, conclusion, environment);
    }

    public BestEffortSolver() {
        structures = new LinkedList<>();
        structures.add(new SetEqualityStructure());
        structures.add(new SubsetStructure());

        inference = new BestEffortInferenceChain();
    }

    private Proof getStructure(List<Statement> given, Statement conclusion, TruthEnvironment environment) {
        for (ProofStructureProducer structure : structures) {
            if (structure.applies(conclusion)) {
                Proof proof = structure.produceStructure(given, conclusion);

                // Scan steps for sub-proofs, which may be partial and need fleshing out
                for (ProofItem item : proof.getProofItems()) {
                    if (item instanceof Proof) {
                        // Try to recursively solve this sub-proof
                        Proof subproof = (Proof)item;

                        TruthEnvironment subproofEnvironment = environment.getChildEnvironment(subproof.getSuppositions());
                        Proof fleshedOutSubproof = getStructure(subproof.getSuppositions(), subproof.getConclusion().getStatement(), subproofEnvironment);
						subproof.setProofItems(fleshedOutSubproof.getProofItems());
					}
				}

				return proof;
            }
        }

        // No further structure could be imposed on this problem, so we must build a chain of inferences
        List<ProofItem> statements = inference.buildInferenceChain(conclusion, environment);
		return new Proof(given, statements, conclusion);
    }

    private List<ProofStructureProducer> structures;
    private InferenceChainProducer inference;
}

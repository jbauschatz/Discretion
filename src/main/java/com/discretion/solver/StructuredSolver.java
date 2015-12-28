package com.discretion.solver;

import com.discretion.proof.Proof;
import com.discretion.proof.ProofItem;
import com.discretion.proof.UnknownSteps;
import com.discretion.solver.environment.NestedTruthEnvironment;
import com.discretion.solver.environment.TruthEnvironment;
import com.discretion.solver.structure.ProofStructureProducer;
import com.discretion.solver.structure.SetEqualityStructure;
import com.discretion.solver.structure.SubsetStructure;
import com.discretion.statement.Statement;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class StructuredSolver implements Solver {
    public Proof solve(Statement conclusion, List<Statement> given) {
        TruthEnvironment environment = new NestedTruthEnvironment(given);

        return getStructure(given, conclusion, environment);
    }

    public StructuredSolver() {
        structures = new LinkedList<>();
		structures.add(new SetEqualityStructure());
        structures.add(new SubsetStructure());
    }

	protected abstract Proof fleshOutProof(List<Statement> given, Statement conclusion, TruthEnvironment environment);

    private Proof getStructure(List<Statement> given, Statement conclusion, TruthEnvironment environment) {
        for (ProofStructureProducer structure : structures) {
            if (structure.applies(conclusion)) {
                Proof proof = structure.produceStructure(given, conclusion, environment);

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

		return fleshOutProof(given, conclusion, environment);
    }

    private List<ProofStructureProducer> structures;
}

package com.discretion.solver;

import com.discretion.problem.Problem;
import com.discretion.proof.Proof;
import com.discretion.proof.ProofItem;
import com.discretion.solver.environment.NestedTruthEnvironment;
import com.discretion.solver.environment.TruthEnvironment;
import com.discretion.solver.structure.ProofStructureProducer;
import com.discretion.solver.structure.SetEqualityStructure;
import com.discretion.solver.structure.SubsetStructure;
import com.discretion.statement.Statement;

import java.util.LinkedList;
import java.util.List;

public abstract class StructuredSolver implements Solver {
    public Proof solve(Problem problem, int maxInferenceDepth) {
        TruthEnvironment environment = new NestedTruthEnvironment(problem.getGiven());
		Proof proof = new Proof(problem.getGiven(), new LinkedList<>(), problem.getConclusion());

        Proof structured = structureProof(proof, environment, maxInferenceDepth);
		structured.setTitle(problem.getTitle());
		return structured;
    }

    public StructuredSolver() {
        structures = new LinkedList<>();
		structures.add(new SetEqualityStructure());
        structures.add(new SubsetStructure());
    }

	/**
	 * Given a Proof (produced by the structuring step), produce a Proof with the same
	 * assumptions and conclusions but with its internals fleshed out
	 */
	protected abstract Proof fleshOutProof(Proof proof, TruthEnvironment environment, int maxInferenceDepth);

	/**
	 * Given a Proof, creates a structured version of the proof.
	 *
	 * This Proof will be populated with any of the required subproofs,
	 * but it is up to the child class to flesh out the internals of all proofs
	 */
    protected Proof structureProof(Proof proof, TruthEnvironment environment, int maxInferenceDepth) {
		Statement conclusion = proof.getConclusion().getStatement();

        for (ProofStructureProducer structure : structures) {
            if (structure.applies(conclusion)) {
                Proof structured = structure.produceStructure(proof.getSuppositions(), conclusion, environment);

                // Scan steps for sub-proofs, which may be partial and need fleshing out
                for (ProofItem item : structured.getProofItems()) {
                    if (item instanceof Proof) {
                        // Try to recursively solve this sub-proof
                        Proof subproof = (Proof)item;

                        TruthEnvironment subproofEnvironment = environment.getChildEnvironment(subproof.getSuppositions());
                        Proof fleshedOutSubproof = structureProof(subproof, subproofEnvironment, maxInferenceDepth);
						subproof.setProofItems(fleshedOutSubproof.getProofItems());
						subproof.setConclusion(fleshedOutSubproof.getConclusion());
					}
				}

				return structured;
            }
        }

		return fleshOutProof(proof, environment, maxInferenceDepth);
    }

    private List<ProofStructureProducer> structures;
}

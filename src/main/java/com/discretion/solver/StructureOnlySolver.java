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

public class StructureOnlySolver extends StructuredSolver {

	@Override
	protected Proof fleshOutProof(Proof proof, TruthEnvironment environment, int maxInferenceDepth) {
		return proof;
	}
}

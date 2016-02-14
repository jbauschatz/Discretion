package com.discretion.solver;

/**
 *
 */
public class SolverFactory {

	public static Solver getSolver(String name) {
		switch (name.toLowerCase()) {
			case "besteffort":
				return new BestEffortSolver();
			case "structureonly":
				return new StructureOnlySolver();
			default:
				throw new IllegalArgumentException("Could not find a Solver by the name: " + name);
		}
	}
}

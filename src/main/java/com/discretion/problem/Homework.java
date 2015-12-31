package com.discretion.problem;

import com.discretion.Variable;
import com.discretion.expression.SetComplement;
import com.discretion.expression.SetDifference;
import com.discretion.expression.SetIntersection;
import com.discretion.expression.SetUnion;
import com.discretion.statement.Equality;
import com.discretion.statement.SubsetOf;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Homework {

	/**
	 * Problems from section 4.2 of Discrete Mathematics and Functional Programming
	 */
	public static final ProblemSet DMFP_4_2 = new ProblemSet("DMFP-4.2", new LinkedList<>());

	/**
	 * Problems from section 4.3 of Discrete Mathematics and Functional Programming
	 */
	public static final ProblemSet DMFP_4_3 = new ProblemSet("DMFP-4.3", new LinkedList<>());

	public static final List<ProblemSet> ALL_PROBLEM_SETS = Arrays.asList(
			DMFP_4_2,
			DMFP_4_3
	);

	public static final Variable UNIVERSAL_SET = new Variable("U");
	public static final Variable EMPTY_SET = new Variable("Ø");
	public static final Variable SET_A = new Variable("A");
	public static final Variable SET_B = new Variable("B");
	public static final Variable SET_C = new Variable("C");

	static {
		// 4.2 Problems
		Problem setInUnion = new Problem(
				"4.2.1",
				new SubsetOf(SET_A, new SetUnion(SET_A, SET_B)),
				Arrays.asList(new SubsetOf(SET_A, UNIVERSAL_SET), new SubsetOf(SET_B, UNIVERSAL_SET))
		);
		DMFP_4_2.getProblems().add(setInUnion);

		Problem setDifference = new Problem(
				"4.2.2",
				new SubsetOf(new SetDifference(SET_A, SET_B), new SetComplement(SET_B)),
				Arrays.asList(new SubsetOf(SET_A, UNIVERSAL_SET), new SubsetOf(SET_B, UNIVERSAL_SET))
		);
		DMFP_4_2.getProblems().add(setDifference);

		Problem intersectionOfComplement = new Problem(
				"4.2.3",
				new SubsetOf(
						new SetIntersection(SET_A, new SetComplement(SET_B)),
						new SetDifference(SET_A, SET_B)),
				Arrays.asList(new SubsetOf(SET_A, UNIVERSAL_SET), new SubsetOf(SET_B, UNIVERSAL_SET))
		);
		DMFP_4_2.getProblems().add(intersectionOfComplement);

		Problem deMorganForSets = new Problem(
				"4.2.4",
				new SubsetOf(
						new SetComplement(new SetUnion(SET_A, SET_B)),
						new SetIntersection(new SetComplement(SET_A), new SetComplement(SET_B))),
				Arrays.asList(new SubsetOf(SET_A, UNIVERSAL_SET), new SubsetOf(SET_B, UNIVERSAL_SET))
		);
		DMFP_4_2.getProblems().add(deMorganForSets);

		Problem distributeUnionSubset = new Problem(
				"4.2.5",
				new SubsetOf(
						new SetUnion(SET_A, new SetIntersection(SET_B, SET_C)),
						new SetIntersection(new SetUnion(SET_A, SET_B), new SetUnion(SET_A, SET_C))),
				Arrays.asList(new SubsetOf(SET_A, UNIVERSAL_SET), new SubsetOf(SET_B, UNIVERSAL_SET), new SubsetOf(SET_C, UNIVERSAL_SET))
		);
		DMFP_4_2.getProblems().add(distributeUnionSubset);

		// TODO add the 4.2 Cartesian Product problems

		// 4.3 Problems - set equality
		Problem intersectEmptySet = new Problem(
				"4.3.1",
				new Equality(new SetUnion(SET_A, EMPTY_SET), SET_A),
				Arrays.asList(new SubsetOf(SET_A, UNIVERSAL_SET))
		);
		DMFP_4_3.getProblems().add(intersectEmptySet);

		Problem unionAndIntersection = new Problem(
				"4.3.2",
				new Equality(
						new SetUnion(SET_A, new SetIntersection(SET_A, SET_B)),
						SET_A),
				Arrays.asList(new SubsetOf(SET_A, UNIVERSAL_SET))
		);
		DMFP_4_3.getProblems().add(unionAndIntersection);

		// TODO 4.3.3 with Cartesian Product

		Problem distributeUnion = new Problem(
				"4.3.4",
				new Equality(
						new SetUnion(SET_A, new SetIntersection(SET_B, SET_C)),
						new SetIntersection(new SetUnion(SET_A, SET_B), new SetUnion(SET_A, SET_C))),
				Arrays.asList(new SubsetOf(SET_A, UNIVERSAL_SET), new SubsetOf(SET_B, UNIVERSAL_SET), new SubsetOf(SET_C, UNIVERSAL_SET))
		);
		DMFP_4_3.getProblems().add(distributeUnion);

		Problem universalSet = new Problem(
				"4.3.5",
				new Equality(
						new SetUnion(SET_A, new SetComplement(SET_A)),
						UNIVERSAL_SET),
				Arrays.asList(new SubsetOf(SET_A, UNIVERSAL_SET))
		);
		DMFP_4_3.getProblems().add(universalSet);

		Problem emptySet = new Problem(
				"4.3.6",
				new Equality(
						new SetDifference(SET_A, EMPTY_SET),
						SET_A),
				Arrays.asList(new SubsetOf(SET_A, UNIVERSAL_SET))
		);
		DMFP_4_3.getProblems().add(emptySet);

		// TODO 4.3.7 with Cartesian Product

		Problem associativeUnion = new Problem(
				"4.3.8",
				new Equality(
						new SetUnion(new SetUnion(SET_A, SET_B), SET_C),
						new SetUnion(SET_A, new SetUnion(SET_B, SET_C))),
				Arrays.asList(new SubsetOf(SET_A, UNIVERSAL_SET), new SubsetOf(SET_B, UNIVERSAL_SET), new SubsetOf(SET_C, UNIVERSAL_SET))
		);
		DMFP_4_3.getProblems().add(associativeUnion);

		Problem associativeIntersection = new Problem(
				"4.3.9",
				new Equality(
						new SetIntersection(new SetIntersection(SET_A, SET_B), SET_C),
						new SetIntersection(SET_A, new SetIntersection(SET_B, SET_C))),
				Arrays.asList(new SubsetOf(SET_A, UNIVERSAL_SET), new SubsetOf(SET_B, UNIVERSAL_SET), new SubsetOf(SET_C, UNIVERSAL_SET))
		);
		DMFP_4_3.getProblems().add(associativeIntersection);

		Problem doubleComplement = new Problem(
				"4.3.10",
				new Equality(
						new SetComplement(new SetComplement(SET_A)),
						SET_A),
				Arrays.asList(new SubsetOf(SET_A, UNIVERSAL_SET))
		);
		DMFP_4_3.getProblems().add(doubleComplement);

		Problem unionUniversal = new Problem(
				"4.3.11",
				new Equality(
						new SetUnion(SET_A, UNIVERSAL_SET),
						UNIVERSAL_SET),
				Arrays.asList(new SubsetOf(SET_A, UNIVERSAL_SET))
		);
		DMFP_4_3.getProblems().add(unionUniversal);

		Problem complementDiff = new Problem(
				"4.3.12",
				new Equality(
						new SetUnion(new SetComplement(SET_A), SET_B),
						new SetComplement(new SetDifference(SET_A, SET_B))),
				Arrays.asList(new SubsetOf(SET_A, UNIVERSAL_SET), new SubsetOf(SET_B, UNIVERSAL_SET))
		);
		DMFP_4_3.getProblems().add(complementDiff);

		Problem deMorganForSetsA = new Problem(
				"4.3.13",
				new Equality(
						new SetComplement(new SetUnion(SET_A, SET_B)),
						new SetIntersection(new SetComplement(SET_A), new SetComplement(SET_B))),
				Arrays.asList(new SubsetOf(SET_A, UNIVERSAL_SET), new SubsetOf(SET_B, UNIVERSAL_SET))
		);
		DMFP_4_3.getProblems().add(deMorganForSetsA);

		Problem deMorganForSetsB = new Problem(
				"4.3.14",
				new Equality(
						new SetComplement(new SetIntersection(SET_A, SET_B)),
						new SetUnion(new SetComplement(SET_A), new SetComplement(SET_B))),
				Arrays.asList(new SubsetOf(SET_A, UNIVERSAL_SET), new SubsetOf(SET_B, UNIVERSAL_SET))
		);
		DMFP_4_3.getProblems().add(deMorganForSetsB);

		Problem complicatedUnion = new Problem(
				"4.3.15",
				new Equality(
						new SetUnion(SET_A, SET_B),
						new SetUnion(SET_A, new SetDifference(SET_B, new SetIntersection(SET_A, SET_B)))),
				Arrays.asList(new SubsetOf(SET_A, UNIVERSAL_SET), new SubsetOf(SET_B, UNIVERSAL_SET))
		);
		DMFP_4_3.getProblems().add(complicatedUnion);

		Problem problem4_3_16 = new Problem(
				"4.3.16",
				new Equality(
						new SetDifference(new SetIntersection(SET_A, SET_C), new SetDifference(SET_C, SET_B)),
						new SetIntersection(SET_A, new SetIntersection(SET_B, SET_C))),
				Arrays.asList(new SubsetOf(SET_A, UNIVERSAL_SET), new SubsetOf(SET_B, UNIVERSAL_SET), new SubsetOf(SET_C, UNIVERSAL_SET))
		);
		DMFP_4_3.getProblems().add(problem4_3_16);

		Problem problem4_3_17 = new Problem(
				"4.3.17",
				new Equality(
						new SetIntersection(
								new SetUnion(SET_A, new SetDifference(SET_B, SET_C)),
								new SetComplement(SET_B)),
						new SetDifference(SET_A, SET_B)),
				Arrays.asList(new SubsetOf(SET_A, UNIVERSAL_SET), new SubsetOf(SET_B, UNIVERSAL_SET))
		);
		DMFP_4_3.getProblems().add(problem4_3_17);

		Problem problem4_3_18 = new Problem(
				"4.3.18",
				new Equality(
						new SetUnion(
								new SetDifference(SET_A, SET_B),
								new SetDifference(SET_C, SET_B)),
						new SetDifference(new SetUnion(SET_A, SET_C), SET_B)),
				Arrays.asList(new SubsetOf(SET_A, UNIVERSAL_SET), new SubsetOf(SET_B, UNIVERSAL_SET), new SubsetOf(SET_C, UNIVERSAL_SET))
		);
		DMFP_4_3.getProblems().add(problem4_3_18);

		Problem problem4_3_19 = new Problem(
				"4.3.19",
				new Equality(
						new SetIntersection(
								new SetDifference(SET_A, SET_B),
								new SetDifference(SET_C, SET_B)),
						new SetDifference(new SetIntersection(SET_A, SET_C), SET_B)),
				Arrays.asList(new SubsetOf(SET_A, UNIVERSAL_SET), new SubsetOf(SET_B, UNIVERSAL_SET), new SubsetOf(SET_C, UNIVERSAL_SET))
		);
		DMFP_4_3.getProblems().add(problem4_3_19);
	}
}

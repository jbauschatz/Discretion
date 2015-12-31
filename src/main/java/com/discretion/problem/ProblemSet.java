package com.discretion.problem;

import java.util.List;

public class ProblemSet {

	public String getTitle() {
		return title;
	}

	public List<Problem> getProblems() {
		return problems;
	}

	public ProblemSet(String title, List<Problem> problems) {
		this.title = title;
		this.problems = problems;
	}

	private String title;
	private List<Problem> problems;
}

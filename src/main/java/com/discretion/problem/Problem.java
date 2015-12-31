package com.discretion.problem;

import com.discretion.statement.Statement;

import java.util.List;

public class Problem {

	public String getTitle() {
		return title;
	}

    public Statement getConclusion() {
        return conclusion;
    }

    public void setConclusion(Statement conclusion) {
        this.conclusion = conclusion;
    }

    public List<Statement> getGiven() {
        return given;
    }

    public void setGiven(List<Statement> given) {
        this.given = given;
    }

    public Problem(String title, Statement conclusion, List<Statement> given) {
		this.title = title;
        this.conclusion = conclusion;
        this.given = given;
    }

	private String title;
    private List<Statement> given;
    private Statement conclusion;
}

package com.discretion.solver;

import com.discretion.statement.Statement;

import java.util.List;

public class Problem {

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

    public Problem(Statement conclusion, List<Statement> given) {
        this.conclusion = conclusion;
        this.given = given;
    }

    public Problem() {
    }

    private List<Statement> given;
    private Statement conclusion;
}

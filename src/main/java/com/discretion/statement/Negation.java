package com.discretion.statement;

import com.discretion.MathObject;
import com.discretion.MathObjectVisitor;

public class Negation extends Statement {

    public boolean equals(Object other) {
        return other instanceof Negation
                && term.equals(((Negation)other).term);
    }

    public MathObject getTerm() {
        return term;
    }

    public void setTerm(MathObject term) {
        this.term = term;
    }

    public void accept(MathObjectVisitor visitor) {
        visitor.visit(this);
    }

	@Override
	public MathObject negate() {
		return term;
	}

	@Override
	public boolean isNegative() {
		return true;
	}

    public Negation(MathObject term) {
        this.term = term;
    }

	public Negation(String term) {
		this.term = new Variable(term);
	}

    public Negation() {
    }

    private MathObject term;
}

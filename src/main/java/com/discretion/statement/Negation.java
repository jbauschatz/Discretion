package com.discretion.statement;

import com.discretion.MathObject;
import com.discretion.MathObjectVisitor;

public class Negation implements Statement {

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

    public Negation(MathObject term) {
        this.term = term;
    }

    public Negation() {
    }

    private MathObject term;
}

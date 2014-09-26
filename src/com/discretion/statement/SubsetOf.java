package com.discretion.statement;

import com.discretion.MathObject;
import com.discretion.MathObjectVisitor;

public class SubsetOf implements Statement {

    public final MathObject subset;
    public final MathObject set;

    public void accept(MathObjectVisitor visitor) {
        visitor.visit(this);
    }

    public SubsetOf(MathObject element, MathObject set) {
        this.subset = element;
        this.set = set;
    }
}

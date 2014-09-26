package com.discretion.statement;

import com.discretion.MathObject;
import com.discretion.MathObjectVisitor;

public class ElementOf implements Statement {

    public final MathObject element;
    public final MathObject set;

    public void accept(MathObjectVisitor visitor) {
        visitor.visit(this);
    }

    public ElementOf(MathObject element, MathObject set) {
        this.element = element;
        this.set = set;
    }
}

package com.discretion.expression;

import com.discretion.MathObject;
import com.discretion.MathObjectVisitor;

public class SetIntersection implements MathObject {

    public final MathObject setA;
    public final MathObject setB;

    public void accept(MathObjectVisitor visitor) {
        visitor.visit(this);
    }

    public SetIntersection(MathObject setA, MathObject setB) {
        this.setA = setA;
        this.setB = setB;
    }
}

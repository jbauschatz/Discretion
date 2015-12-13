package com.discretion.expression;

import com.discretion.MathObject;
import com.discretion.MathObjectVisitor;

public class SetIntersection implements MathObject {

    public boolean equals(Object other) {
        if (!(other instanceof SetIntersection))
            return false;
        SetIntersection otherIntersection = (SetIntersection)other;
        return left.equals(otherIntersection.left) && right.equals(otherIntersection.right);
    }

    public MathObject getLeft() {
        return left;
    }

    public void setLeft(MathObject left) {
        this.left = left;
    }

    public MathObject getRight() {
        return right;
    }

    public void setRight(MathObject right) {
        this.right = right;
    }

    public void accept(MathObjectVisitor visitor) {
        visitor.visit(this);
    }

    public SetIntersection(MathObject left, MathObject right) {
        this.left = left;
        this.right = right;
    }

    public SetIntersection() {
    }

    private MathObject left;
    private MathObject right;
}

package com.discretion.expression;

import com.discretion.MathObject;
import com.discretion.MathObjectVisitor;

public class SetUnion implements MathObject {

    public boolean equals(MathObject other) {
        if (!(other instanceof SetUnion))
            return false;
        SetUnion otherUnion = (SetUnion)other;
        return left.equals(otherUnion.left) && right.equals(otherUnion.right);
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

    public SetUnion(MathObject left, MathObject right) {
        this.left = left;
        this.right = right;
    }

    public SetUnion() {
    }

    private MathObject left;
    private MathObject right;
}

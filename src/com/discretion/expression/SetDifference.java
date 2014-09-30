package com.discretion.expression;

import com.discretion.MathObject;
import com.discretion.MathObjectVisitor;

public class SetDifference implements MathObject {

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

    public SetDifference(MathObject left, MathObject right) {
        this.left = left;
        this.right = right;
    }

    private MathObject left;
    private MathObject right;
}

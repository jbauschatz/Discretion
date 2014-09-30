package com.discretion.statement;

import com.discretion.MathObject;
import com.discretion.MathObjectVisitor;

public class Conjunction implements Statement {

    public boolean equals(MathObject other) {
        if (!(other instanceof Conjunction))
            return false;
        Conjunction otherConj = (Conjunction)other;
        return left.equals(otherConj.left) && right.equals(otherConj.right);
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

    public Conjunction(MathObject left, MathObject right) {
        this.left = left;
        this.right = right;
    }

    public Conjunction() {
    }

    private MathObject left;
    private MathObject right;

}

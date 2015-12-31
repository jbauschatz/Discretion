package com.discretion.expression;

import com.discretion.MathObject;
import com.discretion.MathObjectVisitor;
import com.discretion.Variable;

public class SetDifference implements MathObject {

    public boolean equals(Object other) {
        if (!(other instanceof SetDifference))
            return false;
        SetDifference otherDiff = (SetDifference)other;
        return left.equals(otherDiff.left) && right.equals(otherDiff.right);
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

	public SetDifference(MathObject left, MathObject right) {
		this.left = left;
		this.right = right;
	}

	public SetDifference(String left, String right) {
		this.left = new Variable(left);
		this.right = new Variable(right);
	}

    public SetDifference() {
    }

    private MathObject left;
    private MathObject right;
}

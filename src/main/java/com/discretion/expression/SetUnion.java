package com.discretion.expression;

import com.discretion.MathObject;
import com.discretion.MathObjectVisitor;
import com.discretion.statement.Variable;

public class SetUnion extends MathObject {

    public boolean equals(Object other) {
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

	public SetUnion(String left, String right) {
		this.left = new Variable(left);
		this.right = new Variable(right);
	}

	private MathObject left;
    private MathObject right;
}

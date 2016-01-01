package com.discretion.expression;

import com.discretion.MathObject;
import com.discretion.MathObjectVisitor;
import com.discretion.Variable;

public class SetComplement implements MathObject {

    public boolean equals(Object other) {
        if (!(other instanceof SetComplement))
            return false;
        SetComplement otherComp = (SetComplement)other;
        return otherComp.set.equals(set);
    }

    public MathObject getSet() {
        return set;
    }

    public void setSet(MathObject set) {
        this.set = set;
    }

    public void accept(MathObjectVisitor visitor) {
        visitor.visit(this);
    }

	public SetComplement(MathObject set) {
		this.set = set;
	}

	public SetComplement(String set) {
		this.set = new Variable(set);
	}

    private MathObject set;
}

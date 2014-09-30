package com.discretion.expression;

import com.discretion.MathObject;
import com.discretion.MathObjectVisitor;

public class SetComplement implements MathObject {

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

    public SetComplement() {
    }

    private MathObject set;
}

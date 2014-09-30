package com.discretion.statement;

import com.discretion.MathObject;
import com.discretion.MathObjectVisitor;

public class SubsetOf implements Statement {

    public MathObject getSubset() {
        return subset;
    }

    public void setSubset(MathObject subset) {
        this.subset = subset;
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

    public SubsetOf(MathObject element, MathObject set) {
        this.subset = element;
        this.set = set;
    }

    public SubsetOf() {
    }

    private MathObject subset;
    private MathObject set;
}

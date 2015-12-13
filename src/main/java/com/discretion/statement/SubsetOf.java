package com.discretion.statement;

import com.discretion.MathObject;
import com.discretion.MathObjectVisitor;

public class SubsetOf implements Statement {

    public boolean equals(Object other) {
        if (!(other instanceof SubsetOf))
            return false;
        SubsetOf otherSubset = (SubsetOf)other;
        return set.equals(otherSubset.set) && subset.equals(otherSubset.subset);
    }

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

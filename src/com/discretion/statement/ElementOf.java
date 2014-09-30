package com.discretion.statement;

import com.discretion.MathObject;
import com.discretion.MathObjectVisitor;

public class ElementOf implements Statement {

    public MathObject getElement() {
        return element;
    }

    public void setElement(MathObject element) {
        this.element = element;
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

    public ElementOf(MathObject element, MathObject set) {
        this.element = element;
        this.set = set;
    }

    public ElementOf() {
    }

    private MathObject element;
    private MathObject set;
}

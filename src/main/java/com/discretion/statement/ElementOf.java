package com.discretion.statement;

import com.discretion.MathObject;
import com.discretion.MathObjectVisitor;
import com.discretion.Variable;

public class ElementOf implements Statement {

    public boolean equals(Object other) {
        if (!(other instanceof ElementOf))
            return false;
        ElementOf otherElement = (ElementOf)other;
        return element.equals(otherElement.element) && set.equals(otherElement.set);
    }

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

    public ElementOf(String element, String set) {
        this.element = new Variable(element);
        this.set = new Variable(set);
    }

    public ElementOf() {
    }

    private MathObject element;
    private MathObject set;
}

package com.discretion.statement;

import com.discretion.MathObject;
import com.discretion.MathObjectVisitor;

public class ElementOf extends Statement {

    public boolean equals(Object other) {
        if (!(other instanceof ElementOf))
            return false;
        ElementOf otherElement = (ElementOf)other;
        return element.equals(otherElement.element)
				&& set.equals(otherElement.set)
				&& isNegative() == otherElement.isNegative();
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

	@Override
	public MathObject negate() {
		return new NotElementOf(element, set);
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

    protected MathObject element;
    protected MathObject set;
}

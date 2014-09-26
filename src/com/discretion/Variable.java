package com.discretion;

public class Variable implements MathObject {
    public final String name;

    public void accept(MathObjectVisitor visitor) {
        visitor.visit(this);
    }

    public Variable(String name) {
        this.name = name;
    }

}

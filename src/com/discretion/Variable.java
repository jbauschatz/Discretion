package com.discretion;

public class Variable implements MathObject {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void accept(MathObjectVisitor visitor) {
        visitor.visit(this);
    }

    public Variable(String name) {
        this.name = name;
    }

    public Variable() {
    }

    private String name;
}

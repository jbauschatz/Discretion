package com.discretion.statement;

import com.discretion.MathObject;
import com.discretion.MathObjectVisitor;

public class Variable implements Statement {

    public boolean equals(Object other) {
        return other instanceof Variable && ((Variable)other).name.equals(name);
    }

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

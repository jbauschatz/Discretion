package com.discretion;

/**
 * Any mathematical expression, such as a variable, a function or function application,
 * or a boolean expression.
 */
public interface MathObject {
    public void accept(MathObjectVisitor visitor);
}

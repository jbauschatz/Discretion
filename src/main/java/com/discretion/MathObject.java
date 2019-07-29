package com.discretion;

import com.discretion.statement.Negation;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Any mathematical expression, such as a variable, a function or function application,
 * or a boolean expression.
 */

@JsonTypeInfo(
        use = JsonTypeInfo.Id.MINIMAL_CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
public abstract class MathObject {
    public abstract void accept(MathObjectVisitor visitor);

	public MathObject negate() {
		return new Negation(this);
	}

	public boolean isNegative() {
		return false;
	}
}

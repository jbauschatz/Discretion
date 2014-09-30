package com.discretion;

import org.codehaus.jackson.annotate.JsonTypeInfo;

/**
 * Any mathematical expression, such as a variable, a function or function application,
 * or a boolean expression.
 */

@JsonTypeInfo(
        use = JsonTypeInfo.Id.MINIMAL_CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
public interface MathObject {
    public boolean equals(MathObject other);
    public void accept(MathObjectVisitor visitor);
}

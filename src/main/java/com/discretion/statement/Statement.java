package com.discretion.statement;

import com.discretion.MathObject;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.MINIMAL_CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
public interface Statement extends MathObject {
}

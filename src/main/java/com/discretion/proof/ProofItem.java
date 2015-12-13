package com.discretion.proof;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.MINIMAL_CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
public interface ProofItem {
    void accept(ProofItemVisitor visitor);
}

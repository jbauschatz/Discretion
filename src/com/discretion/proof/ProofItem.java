package com.discretion.proof;

import org.codehaus.jackson.annotate.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.MINIMAL_CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
public interface ProofItem {
    public void accept(ProofItemVisitor visitor);
}

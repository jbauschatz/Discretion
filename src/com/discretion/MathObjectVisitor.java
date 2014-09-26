package com.discretion;

import com.discretion.statement.ElementOf;
import com.discretion.statement.SubsetOf;

public interface MathObjectVisitor {
    public void visit(Variable variable);
    public void visit(ElementOf elem);
    public void visit(SubsetOf elem);
}

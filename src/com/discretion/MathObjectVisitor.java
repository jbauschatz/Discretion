package com.discretion;

import com.discretion.expression.SetDifference;
import com.discretion.expression.SetIntersection;
import com.discretion.expression.SetUnion;
import com.discretion.statement.ElementOf;
import com.discretion.statement.SubsetOf;

public interface MathObjectVisitor {
    public void visit(Variable variable);
    public void visit(ElementOf elem);
    public void visit(SubsetOf elem);
    public void visit(SetUnion union);
    public void visit(SetIntersection intersection);
    public void visit(SetDifference difference);
}

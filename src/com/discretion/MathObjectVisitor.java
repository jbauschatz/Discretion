package com.discretion;

import com.discretion.expression.*;
import com.discretion.statement.*;

public interface MathObjectVisitor {
    public void visit(Variable variable);
    public void visit(ElementOf elem);
    public void visit(Equality elem);
    public void visit(SubsetOf elem);
    public void visit(SetUnion union);
    public void visit(SetIntersection intersection);
    public void visit(SetDifference difference);
    public void visit(SetComplement complement);
    public void visit(Conjunction conjunction);
    public void visit(Disjunction disjunction);
    public void visit(Negation negation);
}

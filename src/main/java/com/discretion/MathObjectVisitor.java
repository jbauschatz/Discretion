package com.discretion;

import com.discretion.expression.*;
import com.discretion.statement.*;

public interface MathObjectVisitor {
    void visit(Variable variable);
	void visit(ElementOf elem);
    void visit(Equality equality);
    void visit(SubsetOf subset);
    void visit(SetUnion union);
    void visit(SetIntersection intersection);
    void visit(SetDifference difference);
    void visit(SetComplement complement);
    void visit(Conjunction conjunction);
    void visit(Disjunction disjunction);
	void visit(Negation negation);
	void visit(CartesianProduct products);
}

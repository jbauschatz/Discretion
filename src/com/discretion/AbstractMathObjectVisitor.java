package com.discretion;

import com.discretion.expression.SetComplement;
import com.discretion.expression.SetDifference;
import com.discretion.expression.SetIntersection;
import com.discretion.expression.SetUnion;
import com.discretion.statement.*;

public class AbstractMathObjectVisitor implements MathObjectVisitor {

    public void traverse(MathObject object) {
        object.accept(this);
    }

    public final void visit(Variable variable) {
        handle(variable);
    }

    public final void visit(ElementOf elem) {
        handle(elem);
        elem.getElement().accept(this);
        elem.getSet().accept(this);
    }

    public final void visit(Equality equality) {
        handle(equality);
        equality.getLeft().accept(this);
        equality.getRight().accept(this);
    }

    public final void visit(SubsetOf subset) {
        handle(subset);
        subset.getSubset().accept(this);
        subset.getSet().accept(this);
    }

    public final void visit(SetUnion union) {
        handle(union);
        union.getLeft().accept(this);
        union.getRight().accept(this);
    }

    public final void visit(SetIntersection intersection) {
        handle(intersection);
        intersection.getLeft().accept(this);
        intersection.getRight().accept(this);
    }

    public final void visit(SetDifference difference) {
        handle(difference);
        difference.getLeft().accept(this);
        difference.getRight().accept(this);
    }

    public final void visit(SetComplement complement) {
        handle(complement);
        complement.getSet().accept(this);
    }

    public final void visit(Conjunction conjunction) {
        handle(conjunction);
        conjunction.getLeft().accept(this);
        conjunction.getRight().accept(this);
    }

    public final void visit(Disjunction disjunction) {
        handle(disjunction);
        disjunction.getLeft().accept(this);
        disjunction.getRight().accept(this);
    }

    public final void visit(Negation negation) {
        handle(negation);
        negation.getTerm().accept(this);
    }

    protected void handle(Variable variable) {
    }

    protected void handle(ElementOf elem) {
    }

    protected void handle(Equality equality) {
    }

    protected void handle(SubsetOf subset) {
    }

    protected void handle(SetUnion union) {
    }

    protected void handle(SetIntersection intersection) {
    }

    protected void handle(SetDifference difference) {
    }

    protected void handle(SetComplement complement) {
    }

    protected void handle(Conjunction conjunction) {
    }

    protected void handle(Disjunction disjunction) {
    }

    protected void handle(Negation negation) {
    }
}

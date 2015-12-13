package com.discretion;

import com.discretion.expression.SetComplement;
import com.discretion.expression.SetDifference;
import com.discretion.expression.SetIntersection;
import com.discretion.expression.SetUnion;
import com.discretion.statement.*;

public class AbstractMathObjectVisitor implements MathObjectVisitor {

    public void traverse(MathObject object) {
        parent = null;
        object.accept(this);
    }

    public final void visit(Variable variable) {
        handle(variable);
    }

    public final void visit(ElementOf elem) {
        handle(elem);

        parent = elem;
        elem.getElement().accept(this);

        parent = elem;
        elem.getSet().accept(this);
    }

    public final void visit(Equality equality) {
        handle(equality);

        parent = equality;
        equality.getLeft().accept(this);

        parent = equality;
        equality.getRight().accept(this);
    }

    public final void visit(SubsetOf subset) {
        handle(subset);

        parent = subset;
        subset.getSubset().accept(this);

        parent = subset;
        subset.getSet().accept(this);
    }

    public final void visit(SetUnion union) {
        handle(union);

        parent = union;
        union.getLeft().accept(this);

        parent = union;
        union.getRight().accept(this);
    }

    public final void visit(SetIntersection intersection) {
        handle(intersection);

        parent = intersection;
        intersection.getLeft().accept(this);

        parent = intersection;
        intersection.getRight().accept(this);
    }

    public final void visit(SetDifference difference) {
        handle(difference);

        parent = difference;
        difference.getLeft().accept(this);

        parent = difference;
        difference.getRight().accept(this);
    }

    public final void visit(SetComplement complement) {
        handle(complement);

        parent = complement;
        complement.getSet().accept(this);
    }

    public final void visit(Conjunction conjunction) {
        handle(conjunction);

        parent = conjunction;
        conjunction.getLeft().accept(this);

        parent = conjunction;
        conjunction.getRight().accept(this);
    }

    public final void visit(Disjunction disjunction) {
        handle(disjunction);

        parent = disjunction;
        disjunction.getLeft().accept(this);

        parent = disjunction;
        disjunction.getRight().accept(this);
    }

    public final void visit(Negation negation) {
        handle(negation);

        parent = negation;
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

    protected MathObject parent;
}

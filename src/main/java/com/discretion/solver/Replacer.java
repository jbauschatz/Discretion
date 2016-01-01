package com.discretion.solver;

import com.discretion.MathObject;
import com.discretion.MathObjectVisitor;
import com.discretion.Variable;
import com.discretion.expression.*;
import com.discretion.statement.*;

public class Replacer implements MathObjectVisitor {

    public MathObject substitute(MathObject object, MathObject replaceThis, MathObject withThis) {
        this.replaceThis = replaceThis;
        this.withThis = withThis;
        return copyAndReplace(object);
    }

    public void visit(Variable variable) {
        replacedCopy = copy(variable);
    }

    public void visit(ElementOf elem) {
        replacedCopy = copy(elem);
    }

    public void visit(Equality equality) {
        replacedCopy = copy(equality);
    }

    public void visit(SubsetOf subset) {
        replacedCopy = copy(subset);
    }

    public void visit(SetUnion union) {
        replacedCopy = copy(union);
    }

    public void visit(SetIntersection intersection) {
        replacedCopy = copy(intersection);
    }

    public void visit(SetDifference difference) {
        replacedCopy = copy(difference);
    }

    public void visit(SetComplement complement) {
        replacedCopy = copy(complement);
    }

    public void visit(Conjunction conjunction) {
        replacedCopy = copy(conjunction);
    }

    public void visit(Disjunction disjunction) {
        replacedCopy = copy(disjunction);
    }

	public void visit(Negation negation) {
		replacedCopy = copy(negation);
	}

	public void visit(CartesianProduct product) {
		replacedCopy = copy(product);
	}

    private MathObject copyAndReplace(MathObject object) {
        if (object == replaceThis)
            return withThis;

        object.accept(this);
        return replacedCopy;
    }

    private MathObject copy(Variable variable) {
        return new Variable(variable.getName());
    }

    private MathObject copy(ElementOf elem) {
        return new ElementOf(copyAndReplace(elem.getElement()), copyAndReplace(elem.getSet()));
    }

    private MathObject copy(Equality equality) {
        return new Equality(copyAndReplace(equality.getLeft()), copyAndReplace(equality.getRight()));
    }

    private MathObject copy(SubsetOf subset) {
        return new SubsetOf(copyAndReplace(subset.getSubset()), copyAndReplace(subset.getSet()));
    }

    private MathObject copy(SetUnion union) {
        return new SetUnion(copyAndReplace(union.getLeft()), copyAndReplace(union.getRight()));
    }

    private MathObject copy(SetIntersection intersection) {
        return new SetIntersection(copyAndReplace(intersection.getLeft()), copyAndReplace(intersection.getRight()));
    }

    private MathObject copy(SetDifference difference) {
        return new SetDifference(copyAndReplace(difference.getLeft()), copyAndReplace(difference.getRight()));
    }

	private MathObject copy(SetComplement complement) {
		return new SetComplement(complement.getSet());
	}

    private MathObject copy(Conjunction conjunction) {
        return new Conjunction(
                copyAndReplace(conjunction.getLeft()),
                copyAndReplace(conjunction.getRight())
        );
    }

    private MathObject copy(Disjunction disjunction) {
        return new Disjunction(copyAndReplace(disjunction.getLeft()), copyAndReplace(disjunction.getRight()));
    }

    private MathObject copy(Negation negation) {
        return new Negation(copyAndReplace(negation.getTerm()));
    }

	private MathObject copy(CartesianProduct product) {
		return new CartesianProduct(product.getSets());
	}

    private MathObject replaceThis;
    private MathObject withThis;
    private MathObject replacedCopy;
}

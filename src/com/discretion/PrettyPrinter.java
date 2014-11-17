package com.discretion;

import com.discretion.expression.SetComplement;
import com.discretion.expression.SetDifference;
import com.discretion.expression.SetIntersection;
import com.discretion.expression.SetUnion;
import com.discretion.statement.Conjunction;
import com.discretion.statement.Disjunction;
import com.discretion.statement.ElementOf;
import com.discretion.statement.Equality;
import com.discretion.statement.Negation;
import com.discretion.statement.SubsetOf;

import java.util.LinkedList;
import java.util.List;

public class PrettyPrinter implements MathObjectVisitor {

    public void prettyPrint(MathObject object) {
        System.out.println(prettyString(object));
    }

    public String prettyString(MathObject object) {
        pretty = "";
        object.accept(this);
        return pretty;
    }

    /**
     * Returns a comma seperated list with the Oxford Comma
     */
    public String commaList(List<? extends MathObject> objects) {
        if (objects.size() == 1)
            return prettyString(objects.get(0));

        if (objects.size() == 2)
            return prettyString(objects.get(0)) + " and " + prettyString(objects.get(1));

        String list = "";
        for (int i = 0; i<objects.size()-1; ++i)
            list += prettyString(objects.get(i)) + ", ";

        list += "and " + prettyString(objects.get(objects.size()-1));
        return list;
    }

    public void visit(Variable variable) {
        pretty += variable.getName();
    }

    public void visit(ElementOf elem) {
        elem.getElement().accept(this);
        pretty += " \u2208 ";
        elem.getSet().accept(this);
    }

    public void visit(Equality elem) {
        elem.getLeft().accept(this);
        pretty += " = ";
        elem.getRight().accept(this);
    }

    public void visit(SubsetOf elem) {
        elem.getSubset().accept(this);
        pretty += " \u2286 ";
        elem.getSet().accept(this);
    }

    public void visit(SetUnion union) {
        parensIfNeeded(union, union.getLeft());
        pretty += " \u222A ";
        parensIfNeeded(union, union.getRight());
    }

    public void visit(SetIntersection intersection) {
        parensIfNeeded(intersection, intersection.getLeft());
        pretty += " ∩ ";
        parensIfNeeded(intersection, intersection.getRight());
    }

    public void visit(SetDifference difference) {
        parensIfNeeded(difference, difference.getLeft());
        pretty += " - ";
        parensIfNeeded(difference, difference.getRight());
    }

    public void visit(SetComplement complement) {
        pretty += "~";
        parensIfNeeded(complement, complement.getSet());
    }

    public void visit(Conjunction conjunction) {
        parensIfNeeded(conjunction, conjunction.getLeft());
        pretty += " \u2227 ";
        parensIfNeeded(conjunction, conjunction.getRight());
    }

    public void visit(Disjunction disjunction) {
        parensIfNeeded(disjunction, disjunction.getLeft());
        pretty += " \u2228 ";
        parensIfNeeded(disjunction, disjunction.getRight());
    }

    public void visit(Negation negation) {
        pretty += "¬";
        parensIfNeeded(negation, negation.getTerm());
    }

	public PrettyPrinter() {
		precedence = new LinkedList<>();
		precedence.addFirst(Variable.class);
		precedence.addFirst(Negation.class);
		precedence.addFirst(SetComplement.class);
		precedence.addFirst(SetIntersection.class);
		precedence.addFirst(SetDifference.class);
		precedence.addFirst(SetUnion.class);
		precedence.addFirst(ElementOf.class);
		precedence.addFirst(SubsetOf.class);
	}

    private void parensIfNeeded(MathObject parent, MathObject object) {
        boolean paren = precedence(parent) >= precedence(object);
        if (paren)
            pretty += "(";
        object.accept(this);
        if (paren)
            pretty += ")";
    }

	private int precedence(MathObject object) {
		return precedence.indexOf(object.getClass());
	}

    String pretty;
	private LinkedList<Class<?>> precedence;
}

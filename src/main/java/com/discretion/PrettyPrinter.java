package com.discretion;

import com.discretion.expression.*;
import com.discretion.statement.Conjunction;
import com.discretion.statement.Disjunction;
import com.discretion.statement.ElementOf;
import com.discretion.statement.Equality;
import com.discretion.statement.Negation;
import com.discretion.statement.SubsetOf;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class PrettyPrinter implements MathObjectVisitor {

    public String prettyString(MathObject object) {
        pretty = "";
        object.accept(this);
        return pretty;
    }

    /**
     * Returns a comma separated list with the Oxford Comma
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
        pretty += " ∈ ";
        elem.getSet().accept(this);
    }

    public void visit(Equality elem) {
        elem.getLeft().accept(this);
        pretty += " = ";
        elem.getRight().accept(this);
    }

    public void visit(SubsetOf elem) {
        elem.getSubset().accept(this);
        pretty += " ⊆ ";
        elem.getSet().accept(this);
    }

    public void visit(SetUnion union) {
        parensIfNeeded(union, union.getLeft());
        pretty += " ∪ ";
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
        pretty += useEnglish ? " and " : " ∧ ";
        parensIfNeeded(conjunction, conjunction.getRight());
    }

    public void visit(Disjunction disjunction) {
        parensIfNeeded(disjunction, disjunction.getLeft());
        pretty += useEnglish ? " or " : " ∨ ";
        parensIfNeeded(disjunction, disjunction.getRight());
    }

    public void visit(Negation negation) {
        pretty += "¬";
        parensIfNeeded(negation, negation.getTerm());
    }

	public void visit(CartesianProduct product) {
		MathObject[] sets = product.getSets();
		parensIfNeeded(product, sets[0]);

		for (int i = 1; i<sets.length; ++i) {
			pretty += " × ";
			parensIfNeeded(product, sets[i]);
		}
	}

	public PrettyPrinter() {
		this(false);
	}

	public PrettyPrinter(boolean useEnglish) {
		this.useEnglish = useEnglish;
		precedence = new HashMap<>();

		int p = 0;

		// Conjunction and Disjunction have the same precedence
		precedence.put(Conjunction.class, p);
		precedence.put(Disjunction.class, p++);

		// These set notations have the same precedence
		precedence.put(SetUnion.class, p);
		precedence.put(SetIntersection.class, p);
		precedence.put(SetDifference.class, p); // TODO is this correct?
		precedence.put(CartesianProduct.class, p++);

		precedence.put(SetComplement.class, p++);

		precedence.put(ElementOf.class, p++);
		precedence.put(SubsetOf.class, p++);
		precedence.put(Negation.class, p++);
		precedence.put(Variable.class, p);
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
		Class<?> mathClass = object.getClass();

		if (!precedence.containsKey(mathClass))
			throw new IllegalArgumentException("No precedence is defined for " + mathClass);

		return precedence.get(mathClass);
	}

	private String pretty;
	private boolean useEnglish;
	private HashMap<Class<?>, Integer> precedence;
}

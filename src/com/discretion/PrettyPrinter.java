package com.discretion;

import com.discretion.expression.*;
import com.discretion.statement.*;

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
        parensIfNeeded(union.getLeft());
        pretty += " \u222A ";
        parensIfNeeded(union.getRight());
    }

    public void visit(SetIntersection intersection) {
        parensIfNeeded(intersection.getLeft());
        pretty += " ∩ ";
        parensIfNeeded(intersection.getRight());
    }

    public void visit(SetDifference difference) {
        parensIfNeeded(difference.getLeft());
        pretty += " - ";
        parensIfNeeded(difference.getRight());
    }

    public void visit(SetComplement complement) {
        pretty += "~";
        parensIfNeeded(complement.getSet());
    }

    public void visit(Conjunction conjunction) {
        parensIfNeeded(conjunction.getLeft());
        pretty += " \u2227 ";
        parensIfNeeded(conjunction.getRight());
    }

    public void visit(Disjunction disjunction) {
        parensIfNeeded(disjunction.getLeft());
        pretty += " \u2228 ";
        parensIfNeeded(disjunction.getRight());
    }

    public void visit(Negation negation) {
        pretty += "~";
        parensIfNeeded(negation.getTerm());
    }

    private void parensIfNeeded(MathObject object) {
        boolean paren = !(object instanceof Variable || object instanceof ElementOf || object instanceof SetComplement);
        if (paren)
            pretty += "(";
        object.accept(this);
        if (paren)
            pretty += ")";
    }

    private void parens(MathObject object) {
        pretty += "(";
        object.accept(this);
        pretty += ")";
    }

    String pretty;
}

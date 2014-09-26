package com.discretion;

import com.discretion.expression.SetDifference;
import com.discretion.expression.SetIntersection;
import com.discretion.expression.SetUnion;
import com.discretion.statement.ElementOf;
import com.discretion.statement.SubsetOf;

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

        list += " and " + prettyString(objects.get(objects.size()-1));
        return list;
    }

    public void visit(Variable variable) {
        pretty += variable.name;
    }

    public void visit(ElementOf elem) {
        elem.element.accept(this);
        pretty += " \u2208 ";
        elem.set.accept(this);
    }

    public void visit(SubsetOf elem) {
        elem.subset.accept(this);
        pretty += " \u2286 ";
        elem.set.accept(this);
    }

    public void visit(SetUnion union) {
        union.setA.accept(this);
        pretty += " \u222A ";
        union.setB.accept(this);
    }

    public void visit(SetIntersection intersection) {
        intersection.setA.accept(this);
        pretty += " \u2229 ";
        intersection.setB.accept(this);
    }

    public void visit(SetDifference difference) {
        difference.setA.accept(this);
        pretty += " - ";
        difference.setB.accept(this);
    }

    String pretty;
}

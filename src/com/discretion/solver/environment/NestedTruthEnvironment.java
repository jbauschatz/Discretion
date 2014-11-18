package com.discretion.solver.environment;

import com.discretion.MathObject;
import com.discretion.Variable;
import com.discretion.statement.Statement;

import java.util.LinkedList;
import java.util.List;

public class NestedTruthEnvironment implements TruthEnvironment {

	public boolean containsTruth(Statement truth) {
		return truths.contains(truth)
			   || (parent != null && parent.containsTruth(truth));
	}

	public boolean containsName(String name) {
		return names.contains(name)
			   || (parent != null && parent.containsName(name));
	}

    /**
     * Returns whether the new statement was added
     * (false if it already existed)
     */
    public boolean addTruth(Statement truth) {
        if (truths.contains(truth))
            return false;

        if (parent != null && parent.containsTruth(truth))
            return false;

        truths.add(truth);

		for (String name : nameExtractor.getNames(truth)) {
			if (!containsName(name))
				names.add(name);
		}

        return true;
    }

    public <S extends Statement> List<S> getTruths(Class<S> statementClass) {
        LinkedList<S> filtered = new LinkedList<>();

        for (Statement s : getTruths()) {
            if (s.getClass().equals(statementClass))
                filtered.add((S)s);
        }

        return filtered;
    }

    public List<Statement> getTruths() {
        // TODO: Custom linked list that iterates up the parent
        if (parent != null) {
            LinkedList<Statement> all = new LinkedList<>();
            all.addAll(parent.getTruths());

            all.addAll(truths);

            return all;
        }

        return truths;
    }

    public NestedTruthEnvironment getChildEnvironment(Statement newTruth) {
        NestedTruthEnvironment child = new NestedTruthEnvironment(newTruth);
        child.parent = this;
        return child;
    }

    public NestedTruthEnvironment getChildEnvironment(List<Statement> newTruths) {
        NestedTruthEnvironment child = new NestedTruthEnvironment(newTruths);
        child.parent = this;
        return child;
    }

	public Variable newVariableName(String likeThis) {
		String uniqueName = likeThis;
		while (containsName(uniqueName))
			uniqueName = uniqueName + "'";

		return new Variable(uniqueName);
	}

	public Variable newElementName(MathObject set) {
		if (set instanceof Variable) {
			String setName = ((Variable)set).getName();
			return newVariableName(setName.toLowerCase());
		}
		return newVariableName("x");
	}

    public NestedTruthEnvironment(List<Statement> initialTruths) {
		this();

        for (Statement s : initialTruths)
            addTruth(s);
    }

    public NestedTruthEnvironment(Statement... initialTruths) {
		this();

        for (Statement s : initialTruths)
            addTruth(s);
    }

	private NestedTruthEnvironment() {
		truths = new LinkedList<>();
		names = new LinkedList<>();
		nameExtractor = new VariableNameExtractor();
	}

	private VariableNameExtractor nameExtractor;

    private TruthEnvironment parent;
	private List<Statement> truths;
	private List<String> names;
}

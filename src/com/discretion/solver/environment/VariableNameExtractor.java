
package com.discretion.solver.environment;

import com.discretion.AbstractMathObjectVisitor;
import com.discretion.MathObject;
import com.discretion.Variable;

import java.util.LinkedList;
import java.util.List;

public class VariableNameExtractor extends AbstractMathObjectVisitor {

	public List<String> getNames(MathObject object) {
		names = new LinkedList<>();
		traverse(object);

		return names;
	}

	@Override
	public void handle(Variable variable) {
		names.add(variable.getName());
	}

	private List<String> names;

}

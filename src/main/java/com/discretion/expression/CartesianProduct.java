package com.discretion.expression;

import com.discretion.MathObject;
import com.discretion.MathObjectVisitor;

import java.util.Arrays;

public class CartesianProduct implements MathObject {

	public void accept(MathObjectVisitor visitor) {
		visitor.visit(this);
	}

	public boolean equals(Object object) {
		if (!(object instanceof CartesianProduct))
			return false;

		CartesianProduct otherProduct = (CartesianProduct)object;
		return Arrays.equals(sets, otherProduct.sets);
	}

	public MathObject[] getSets() {
		return sets;
	}

	public CartesianProduct(MathObject... sets) {
		this.sets = sets;
	}

	private MathObject[] sets;
}

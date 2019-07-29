package com.discretion.statement;

import com.discretion.MathObject;

public class NotElementOf extends ElementOf {

	@Override
	public boolean isNegative() {
		return true;
	}

	@Override
	public MathObject negate() {
		return new ElementOf(element, set);
	}

	public NotElementOf(MathObject element, MathObject set) {
		super(element, set);
	}

	public NotElementOf(String element, String set) {
		super(element, set);
	}
}

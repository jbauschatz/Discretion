package com.discretion;

import org.hamcrest.*;

public class EqualExpression extends BaseMatcher<MathObject> {

	private static PrettyPrinter PRINTER = new PrettyPrinter();

	public static Matcher<MathObject> equalToExpression(MathObject expected) {
		return new EqualExpression(expected);
	}

	public boolean matches(Object item) {
		return expected.equals(item);
	}

	public void describeTo(Description description) {
		description.appendText("expression equal to " + PRINTER.prettyString(expected));
	}

	public void describeMismatch(Object item, Description description) {
		if (item == null) {
			super.describeMismatch(item, description);
		} else {
			MathObject object = (MathObject)item;
			description.appendText("was " + PRINTER.prettyString(object));
		}
	}

	protected EqualExpression(MathObject expected) {
		this.expected = expected;
	}

	private MathObject expected;

}
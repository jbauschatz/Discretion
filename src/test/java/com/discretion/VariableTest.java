package com.discretion;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class VariableTest {
    @Test
    public void testEquality() {
        assertThat("Variables with same name should be equal", new Variable("X"), equalTo(new Variable("X")));
        assertThat("Variables with different name should not be equal", new Variable("X"), not(equalTo(new Variable("Y"))));
    }
}

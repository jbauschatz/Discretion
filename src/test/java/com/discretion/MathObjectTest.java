package com.discretion;

import org.junit.Assert;
import org.junit.Test;

public class MathObjectTest {
    @Test
    public void testEquality() {
        Assert.assertEquals("Variables with same name", new Variable("X"), new Variable("X"));
    }
}

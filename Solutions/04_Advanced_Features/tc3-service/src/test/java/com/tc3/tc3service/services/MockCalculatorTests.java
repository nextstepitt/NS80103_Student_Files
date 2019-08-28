package com.tc3.tc3service.services;

import org.mockito.ArgumentMatcher;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalMatchers.and;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MockCalculatorTests {

    @Mock
    ICalculator calculatorMock;

    @Test
    public void multiplyTwoByTwoReturnsFour() {

        when(calculatorMock.multiply(2.0, 2.0)).thenReturn(4.0);

        assertEquals(4.0, calculatorMock.multiply(2.0, 2.0));
    }

    @Test
    public void multiplyNumberByZeroReturnsZero() {

        when(calculatorMock.multiply(anyDouble(), eq(0.0))).thenReturn(0.0);

        assertEquals(0.0, calculatorMock.multiply(2.0, 0.0));
    }

    @Test
    public void multiplyZeroByNumberReturnsZero() {

        when(calculatorMock.multiply( eq(0.0), anyDouble())).thenReturn(0.0);

        assertEquals(0.0, calculatorMock.multiply(0.0, 2.0));
    }

    @Test
    public void anythingNotFALSEIsTrue() {

        when(calculatorMock.isTrue(and(not(eq("FALSE")), not(eq("false"))))).thenReturn(true);

        assertTrue(calculatorMock.isTrue("true"));
    }

    @Test
    public void anythingFALSEIsFalse() {

        when(calculatorMock.isTrue(argThat(new BooleanNotFalseMatcher()))).thenReturn(true);

        assertTrue(calculatorMock.isTrue("FaLsE"));
    }

    public class BooleanNotFalseMatcher implements ArgumentMatcher<String> {

        public boolean matches(String value) {

            return value.toLowerCase().equals("false") == false;
        }
    }
}

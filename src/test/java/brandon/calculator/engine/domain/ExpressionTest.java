package brandon.calculator.engine.domain;

import brandon.calculator.engine.CalculatorEngine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpressionTest {


    @Test
    void parse_add() {
        BigDecimal answer = Expression.parse("1+2").evaluate();

        assertEquals(new BigDecimal("3"), answer);
    }


    @Test
    void parse_add_multiply() {
        BigDecimal answer = Expression.parse("1+2x7").evaluate();

        assertEquals(new BigDecimal("15"), answer);
    }


    @Test
    void parse_many_operators() {
        Expression e = Expression.parse("1+2x7+3-8x7/2");
        BigDecimal answer = e.evaluate();

        assertEquals(new BigDecimal("-10"), answer);
    }


    @Test
    void parse_many_operators_different_order() {
        BigDecimal answer = Expression.parse("8/2x3+1+1+1+1").evaluate();

        assertEquals(new BigDecimal("16"), answer);
    }


    @Test
    void parse_with_parenthesis() {
        BigDecimal answer = Expression.parse("(9+5)+(4x(7/8))").evaluate();

        assertEquals(new BigDecimal("17.5"), answer);
    }


    @Test
    void parse_lone_number_in_parenthesis() {
        String input = CalculatorEngine.clean("(9+5)(8)(1+7)");
        BigDecimal answer = Expression.parse(input).evaluate();

        assertEquals(new BigDecimal("896"), answer);
    }

}

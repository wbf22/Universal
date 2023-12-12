package brandon.calculator.engine.domain;

import java.math.BigDecimal;

public class Multiply implements Operator {
    @Override
    public BigDecimal apply(Expression leftOperand, Expression rightOperand) {
        BigDecimal result = leftOperand.evaluate().multiply(rightOperand.evaluate());
        return result;
    }

    @Override
    public int order() {
        return 1;
    }


    @Override
    public String toString() {
        return "x";
    }
}

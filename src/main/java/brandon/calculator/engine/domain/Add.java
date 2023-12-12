package brandon.calculator.engine.domain;

import java.math.BigDecimal;

public class Add implements Operator {
    @Override
    public BigDecimal apply(Expression leftOperand, Expression rightOperand) {
        BigDecimal result = leftOperand.evaluate().add(rightOperand.evaluate());
        return result;
    }

    @Override
    public int order() {
        return 0;
    }

    @Override
    public String toString() {
        return "+";
    }
}

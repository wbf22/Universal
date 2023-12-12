package brandon.calculator.engine.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Divide implements Operator {
    @Override
    public BigDecimal apply(Expression leftOperand, Expression rightOperand) {
        BigDecimal result = leftOperand.evaluate().divide(rightOperand.evaluate(), 16, RoundingMode.HALF_UP).stripTrailingZeros();
        return result;
    }

    @Override
    public int order() {
        return 1;
    }


    @Override
    public String toString() {
        return "/";
    }
}

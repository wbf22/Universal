package brandon.calculator.engine.domain;

import java.math.BigDecimal;
import java.util.Stack;

public class Number extends Expression {

    private final BigDecimal value;

    public Number(BigDecimal value) {
        super(null, null, null);
        this.value = value;
    }

    @Override
    public BigDecimal evaluate() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

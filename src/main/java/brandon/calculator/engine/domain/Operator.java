package brandon.calculator.engine.domain;

import java.math.BigDecimal;
import java.util.List;

public interface Operator {

    BigDecimal apply(Expression leftOperand, Expression rightOperand);

    int order();

    List<Character> operators = List.of('-', '+', '/', 'x', '%');
    static Operator parseIfOperator(Character charBefore, Character charOfInterest) {
        if ( !operators.contains(charOfInterest) ) {
            return null;
        }

        if (charOfInterest == '-' && (charBefore == null || operators.contains(charBefore))) {
                return null;
        }

        return switch (charOfInterest) {
            case '-' -> new Subtract();
            case '+' -> new Add();
            case '/' -> new Divide();
            case 'x' -> new Multiply();
            case '%' -> new Mod();
            default -> throw new IllegalStateException("Unexpected value: " + charOfInterest);
        };
    }

}

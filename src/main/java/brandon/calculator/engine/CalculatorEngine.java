package brandon.calculator.engine;

import brandon.calculator.engine.domain.Expression;
import brandon.calculator.engine.domain.Operator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Stack;

import static brandon.calculator.engine.domain.Expression.parse;

public class CalculatorEngine {

    private Stack<BigDecimal> pastResults = new Stack<>();


    public BigDecimal evaluate(String expression) {

        expression = clean(expression);

        Expression parsed = Expression.parse(expression);

        BigDecimal result = parsed.evaluate();

        pastResults.push(result);
        while (pastResults.size() > 100) {
            pastResults.pop();
        }

        return result;

    }

    public static String clean(String input) {
        input = input.replace(" ", "");

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            Character charBefore = (i > 0)? input.charAt(i - 1) : null;
            Character charAfter = (i >= input.length() - 1)? null : input.charAt(i + 1);
            Operator op = Operator.parseIfOperator(charBefore, input.charAt(i));

            builder.append(input.charAt(i));

            if (op == null && charAfter != null && charAfter == '(') {
                builder.append("x");
            }
        }

        return builder.toString();
    }


}

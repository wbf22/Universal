package brandon.calculator.engine.domain;

import java.math.BigDecimal;
import java.util.Stack;

public class Expression {


    private Expression leftOperand;
    private Operator operator;
    private Expression rightOperand;


    public Expression(Expression leftOperand, Operator operator, Expression rightOperand) {
        this.leftOperand = leftOperand;
        this.operator = operator;
        this.rightOperand = rightOperand;
    }

    public static Expression parse( String input) {
        // 4 + 3 * (9 + 2 )
        // -4 * (7 * 8 )
        // (9 + 1) % 76
        // 5 * 3 + 1
        // (9 + 5) + ( 4 * (7 / 8) )
        // (9 + 5)( 8 )(1 + 7)

        // resolve parenthesis
        if (input.contains("(")) {
            return parseParenToken(input);
        }

        // find lowest precedence operator
        Operator lowestPrecedenc = null;
        int opIndex = 0;
        for (int i = 0; i < input.length(); i++) {
            Character charBefore = (i > 0)? input.charAt(i - 1) : null;
            Operator op = Operator.parseIfOperator(charBefore, input.charAt(i));

            if (
                op != null &&
                ( lowestPrecedenc == null || lowestPrecedenc.order() >= op.order() )
            ) {
                    lowestPrecedenc = op;
                    opIndex = i;
            }
        }


        if (lowestPrecedenc == null) {
            // parse as single number
            return new Number(
                new BigDecimal( input )
            );
        }
        else {
            if (opIndex == 0) {
                throw new IllegalStateException("Leading operator");
            } else if (opIndex == input.length() - 1) {
                throw new IllegalStateException("Trailing operator");
            }
            else {
                return new Expression(
                    parse( input.substring(0, opIndex) ),
                    lowestPrecedenc,
                    parse( input.substring(opIndex + 1))
                );
            }
        }




    }

    private static Expression parseParenToken(String input) {
        Stack<Integer> innerParen = new Stack<>();
        int firstOccurance = input.indexOf('(');
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(')
                innerParen.push(i);

            if (input.charAt(i) == ')') {
                innerParen.pop();

                if (innerParen.isEmpty()) {
                    Expression innerExpression = Expression.parse(input.substring(firstOccurance + 1, i));

                    if (firstOccurance == 0) {
                        return parse(
                            innerExpression.evaluate() + input.substring(i + 1)
                        );
                    } else if (i == input.length() - 1) {
                        return parse(
                            input.substring(0, firstOccurance) + innerExpression.evaluate()
                        );
                    }
                    else {
                        return parse(
                            input.substring(0, firstOccurance) + innerExpression.evaluate() + input.substring(i+1)
                        );
                    }

                }
            }
        }

        throw new IllegalStateException("Missing parenthesis");
    }

    public BigDecimal evaluate() {
        String valueRounded = operator.apply( leftOperand, rightOperand ).stripTrailingZeros().toPlainString();
        return new BigDecimal(valueRounded);
    }


    @Override
    public String toString() {
        return leftOperand.toString() + operator.toString() + rightOperand.toString();
    }
}

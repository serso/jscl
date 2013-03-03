package jscl.util;

import javax.annotation.Nonnull;

/**
 * User: serso
 * Date: 12/14/11
 * Time: 10:21 PM
 */
public class ExpressionGenerator extends AbstractExpressionGenerator<String> {

    public ExpressionGenerator() {
        super();
    }

    public ExpressionGenerator(int depth) {
        super(depth);
    }

    @Nonnull
    @Override
    public String generate() {
        StringBuilder result = new StringBuilder();

        result.append(generateNumber());

        int i = 0;
        while (i < getDepth()) {

            final Operation operation = generateOperation();
            final Function function = generateFunction();
            final boolean brackets = generateBrackets();

            result.append(operation.getToken());

            if (function == null) {
                result.append(generateNumber());
            } else {
                result.append(function.getToken()).append("(").append(generateNumber()).append(")");
            }

            if (brackets) {
                result = new StringBuilder("(").append(result).append(")");
            }

            i++;
        }

        return result.toString();
    }

    public static void main(String... args) {
        System.out.println(new ExpressionGenerator(20).generate());
    }

}

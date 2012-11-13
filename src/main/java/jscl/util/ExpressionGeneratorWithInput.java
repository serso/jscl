package jscl.util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * User: serso
 * Date: 12/14/11
 * Time: 5:09 PM
 */
public class ExpressionGeneratorWithInput extends AbstractExpressionGenerator<List<String>> {

    @NotNull
    private final List<String> subExpressions;

    public ExpressionGeneratorWithInput(@NotNull List<String> subExpressions) {
        this(subExpressions, 10);
    }

    public ExpressionGeneratorWithInput(@NotNull List<String> subExpressions, int depth) {
        super(depth);
        this.subExpressions = new ArrayList<String>(subExpressions);
    }

    @NotNull
    public List<String> generate() {
        final List<StringBuilder> expressions = new ArrayList<StringBuilder>();
        for (String subExpression : subExpressions) {
            expressions.add(new StringBuilder(subExpression));
        }

        int i = 0;
        while (i < getDepth()) {

            final Operation operation = generateOperation();
            final Function function = generateFunction();
            final boolean brackets = generateBrackets();

            for (int j = 0; j < subExpressions.size(); j++) {
                final StringBuilder expression = expressions.get(j);
                expression.append(operation.getToken());

                if (function == null) {
                    expression.append(subExpressions.get(j));
                } else {
                    expression.append(function.getToken()).append("(").append(subExpressions.get(j)).append(")");
                }

                if (brackets) {
                    expressions.set(j, new StringBuilder("(").append(expression).append(")"));
                }
            }
            i++;
        }

        final List<String> result = new ArrayList<String>();
        for (StringBuilder expression : expressions) {
            result.add(expression.toString());
        }

        return result;
    }

    public static void main(String... args) {
        final ArrayList<String> input = new ArrayList<String>();
        input.add("3");
        input.add("0x:fed");
        input.add("0b:101");
        for (String expression : new ExpressionGeneratorWithInput(input, 20).generate()) {
            System.out.println(expression);
        }
    }
}

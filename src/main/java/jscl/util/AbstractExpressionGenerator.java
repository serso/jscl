package jscl.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: serso
 * Date: 12/14/11
 * Time: 10:18 PM
 */
public abstract class AbstractExpressionGenerator<T> {

    protected static enum Operation {
        addition(0, "+"),
        subtraction(1, "-"),
        multiplication(2, "*"),
        division(3, "/");

        private final int operationId;
        private final String token;

        Operation(int operationId, @NotNull String token) {
            this.operationId = operationId;
            this.token = token;
        }

        @Nullable
        public static Operation getOperationById(int operationId) {
            for (Operation operation : Operation.values()) {
                if (operation.operationId == operationId) {
                    return operation;
                }
            }

            return null;
        }


        public String getToken() {
            return token;
        }
    }

    protected static enum Function {
        sin(0, "sin"),
        cos(1, "cos"),
        sqrt(2, "√"),
        ln(3, "ln");

        private final int functionId;
        private final String token;

        Function(int functionId, @NotNull String token) {
            this.functionId = functionId;
            this.token = token;
        }

        @Nullable
        public static Function getFunctionById(int functionId) {
            for (Function function : Function.values()) {
                if (function.functionId == functionId) {
                    return function;
                }
            }

            return null;
        }

        public String getToken() {
            return token;
        }
    }

    public static final double MAX_VALUE = Math.pow(10, 4);
    private final int depth;

    protected AbstractExpressionGenerator() {
        this(10);
    }

    public AbstractExpressionGenerator(int depth) {
        this.depth = depth;
    }

    public int getDepth() {
        return depth;
    }

    @NotNull
    public abstract T generate();

    protected boolean generateBrackets() {
        return Math.random() > 0.8d;
    }

    @NotNull
    protected Operation generateOperation() {
        final int operationId = (int) (Math.random() * 4d);
        final Operation result = Operation.getOperationById(operationId);
        if (result == null) {
            throw new UnsupportedOperationException("Check!");
        }
        return result;
    }

    @Nullable
    protected Function generateFunction() {
        final int functionId = (int) (Math.random() * 8d);
        return Function.getFunctionById(functionId);
    }

    // only positive values (as - operator exists)
    @NotNull
    protected Double generateNumber() {
        return Math.random() * MAX_VALUE;
    }
}

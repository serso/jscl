package jscl.math.function;

import jscl.math.Generic;
import jscl.math.operator.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.math.AbstractMathRegistry;

/**
 * User: serso
 * Date: 10/31/11
 * Time: 10:56 PM
 */
public class PostfixFunctionsRegistry extends AbstractMathRegistry<Operator> {

    private final static PostfixFunctionsRegistry instance = new PostfixFunctionsRegistry();

    static {
        instance.add(new DoubleFactorial(null));
        instance.add(new Factorial(null));
        instance.add(new Degree(null));
        instance.add(new Percent(null, null));
    }

    @NotNull
    public static PostfixFunctionsRegistry getInstance() {
        return instance;
    }

    @Nullable
    public Operator get(@NotNull String name, @NotNull Generic[] parameters) {
        final Operator operator = super.get(name);
        return operator == null ? null : operator.newInstance(parameters);
    }

    @Override
    public Operator get(@NotNull String name) {
        final Operator operator = super.get(name);
        return operator == null ? null : (Operator) operator.newInstance();
    }
}

package jscl.math.operator;

import jscl.AngleUnit;
import jscl.JsclMathEngine;
import jscl.math.Generic;
import jscl.math.Variable;
import jscl.text.ParserUtils;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 10/31/11
 * Time: 10:58 PM
 */
public class Degree extends PostfixFunction {

    public static final String NAME = "°";

    public Degree(Generic expression) {
        super(NAME, new Generic[]{expression});
    }

    private Degree(Generic[] parameter) {
        super(NAME, ParserUtils.copyOf(parameter, 1));
    }

    @Override
    public int getMinParameters() {
        return 1;
    }

    public Generic selfExpand() {
        return expressionValue();
    }

    @Override
    public Generic selfNumeric() {
        return AngleUnit.deg.transform(JsclMathEngine.getInstance().getAngleUnits(), parameters[0]);
    }

    @NotNull
    @Override
    public Operator newInstance(@NotNull Generic[] parameters) {
        return new Degree(parameters);
    }

    @NotNull
    @Override
    public Variable newInstance() {
        return new Degree((Generic) null);
    }
}

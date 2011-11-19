package jscl.math.function.hyperbolic;

import jscl.math.Generic;
import jscl.math.JsclInteger;
import jscl.math.NumericWrapper;
import jscl.math.Variable;
import jscl.math.function.*;

public class Acosh extends ArcTrigonometric {
    public Acosh(Generic generic) {
        super("acosh",new Generic[] {generic});
    }

    public Generic derivative(int n) {
        return new Inv(
            new Sqrt(
                parameters[0].pow(2).subtract(
                    JsclInteger.valueOf(1)
                )
            ).evaluate()
        ).evaluate();
    }

    public Generic evaluate() {
        if(parameters[0].signum()==0) {
            return JsclInteger.valueOf(0);
        }
        return expressionValue();
    }

    public Generic evaluateElementary() {
        return new Ln(
            new Root(
                new Generic[] {
                    JsclInteger.valueOf(-1),
                    JsclInteger.valueOf(2).multiply(parameters[0]),
                    JsclInteger.valueOf(-1)
                },
                0
            ).evaluateElementary()
        ).evaluateElementary();
    }

    public Generic evaluateNumerically() {
        return ((NumericWrapper) parameters[0]).acosh();
    }

    public Variable newInstance() {
        return new Acosh(null);
    }
}

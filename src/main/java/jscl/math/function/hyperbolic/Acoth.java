package jscl.math.function.hyperbolic;

import jscl.math.Generic;
import jscl.math.JsclInteger;
import jscl.math.NumericWrapper;
import jscl.math.Variable;
import jscl.math.function.*;

public class Acoth extends ArcTrigonometric {
    public Acoth(Generic generic) {
        super("acoth",new Generic[] {generic});
    }

    public Generic derivative(int n) {
        return new Inv(
            JsclInteger.valueOf(1).subtract(
                parameters[0].pow(2)
            )
        ).evaluate();
    }

    public Generic evaluate() {
        if(parameters[0].signum()<0) {
            return new Acoth(parameters[0].negate()).evaluate().negate();
        }
        return expressionValue();
    }

    public Generic evaluateElementary() {
        return new Ln(
            new Root(
                new Generic[] {
                    JsclInteger.valueOf(1).add(parameters[0]),
                    JsclInteger.valueOf(0),
                    JsclInteger.valueOf(1).subtract(parameters[0])
                },
                0
            ).evaluateElementary()
        ).evaluateElementary();
    }

    public Generic evaluateNumerically() {
        return ((NumericWrapper) parameters[0]).acoth();
    }

    public Variable newInstance() {
        return new Acoth(null);
    }
}

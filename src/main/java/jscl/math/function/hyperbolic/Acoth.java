package jscl.math.function.hyperbolic;

import jscl.math.Generic;
import jscl.math.JSCLInteger;
import jscl.math.NumericWrapper;
import jscl.math.Variable;
import jscl.math.function.ArcTrigonometric;
import jscl.math.function.Inv;
import jscl.math.function.Lg;
import jscl.math.function.Root;

public class Acoth extends ArcTrigonometric {
    public Acoth(Generic generic) {
        super("acoth",new Generic[] {generic});
    }

    public Generic derivative(int n) {
        return new Inv(
            JSCLInteger.valueOf(1).subtract(
                parameter[0].pow(2)
            )
        ).evaluate();
    }

    public Generic evaluate() {
        if(parameter[0].signum()<0) {
            return new Acoth(parameter[0].negate()).evaluate().negate();
        }
        return expressionValue();
    }

    public Generic evaluateElementary() {
        return new Lg(
            new Root(
                new Generic[] {
                    JSCLInteger.valueOf(1).add(parameter[0]),
                    JSCLInteger.valueOf(0),
                    JSCLInteger.valueOf(1).subtract(parameter[0])
                },
                0
            ).evaluateElementary()
        ).evaluateElementary();
    }

    public Generic evaluateNumerically() {
        return ((NumericWrapper)parameter[0]).acoth();
    }

    public Variable newInstance() {
        return new Acoth(null);
    }
}

package jscl.math.function.trigonometric;

import jscl.math.Generic;
import jscl.math.JsclInteger;
import jscl.math.NumericWrapper;
import jscl.math.Variable;
import jscl.math.function.*;

public class Acot extends ArcTrigonometric {
    public Acot(Generic generic) {
        super("acot",new Generic[] {generic});
    }

    public Generic derivative(int n) {
        return new Inv(
            JsclInteger.valueOf(1).add(parameters[0].pow(2))
        ).evaluate().negate();
    }

    public Generic evaluate() {
		if (parameters[0].signum() < 0) {
			return Constant.pi.subtract(new Acot(parameters[0].negate()).evaluate());
		}

        return expressionValue();
    }

    public Generic evaluateElementary() {
        return Constant.i.multiply(
            new Ln(
                new Root(
                    new Generic[] {
                        Constant.i.add(parameters[0]),
                        JsclInteger.valueOf(0),
                        Constant.i.subtract(parameters[0])
                    },
                    0
                ).evaluateElementary()
            ).evaluateElementary()
        );
    }

    public Generic evaluateNumerically() {
        return ((NumericWrapper) parameters[0]).acot();
    }

    public Variable newInstance() {
        return new Acot(null);
    }
}

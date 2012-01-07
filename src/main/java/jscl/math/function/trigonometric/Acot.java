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
        return new Inverse(
            JsclInteger.valueOf(1).add(parameters[0].pow(2))
        ).evaluate().negate();
    }

    public Generic evaluate() {
		if (parameters[0].signum() < 0) {
			return Constants.Generic.PI.subtract(new Acot(parameters[0].negate()).evaluate());
		}

        return expressionValue();
    }

    public Generic selfElementary() {
        return Constants.Generic.I.multiply(
            new Ln(
                new Root(
                    new Generic[] {
                        Constants.Generic.I.add(parameters[0]),
                        JsclInteger.valueOf(0),
                        Constants.Generic.I.subtract(parameters[0])
                    },
                    0
                ).selfElementary()
            ).selfElementary()
        );
    }

    public Generic selfNumeric() {
        return ((NumericWrapper) parameters[0]).acot();
    }

    public Variable newInstance() {
        return new Acot(null);
    }
}

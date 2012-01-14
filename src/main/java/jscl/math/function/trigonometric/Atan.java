package jscl.math.function.trigonometric;

import jscl.math.Generic;
import jscl.math.JsclInteger;
import jscl.math.NumericWrapper;
import jscl.math.Variable;
import jscl.math.function.*;
import org.jetbrains.annotations.NotNull;

public class Atan extends ArcTrigonometric {
    public Atan(Generic generic) {
        super("atan",new Generic[] {generic});
    }

    public Generic derivative(int n) {
        return new Inverse(
            JsclInteger.valueOf(1).add(parameters[0].pow(2))
        ).evaluate();
    }

    public Generic evaluate() {
        if(parameters[0].signum()<0) {
            return new Atan(parameters[0].negate()).evaluate().negate();
        } else if(parameters[0].signum()==0) {
            return JsclInteger.valueOf(0);
        }
        return expressionValue();
    }

    public Generic selfElementary() {
        return Constants.Generic.I.multiply(
            new Ln(
                new Root(
                    new Generic[] {
                        JsclInteger.valueOf(-1).add(Constants.Generic.I.multiply(parameters[0])),
                        JsclInteger.valueOf(0),
                        JsclInteger.valueOf(1).add(Constants.Generic.I.multiply(parameters[0]))
                    },
                    0
                ).selfElementary()
            ).selfElementary()
        );
    }

    public Generic selfNumeric() {
        return ((NumericWrapper) parameters[0]).atan();
    }

    @NotNull
	public Variable newInstance() {
        return new Atan(null);
    }
}

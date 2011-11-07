package jscl.math.function.trigonometric;

import jscl.math.Generic;
import jscl.math.JsclInteger;
import jscl.math.NumericWrapper;
import jscl.math.Variable;
import jscl.math.function.ArcTrigonometric;
import jscl.math.function.Constant;
import jscl.math.function.Inv;
import jscl.math.function.Lg;
import jscl.math.function.Root;

public class Acot extends ArcTrigonometric {
    public Acot(Generic generic) {
        super("acot",new Generic[] {generic});
    }

    public Generic derivative(int n) {
        return new Inv(
            JsclInteger.valueOf(1).add(parameter[0].pow(2))
        ).evaluate().negate();
    }

    public Generic evaluate() {
        if(parameter[0].signum()<0) {
            return Constant.pi.subtract(new Acot(parameter[0].negate()).evaluate());
        }
        return expressionValue();
    }

    public Generic evaluateElementary() {
        return Constant.i.multiply(
            new Lg(
                new Root(
                    new Generic[] {
                        Constant.i.add(parameter[0]),
                        JsclInteger.valueOf(0),
                        Constant.i.subtract(parameter[0])
                    },
                    0
                ).evaluateElementary()
            ).evaluateElementary()
        );
    }

    public Generic evaluateNumerically() {
        return ((NumericWrapper)parameter[0]).acot();
    }

    public Variable newInstance() {
        return new Acot(null);
    }
}

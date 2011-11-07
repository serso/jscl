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
import jscl.math.function.Sqrt;

public class Asin extends ArcTrigonometric {
    public Asin(Generic generic) {
        super("asin",new Generic[] {generic});
    }

    public Generic derivative(int n) {
        return new Inv(
            new Sqrt(
                JsclInteger.valueOf(1).subtract(parameter[0].pow(2))
            ).evaluate()
        ).evaluate();
    }

    public Generic evaluate() {
        if(parameter[0].signum()<0) {
            return new Asin(parameter[0].negate()).evaluate().negate();
        } else if(parameter[0].signum()==0) {
            return JsclInteger.valueOf(0);
        }
        return expressionValue();
    }

    public Generic evaluateElementary() {
        return Constant.i.multiply(
            new Lg(
                new Root(
                    new Generic[] {
                        JsclInteger.valueOf(-1),
                        JsclInteger.valueOf(2).multiply(Constant.i.multiply(parameter[0])),
                        JsclInteger.valueOf(1)
                    },
                    0
                ).evaluateElementary()
            ).evaluateElementary()
        );
    }

    public Generic evaluateNumerically() {
        return ((NumericWrapper)parameter[0]).asin();
    }

    public Variable newInstance() {
        return new Asin(null);
    }
}

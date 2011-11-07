package jscl.math.function;

import jscl.math.Generic;
import jscl.math.JsclInteger;
import jscl.math.NotDivisibleException;
import jscl.math.Variable;

public class Inv extends Frac {
    public Inv(Generic generic) {
        super(JsclInteger.valueOf(1),generic);
    }

    public Generic evaluate() {
        try {
            return JsclInteger.valueOf(1).divide(parameter());
        } catch (NotDivisibleException e) {}
        return expressionValue();
    }

    public Generic parameter() {
        return parameter[1];
    }

    public Variable newInstance() {
        return new Inv(null);
    }
}

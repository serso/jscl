package jscl.math.operator.number;

import jscl.math.Generic;
import jscl.math.JsclInteger;
import jscl.math.NotIntegerException;
import jscl.math.Variable;
import jscl.math.operator.Operator;

public class ModPow extends Operator {
    public ModPow(Generic integer, Generic exponent, Generic modulo) {
        super("modpow",new Generic[] {integer,exponent,modulo});
    }

    public Generic compute() {
        try {
            JsclInteger en= parameters[0].integerValue();
            JsclInteger exponent= parameters[1].integerValue();
            JsclInteger modulo= parameters[2].integerValue();
            return en.modPow(exponent,modulo);
        } catch (NotIntegerException e) {}
        return expressionValue();
    }

    public Variable newInstance() {
        return new ModPow(null,null,null);
    }
}

package jscl.math.operator;

import jscl.math.Generic;
import jscl.math.JsclInteger;
import jscl.math.NotIntegerException;
import jscl.math.Variable;

public class Modulo extends Operator {
    public Modulo(Generic expression1, Generic expression2) {
        super("mod",new Generic[] {expression1,expression2});
    }

    public Generic compute() {
        try {
            JsclInteger en=parameter[0].integerValue();
            JsclInteger en2=parameter[1].integerValue();
            return en.mod(en2);
        } catch (NotIntegerException e) {}
        return parameter[0].remainder(parameter[1]);
    }

    public Variable newInstance() {
        return new Modulo(null,null);
    }
}

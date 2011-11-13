package jscl.math.operator.number;

import jscl.math.*;
import jscl.math.JsclInteger;
import jscl.math.operator.Operator;

public class PrimitiveRoots extends Operator {
    public PrimitiveRoots(Generic integer) {
        super("primitiveroots",new Generic[] {integer});
    }

    public Generic compute() {
        try {
            JsclInteger en= parameters[0].integerValue();
            Generic a[]=en.primitiveRoots();
            return new JsclVector(a.length>0?a:new Generic[] {JsclInteger.valueOf(0)});
        } catch (NotIntegerException e) {}
        return expressionValue();
    }

    public Variable newInstance() {
        return new PrimitiveRoots(null);
    }
}

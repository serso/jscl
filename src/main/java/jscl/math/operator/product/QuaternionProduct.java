package jscl.math.operator.product;

import jscl.math.Generic;
import jscl.math.JsclVector;
import jscl.math.Variable;
import jscl.math.operator.VectorOperator;
import jscl.mathml.MathML;

public class QuaternionProduct extends VectorOperator {
    public QuaternionProduct(Generic vector1, Generic vector2) {
        super("quaternion",new Generic[] {vector1,vector2});
    }

    public Generic compute() {
        if(parameters[0] instanceof JsclVector && parameters[1] instanceof JsclVector) {
            JsclVector v1=(JsclVector) parameters[0];
            JsclVector v2=(JsclVector) parameters[1];
            return v1.quaternionProduct(v2);
        }
        return expressionValue();
    }

    protected void bodyToMathML(MathML element) {
        parameters[0].toMathML(element,null);
        parameters[1].toMathML(element,null);
    }

    public Variable newInstance() {
        return new QuaternionProduct(null,null);
    }
}

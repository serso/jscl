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
        if(parameter[0] instanceof JsclVector && parameter[1] instanceof JsclVector) {
            JsclVector v1=(JsclVector)parameter[0];
            JsclVector v2=(JsclVector)parameter[1];
            return v1.quaternionProduct(v2);
        }
        return expressionValue();
    }

    protected void bodyToMathML(MathML element) {
        parameter[0].toMathML(element,null);
        parameter[1].toMathML(element,null);
    }

    public Variable newInstance() {
        return new QuaternionProduct(null,null);
    }
}

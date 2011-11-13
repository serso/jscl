package jscl.math.operator.product;

import jscl.math.Generic;
import jscl.math.JsclVector;
import jscl.math.Variable;
import jscl.math.operator.VectorOperator;
import jscl.mathml.MathML;

public class VectorProduct extends VectorOperator {
    public VectorProduct(Generic vector1, Generic vector2) {
        super("vector",new Generic[] {vector1,vector2});
    }

    public Generic compute() {
        if(parameters[0] instanceof JsclVector && parameters[1] instanceof JsclVector) {
            JsclVector v1=(JsclVector) parameters[0];
            JsclVector v2=(JsclVector) parameters[1];
            return v1.vectorProduct(v2);
        }
        return expressionValue();
    }

    protected void bodyToMathML(MathML element) {
        parameters[0].toMathML(element,null);
        MathML e1=element.element("mo");
        e1.appendChild(element.text("\u2227"));
        element.appendChild(e1);
        parameters[1].toMathML(element,null);
    }

    public Variable newInstance() {
        return new VectorProduct(null,null);
    }
}

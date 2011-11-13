package jscl.math.operator.vector;

import jscl.math.Generic;
import jscl.math.JsclVector;
import jscl.math.Variable;
import jscl.math.operator.VectorOperator;
import jscl.mathml.MathML;

public class Divergence extends VectorOperator {
    public Divergence(Generic vector, Generic variable) {
        super("diverg",new Generic[] {vector,variable});
    }

    public Generic compute() {
        Variable variable[]=variables(parameters[1]);
        if(parameters[0] instanceof JsclVector) {
            JsclVector vector=(JsclVector) parameters[0];
            return vector.divergence(variable);
        }
        return expressionValue();
    }

    protected void bodyToMathML(MathML element) {
        operator(element,"nabla");
        parameters[0].toMathML(element,null);
    }

    public Variable newInstance() {
        return new Divergence(null,null);
    }
}

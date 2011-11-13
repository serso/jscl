package jscl.math.operator.vector;

import jscl.math.Generic;
import jscl.math.JsclVector;
import jscl.math.Variable;
import jscl.math.operator.VectorOperator;
import jscl.math.operator.product.GeometricProduct;
import jscl.mathml.MathML;

public class Del extends VectorOperator {
    public Del(Generic vector, Generic variable, Generic algebra) {
        super("del",new Generic[] {vector,variable,algebra});
    }

    public Generic compute() {
        Variable variable[]=variables(parameters[1]);
        int algebra[]=GeometricProduct.algebra(parameters[2]);
        if(parameters[0] instanceof JsclVector) {
            JsclVector vector=(JsclVector) parameters[0];
            return vector.del(variable,algebra);
        }
        return expressionValue();
    }

    public String toString() {
        StringBuffer buffer=new StringBuffer();
        int n=3;
        if(parameters[2].signum()==0) n=2;
        buffer.append(name);
        buffer.append("(");
        for(int i=0;i<n;i++) {
            buffer.append(parameters[i]).append(i<n-1?", ":"");
        }
        buffer.append(")");
        return buffer.toString();
    }

    protected void bodyToMathML(MathML element) {
        operator(element,"nabla");
        parameters[0].toMathML(element,null);
    }

    public Variable newInstance() {
        return new Del(null,null,null);
    }
}

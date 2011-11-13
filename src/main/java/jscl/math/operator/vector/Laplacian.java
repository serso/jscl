package jscl.math.operator.vector;

import jscl.math.Expression;
import jscl.math.Generic;
import jscl.math.Variable;
import jscl.math.operator.VectorOperator;
import jscl.mathml.MathML;

public class Laplacian extends VectorOperator {
    public Laplacian(Generic vector, Generic variable) {
        super("laplacian",new Generic[] {vector,variable});
    }

    public Generic compute() {
        Variable variable[]=variables(parameters[1]);
        Expression expression= parameters[0].expressionValue();
        return expression.laplacian(variable);
    }

    protected void bodyToMathML(MathML element) {
        operator(element,"Delta");
        parameters[0].toMathML(element,null);
    }

    public Variable newInstance() {
        return new Laplacian(null,null);
    }
}

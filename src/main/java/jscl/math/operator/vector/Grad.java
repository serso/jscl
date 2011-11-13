package jscl.math.operator.vector;

import jscl.math.Expression;
import jscl.math.Generic;
import jscl.math.Variable;
import jscl.math.operator.VectorOperator;
import jscl.mathml.MathML;

public class Grad extends VectorOperator {
    public Grad(Generic expression, Generic variable) {
        super("grad",new Generic[] {expression,variable});
    }

    public Generic compute() {
        Variable variable[]=variables(parameters[1]);
        Expression expression= parameters[0].expressionValue();
        return expression.grad(variable);
    }

    protected void bodyToMathML(MathML element) {
        operator(element,"nabla");
        parameters[0].toMathML(element,null);
    }

    public Variable newInstance() {
        return new Grad(null,null);
    }
}

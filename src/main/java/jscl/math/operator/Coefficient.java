package jscl.math.operator;

import jscl.math.Generic;
import jscl.math.JsclVector;
import jscl.math.Variable;
import jscl.math.polynomial.Polynomial;

public class Coefficient extends Operator {
    public Coefficient(Generic expression, Generic variable) {
        super("coef",new Generic[] {expression,variable});
    }

    public Generic compute() {
        Variable variable= parameters[1].variableValue();
        if(parameters[0].isPolynomial(variable)) {
            return new JsclVector(Polynomial.factory(variable).valueof(parameters[0]).elements());
        }
        return expressionValue();
    }

    public Variable newInstance() {
        return new Coefficient(null,null);
    }
}

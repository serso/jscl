package jscl.math.operator;

import jscl.math.Generic;
import jscl.math.JsclVector;
import jscl.math.Variable;
import jscl.math.polynomial.Polynomial;
import org.jetbrains.annotations.NotNull;

public class Coefficient extends Operator {

	public static final String NAME = "coef";

	public Coefficient(Generic expression, Generic variable) {
        super(NAME,new Generic[] {expression,variable});
    }

	private Coefficient(Generic parameters[]) {
		super(NAME, parameters);
	}

	@Override
	public int getMinimumNumberOfParameters() {
		return 2;
	}

	public Generic compute() {
        Variable variable= parameters[1].variableValue();
        if(parameters[0].isPolynomial(variable)) {
            return new JsclVector(Polynomial.factory(variable).valueof(parameters[0]).elements());
        }
        return expressionValue();
    }

	@NotNull
	@Override
	public Operator newInstance(@NotNull Generic[] parameters) {
		return new Coefficient(parameters);
	}

	public Variable newInstance() {
        return new Coefficient(null,null);
    }
}

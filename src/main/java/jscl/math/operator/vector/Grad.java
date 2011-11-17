package jscl.math.operator.vector;

import jscl.math.Expression;
import jscl.math.Generic;
import jscl.math.Variable;
import jscl.math.operator.Operator;
import jscl.math.operator.VectorOperator;
import jscl.mathml.MathML;
import org.jetbrains.annotations.NotNull;

public class Grad extends VectorOperator {

	public static final String NAME = "grad";

	public Grad(Generic expression, Generic variable) {
        super(NAME,new Generic[] {expression,variable});
    }

	private Grad(Generic parameter[]) {
		super(NAME, parameter);
	}

	public Generic compute() {
        Variable variable[]=variables(parameters[1]);
        Expression expression= parameters[0].expressionValue();
        return expression.grad(variable);
    }

	@NotNull
	@Override
	public Operator newInstance(@NotNull Generic[] parameters) {
		return new Grad(parameters);
	}

	protected void bodyToMathML(MathML element) {
        operator(element,"nabla");
        parameters[0].toMathML(element,null);
    }

    public Variable newInstance() {
        return new Grad(null,null);
    }
}

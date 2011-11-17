package jscl.math.operator.vector;

import jscl.math.Generic;
import jscl.math.JsclVector;
import jscl.math.Variable;
import jscl.math.operator.Operator;
import jscl.math.operator.VectorOperator;
import jscl.mathml.MathML;
import org.jetbrains.annotations.NotNull;

public class Divergence extends VectorOperator {

	public static final String NAME = "diverg";

	public Divergence(Generic vector, Generic variable) {
        super(NAME,new Generic[] {vector,variable});
    }

	private Divergence(@NotNull Generic parameter[]) {
		super(NAME, parameter);
	}

	@Override
	public int getMinimumNumberOfParameters() {
		return 2;
	}

	public Generic compute() {
        Variable variable[]=variables(parameters[1]);
        if(parameters[0] instanceof JsclVector) {
            JsclVector vector=(JsclVector) parameters[0];
            return vector.divergence(variable);
        }
        return expressionValue();
    }

	@NotNull
	@Override
	public Operator newInstance(@NotNull Generic[] parameters) {
		return new Divergence(parameters);
	}

	protected void bodyToMathML(MathML element) {
        operator(element,"nabla");
        parameters[0].toMathML(element,null);
    }

    public Variable newInstance() {
        return new Divergence(null,null);
    }
}

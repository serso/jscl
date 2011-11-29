package jscl.math.operator.vector;

import jscl.math.Generic;
import jscl.math.JsclVector;
import jscl.math.Variable;
import jscl.math.operator.Operator;
import jscl.math.operator.VectorOperator;
import jscl.mathml.MathML;
import org.jetbrains.annotations.NotNull;

public class Curl extends VectorOperator {

	public static final String NAME = "curl";

	public Curl(Generic vector, Generic variable) {
        super(NAME,new Generic[] {vector,variable});
    }

	private Curl(Generic parameter[]) {
		super(NAME, parameter);
	}

	@Override
	public int getMinimumNumberOfParameters() {
		return 2;
	}

	public Generic evaluate() {
        Variable variable[]=variables(parameters[1]);
        if(parameters[0] instanceof JsclVector) {
            JsclVector vector=(JsclVector) parameters[0];
            return vector.curl(variable);
        }
        return expressionValue();
    }

	@NotNull
	@Override
	public Operator newInstance(@NotNull Generic[] parameters) {
		return new Curl(parameters);
	}

	protected void bodyToMathML(MathML element) {
        operator(element,"nabla");
        MathML e1=element.element("mo");
        e1.appendChild(element.text("\u2227"));
        element.appendChild(e1);
        parameters[0].toMathML(element,null);
    }

    public Variable newInstance() {
        return new Curl(null,null);
    }
}

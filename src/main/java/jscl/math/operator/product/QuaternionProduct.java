package jscl.math.operator.product;

import jscl.math.Generic;
import jscl.math.JsclVector;
import jscl.math.Variable;
import jscl.math.operator.Operator;
import jscl.math.operator.VectorOperator;
import jscl.mathml.MathML;
import org.jetbrains.annotations.NotNull;

public class QuaternionProduct extends VectorOperator {

	public static final String NAME = "quaternion";

	public QuaternionProduct(Generic vector1, Generic vector2) {
        super(NAME,new Generic[] {vector1,vector2});
    }

	private QuaternionProduct(Generic parameter[]) {
		super(NAME, parameter);
	}

	@Override
	public int getMinParameters() {
		return 2;
	}

	public Generic selfExpand() {
        if(parameters[0] instanceof JsclVector && parameters[1] instanceof JsclVector) {
            JsclVector v1=(JsclVector) parameters[0];
            JsclVector v2=(JsclVector) parameters[1];
            return v1.quaternionProduct(v2);
        }
        return expressionValue();
    }

	@NotNull
	@Override
	public Operator newInstance(@NotNull Generic[] parameters) {
		return new QuaternionProduct(parameters);
	}

	protected void bodyToMathML(MathML element) {
        parameters[0].toMathML(element,null);
        parameters[1].toMathML(element,null);
    }

    @NotNull
	public Variable newInstance() {
        return new QuaternionProduct(null,null);
    }
}

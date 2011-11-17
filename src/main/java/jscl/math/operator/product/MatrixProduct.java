package jscl.math.operator.product;

import jscl.math.Generic;
import jscl.math.Matrix;
import jscl.math.Variable;
import jscl.math.operator.Operator;
import jscl.math.operator.VectorOperator;
import jscl.mathml.MathML;
import org.jetbrains.annotations.NotNull;

public class MatrixProduct extends VectorOperator {

	public static final String NAME = "matrix";

	public MatrixProduct(Generic matrix1, Generic matrix2) {
        super(NAME,new Generic[] {matrix1,matrix2});
    }

	private MatrixProduct(Generic parameter[]) {
		super(NAME, parameter);
	}

	@Override
	public int getMinimumNumberOfParameters() {
		return 2;
	}

	public Generic compute() {
        if(Matrix.product(parameters[0], parameters[1])) {
            return parameters[0].multiply(parameters[1]);
        }
        return expressionValue();
    }

    public String toJava() {
		final StringBuilder result = new StringBuilder();
        result.append(parameters[0].toJava());
        result.append(".multiply(");
        result.append(parameters[1].toJava());
        result.append(")");
        return result.toString();
    }

	@NotNull
	@Override
	public Operator newInstance(@NotNull Generic[] parameters) {
		return new MatrixProduct(parameters);
	}

	protected void bodyToMathML(MathML element) {
        parameters[0].toMathML(element,null);
        parameters[1].toMathML(element,null);
    }

    public Variable newInstance() {
        return new MatrixProduct(null,null);
    }
}

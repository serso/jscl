package jscl.math.operator.product;

import jscl.math.Generic;
import jscl.math.Matrix;
import jscl.math.Variable;
import jscl.math.operator.VectorOperator;
import jscl.mathml.MathML;

public class MatrixProduct extends VectorOperator {
    public MatrixProduct(Generic matrix1, Generic matrix2) {
        super("matrix",new Generic[] {matrix1,matrix2});
    }

    public Generic compute() {
        if(Matrix.product(parameters[0], parameters[1])) {
            return parameters[0].multiply(parameters[1]);
        }
        return expressionValue();
    }

    public String toJava() {
        StringBuffer buffer=new StringBuffer();
        buffer.append(parameters[0].toJava());
        buffer.append(".multiply(");
        buffer.append(parameters[1].toJava());
        buffer.append(")");
        return buffer.toString();
    }

    protected void bodyToMathML(MathML element) {
        parameters[0].toMathML(element,null);
        parameters[1].toMathML(element,null);
    }

    public Variable newInstance() {
        return new MatrixProduct(null,null);
    }
}

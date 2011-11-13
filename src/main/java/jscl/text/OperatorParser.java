package jscl.text;

import jscl.math.Expression;
import jscl.math.Generic;
import jscl.math.JsclInteger;
import jscl.math.function.Constant;
import jscl.math.operator.Coefficient;
import jscl.math.operator.Derivative;
import jscl.math.operator.Division;
import jscl.math.operator.Groebner;
import jscl.math.operator.IndefiniteIntegral;
import jscl.math.operator.Integral;
import jscl.math.operator.Limit;
import jscl.math.operator.Modulo;
import jscl.math.operator.Operator;
import jscl.math.operator.Product;
import jscl.math.operator.Solve;
import jscl.math.operator.Substitute;
import jscl.math.operator.Sum;
import jscl.math.operator.matrix.Determinant;
import jscl.math.operator.matrix.Trace;
import jscl.math.operator.matrix.Transpose;
import jscl.math.operator.number.EulerPhi;
import jscl.math.operator.number.ModInverse;
import jscl.math.operator.number.ModPow;
import jscl.math.operator.number.PrimitiveRoots;
import jscl.math.operator.product.ComplexProduct;
import jscl.math.operator.product.GeometricProduct;
import jscl.math.operator.product.MatrixProduct;
import jscl.math.operator.product.QuaternionProduct;
import jscl.math.operator.product.TensorProduct;
import jscl.math.operator.product.VectorProduct;
import jscl.math.operator.vector.Curl;
import jscl.math.operator.vector.Dalembertian;
import jscl.math.operator.vector.Del;
import jscl.math.operator.vector.Divergence;
import jscl.math.operator.vector.Grad;
import jscl.math.operator.vector.Jacobian;
import jscl.math.operator.vector.Laplacian;
import org.jetbrains.annotations.NotNull;

public class OperatorParser implements Parser<Operator> {
	public static final Parser<Operator> parser = new OperatorParser();

	private OperatorParser() {
	}

	public Operator parse(@NotNull String string, @NotNull MutableInt position, int depth) throws ParseException {
		int pos0 = position.intValue();

		String operatorName;
		try {
			operatorName = Identifier.parser.parse(string, position, depth);
			if (!valid(operatorName)) {
				position.setValue(pos0);
				throw new ParseException();
			}
		} catch (ParseException e) {
			throw e;
		}

		final Generic parameters[];
		try {
			parameters = (Generic[]) ParameterList.parser.parse(string, position, depth);
		} catch (ParseException e) {
			position.setValue(pos0);
			throw e;
		}

		Operator v = null;
		if (operatorName.compareTo("d") == 0)
			v = new Derivative(parameters[0], parameters[1], parameters.length > 2 ? parameters[2] : parameters[1], parameters.length > 3 ? parameters[3] : JsclInteger.valueOf(1));
		else if (operatorName.compareTo("grad") == 0) v = new Grad(parameters[0], parameters[1]);
		else if (operatorName.compareTo("diverg") == 0) v = new Divergence(parameters[0], parameters[1]);
		else if (operatorName.compareTo("curl") == 0) v = new Curl(parameters[0], parameters[1]);
		else if (operatorName.compareTo("jacobian") == 0) v = new Jacobian(parameters[0], parameters[1]);
		else if (operatorName.compareTo("laplacian") == 0) v = new Laplacian(parameters[0], parameters[1]);
		else if (operatorName.compareTo("dalembertian") == 0) v = new Dalembertian(parameters[0], parameters[1]);
		else if (operatorName.compareTo("del") == 0)
			v = new Del(parameters[0], parameters[1], parameters.length > 2 ? parameters[2] : JsclInteger.valueOf(0));
		else if (operatorName.compareTo("vector") == 0) v = new VectorProduct(parameters[0], parameters[1]);
		else if (operatorName.compareTo("complex") == 0) v = new ComplexProduct(parameters[0], parameters[1]);
		else if (operatorName.compareTo("quaternion") == 0) v = new QuaternionProduct(parameters[0], parameters[1]);
		else if (operatorName.compareTo("geometric") == 0)
			v = new GeometricProduct(parameters[0], parameters[1], parameters.length > 2 ? parameters[2] : JsclInteger.valueOf(0));
		else if (operatorName.compareTo("matrix") == 0) v = new MatrixProduct(parameters[0], parameters[1]);
		else if (operatorName.compareTo("tensor") == 0) v = new TensorProduct(parameters[0], parameters[1]);
		else if (operatorName.compareTo("tran") == 0) v = new Transpose(parameters[0]);
		else if (operatorName.compareTo("trace") == 0) v = new Trace(parameters[0]);
		else if (operatorName.compareTo("det") == 0) v = new Determinant(parameters[0]);
		else if (operatorName.compareTo("coef") == 0) v = new Coefficient(parameters[0], parameters[1]);
		else if (operatorName.compareTo("solve") == 0)
			v = new Solve(parameters[0], parameters[1], parameters.length > 2 ? parameters[2] : JsclInteger.valueOf(0));
		else if (operatorName.compareTo("subst") == 0)
			v = new Substitute(parameters[0], parameters[1], parameters[2]).transmute();
		else if (operatorName.compareTo("lim") == 0)
			v = new Limit(parameters[0], parameters[1], parameters[2], parameters.length > 3 && (parameters[2].compareTo(Constant.infinity) != 0 && parameters[2].compareTo(Constant.infinity.negate()) != 0) ? JsclInteger.valueOf(parameters[3].signum()) : JsclInteger.valueOf(0));
		else if (operatorName.compareTo("sum") == 0)
			v = new Sum(parameters[0], parameters[1], parameters[2], parameters[3]);
		else if (operatorName.compareTo("prod") == 0)
			v = new Product(parameters[0], parameters[1], parameters[2], parameters[3]);
		else if (operatorName.compareTo("integral") == 0)
			v = parameters.length > 2 ? (Operator) new Integral(parameters[0], parameters[1], parameters[2], parameters[3]) : new IndefiniteIntegral(parameters[0], parameters[1]);
		else if (operatorName.compareTo("groebner") == 0)
			v = new Groebner(parameters[0], parameters[1], parameters.length > 2 ? parameters[2] : Expression.valueOf("lex"), parameters.length > 3 ? parameters[3] : JsclInteger.valueOf(0)).transmute();
		else if (operatorName.compareTo("div") == 0) v = new Division(parameters[0], parameters[1]);
		else if (operatorName.compareTo("mod") == 0) v = new Modulo(parameters[0], parameters[1]);
		else if (operatorName.compareTo("modpow") == 0) v = new ModPow(parameters[0], parameters[1], parameters[2]);
		else if (operatorName.compareTo("modinv") == 0) v = new ModInverse(parameters[0], parameters[1]);
		else if (operatorName.compareTo("eulerphi") == 0) v = new EulerPhi(parameters[0]);
		else if (operatorName.compareTo("primitiveroots") == 0) v = new PrimitiveRoots(parameters[0]);
		return v;
	}

	static boolean valid(String name) {
		for (int i = 0; i < na.length; i++) if (name.compareTo(na[i]) == 0) return true;
		return false;
	}

	private static String na[] = {"d", "grad", "diverg", "curl", "jacobian", "laplacian", "dalembertian", "del", "vector", "complex", "quaternion", "geometric", "matrix", "tensor", "tran", "trace", "det", "coef", "solve", "subst", "lim", "sum", "prod", "integral", "groebner", "div", "mod", "modpow", "modinv", "eulerphi", "primitiveroots"};
}

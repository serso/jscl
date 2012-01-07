package jscl.math.function;

import jscl.math.JsclInteger;

/**
 * User: serso
 * Date: 1/7/12
 * Time: 3:40 PM
 */
public final class Constants {

	public static final Constant PI = new Constant("π");
	public static final Constant PI_INV = new Constant("Π");
	public static final Constant I = new Constant("i");
	public static final Constant INF = new Constant("∞");
	public static final Constant INF_2 = new Constant("Infinity");

	public static class Generic {

		public static final jscl.math.Generic E = new Exp(JsclInteger.ONE).expressionValue();
		public static final jscl.math.Generic PI = Constants.PI.expressionValue();
		public static final jscl.math.Generic PI_INV = Constants.PI_INV.expressionValue();
		public static final jscl.math.Generic INF = Constants.INF.expressionValue();
		public static final jscl.math.Generic I = new Sqrt(JsclInteger.valueOf(-1)).expressionValue();
		// i * PI
		public static final jscl.math.Generic I_BY_PI = I.multiply(PI_INV);

		// fraction = 1/2
		public static final jscl.math.Generic HALF = new Inv(JsclInteger.valueOf(2)).expressionValue();
		// fraction = 1/3
		public static final jscl.math.Generic THIRD = new Inv(JsclInteger.valueOf(3)).expressionValue();

		// -1/2 * (1 - i * sqrt (3) )
		public static final jscl.math.Generic J = HALF.negate().multiply(JsclInteger.ONE.subtract(I.multiply(new Sqrt(JsclInteger.valueOf(3)).expressionValue())));

		// -1/2 * (1 + i * sqrt (3) )
		public static final jscl.math.Generic J_BAR = HALF.negate().multiply(JsclInteger.ONE.add(I.multiply(new Sqrt(JsclInteger.valueOf(3)).expressionValue())));

	}

	// not intended for instantiation
	private Constants() {
		throw new AssertionError();
	}
}

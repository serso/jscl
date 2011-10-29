package jscl.math.function;

import jscl.math.function.hyperbolic.*;
import jscl.math.function.trigonometric.*;
import org.jetbrains.annotations.NotNull;
import org.solovyev.common.math.AbstractMathRegistry;

/**
 * User: serso
 * Date: 10/29/11
 * Time: 12:54 PM
 */
public class FunctionsRegistry extends AbstractMathRegistry<Function> {

	private final static FunctionsRegistry instance = new FunctionsRegistry();

	static {
		instance.add(new Sin(null));
		instance.add(new Cos(null));
		instance.add(new Tan(null));
		instance.add(new Cot(null));

		instance.add(new Asin(null));
		instance.add(new Acos(null));
		instance.add(new Atan(null));
		instance.add(new Acot(null));

		instance.add(new Ln(null));
		instance.add(new Lg(null));
		instance.add(new Exp(null));
		instance.add(new Sqrt(null));
		instance.add(new Cubic(null));

		instance.add(new Sinh(null));
		instance.add(new Cosh(null));
		instance.add(new Tanh(null));
		instance.add(new Coth(null));

		instance.add(new Asinh(null));
		instance.add(new Acosh(null));
		instance.add(new Atanh(null));
		instance.add(new Acoth(null));

		instance.add(new Abs(null));
		instance.add(new Sgn(null));

		instance.add(new Conjugate(null));

		instance.add(new Comparison("eq", null, null));
		instance.add(new Comparison("le", null, null));
		instance.add(new Comparison("ge", null, null));
		instance.add(new Comparison("ne", null, null));
		instance.add(new Comparison("lt", null, null));
		instance.add(new Comparison("gt", null, null));
		instance.add(new Comparison("ap", null, null));
	}


	@NotNull
	public static FunctionsRegistry getInstance() {
		return instance;
	}

	@Override
	public Function get(@NotNull String name) {
		final Function function = super.get(name);
		return function == null ? null : (Function) function.newInstance();
	}
}

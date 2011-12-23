package jscl.math.function;

import jscl.math.Generic;
import jscl.math.JsclInteger;
import jscl.mathml.MathML;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: serso
 * Date: 11/12/11
 * Time: 3:48 PM
 */
public abstract class AbstractDms extends Algebraic {

	protected AbstractDms(@NotNull String name, Generic degrees, Generic minutes, Generic seconds) {
		super(name, new Generic[]{degrees, minutes, seconds});
	}

	@NotNull
	private static Generic[] createParameters(@Nullable Generic degrees,
											  @Nullable Generic minutes,
											  @Nullable Generic seconds) {
		final Generic[] result = new Generic[3];

		setDefaultValue(result, degrees, 0);
		setDefaultValue(result, minutes, 1);
		setDefaultValue(result, seconds, 2);

		return result;
	}

	@Override
	public int getMaxParameters() {
		return 3;
	}

	@Override
	public void setParameters(@Nullable Generic[] parameters) {
		super.setParameters(createParameters(getParameter(parameters, 0), getParameter(parameters, 1), getParameter(parameters, 2)));
	}

	private static void setDefaultValue(@NotNull Generic[] parameters,
										@Nullable Generic parameter,
										int position) {
		if ( parameter == null) {
			parameters[position] = JsclInteger.valueOf(0);
		} else {
			parameters[position] = parameter;
		}
	}

	@Override
	public Root rootValue() throws NotRootException {
		throw new UnsupportedOperationException("Root for " + name + "() is not supported!");
	}

	@Override
	void bodyToMathML(MathML element, boolean fenced) {
		final MathML child = element.element(name);
		parameters[0].toMathML(child, null);
		// todo serso: add other parameters
		element.appendChild(child);
	}

	@Override
	public Generic evaluate() {
		return expressionValue();
	}

	@Override
	public Generic selfElementary() {
		return evaluate();
	}

	@Override
	public Generic selfSimplify() {
		return evaluate();
	}

	@Override
	public Generic selfNumeric() {
		Generic degrees = parameters[0];

		if ( parameters.length > 1 && parameters[1] != null ) {
			Generic minutes = parameters[1];
			degrees = degrees.add(minutes.divide(JsclInteger.valueOf(60)));
		}

		if ( parameters.length > 2 && parameters[2] != null ) {
			Generic seconds = parameters[2];
			degrees = degrees.add(seconds.divide(JsclInteger.valueOf(60 * 60)));
		}

		return degrees;
	}

	@Override
	public Generic derivative(int n) {
		throw new UnsupportedOperationException("Derivative for " + name + "() is not supported!");
	}
}

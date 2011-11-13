package jscl.math.function;

import jscl.math.Generic;
import jscl.math.JsclInteger;
import jscl.math.Variable;
import jscl.mathml.MathML;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: serso
 * Date: 11/12/11
 * Time: 3:48 PM
 */
public class Rad extends Algebraic {

	public Rad(Generic degrees, Generic minutes, Generic seconds) {
		super("rad", createParameters(degrees, minutes, seconds));
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
		throw new UnsupportedOperationException("Root for rad() is not supported!");
	}

	@Override
	void bodyToMathML(MathML element, boolean fenced) {
		final MathML child = element.element("rad");
		parameters[0].toMathML(child, null);
		// todo serso: add other parameters
		element.appendChild(child);
	}

	@Override
	public Generic evaluate() {
		return expressionValue();
	}

	@Override
	public Generic evaluateElementary() {
		return evaluate();
	}

	@Override
	public Generic evaluateSimplify() {
		return evaluate();
	}

	@Override
	public Generic evaluateNumerically() {
		Generic degrees = parameters[0];

		if ( parameters.length > 1 && parameters[1] != null ) {
			Generic minutes = parameters[1];
			degrees = degrees.add(minutes.divide(JsclInteger.valueOf(60)));
		}

		if ( parameters.length > 2 && parameters[2] != null ) {
			Generic seconds = parameters[2];
			degrees = degrees.add(seconds.divide(JsclInteger.valueOf(60 * 60)));
		}

		return degrees.multiply(Constant.pi.numeric()).divide(JsclInteger.valueOf(180));
	}

	@Override
	public Generic derivative(int n) {
		throw new UnsupportedOperationException("Derivative for rad() is not supported!");
	}

	@Override
	public Variable newInstance() {
		return new Rad(null, null, null);
	}
}

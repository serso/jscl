package jscl.math.operator;

import jscl.math.*;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 12/26/11
 * Time: 11:09 AM
 */
public class Mean extends Operator {

	public static final String NAME = "mean";

	public Mean(JsclVector vector) {
		this(new Generic[]{vector});
	}

	private Mean(@NotNull Generic[] parameters) {
		super(NAME, parameters);
	}

	@NotNull
	@Override
	public Operator newInstance(@NotNull Generic[] parameters) {
		return new Mean(parameters);
	}

	@Override
	public int getMinParameters() {
		return 1;
	}

	@Override
	public Generic evaluate() {

		final JsclVector vector = (JsclVector)parameters[0];
		final Generic[] elements = vector.elements();

		if ( elements.length == 0 ) {
			return new NumericWrapper(JsclInteger.ZERO);
		} else if ( elements.length == 1 ) {
			return elements[0];
		} else {
			Generic result = elements[0];
			for ( int i = 1; i < elements.length; i++ ) {
				result = result.add(elements[i]);
			}
			return result.divide(JsclInteger.valueOf(elements.length));
		}
	}

	@Override
	public Generic numeric() {
		for ( int i = 0; i < parameters.length; i++ ) {
			parameters[i] = parameters[i].numeric();
		}
		return evaluate();
	}

	@Override
	public Variable newInstance() {
		return new Mean((JsclVector)null);
	}
}

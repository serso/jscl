package jscl.math.operator;

import jscl.math.Generic;
import jscl.math.NumericWrapper;
import jscl.math.Variable;
import jscl.math.function.Constant;
import jscl.math.numeric.JsclDouble;
import jscl.text.ParserUtils;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 10/31/11
 * Time: 10:58 PM
 */
public class Degree extends PostfixFunction {

	public static final String NAME = "°";

	public Degree(Generic expression) {
		super(NAME, new Generic[]{expression});
	}

	private Degree(Generic[] parameter) {
		super(NAME, ParserUtils.copyOf(parameter, 1));
	}

	@Override
	public int getMinimumNumberOfParameters() {
		return 1;
	}

	public Generic compute() {
		/*try {
			return numeric();
			// todo serso: check if really need to catch arithmetic exception
		} catch (ArithmeticException e) {
			// ok
		}*/

		return expressionValue();
	}

	@Override
	public Generic numeric() {
		// todo serso: check
		Generic parameter0 = parameters[0].numeric();
		Generic multiply = parameter0.multiply(Constant.pi.numeric());
		return multiply.divide(new NumericWrapper(JsclDouble.valueOf(180)));
	}

	@NotNull
	@Override
	public Operator newInstance(@NotNull Generic[] parameters) {
		return new Degree(parameters);
	}

	@Override
	public Variable newInstance() {
		return new Degree((Generic)null);
	}
}

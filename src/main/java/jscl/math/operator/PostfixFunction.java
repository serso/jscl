package jscl.math.operator;

import jscl.math.*;
import jscl.math.function.Frac;
import jscl.math.function.Pow;

/**
 * User: serso
 * Date: 11/2/11
 * Time: 11:07 AM
 */
abstract class PostfixFunction extends Operator {

	PostfixFunction(String name, Generic[] parameter) {
		super(name, parameter);
	}

	public String toString() {
		final StringBuilder result = new StringBuilder();

		try {
			result.append(parameter[0].integerValue());
		} catch (NotIntegerException e) {
			try {
				final Variable v = parameter[0].variableValue();
				if (v instanceof Frac || v instanceof Pow) {
					result.append(GenericVariable.valueOf(parameter[0]));
				} else {
					result.append(v);
				}
			} catch (NotVariableException e2) {
				result.append(GenericVariable.valueOf(parameter[0]));
			}
		}
		result.append(getName());

		return result.toString();
	}
}
package jscl.math.operator;

import jscl.math.Generic;
import jscl.math.Variable;

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

		/*try {*/
			result.append(formatParameter(0));
		/*} catch (NotIntegerException e) {
			try {
				final Variable v = parameters[0].variableValue();
				if (v instanceof Frac || v instanceof Pow) {
					result.append(GenericVariable.valueOf(parameters[0]));
				} else {
					result.append(v);
				}
			} catch (NotVariableException e2) {
				result.append(GenericVariable.valueOf(parameters[0]));
			}
		}*/
		result.append(getName());

		return result.toString();
	}

	public boolean isConstant(Variable variable) {
		boolean result = !isIdentity(variable);

		if (result) {
			for (Generic parameter : parameters) {
				if (!parameter.isConstant(variable)) {
					result = false;
					break;
				}
			}
		}

		return result;
	}
}

package jscl.math.function;

import jscl.AngleUnit;
import jscl.JsclMathEngine;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: serso
 * Date: 11/29/11
 * Time: 11:28 AM
 */
public class PiConstant extends ExtendedConstant {

	public PiConstant() {
		super(Constant.PI_CONST, Math.PI, "JsclDouble.valueOf(Math.PI)");
	}

	@Override
	public Double getDoubleValue() {
		Double result = null;

		try {
			result = AngleUnit.rad.transform(JsclMathEngine.instance.getDefaultAngleUnit(), Double.valueOf(getValue()));
		} catch (NumberFormatException e) {
			// do nothing - string is not a double
		}

		return result;
	}
}

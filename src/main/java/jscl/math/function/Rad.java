package jscl.math.function;

import jscl.math.Generic;
import jscl.math.JsclInteger;
import jscl.math.Variable;

/**
 * User: serso
 * Date: 11/14/11
 * Time: 1:40 PM
 */
public class Rad extends AbstractDms {

	public Rad(Generic degrees, Generic minutes, Generic seconds) {
		super("rad", degrees, minutes, seconds);
	}

	@Override
	public Generic evaluateNumerically() {
		return super.evaluateNumerically().multiply(Constant.pi.numeric()).divide(JsclInteger.valueOf(180));
	}

	@Override
	public Variable newInstance() {
		return new Rad(null, null, null);
	}
}

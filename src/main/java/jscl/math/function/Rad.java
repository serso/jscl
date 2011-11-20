package jscl.math.function;

import jscl.AngleUnits;
import jscl.math.Generic;
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
		return AngleUnits.deg.transform(AngleUnits.rad, super.evaluateNumerically());
	}

	@Override
	public Variable newInstance() {
		return new Rad(null, null, null);
	}
}

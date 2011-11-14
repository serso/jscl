package jscl.math.function;

import jscl.math.Generic;
import jscl.math.Variable;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 11/14/11
 * Time: 1:44 PM
 */
public class Dms extends AbstractDms {

	public Dms(Generic degrees, Generic minutes, Generic seconds) {
		super("dms", degrees, minutes, seconds);
	}

	@Override
	public Variable newInstance() {
		return new Dms(null, null, null);
	}
}

package jscl2.math.numeric.matrix;

import jscl2.MathContext;
import jscl2.math.numeric.Numeric;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 2/9/12
 * Time: 6:23 PM
 */
public abstract class AbstractVector extends Numeric implements Vector {

	protected AbstractVector(@NotNull MathContext mc) {
		super(mc);
	}
}

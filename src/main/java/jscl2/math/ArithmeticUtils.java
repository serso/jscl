package jscl2.math;

import jscl.math.NotDivisibleException;
import jscl2.math.numeric.*;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 1/30/12
 * Time: 1:15 PM
 */
public class ArithmeticUtils {


	@NotNull
	public static Numeric subtract(Numeric l, Numeric r) {
		if ( l instanceof Real && r instanceof Complex ) {
			return r.subtract(l);
		}
		throw new UnsupportedOperationException();
	}

	@NotNull
	public static Numeric add(Numeric l, Numeric r) {
		if ( l instanceof Real && r instanceof Complex ) {
			return r.add(l);
		}
		throw new UnsupportedOperationException();
	}

	@NotNull
	public static Numeric divide(Numeric l, Numeric r) {
		if ( l instanceof Real && r instanceof Complex ) {
			return r.divide(l);
		}
		throw new UnsupportedOperationException();
	}

	@NotNull
	public static Numeric multiply(Numeric l, Numeric r) {
		if (l instanceof Real && r instanceof Complex) {
			return r.multiply(l);
		}
		throw new UnsupportedOperationException();
	}

	public static int compare(Numeric l, Numeric r) {
		if (l instanceof Real && r instanceof Complex) {
			return r.compareTo(l);
		}
		throw new UnsupportedOperationException();
	}

	public static Numeric pow(Real real, Numeric that) {
		throw new UnsupportedOperationException();
	}
}

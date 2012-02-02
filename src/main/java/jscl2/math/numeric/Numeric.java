package jscl2.math.numeric;

import jscl2.MathContext;
import jscl2.math.RawNumber;
import org.jetbrains.annotations.NotNull;

public abstract class Numeric implements INumeric<Numeric> {

	@NotNull
	protected final MathContext mc;

	protected Numeric(@NotNull MathContext mc) {
		this.mc = mc;
	}

	@NotNull
	protected MathContext getMathContext() {
		return mc;
	}

	@NotNull
	protected Real ZERO() {
		return Real.ZERO(mc);
	}

	@NotNull
	protected Real ONE() {
		return Real.ONE(mc);
	}

	@NotNull
	protected Real TWO() {
		return Real.TWO(mc);
	}

	@NotNull
	protected Complex I() {
		return Complex.I(mc);
	}

	@NotNull
	@Override
	public Numeric pow(int exponent) {
		Numeric result = ONE();

		for (int i = 0; i < exponent; i++) {
			result = result.multiply(this);
		}

		return result;
	}

	/*public static Numeric root(int subscript, Numeric parameter[]) {
		throw new ArithmeticException();
	}*/

	/*public abstract int compareTo(@NotNull Numeric numeric);

	public int compareTo(Object o) {
		return compareTo((Numeric) o);
	}

	public boolean equals(Object obj) {
		return obj instanceof Numeric && compareTo((Numeric) obj) == 0;
	}
*/
	@NotNull
	protected String toString(@NotNull final RawNumber value) {
		return this.mc.format(value);
	}
}

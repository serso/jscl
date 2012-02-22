package jscl.math;

import jscl.math.function.Fraction;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 2/22/12
 * Time: 10:57 PM
 */
enum LiteralDivide {

	instance;

	@NotNull
	public Literal divide(@NotNull Literal l, @NotNull Literal r) {

		Literal result = Literal.newInstance(l.size() + r.size());
		int i = 0;
		int li = 0;
		int ri = 0;
		Variable lv = li < l.size() ? l.variables[li] : null;
		Variable rv = ri < r.size() ? r.variables[ri] : null;
		while (lv != null || rv != null) {
			int c;

			boolean fraction = false;
			if (lv == null) {
				c = 1;
			} else if (rv == null) {
				c = -1;
			} else {
				c = lv.compareTo(rv);
				if (c != 0) {
					if (lv instanceof Fraction) {
						final Fraction f = (Fraction) lv;
						final Generic n = f.getParameters()[0].divide(rv.expressionValue());

						lv = new Fraction(n, f.getParameters()[1]);
						c = 0;
						fraction = true;
					}
				}
			}

			if (c < 0) {
				int s = l.powers[li];
				result.variables[i] = lv;
				result.powers[i] = s;
				result.degree += s;
				i++;
				li++;
				lv = li < l.size() ? l.variables[li] : null;
			} else if (c > 0) {
				throw new NotDivisibleException();
			} else {
				int s = l.powers[li] - r.powers[ri];
				if (!fraction) {
					if (s < 0) throw new NotDivisibleException();
					else if (s == 0) ;
					else {
						result.variables[i] = lv;
						result.powers[i] = s;
						result.degree += s;
						i++;
					}
				} else {
					if (s == 0) {
						result.variables[i] = lv;
						result.powers[i] = 1;
						result.degree += 1;
						i++;
					} else {
						throw new NotDivisibleException();
					}
				}
				li++;
				ri++;
				lv = li < l.size() ? l.variables[li] : null;
				rv = ri < r.size() ? r.variables[ri] : null;
			}
		}
		result.resize(i);
		return result;
	}
}

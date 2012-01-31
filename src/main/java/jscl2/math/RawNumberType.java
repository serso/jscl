package jscl2.math;

import jscl2.math.numeric.Complex;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/**
 * User: serso
 * Date: 1/31/12
 * Time: 11:15 AM
 */
public enum RawNumberType implements RawNumberHelper {

	DOUBLE {
		@NotNull
		@Override
		public DoubleRawNumber getPI() {
			return DoubleRawNumber.newInstance(Math.PI);
		}

		@NotNull
		@Override
		public DoubleRawNumber toRawNumber(double value) {
			return DoubleRawNumber.newInstance(value);
		}

		@NotNull
		@Override
		public DoubleRawNumber toRawNumber(long value) {
			return DoubleRawNumber.newInstance(value);
		}
	},

	BIG_DECIMAL {
		@NotNull
		@Override
		public RawNumber getPI() {
			return toRawNumber(Math.PI);
		}

		@NotNull
		@Override
		public RawNumber toRawNumber(double value) {
			return BigDecimalRawNumber.newInstance(DoubleRawNumber.newInstance(value));
		}

		@NotNull
		@Override
		public RawNumber toRawNumber(long value) {
			return BigDecimalRawNumber.newInstance(BigDecimal.valueOf(value));
		}
	};
}

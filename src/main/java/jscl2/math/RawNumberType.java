package jscl2.math;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/**
 * User: serso
 * Date: 1/31/12
 * Time: 11:15 AM
 */
public enum RawNumberType implements RawNumberCreator {

	DOUBLE {

		@NotNull
		@Override
		public RawNumber getPI() {
			return DoubleRawNumber.newInstance(Math.PI);
		}

		@NotNull
		@Override
		public RawNumber fromDouble(double value) {
			return DoubleRawNumber.newInstance(value);
		}

		@NotNull
		@Override
		public RawNumber fromLong(long value) {
			/*if ( value >= 0L && value < CONSTANT_POOL_SIZE ) {
				return DOUBLE_CONSTANTS[(int)value];
			}*/
			return DoubleRawNumber.newInstance(value);
		}
	},

	BIG_DECIMAL {
		@NotNull
		@Override
		public RawNumber getPI() {
			return fromDouble(Math.PI);
		}

		@NotNull
		@Override
		public RawNumber fromDouble(double value) {
			return BigDecimalRawNumber.newInstance(DoubleRawNumber.newInstance(value));
		}

		@NotNull
		@Override
		public RawNumber fromLong(long value) {
			/*if (value >= 0 && value < CONSTANT_POOL_SIZE) {
				return BIG_DECIMAL_CONSTANTS[(int) value];
			}*/
			return BigDecimalRawNumber.newInstance(BigDecimal.valueOf(value));
		}
	};

	private static final long CONSTANT_POOL_SIZE = 10;
	private static final RawNumber[] DOUBLE_CONSTANTS = new RawNumber[(int)CONSTANT_POOL_SIZE];
	private static final RawNumber[] BIG_DECIMAL_CONSTANTS = new RawNumber[(int)CONSTANT_POOL_SIZE];
	static {
		for (int i = 0; i < CONSTANT_POOL_SIZE; i++) {
			DOUBLE_CONSTANTS[i] = DoubleRawNumber.newInstance((long)i);
			BIG_DECIMAL_CONSTANTS[i] = BigDecimalRawNumber.newInstance(BigDecimal.valueOf(i));
		}
	}
}

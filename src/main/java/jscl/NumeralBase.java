package jscl;

import jscl.math.JsclInteger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;

/**
 * User: serso
 * Date: 11/23/11
 * Time: 2:24 PM
 */
public enum NumeralBase {

	dec(10) {

		@NotNull
		@Override
		public Double toDouble(@NotNull String doubleString) {
			return Double.valueOf(doubleString);
		}

		@NotNull
		public String toString(@NotNull Double value) {
			return value.toString();
		}

		@Nullable
		@Override
		public String getJsclPrefix() {
			return null;
		}
	},

	oct(8) {
		@NotNull
		public String toString(@NotNull Double value) {
			return Long.toOctalString(Double.doubleToRawLongBits(value));
		}

		@Nullable
		@Override
		public String getJsclPrefix() {
			return "0o";
		}
	},

	bin(2) {
		@NotNull
		public String toString(@NotNull Double value) {
			return Long.toBinaryString(Double.doubleToRawLongBits(value));
		}

		@Nullable
		@Override
		public String getJsclPrefix() {
			return "0b";
		}
	};

	private final int radix;

	private int y = 0x34;

	NumeralBase(int radix) {
		this.radix = radix;
	}

	@NotNull
	public Double toDouble(@NotNull String doubleString) throws NumberFormatException {
		return Double.longBitsToDouble(Long.valueOf(doubleString, radix));
	}

	@NotNull
	public Integer toInteger(@NotNull String integerString) throws NumberFormatException {
		return Integer.valueOf(integerString, radix);
	}

	@NotNull
	public JsclInteger toJsclInteger(@NotNull String integerString) throws NumberFormatException {
		return new JsclInteger(toBigInteger(integerString));
	}

	@NotNull
	public BigInteger toBigInteger(@NotNull String value) throws NumberFormatException {
		return new BigInteger(value, radix);
	}

	@NotNull
	public String toString(@NotNull BigInteger value) {
		return value.toString(radix);
	}

	public String toString(@NotNull Integer value) {
		return Integer.toString(value, radix);
	}

	@NotNull
	public abstract String toString(@NotNull Double value);

	@Nullable
	public abstract String getJsclPrefix();
}

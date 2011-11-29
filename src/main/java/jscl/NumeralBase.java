package jscl;

import jscl.math.JsclInteger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/**
 * User: serso
 * Date: 11/23/11
 * Time: 2:24 PM
 */
public enum NumeralBase {

	dec(10) {

		private final List<Character> characters = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');

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

		@NotNull
		@Override
		public List<Character> getAcceptableCharacters() {
			return characters;
		}
	},

	hex(16) {

		private final List<Character> characters = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f');

		@NotNull
		public String toString(@NotNull Double value) {
			return Long.toHexString(Double.doubleToRawLongBits(value));
		}

		@Nullable
		@Override
		public String getJsclPrefix() {
			return "0x";
		}

		@NotNull
		@Override
		public List<Character> getAcceptableCharacters() {
			return characters;
		}
	},

	oct(8) {

		private final List<Character> characters = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7');

		@NotNull
		public String toString(@NotNull Double value) {
			return Long.toOctalString(Double.doubleToRawLongBits(value));
		}

		@Nullable
		@Override
		public String getJsclPrefix() {
			return "0o";
		}

		@NotNull
		@Override
		public List<Character> getAcceptableCharacters() {
			return characters;
		}
	},

	bin(2) {

		private final List<Character> characters = Arrays.asList('0', '1');

		@NotNull
		public String toString(@NotNull Double value) {
			return Long.toBinaryString(Double.doubleToRawLongBits(value));
		}

		@Nullable
		@Override
		public String getJsclPrefix() {
			return "0b";
		}

		@NotNull
		@Override
		public List<Character> getAcceptableCharacters() {
			return characters;
		}
	};

	private final int radix;

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

	@NotNull
	public abstract List<Character> getAcceptableCharacters();

	@Nullable
	public static NumeralBase getByPrefix ( @NotNull String prefix ) {
		for (NumeralBase nb : NumeralBase.values()) {
			if ( prefix.equals(nb.getJsclPrefix()) ) {
				return nb;
			}
		}

		return null;
	}
}

package jscl2;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * User: serso
 * Date: 11/23/11
 * Time: 2:24 PM
 */
public enum NumeralBase {

	dec(10, 3) {

		private final List<Character> characters = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');

		@Override
		public double toDouble(@NotNull String doubleString) {
			return Double.valueOf(doubleString);
		}

		@NotNull
		public String toString(double value) {
			return Double.toString(value);
		}

		@NotNull
		@Override
		public String getJsclPrefix() {
			return "0d:";
		}

		@NotNull
		@Override
		public List<Character> getAcceptableCharacters() {
			return characters;
		}
	},

	hex(16, 2) {

		private final List<Character> characters = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F');

		@NotNull
		public String toString(double value) {
			return Long.toHexString(Double.doubleToRawLongBits(value)).toUpperCase();
		}

		@NotNull
		@Override
		public String getJsclPrefix() {
			return "0x:";
		}

		@NotNull
		@Override
		public List<Character> getAcceptableCharacters() {
			return characters;
		}
	},

	oct(8, 4) {

		private final List<Character> characters = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7');

		@NotNull
		public String toString(double value) {
			return Long.toOctalString(Double.doubleToRawLongBits(value)).toUpperCase();
		}

		@NotNull
		@Override
		public String getJsclPrefix() {
			return "0o:";
		}

		@NotNull
		@Override
		public List<Character> getAcceptableCharacters() {
			return characters;
		}
	},

	bin(2, 4) {

		private final List<Character> characters = Arrays.asList('0', '1');

		@NotNull
		public String toString(double value) {
			return Long.toBinaryString(Double.doubleToRawLongBits(value)).toUpperCase();
		}

		@NotNull
		@Override
		public String getJsclPrefix() {
			return "0b:";
		}

		@NotNull
		@Override
		public List<Character> getAcceptableCharacters() {
			return characters;
		}
	};

	private final int radix;
	private final int groupingSize;

	NumeralBase(int radix, int groupingSize) {
		this.radix = radix;
		this.groupingSize = groupingSize;
	}

	public double toDouble(@NotNull String doubleString) throws NumberFormatException {
		return Double.longBitsToDouble(Long.valueOf(doubleString, radix));
	}

	@NotNull
	public Integer toInteger(@NotNull String integerString) throws NumberFormatException {
		return Integer.valueOf(integerString, radix);
	}

	public String toString(@NotNull Integer value) {
		return Integer.toString(value, radix).toUpperCase();
	}

	@NotNull
	public abstract String toString(double value);

	@NotNull
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

	public int getGroupingSize() {
		return groupingSize;
	}
}

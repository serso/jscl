package jscl;

import jscl.math.JsclInteger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;
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

        @NotNull
        @Override
        public Double toDouble(@NotNull String doubleString) {
            return Double.valueOf(doubleString);
        }

        @NotNull
        public String toString(@NotNull Double value) {
            return value.toString();
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

/*		@NotNull
		public String toString(@NotNull Double value) {
			return Long.toHexString(Double.doubleToRawLongBits(value)).toUpperCase();
		}*/

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

/*		@NotNull
		public String toString(@NotNull Double value) {
			return Long.toOctalString(Double.doubleToRawLongBits(value)).toUpperCase();
		}*/

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

/*		@NotNull
		public String toString(@NotNull Double value) {
			return Long.toBinaryString(Double.doubleToRawLongBits(value)).toUpperCase();
		}*/

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

    protected final int radix;
    protected final int groupingSize;

    NumeralBase(int radix, int groupingSize) {
        this.radix = radix;
        this.groupingSize = groupingSize;
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

    public String toString(@NotNull BigInteger value) {
        return value.toString(radix).toUpperCase();
    }

    public String toString(@NotNull Integer value) {
        return Integer.toString(value, radix).toUpperCase();
    }

    @NotNull
    public abstract String getJsclPrefix();

    @NotNull
    public abstract List<Character> getAcceptableCharacters();


    @Nullable
    public static NumeralBase getByPrefix(@NotNull String prefix) {
        for (NumeralBase nb : NumeralBase.values()) {
            if (prefix.equals(nb.getJsclPrefix())) {
                return nb;
            }
        }

        return null;
    }

    public int getGroupingSize() {
        return groupingSize;
    }

    @NotNull
    public String toString(@NotNull Double value, int fractionDigits) {
        return toString(value, radix, fractionDigits);
    }

    @NotNull
    protected static String toString(@NotNull Double value, int radix, int fractionDigits) {
        final BigDecimal mult = BigDecimal.valueOf(radix).pow(fractionDigits);
        final BigDecimal bd = BigDecimal.valueOf(value).multiply(mult);

        final BigInteger bi = bd.toBigInteger();
        final StringBuilder result = new StringBuilder(bi.toString(radix));

        while (result.length() < fractionDigits + 1) {  // +1 for leading zero
            result.insert(0, "0");
        }
        result.insert(result.length() - fractionDigits, ".");

        return result.toString().toUpperCase();
    }
}

package jscl.math;

import au.com.bytecode.opencsv.CSVReader;
import jscl.JsclMathEngine;
import jscl.MathEngine;
import jscl.text.ParseException;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;
import org.solovyev.common.utils.Converter;

import java.io.InputStreamReader;

/**
 * User: serso
 * Date: 12/14/11
 * Time: 4:01 PM
 */
public class NumeralBaseConversionTest {

	@Test
	public void testConversion() throws Exception {
		CSVReader reader = null;
		try {
			final MathEngine me = JsclMathEngine.instance;

			reader = new CSVReader(new InputStreamReader(NumeralBaseConversionTest.class.getResourceAsStream("/jscl/math/nb_table.csv")), '\t');

			// skip first line
			reader.readNext();

			String[] line = reader.readNext();
			for (; line != null; line = reader.readNext()) {
				testExpression(line, new DummyExpression());
				testExpression(line, new Expression1());
				testExpression(line, new Expression2());
				testExpression(line, new Expression3());
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	public static void testExpression(@NotNull String[] line, @NotNull Converter<String, String> converter) throws ParseException {
		final String dec = line[0];
		final String hex = "0x:" + line[1];
		final String bin = "0b:" + line[2];

		final String decResult = Expression.valueOf(converter.convert(dec)).numeric().toString();
		final String hexResult = Expression.valueOf(converter.convert(hex)).numeric().toString();
		final String binResult = Expression.valueOf(converter.convert(bin)).numeric().toString();

		Assert.assertEquals(decResult, hexResult);
		Assert.assertEquals(decResult, binResult);
	}

	private static class DummyExpression implements Converter<String, String> {

		@NotNull
		@Override
		public String convert(@NotNull String s) {
			return s;
		}
	}

	private static class Expression1 implements Converter<String, String> {

		@NotNull
		@Override
		public String convert(@NotNull String s) {
			return s + "*" + s;
		}
	}

	private static class Expression2 implements Converter<String, String> {

		@NotNull
		@Override
		public String convert(@NotNull String s) {
			return s + "*" + s + " * sin(" + s + ") - 0b:1101";
		}
	}

	private static class Expression3 implements Converter<String, String> {

		@NotNull
		@Override
		public String convert(@NotNull String s) {
			return s + "*" + s + " * sin(" + s + ") - 0b:1101 + √(" + s + ") + exp ( " + s + ")";
		}
	}
}

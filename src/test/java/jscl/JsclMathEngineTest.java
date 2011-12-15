package jscl;

import org.junit.Assert;
import org.junit.Test;

/**
 * User: serso
 * Date: 12/15/11
 * Time: 11:25 AM
 */
public class JsclMathEngineTest {
	@Test
	public void testFormat() throws Exception {
		final MathContext me = JsclMathEngine.instance;

		try {
			me.setUseGroupingSeparator(true);
			Assert.assertEquals("1", me.format(1d, NumeralBase.bin));
			Assert.assertEquals("10", me.format(2d, NumeralBase.bin));
			Assert.assertEquals("11", me.format(3d, NumeralBase.bin));
			Assert.assertEquals("100", me.format(4d, NumeralBase.bin));
			Assert.assertEquals("101", me.format(5d, NumeralBase.bin));
			Assert.assertEquals("110", me.format(6d, NumeralBase.bin));
			Assert.assertEquals("111", me.format(7d, NumeralBase.bin));
			Assert.assertEquals("1000", me.format(8d, NumeralBase.bin));
			Assert.assertEquals("1001", me.format(9d, NumeralBase.bin));
			Assert.assertEquals("1 0001", me.format(17d, NumeralBase.bin));
			Assert.assertEquals("1 0100", me.format(20d, NumeralBase.bin));
			Assert.assertEquals("1 0100", me.format(20d, NumeralBase.bin));
			Assert.assertEquals("1 1111", me.format(31d, NumeralBase.bin));
			Assert.assertEquals("111 1111 0011 0110", me.format(32566d, NumeralBase.bin));

			Assert.assertEquals("7F 36", me.format(32566d, NumeralBase.hex));
			Assert.assertEquals("24", me.format(36d, NumeralBase.hex));
			Assert.assertEquals("8", me.format(8d, NumeralBase.hex));
			Assert.assertEquals("1 3D", me.format(317d, NumeralBase.hex));
		} finally {
			me.setUseGroupingSeparator(false);
		}

		Assert.assertEquals("1", me.format(1d, NumeralBase.bin));
		Assert.assertEquals("10", me.format(2d, NumeralBase.bin));
		Assert.assertEquals("11", me.format(3d, NumeralBase.bin));
		Assert.assertEquals("100", me.format(4d, NumeralBase.bin));
		Assert.assertEquals("101", me.format(5d, NumeralBase.bin));
		Assert.assertEquals("110", me.format(6d, NumeralBase.bin));
		Assert.assertEquals("111", me.format(7d, NumeralBase.bin));
		Assert.assertEquals("1000", me.format(8d, NumeralBase.bin));
		Assert.assertEquals("1001", me.format(9d, NumeralBase.bin));
		Assert.assertEquals("10001", me.format(17d, NumeralBase.bin));
		Assert.assertEquals("10100", me.format(20d, NumeralBase.bin));
		Assert.assertEquals("10100", me.format(20d, NumeralBase.bin));
		Assert.assertEquals("11111", me.format(31d, NumeralBase.bin));
		Assert.assertEquals("111111100110110", me.format(32566d, NumeralBase.bin));

		Assert.assertEquals("7F36", me.format(32566d, NumeralBase.hex));
		Assert.assertEquals("24", me.format(36d, NumeralBase.hex));
		Assert.assertEquals("8", me.format(8d, NumeralBase.hex));
		Assert.assertEquals("13D", me.format(317d, NumeralBase.hex));
	}
}

package jscl2.math.numeric.matrix;

import jscl2.MathContext;
import jscl2.MathContextImpl;
import jscl2.math.numeric.Numeric;
import jscl2.math.numeric.Real;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * User: serso
 * Date: 2/2/12
 * Time: 5:39 PM
 */
public abstract class AbstractMatrixTest<M extends AbstractMatrix> {

	public static final double MIN_E = 0.001;

	@NotNull
	protected abstract Matrix.Builder<M> getBuilder(@NotNull MathContext mc, int rows, int cols);

	@Test
	public void testCreate() throws Exception {
		MathContext mc = MathContextImpl.defaultInstance();

		Matrix.Builder<M> b = getBuilder(mc, 2, 2);
		b.setIJ(0, 0, mc.newReal(1L));
		b.setIJ(1, 1, mc.newReal(2L));
		final Matrix m = b.build();

		Assert.assertEquals(Real.ZERO(mc), m.getIJ(0, 1));
		Assert.assertEquals(Real.ZERO(mc), m.getIJ(1, 0));
		Assert.assertEquals(Real.ONE(mc), m.getIJ(0, 0));
		Assert.assertEquals(Real.TWO(mc), m.getIJ(1, 1));

		try {
			b = getBuilder(mc, 1, 1);
			Assert.fail();
		} catch (AssertionError e) {
			// OK, should have an exception while creating 1x1 matrix
		}

	}

	private static final List<String> aMatrices = new ArrayList<String>();
	static {
		aMatrices.add("0.0487    0.2748\n" +
				"    0.5527    0.2415");
		aMatrices.add(" 0.8187    0.1758    0.1888\n" +
				"    0.7283    0.3604    0.0012");
		aMatrices.add("0.5017    0.7624    0.7477    0.1232\n" +
				"    0.7615    0.5761    0.6455    0.5044");
		aMatrices.add(" 0.0098    0.2794    0.9064    0.0249    0.8372\n" +
				"    0.5323    0.9462    0.3927    0.6714    0.9715");
		aMatrices.add("0.0577    0.5950\n" +
				"    0.9798    0.9622\n" +
				"    0.2848    0.1858");
		aMatrices.add("0.3971    0.4350    0.0110\n" +
				"    0.3747    0.0915    0.5733\n" +
				"    0.1311    0.6146    0.7897");
		aMatrices.add("0.8464    0.7466    0.6203    0.0903\n" +
				"    0.5060    0.2369    0.6003    0.2553\n" +
				"    0.2789    0.9573    0.1726    0.8586");
		aMatrices.add("0.7257    0.8300    0.3178    0.1099    0.5246\n" +
				"    0.5566    0.8588    0.4522    0.1097    0.9727\n" +
				"    0.5294    0.7890    0.7522    0.2699    0.7104");
		aMatrices.add("0.4425    0.2076\n" +
				"    0.3934    0.3181\n" +
				"    0.8266    0.1338\n" +
				"    0.6769    0.6715");
		aMatrices.add("0.8051    0.3643    0.3287\n" +
				"    0.4514    0.5323    0.6501\n" +
				"    0.3826    0.7117    0.9748\n" +
				"    0.7896    0.8715    0.0760");
		aMatrices.add("0.7289    0.0732    0.4324    0.7637\n" +
				"    0.4984    0.5910    0.7492    0.5588\n" +
				"    0.8090    0.9102    0.0392    0.1838\n" +
				"    0.3565    0.1938    0.9463    0.4979");
		aMatrices.add("0.9639    0.5802    0.4320    0.8009    0.3691\n" +
				"    0.1156    0.5310    0.5427    0.1425    0.6618\n" +
				"    0.0514    0.9012    0.7124    0.4785    0.1696\n" +
				"    0.3043    0.5406    0.0167    0.2568    0.2788");
		aMatrices.add("0.9020    0.5428\n" +
				"    0.7021    0.5401\n" +
				"    0.3775    0.3111\n" +
				"    0.7350    0.0712\n" +
				"    0.9541    0.1820");
		aMatrices.add("0.0086    0.4366    0.2411\n" +
				"    0.7271    0.0492    0.8414\n" +
				"    0.3541    0.0496    0.8572\n" +
				"    0.7804    0.0911    0.9636\n" +
				"    0.4367    0.5940    0.4889");
		aMatrices.add("0.6333    0.9810    0.1108    0.2083\n" +
				"    0.6240    0.1270    0.4075    0.4409\n" +
				"    0.3279    0.2322    0.8841    0.9562\n" +
				"    0.8030    0.0236    0.5481    0.1240\n" +
				"    0.9995    0.6074    0.3690    0.4708");
		aMatrices.add("0.9508    0.1565    0.8954    0.8854    0.1439\n" +
				"    0.4976    0.4573    0.5825    0.4077    0.6060\n" +
				"    0.7551    0.6181    0.5827    0.0364    0.2545\n" +
				"    0.7424    0.9322    0.8549    0.7461    0.3242\n" +
				"    0.8311    0.8351    0.0349    0.1548    0.4018");
	}

	private static final List<String> bMatrices = new ArrayList<String>();
	static {
		bMatrices.add("0.2431    0.9564\n" +
				"    0.1542    0.9357");
		bMatrices.add("0.3164    0.5431\n" +
				"    0.6996    0.4390\n" +
				"    0.6253    0.2874");
		bMatrices.add("0.3473    0.6723\n" +
				"    0.0921    0.4315\n" +
				"    0.1478    0.6944\n" +
				"    0.1982    0.2568");
		bMatrices.add("0.0569    0.6500\n" +
				"    0.4503    0.7269\n" +
				"    0.5825    0.3738\n" +
				"    0.6866    0.5816\n" +
				"    0.7194    0.1161");
		bMatrices.add("0.1930    0.9329    0.2732\n" +
				"    0.3416    0.3907    0.1519");
		bMatrices.add("0.2354    0.0614    0.2213\n" +
				"    0.4480    0.4963    0.8371\n" +
				"    0.5694    0.6423    0.9711");
		bMatrices.add("0.9111    0.5761    0.0900\n" +
				"    0.6996    0.8106    0.3209\n" +
				"    0.7252    0.4038    0.5114\n" +
				"    0.2299    0.9884    0.0606");
		bMatrices.add("0.3119    0.2554    0.0610\n" +
				"    0.2915    0.0887    0.5846\n" +
				"    0.8504    0.8383    0.2851\n" +
				"    0.9116    0.5847    0.8277\n" +
				"    0.6393    0.9481    0.1910");
		bMatrices.add("0.5710    0.1477    0.9081    0.0329\n" +
				"    0.1698    0.4761    0.5522    0.0539");
		bMatrices.add("0.5870    0.2638    0.1866    0.9924\n" +
				"    0.4139    0.7588    0.7811    0.8023\n" +
				"    0.3091    0.9952    0.1958    0.4242");
		bMatrices.add("0.5178    0.6789    0.2318    0.7566\n" +
				"    0.9942    0.4035    0.3963    0.9955\n" +
				"    0.8549    0.9350    0.7051    0.9624\n" +
				"    0.9624    0.4795    0.5586    0.5351");
		bMatrices.add("0.1982    0.4040    0.6153    0.8140\n" +
				"    0.1951    0.1792    0.3766    0.8984\n" +
				"    0.3268    0.9689    0.8772    0.4292\n" +
				"    0.8803    0.4075    0.7849    0.3343\n" +
				"    0.4711    0.8445    0.4650    0.5966");
		bMatrices.add("0.0930    0.0093    0.6427    0.0304    0.4550\n" +
				"    0.4635    0.9150    0.0014    0.2085    0.1273");
		bMatrices.add("0.2203    0.7621    0.6393    0.7156    0.8842\n" +
				"    0.2262    0.3476    0.9173    0.5777    0.3931\n" +
				"    0.5368    0.4612    0.1616    0.4333    0.1790");
		bMatrices.add("0.8569    0.2833    0.6109    0.3463    0.6249\n" +
				"    0.0434    0.1338    0.9000    0.4186    0.7386\n" +
				"    0.6916    0.6853    0.1934    0.1557    0.8051\n" +
				"    0.9790    0.9095    0.7544    0.8190    0.0672");
		bMatrices.add("0.4064    0.0946    0.6928    0.5230    0.2630\n" +
				"    0.3862    0.3232    0.8241    0.3253    0.6806\n" +
				"    0.6098    0.7696    0.8280    0.8318    0.2337\n" +
				"    0.1669    0.2341    0.2934    0.8103    0.4564\n" +
				"    0.1881    0.7404    0.3094    0.5570    0.3846");

	}

	private static final List<String> abMatrices = new ArrayList<String>();
	static {
		abMatrices.add("0.0542    0.3037\n" +
				"    0.1716    0.7546");
		abMatrices.add("0.5001    0.5761\n" +
				"    0.4833    0.5541");
		abMatrices.add("0.3794    1.2171\n" +
				"    0.5129    1.3383");
		abMatrices.add("1.2737    0.6600\n" +
				"    1.8451    1.6839");
		abMatrices.add("0.2144    0.2862    0.1062\n" +
				"    0.5179    1.2899    0.4139\n" +
				"    0.1185    0.3383    0.1060");
		abMatrices.add("0.2946    0.2473    0.4627\n" +
				"    0.4556    0.4366    0.7162\n" +
				"    0.7559    0.8203    1.3104");
		abMatrices.add("1.7640    1.4326    0.6385\n" +
				"    1.1207    0.9783    0.4440\n" +
				"    1.2464    1.8550    0.4727");
		abMatrices.add("1.1740    1.0870    0.8113\n" +
				"    1.5302    1.5837    0.9416\n" +
				"    1.7349    1.6671    1.0671");
		abMatrices.add("0.2879    0.1642    0.5165    0.0258\n" +
				"    0.2786    0.2095    0.5329    0.0301\n" +
				"    0.4947    0.1858    0.8245    0.0344\n" +
				"    0.5005    0.4196    0.9854    0.0585");
		abMatrices.add("0.7250    0.8159    0.4991    1.2306\n" +
				"    0.6863    1.1700    0.6273    1.1508\n" +
				"    0.8205    1.6111    0.8182    1.3642\n" +
				"    0.8477    0.9452    0.8429    1.5150");
		abMatrices.add("1.5548    1.2948    0.9294    1.4491\n" +
				"    2.0239    1.5452    1.1901    1.9854\n" +
				"    1.5343    1.0413    0.6785    1.6543\n" +
				"    1.6655    1.4438    1.1048    1.6398");
		abMatrices.add("1.3244    1.5500    1.9907    1.9792\n" +
				"    0.7411    1.2846    1.1667    1.2466\n" +
				"    0.9200    1.2108    1.4504    1.4185\n" +
				"    0.5287    0.5761    0.7367    0.9927");
		abMatrices.add("0.3355    0.5051    0.5805    0.1406    0.4795\n" +
				"    0.3156    0.5008    0.4520    0.1339    0.3882\n" +
				"    0.1793    0.2882    0.2430    0.0763    0.2113\n" +
				"    0.1014    0.0720    0.4725    0.0372    0.3434\n" +
				"    0.1731    0.1754    0.6135    0.0669    0.4572");
		abMatrices.add("0.2301    0.2695    0.4449    0.3629    0.2224\n" +
				"    0.6230    0.9593    0.6459    0.9133    0.8128\n" +
				"    0.5494    0.6825    0.4104    0.6535    0.4861\n" +
				"    0.7098    1.0709    0.7382    1.0287    0.8984\n" +
				"    0.4930    0.7647    0.9031    0.8675    0.7071");
		abMatrices.add("0.8659    0.5761    1.4484    0.8179    1.2235\n" +
				"    1.2537    0.8740    0.9070    0.6938    0.8415\n" +
				"    1.8386    1.5994    1.3017    1.1316    1.1525\n" +
				"    1.1896    0.7190    0.7114    0.4749    0.9689\n" +
				"    1.5989    1.0454    1.5838    1.0434    1.4020");
		abMatrices.add("1.1677    1.1435    1.8334    2.0907    1.0252\n" +
				"    0.9161    1.1873    1.5110    1.5615    0.9973\n" +
				"    0.9549    0.9166    1.6044    1.2520    0.8699\n" +
				"    1.3685    1.4441    2.3096    2.1879    1.4946\n" +
				"    0.7829    0.7091    1.4626    1.0846    1.0202");
	}


	private static final String A2_str = 	" 1 0 -2\n " +
											" 0 3 -1";
	private static final String B2_str = 	" 0   3\n " +
											"-2  -1\n " +
											" 0   4";
	private static final String A2B2_str = 	" 0  -5\n " +
											"-6  -7";


	@Test
	public void testMultiply() throws Exception {
		MathContext mc = MathContextImpl.defaultInstance();

		final Matrix E = AbstractMatrix.identity(mc, 5);
		final Matrix A = AbstractMatrix.random(mc, 5);
		final Matrix B = AbstractMatrix.random(mc, 5, 3);

		Assert.assertEquals(A, E.multiply(A));
		Assert.assertEquals(A, A.multiply(E));
		Assert.assertEquals(E, E.multiply(E));
		Assert.assertEquals(B, E.multiply(B));

		Matrix.Builder<M> b = getBuilder(mc, 4, 3);
		b.setIJ(0, 0, mc.newReal(14));
		b.setIJ(0, 1, mc.newReal(9));
		b.setIJ(0, 2, mc.newReal(3));

		b.setIJ(1, 0, mc.newReal(2));
		b.setIJ(1, 1, mc.newReal(11));
		b.setIJ(1, 2, mc.newReal(15));

		b.setIJ(2, 0, mc.newReal(0));
		b.setIJ(2, 1, mc.newReal(12));
		b.setIJ(2, 2, mc.newReal(17));

		b.setIJ(3, 0, mc.newReal(5));
		b.setIJ(3, 1, mc.newReal(2));
		b.setIJ(3, 2, mc.newReal(3));

		final Matrix A1 = b.build();

		b = getBuilder(mc, 3, 2);
		b.setIJ(0, 0, mc.newReal(12));
		b.setIJ(0, 1, mc.newReal(25));

		b.setIJ(1, 0, mc.newReal(9));
		b.setIJ(1, 1, mc.newReal(10));

		b.setIJ(2, 0, mc.newReal(8));
		b.setIJ(2, 1, mc.newReal(5));

		final Matrix B1 = b.build();

		b = getBuilder(mc, 4, 2);

		b.setIJ(0, 0, mc.newReal(273));
		b.setIJ(0, 1, mc.newReal(455));

		b.setIJ(1, 0, mc.newReal(243));
		b.setIJ(1, 1, mc.newReal(235));

		b.setIJ(2, 0, mc.newReal(244));
		b.setIJ(2, 1, mc.newReal(205));

		b.setIJ(3, 0, mc.newReal(102));
		b.setIJ(3, 1, mc.newReal(160));

		final Matrix A1B1 = b.build();

		Assert.assertEquals(A1B1, A1.multiply(B1));

		final M A2 = parseMatrix(A2_str, getBuilder(mc, 2, 3), mc, false);
		final M B2 = parseMatrix(B2_str, getBuilder(mc, 3, 2), mc, false);
		final M A2B2 = parseMatrix(A2B2_str, getBuilder(mc, 2, 2), mc, false);

		Assert.assertEquals(A2B2, A2.multiply((Numeric)B2));

		int i = 0;
		for (int rows = 2; rows < 6; rows++) {
			for (int cols = 2; cols < 6; cols++) {
				final M A3 = parseMatrix(aMatrices.get(i), getBuilder(mc, rows, cols), mc, true);
				final M B3 = parseMatrix(bMatrices.get(i), getBuilder(mc, cols, rows), mc, true);
				final M A3B3 = parseMatrix(abMatrices.get(i), getBuilder(mc, rows, rows), mc, true);
				Assert.assertTrue(A3B3.subtract(A3.multiply((Numeric) B3)).norm().less(mc.newReal(MIN_E)));
				i++;
			}
		}
	}

	@Test
	public void testDeterminant() throws Exception {
		MathContext mc = MathContextImpl.defaultInstance();

		Matrix A = parseMatrix("1 2\n" +
					"3 4", getBuilder(mc, 2, 2), mc, false);

		Assert.assertEquals(mc.newReal(-2L), A.determinant());

		A = parseMatrix("5 -2  1\n" +
						"0  3 -1\n" +
						"2  0  7", getBuilder(mc, 3, 3), mc, false);

		Assert.assertEquals(mc.newReal(103L), A.determinant());
	}

	@Test
	public void testTranspose() throws Exception {
		MathContext mc = MathContextImpl.defaultInstance();

		for (int rows = 2; rows < 10; rows++) {
			for (int cols = 2; cols < 10; cols++) {
				final Matrix A = AbstractMatrix.random(mc, rows, cols);
				final Matrix B = AbstractMatrix.random(mc, rows, cols);
				final Matrix AT = A.transpose();
				final Matrix BT = B.transpose();
				final Matrix ATT = AT.transpose();

				Assert.assertEquals(A.getRows(), AT.getCols());
				Assert.assertEquals(A.getCols(), AT.getRows());
				for ( int i = 0; i < A.getRows(); i++ ) {
					for ( int j = 0; j < A.getCols(); j++ ) {
						Assert.assertEquals(A.getIJ(i, j), AT.getIJ(j, i));
					}
				}

				Assert.assertEquals(A, ATT);
				Assert.assertEquals(A.add(B).transpose(), AT.add(BT));
				if (A.getRows() == B.getCols()) {
					Assert.assertEquals(A.multiply(B).transpose(), BT.multiply(AT));
				}
			}
		}
	}

	public static <M extends Matrix> M parseMatrix(@NotNull String s, @NotNull Matrix.Builder<M> m, @NotNull MathContext mc, boolean asDouble) {
		int row = 0;
		int col = 0;
		for (int i = 0; i < s.length(); i++) {
			final char ch = s.charAt(i);

			if ( Character.isWhitespace(ch) ) {
				continue;
			}

			int j = s.indexOf(" ", i);
			int end = s.indexOf("\n", i);
			if ( j >= 0 && (( end >= 0 && j < end ) || end < 0) ) {
				if (asDouble) {
					m.setIJ(row, col, mc.newReal(Double.valueOf(s.substring(i, j))));
				} else {
					m.setIJ(row, col, mc.newReal(Long.valueOf(s.substring(i, j))));
				}
				i += (j - i);
				col++;
			} else {
				if (end >= 0) {
					if ( asDouble ) {
						m.setIJ(row, col, mc.newReal(Double.valueOf(s.substring(i, end))));
					} else {
						m.setIJ(row, col, mc.newReal(Long.valueOf(s.substring(i, end))));
					}
					i += (end - i);
					row++;
					col = 0;
				} else {
					if (asDouble) {
						m.setIJ(row, col, mc.newReal(Double.valueOf(s.substring(i))));
					} else {
						m.setIJ(row, col, mc.newReal(Long.valueOf(s.substring(i))));
					}
					break;
				}
			}
			
		}

		return m.build();
	}
}

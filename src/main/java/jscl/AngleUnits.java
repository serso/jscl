package jscl;

import jscl.math.Generic;
import jscl.math.NumericWrapper;
import jscl.math.numeric.JsclDouble;
import jscl.math.numeric.Numeric;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 11/19/11
 * Time: 8:55 PM
 */
public enum AngleUnits {

	deg {
		@Override
		public double transform(@NotNull AngleUnits to, double value) {
			switch (to) {
				case deg:
					return value;
				case rad:
					return value * FROM_DEG_TO_RAD;
				default:
					throw new UnsupportedOperationException(to + " is not supported yet!");
			}
		}

		@Override
		public Numeric transform(@NotNull AngleUnits to, Numeric value) {
			switch (to) {
				case deg:
					return value;
				case rad:
					return value.multiply(JsclDouble.valueOf(FROM_DEG_TO_RAD));
				default:
					throw new UnsupportedOperationException(to + " is not supported yet!");
			}
		}

		@Override
		public Generic transform(@NotNull AngleUnits to, Generic value) {
			switch (to) {
				case deg:
					return value;
				case rad:
					return value.multiply(new NumericWrapper(JsclDouble.valueOf(FROM_DEG_TO_RAD)));
				default:
					throw new UnsupportedOperationException(to + " is not supported yet!");
			}
		}
	},

	rad {
		@Override
		public double transform(@NotNull AngleUnits to, double value) {
			switch (to) {
				case deg:
					return value * FROM_RAD_TO_DEG;
				case rad:
					return value;
				default:
					throw new UnsupportedOperationException(to + " is not supported yet!");
			}
		}

		@Override
		public Numeric transform(@NotNull AngleUnits to, Numeric value) {
			switch (to) {
				case deg:
					return value.multiply(JsclDouble.valueOf(FROM_RAD_TO_DEG));
				case rad:
					return value;
				default:
					throw new UnsupportedOperationException(to + " is not supported yet!");
			}
		}

		@Override
		public Generic transform(@NotNull AngleUnits to, Generic value) {
			switch (to) {
				case deg:
					return value.multiply(new NumericWrapper(JsclDouble.valueOf(FROM_RAD_TO_DEG)));
				case rad:
					return value;
				default:
					throw new UnsupportedOperationException(to + " is not supported yet!");
			}
		}
	};

	private static final double FROM_DEG_TO_RAD = Math.PI / 180d;
	private static final double FROM_RAD_TO_DEG = 180d / Math.PI;


	public abstract double transform(@NotNull AngleUnits to, double value);

	public abstract Numeric transform(@NotNull AngleUnits to, Numeric value);

	public abstract Generic transform(@NotNull AngleUnits to, Generic value);
}

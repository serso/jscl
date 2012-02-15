package jscl2.math.numeric.matrix;

import jscl.AbstractJsclArithmeticException;
import org.jetbrains.annotations.NotNull;
import org.solovyev.common.msg.Message;

/**
 * User: serso
 * Date: 2/15/12
 * Time: 10:59 AM
 */
public class DimensionMustAgreeException extends ArithmeticException {

	private static final String VECTOR_DIMENSIONS_MUST_AGREE = "Vector dimensions must agree!";
	private static final String MATRIX_DIMENSIONS_MUST_AGREE = "Matrix dimensions must agree!";

	public DimensionMustAgreeException() {
	}

	public DimensionMustAgreeException(@NotNull Vector l, @NotNull Vector r) {
		super(VECTOR_DIMENSIONS_MUST_AGREE);
	}

	public DimensionMustAgreeException(@NotNull Matrix l, @NotNull Matrix r) {
		super(MATRIX_DIMENSIONS_MUST_AGREE);
	}

	public DimensionMustAgreeException(@NotNull Matrix l, @NotNull Vector r) {
		super(MATRIX_DIMENSIONS_MUST_AGREE);
	}
}

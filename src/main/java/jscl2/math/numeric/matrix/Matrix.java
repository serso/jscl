package jscl2.math.numeric.matrix;

import jscl2.math.numeric.Numeric;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 2/2/12
 * Time: 12:09 PM
 */
public interface Matrix {

	int getRows();

	int getCols();

	@NotNull
	Numeric getIJ(int row, int col);

	@NotNull
	Matrix add(@NotNull Matrix that);

	@NotNull
	Matrix subtract(@NotNull Matrix that);

	@NotNull
	Numeric multiply(@NotNull Matrix that);

	@NotNull
	Numeric transpose();

	@NotNull
	Numeric trace();

	@NotNull
	Numeric determinant();

	public static interface Builder<T extends Matrix> {

		void setIJ(int row, int col, @NotNull Numeric value);

		@NotNull
		T build();
	}
}

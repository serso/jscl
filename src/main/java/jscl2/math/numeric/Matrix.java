package jscl2.math.numeric;

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
	Numeric[][] asArray();

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
}

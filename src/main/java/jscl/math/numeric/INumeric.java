package jscl.math.numeric;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 11/20/11
 * Time: 7:58 PM
 */
public interface INumeric<T extends INumeric<T>> {

	@NotNull
	T pow(int exponent);

	@NotNull
	T abs();

	@NotNull
	T negate();

	int signum();

	@NotNull
	T sgn();

	@NotNull
	T ln();

	@NotNull
	T lg();

	@NotNull
	T exp();

	@NotNull
	T inverse();

	@NotNull
	T sqrt();

	@NotNull
	T nthrt(int n);

	@NotNull
	T acos();

	@NotNull
	T asin();

	@NotNull
	T atan();

	@NotNull
	T acot();

	@NotNull
	T cos();

	@NotNull
	T sin();

	@NotNull
	T tan();

	@NotNull
	T cot();

	@NotNull
	T acosh();

	@NotNull
	T asinh();

	@NotNull
	T atanh();

	@NotNull
	T acoth();

	@NotNull
	T cosh();

	@NotNull
	T sinh();

	@NotNull
	T tanh();

	@NotNull
	T coth();
}

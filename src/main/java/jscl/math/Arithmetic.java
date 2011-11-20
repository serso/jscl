package jscl.math;

import org.jetbrains.annotations.NotNull;

public interface Arithmetic<T extends Arithmetic<T>> {

	@NotNull
	T add(@NotNull T arithmetic);

	@NotNull
	T subtract(@NotNull T arithmetic);

	@NotNull
	T multiply(@NotNull T arithmetic);

	@NotNull
	T divide(@NotNull T arithmetic) throws ArithmeticException;

}

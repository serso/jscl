package jscl2.math;

import jscl.math.NotDivisibleException;
import org.jetbrains.annotations.NotNull;

public interface Arithmetic<T extends Arithmetic<? extends T>> {

	@NotNull
	T add(@NotNull T that);

	@NotNull
	T subtract(@NotNull T that);

	@NotNull
	T multiply(@NotNull T that);

	@NotNull
	T divide(@NotNull T that) throws NotDivisibleException;

}

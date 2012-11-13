package jscl.math;

import org.jetbrains.annotations.NotNull;

public interface Arithmetic<T extends Arithmetic<T>> {

    @NotNull
    T add(@NotNull T that);

    @NotNull
    T subtract(@NotNull T that);

    @NotNull
    T multiply(@NotNull T that);

    @NotNull
    T divide(@NotNull T that) throws NotDivisibleException;

}

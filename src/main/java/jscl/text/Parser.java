package jscl.text;

import org.apache.commons.lang.mutable.MutableInt;
import org.jetbrains.annotations.NotNull;

public interface Parser<T> {

	T parse(@NotNull String string,
				 @NotNull MutableInt position) throws ParseException;

}

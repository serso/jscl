package jscl.text;

import jscl.text.MutableInt;
import org.jetbrains.annotations.NotNull;

public interface Parser<T> {

	T parse(@NotNull String string,
				 @NotNull MutableInt position) throws ParseException;

}

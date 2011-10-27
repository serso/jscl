package jscl.text;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ParseException extends Exception {

	public ParseException() {
	}

	public ParseException(@NotNull MutableInt position, @NotNull String expression) {
		this(null, position, expression);
	}

	public ParseException(@Nullable String message, @NotNull MutableInt position, @NotNull String expression) {
		super("Parsing was stopped on " + position + " symbol of '" + expression + "'" +  (message == null ? "." : (" due to next error: " + message + ".")));
	}
}

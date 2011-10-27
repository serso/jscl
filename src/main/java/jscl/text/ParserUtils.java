package jscl.text;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 10/27/11
 * Time: 2:40 PM
 */
public class ParserUtils {

	public static void checkInterruption() {
		if (Thread.currentThread().isInterrupted()) {
			throw new ParseInterruptedException("Interrupted!");
		}
	}

	public static void skipWhitespaces(@NotNull String string, @NotNull MutableInt position) {
		while (position.intValue() < string.length() && Character.isWhitespace(string.charAt(position.intValue()))) {
			position.increment();
		}
	}
}

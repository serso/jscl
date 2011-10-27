package jscl.text;

import org.apache.commons.lang.mutable.MutableInt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Parser {

	public abstract Object parse(String str, int pos[]) throws ParseException;

	public static void skipWhitespaces(@NotNull String string, @NotNull int pos[]) {
//      while(pos[0]<str.length() && Character.isWhitespace(str.charAt(pos[0]))) pos[0]++;
		while (pos[0] < string.length() && isWhitespace(string.charAt(pos[0]))) {
			pos[0]++;
		}
	}

	/*	public static void skipWhitespaces(@NotNull String string, @NotNull MutableInt position) {
		while (position.intValue() < string.length() && isWhitespace(string.charAt(position.intValue()))) {
			position.increment();
		}
	}*/

	static boolean isWhitespace(char c) {
		return c == ' ' || c == '\t' || c == '\n';
	}
}

package jscl.text;

import org.jetbrains.annotations.NotNull;

public class PrimeCharacters implements Parser<Integer> {
    public static final Parser<Integer> parser=new PrimeCharacters();

    private PrimeCharacters() {}

    public Integer parse(@NotNull String string, @NotNull MutableInt position, int depth) throws ParseException {
        int pos0= position.intValue();
        int c;
        ParserUtils.skipWhitespaces(string, position);
        if(position.intValue()< string.length() && string.charAt(position.intValue())=='\'') {
            string.charAt(position.intValue());
			position.increment();
            c=1;
        } else {
            position.setValue(pos0);
            throw new ParseException();
        }
        while(position.intValue()< string.length() && string.charAt(position.intValue())=='\'') {
            string.charAt(position.intValue());
			position.increment();
            c++;
        }
        return c;
    }
}

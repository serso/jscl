package jscl.text;

import jscl.text.MutableInt;
import org.jetbrains.annotations.NotNull;

public class PrimeCharacters implements Parser {
    public static final Parser parser=new PrimeCharacters();

    private PrimeCharacters() {}

    public Object parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
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
        return new Integer(c);
    }
}

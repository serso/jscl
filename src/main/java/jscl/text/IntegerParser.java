package jscl.text;

import org.apache.commons.lang.mutable.MutableInt;
import org.jetbrains.annotations.NotNull;

public class IntegerParser implements Parser {
    public static final Parser parser=new IntegerParser();

    private IntegerParser() {}

    public Object parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
        int pos0= position.intValue();
//      StringBuffer buffer=new StringBuffer();
        int n;
        ParserUtils.skipWhitespaces(string, position);
        if(position.intValue()< string.length() && Character.isDigit(string.charAt(position.intValue()))) {
            char c= string.charAt(position.intValue());
			position.increment();
//          buffer.append(c);
            n=c-'0';
        } else {
            position.setValue(pos0);
            throw new ParseException();
        }
        while(position.intValue()< string.length() && Character.isDigit(string.charAt(position.intValue()))) {
            char c= string.charAt(position.intValue());
			position.increment();
//          buffer.append(c);
            n=10*n+(c-'0');
        }
//      return new Integer(buffer.toString());
        return new Integer(n);
    }
}

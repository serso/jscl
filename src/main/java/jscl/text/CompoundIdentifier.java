package jscl.text;

import jscl.text.MutableInt;
import org.jetbrains.annotations.NotNull;

public class CompoundIdentifier implements Parser {
    public static final Parser parser=new CompoundIdentifier();

    private CompoundIdentifier() {}

    public Object parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
        int pos0= position.intValue();
        StringBuffer buffer=new StringBuffer();
        ParserUtils.skipWhitespaces(string, position);
        try {
            String s=(String)Identifier.parser.parse(string, position);
            buffer.append(s);
        } catch (ParseException e) {
            position.setValue(pos0);
            throw e;
        }
        while(true) {
            try {
                String s=(String)DotAndIdentifier.parser.parse(string, position);
                buffer.append(".").append(s);
            } catch (ParseException e) {
                break;
            }
        }
        return buffer.toString();
    }
}

class DotAndIdentifier implements Parser {
    public static final Parser parser=new DotAndIdentifier();

    private DotAndIdentifier() {}

    public Object parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
        int pos0= position.intValue();
        String s;
        ParserUtils.skipWhitespaces(string, position);
        if(position.intValue()< string.length() && string.charAt(position.intValue())=='.') {
            string.charAt(position.intValue());
			position.increment();
        } else {
            position.setValue(pos0);
            throw new ParseException();
        }
        try {
            s=(String)Identifier.parser.parse(string, position);
        } catch (ParseException e) {
            position.setValue(pos0);
            throw e;
        }
        return s;
    }
}

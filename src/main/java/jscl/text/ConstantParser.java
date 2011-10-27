package jscl.text;

import java.util.ArrayList;
import java.util.List;
import jscl.math.Generic;
import jscl.math.function.Constant;
import jscl.util.ArrayUtils;
import jscl.text.MutableInt;
import org.jetbrains.annotations.NotNull;

public class ConstantParser implements Parser {
    public static final Parser parser=new ConstantParser();

    private ConstantParser() {}

    public Object parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
        String name;
        int prime=0;
        List l=new ArrayList();
        try {
            name=(String)CompoundIdentifier.parser.parse(string, position);
        } catch (ParseException e) {
            throw e;
        }
        while(true) {
            try {
                Generic s=(Generic)Subscript.parser.parse(string, position);
                l.add(s);
            } catch (ParseException e) {
                break;
            }
        }
        try {
            prime=((Integer)Prime.parser.parse(string, position)).intValue();
        } catch (ParseException e) {}
        Generic s[]=(Generic[])ArrayUtils.toArray(l,new Generic[l.size()]);
        Constant v=new Constant(name,prime,s);
        return v;
    }
}

class Prime implements Parser {
    public static final Parser parser=new Prime();

    private Prime() {}

    public Object parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
        int pos0= position.intValue();
        int c;
        try {
            c=((Integer)PrimeCharacters.parser.parse(string, position)).intValue();
        } catch (ParseException e) {
            try {
                c=((Integer)Superscript.parser.parse(string, position)).intValue();
            } catch (ParseException e2) {
                throw e2;
            }
        }
        return new Integer(c);
    }
}

class Superscript implements Parser {
    public static final Parser parser=new Superscript();

    private Superscript() {}

    public Object parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
        int pos0= position.intValue();
        int c;
        ParserUtils.skipWhitespaces(string, position);
        if(position.intValue()< string.length() && string.charAt(position.intValue())=='{') {
            string.charAt(position.intValue());
			position.increment();
        } else {
            position.setValue(pos0);
            throw new ParseException();
        }
        try {
            c=((Integer)IntegerParser.parser.parse(string, position)).intValue();
        } catch (ParseException e) {
            position.setValue(pos0);
            throw e;
        }
        ParserUtils.skipWhitespaces(string, position);
        if(position.intValue()< string.length() && string.charAt(position.intValue())=='}') {
            string.charAt(position.intValue());
			position.increment();
        } else {
            position.setValue(pos0);
            throw new ParseException();
        }
        return new Integer(c);
    }
}

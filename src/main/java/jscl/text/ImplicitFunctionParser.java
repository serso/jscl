package jscl.text;

import java.util.ArrayList;
import java.util.List;
import jscl.math.Generic;
import jscl.math.function.ImplicitFunction;
import jscl.util.ArrayUtils;
import jscl.text.MutableInt;
import org.jetbrains.annotations.NotNull;

public class ImplicitFunctionParser implements Parser {
    public static final Parser parser=new ImplicitFunctionParser();

    private ImplicitFunctionParser() {}

    public Object parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
        int pos0= position.intValue();
        String name;
        Generic a[];
        int b[];
        List l=new ArrayList();
        try {
            name=(String)CompoundIdentifier.parser.parse(string, position);
        } catch (ParseException e) {
            position.setValue(pos0);
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
            b=(int [])Derivation.parser.parse(string, position);
        } catch (ParseException e) {
            b=new int[0];
                }
        try {
            a=(Generic[])ParameterList.parser.parse(string, position);
        } catch (ParseException e) {
            position.setValue(pos0);
            throw e;
        }
        Generic s[]=(Generic[])ArrayUtils.toArray(l,new Generic[l.size()]);
        int derivation[]=new int[a.length];
        for(int i=0;i<a.length && i<b.length;i++) derivation[i]=b[i];
        return new ImplicitFunction(name,a,derivation,s);
    }
}

class Derivation implements Parser {
    public static final Parser parser=new Derivation();

    private Derivation() {}

    public Object parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
        int pos0= position.intValue();
        int c[];
        try {
            c=new int[] {((Integer)PrimeCharacters.parser.parse(string, position)).intValue()};
        } catch (ParseException e) {
            try {
                c=(int [])SuperscriptList.parser.parse(string, position);
            } catch (ParseException e2) {
                throw e2;
            }
        }
        return c;
    }
}

class SuperscriptList implements Parser {
    public static final Parser parser=new SuperscriptList();

    private SuperscriptList() {}

    public Object parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
        int pos0= position.intValue();
        List l=new ArrayList();
        ParserUtils.skipWhitespaces(string, position);
        if(position.intValue()< string.length() && string.charAt(position.intValue())=='{') {
            string.charAt(position.intValue());
			position.increment();
        } else {
            position.setValue(pos0);
            throw new ParseException();
        }
        try {
            Integer in=(Integer)IntegerParser.parser.parse(string, position);
            l.add(in);
        } catch (ParseException e) {
            position.setValue(pos0);
            throw e;
        }
        while(true) {
            try {
                Integer in=(Integer)CommaAndInteger.parser.parse(string, position);
                l.add(in);
            } catch (ParseException e) {
                break;
            }
        }
        ParserUtils.skipWhitespaces(string, position);
        if(position.intValue()< string.length() && string.charAt(position.intValue())=='}') {
            string.charAt(position.intValue());
			position.increment();
        } else {
            position.setValue(pos0);
            throw new ParseException();
        }
        return (int[])ArrayUtils.toArray(l,new int[l.size()]);
    }
}

class CommaAndInteger implements Parser {
    public static final Parser parser=new CommaAndInteger();

    private CommaAndInteger() {}

    public Object parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
        int pos0= position.intValue();
        int c;
        ParserUtils.skipWhitespaces(string, position);
        if(position.intValue()< string.length() && string.charAt(position.intValue())==',') {
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
        return new Integer(c);
    }
}

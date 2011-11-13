package jscl.text;

import jscl.math.Generic;
import jscl.math.function.Function;
import jscl.math.function.ImplicitFunction;
import jscl.util.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ImplicitFunctionParser implements Parser<Function> {
    public static final Parser<Function> parser=new ImplicitFunctionParser();

    private ImplicitFunctionParser() {}

    public Function parse(@NotNull String expression, @NotNull MutableInt position, int depth) throws ParseException {
        int pos0= position.intValue();
        String name;
        Generic a[];
        int b[];
        List l=new ArrayList();
        try {
            name=(String)CompoundIdentifier.parser.parse(expression, position, depth);
        } catch (ParseException e) {
            position.setValue(pos0);
            throw e;
        }
        while(true) {
            try {
                Generic s=(Generic)Subscript.parser.parse(expression, position, depth);
                l.add(s);
            } catch (ParseException e) {
                break;
            }
        }
        try {
            b=(int [])Derivation.parser.parse(expression, position, depth);
        } catch (ParseException e) {
            b=new int[0];
                }
        try {
            a=(Generic[])ParameterList.parser.parse(expression, position, depth);
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

    public Object parse(@NotNull String expression, @NotNull MutableInt position, int depth) throws ParseException {
        int pos0= position.intValue();
        int c[];
        try {
            c=new int[] {((Integer)PrimeCharacters.parser.parse(expression, position, depth)).intValue()};
        } catch (ParseException e) {
            try {
                c=(int [])SuperscriptList.parser.parse(expression, position, depth);
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

    public Object parse(@NotNull String expression, @NotNull MutableInt position, int depth) throws ParseException {
        int pos0= position.intValue();
        List l=new ArrayList();
        ParserUtils.skipWhitespaces(expression, position);
        if(position.intValue()< expression.length() && expression.charAt(position.intValue())=='{') {
            expression.charAt(position.intValue());
			position.increment();
        } else {
            position.setValue(pos0);
            throw new ParseException();
        }
        try {
            Integer in=(Integer)IntegerParser.parser.parse(expression, position, depth);
            l.add(in);
        } catch (ParseException e) {
            position.setValue(pos0);
            throw e;
        }
        while(true) {
            try {
                Integer in=(Integer)CommaAndInteger.parser.parse(expression, position, depth);
                l.add(in);
            } catch (ParseException e) {
                break;
            }
        }
        ParserUtils.skipWhitespaces(expression, position);
        if(position.intValue()< expression.length() && expression.charAt(position.intValue())=='}') {
            expression.charAt(position.intValue());
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

    public Object parse(@NotNull String expression, @NotNull MutableInt position, int depth) throws ParseException {
        int pos0= position.intValue();
        int c;
        ParserUtils.skipWhitespaces(expression, position);
        if(position.intValue()< expression.length() && expression.charAt(position.intValue())==',') {
            expression.charAt(position.intValue());
			position.increment();
        } else {
            position.setValue(pos0);
            throw new ParseException();
        }
        try {
            c=((Integer)IntegerParser.parser.parse(expression, position, depth)).intValue();
        } catch (ParseException e) {
            position.setValue(pos0);
            throw e;
        }
        return new Integer(c);
    }
}

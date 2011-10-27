package jscl.text;

import java.math.BigInteger;

import jscl.math.Generic;
import jscl.math.JSCLInteger;
import jscl.text.MutableInt;
import org.jetbrains.annotations.NotNull;

public class JSCLIntegerParser implements Parser<Generic> {
    public static final Parser<Generic> parser=new JSCLIntegerParser();

    private JSCLIntegerParser() {}

    public Generic parse(@NotNull String string, @NotNull MutableInt position) throws ParseException {
        int pos0= position.intValue();
        StringBuffer buffer=new StringBuffer();
        try {
            String s=(String)Digits.parser.parse(string, position);
            buffer.append(s);
        } catch (ParseException e) {
            throw e;
        }
        return new JSCLInteger(new BigInteger(buffer.toString()));
    }
}

package jscl;

import jscl.text.msg.Messages;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 12/6/11
 * Time: 11:34 AM
 */
public class NumeralBaseException extends AbstractJsclArithmeticException {

    public NumeralBaseException(@NotNull Double value) {
        super(Messages.msg_17, value);
    }
}

package jscl;

import jscl.text.msg.Messages;
import javax.annotation.Nonnull;

/**
 * User: serso
 * Date: 12/6/11
 * Time: 11:34 AM
 */
public class NumeralBaseException extends AbstractJsclArithmeticException {

    public NumeralBaseException(@Nonnull Double value) {
        super(Messages.msg_17, value);
    }
}

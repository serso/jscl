package jscl.math;

import jscl.AbstractJsclArithmeticException;
import jscl.text.msg.Messages;
import org.jetbrains.annotations.NotNull;

public class NotIntegrableException extends AbstractJsclArithmeticException {

    public NotIntegrableException(@NotNull String messageCode, Object... parameters) {
        super(messageCode, parameters);
    }

    public NotIntegrableException(@NotNull Expression e) {
        this(Messages.msg_21, e.toString());
    }

    public NotIntegrableException(@NotNull Variable v) {
        this(Messages.msg_21, v.getName());
    }

    public NotIntegrableException() {
        this(Messages.msg_22);
    }
}

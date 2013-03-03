package jscl;

import javax.annotation.Nonnull;

/**
 * User: serso
 * Date: 12/15/11
 * Time: 10:45 PM
 */
public class NotSupportedException extends AbstractJsclArithmeticException {

    public NotSupportedException(@Nonnull String messageCode, Object... parameters) {
        super(messageCode, parameters);
    }
}
